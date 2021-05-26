package com.lwkandroid.lib.core.utils.common;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.view.Window;

import com.lwkandroid.lib.core.callback.WingsCallBack;
import com.lwkandroid.lib.core.context.AppContext;
import com.lwkandroid.lib.core.log.KLog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.lwkandroid.lib.core.utils.common.FileUtils.createFileByDeleteOldFile;

/**
 * 图片操作相关类
 */

public final class ImageUtils
{
    private ImageUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * bitmap转byteArr
     *
     * @param bitmap bitmap对象
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap)
    {
        return bitmap2Bytes(bitmap, Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * bitmap转byteArr
     *
     * @param bitmap  bitmap对象
     * @param format  格式
     * @param quality 质量 [0-100]
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat format, int quality)
    {
        if (bitmap == null)
        {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, quality, baos);
        return baos.toByteArray();
    }

    /**
     * bitmap转drawable
     *
     * @param bitmap bitmap对象
     * @return drawable
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap)
    {
        return bitmap == null ? null : new BitmapDrawable(AppContext.get().getResources(), bitmap);
    }

    /**
     * drawable转byteArr
     *
     * @param drawable drawable对象
     * @return 字节数组
     */
    public static byte[] drawable2Bytes(Drawable drawable)
    {
        return drawable2Bytes(drawable, Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * drawable转byteArr
     *
     * @param drawable drawable对象
     * @param format   格式
     * @param quality  质量[0-100]
     * @return 字节数组
     */
    public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat format, int quality)
    {
        return drawable == null ? null : bitmap2Bytes(getBitmap(drawable), format, quality);
    }

    /**
     * byteArr转drawable
     *
     * @param bytes 字节数组
     * @return drawable
     */
    public static Drawable bytes2Drawable(byte[] bytes)
    {
        return bitmap2Drawable(getBitmap(bytes));
    }

    /**
     * 根据View创建Bitmap
     *
     * @param window   Window窗口对象
     * @param view     待转换View
     * @param callBack 回调
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createBitmapFromView(final Window window, final View view, final WingsCallBack<Bitmap> callBack)
    {
        if (view == null)
        {
            if (callBack != null)
            {
                callBack.onCallBackSuccess(null);
            }
            return;
        }

        view.post(() -> {
            try
            {
                int width = view.getWidth();
                int height = view.getHeight();
                int[] location = new int[2];
                view.getLocationInWindow(location);
                final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888, true);
                PixelCopy.request(window, new Rect(location[0], location[1], location[0] + width, location[1] + height),
                        bitmap, copyResult -> {
                            if (copyResult == PixelCopy.SUCCESS)
                            {
                                if (callBack != null)
                                {
                                    callBack.onCallBackSuccess(bitmap);
                                }
                            } else
                            {
                                if (callBack != null)
                                {
                                    callBack.onCallBackError(copyResult, new Exception("Fail to create bitmap from view"));
                                }
                            }
                        }, new Handler(Looper.getMainLooper()));
            } catch (Exception e)
            {
                if (callBack != null)
                {
                    callBack.onCallBackError(-1, e);
                }
            }
        });
    }

    /**
     * View to bitmap.
     *
     * @param view The view.
     * @return bitmap
     */
    public static Bitmap createBitmapFromView(final View view)
    {
        if (view == null)
            return null;
        boolean drawingCacheEnabled = view.isDrawingCacheEnabled();
        boolean willNotCacheDrawing = view.willNotCacheDrawing();
        view.setDrawingCacheEnabled(true);
        view.setWillNotCacheDrawing(false);
        Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (null == drawingCache || drawingCache.isRecycled())
        {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            drawingCache = view.getDrawingCache();
            if (null == drawingCache || drawingCache.isRecycled())
            {
                bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);
            } else
            {
                bitmap = Bitmap.createBitmap(drawingCache);
            }
        } else
        {
            bitmap = Bitmap.createBitmap(drawingCache);
        }
        view.setWillNotCacheDrawing(willNotCacheDrawing);
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        return bitmap;
    }

    /**
     * drawable转bitmap
     *
     * @param drawable drawable对象
     * @return bitmap
     */
    public static Bitmap getBitmap(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null)
            {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0)
        {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else
        {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * byteArr转bitmap
     *
     * @param data 字节数组
     * @return bitmap
     */
    public static Bitmap getBitmap(byte[] data)
    {
        return getBitmap(data, 0);
    }

    /**
     * byteArr转bitmap
     *
     * @param data   字节数组
     * @param offset 偏移量
     * @return bitmap
     */
    public static Bitmap getBitmap(final byte[] data, final int offset)
    {
        if (data.length == 0)
        {
            return null;
        }
        return BitmapFactory.decodeByteArray(data, offset, data.length);
    }

    /**
     * byteArr转bitmap
     *
     * @param data      字节数组
     * @param offset    偏移量
     * @param maxWidth  最大读取宽度
     * @param maxHeight 最大读取高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final byte[] data,
                                   final int offset,
                                   final int maxWidth,
                                   final int maxHeight)
    {
        if (data.length == 0)
        {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, offset, data.length, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, offset, data.length, options);
    }


    /**
     * 获取bitmap
     *
     * @param file 文件
     * @return bitmap
     */
    public static Bitmap getBitmap(final File file)
    {
        if (file == null)
        {
            return null;
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    /**
     * 获取bitmap
     *
     * @param file      文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(File file, int maxWidth, int maxHeight)
    {
        if (file == null)
        {
            return null;
        }
        InputStream is = null;
        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            is = new BufferedInputStream(new FileInputStream(file));
            BitmapFactory.decodeStream(is, null, options);
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(is, null, options);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            closeIO(is);
        }
    }

    /**
     * 获取bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    public static Bitmap getBitmap(final String filePath)
    {
        if (StringUtils.isSpace(filePath))
        {
            return null;
        }
        return BitmapFactory.decodeFile(filePath);
    }

    /**
     * 获取bitmap
     *
     * @param filePath  文件路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(String filePath, int maxWidth, int maxHeight)
    {
        if (StringUtils.isSpace(filePath))
        {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取bitmap
     *
     * @param is 输入流
     * @return bitmap
     */
    public static Bitmap getBitmap(final InputStream is)
    {
        if (is == null)
        {
            return null;
        }
        return BitmapFactory.decodeStream(is);
    }

    /**
     * 获取bitmap
     *
     * @param is        输入流
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(InputStream is, int maxWidth, int maxHeight) throws IOException
    {
        if (is == null)
        {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     * 获取bitmap
     *
     * @param resId 资源id
     * @return bitmap
     */
    public static Bitmap getBitmap(@DrawableRes final int resId)
    {
        Drawable drawable = ContextCompat.getDrawable(AppContext.get(), resId);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 获取bitmap
     *
     * @param resId     资源id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(@DrawableRes int resId, int maxWidth, int maxHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(AppContext.get().getResources(), resId, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(AppContext.get().getResources(), resId, options);
    }

    /**
     * 获取bitmap
     *
     * @param fd The file descriptor.
     * @return bitmap
     */
    public static Bitmap getBitmap(final FileDescriptor fd)
    {
        if (fd == null)
        {
            return null;
        }
        return BitmapFactory.decodeFileDescriptor(fd);
    }

    /**
     * 获取bitmap
     *
     * @param fd        The file descriptor
     * @param maxWidth  The maximum width.
     * @param maxHeight The maximum height.
     * @return bitmap
     */
    public static Bitmap getBitmap(final FileDescriptor fd,
                                   final int maxWidth,
                                   final int maxHeight)
    {
        if (fd == null)
        {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 获取图片旋转角度
     *
     * @param filePath 文件路径
     * @return 旋转角度
     */
    public static int getRotateDegree(String filePath)
    {
        int degree = 0;
        try
        {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation)
            {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 获取图片原始宽高
     *
     * @param filePath 图片地址
     * @return 宽高组成的int数组
     */
    public static int[] getImageSize(String filePath)
    {
        int[] res = new int[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(filePath, options);

        res[0] = options.outWidth;
        res[1] = options.outHeight;

        return res;
    }

    /**
     * 保存bitmap.
     *
     * @param src      Bitmap数据源
     * @param filePath File绝对路径.
     * @param format   Bitmap.CompressFormat 对象.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format)
    {
        return save(src, filePath, format, 100, false);
    }

    /**
     * 保存bitmap.
     *
     * @param src    Bitmap数据源
     * @param file   File对象.
     * @param format Bitmap.CompressFormat 对象.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src, final File file, final Bitmap.CompressFormat format)
    {
        return save(src, file, format, 100, false);
    }

    /**
     * 保存bitmap.
     *
     * @param src      Bitmap数据源
     * @param filePath File绝对路径
     * @param format   Bitmap.CompressFormat 对象.
     * @param recycle  true:回收Bitmap，false不回收
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format,
                               final boolean recycle)
    {
        return save(src, filePath, format, 100, recycle);
    }

    /**
     * 保存bitmap.
     *
     * @param src     Bitmap数据源
     * @param file    File对象.
     * @param format  Bitmap.CompressFormat 对象.
     * @param recycle true:回收Bitmap，false不回收
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final File file,
                               final Bitmap.CompressFormat format,
                               final boolean recycle)
    {
        return save(src, file, format, 100, recycle);
    }

    /**
     * 保存bitmap.
     *
     * @param src      Bitmap数据源
     * @param filePath File绝对路径
     * @param format   Bitmap.CompressFormat 对象.
     * @param quality  图片质量[0-100]. 0 meaning compress for
     *                 small size, 100 meaning compress for max quality. Some
     *                 formats, like PNG which is lossless, will ignore the
     *                 quality setting
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format,
                               final int quality)
    {
        return save(src, new File(filePath), format, quality, false);
    }

    /**
     * 保存bitmap.
     *
     * @param src    Bitmap数据源.
     * @param file   File对象
     * @param format Bitmap.CompressFormat 对象.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final File file,
                               final Bitmap.CompressFormat format,
                               final int quality)
    {
        return save(src, file, format, quality, false);
    }

    /**
     * 保存bitmap.
     *
     * @param src      Bitmap数据源.
     * @param filePath File绝对路径
     * @param format   Bitmap.CompressFormat 对象.
     * @param quality  图片质量[0-100]. 0 meaning compress for
     *                 small size, 100 meaning compress for max quality. Some
     *                 formats, like PNG which is lossless, will ignore the
     *                 quality setting
     * @param recycle  true:回收Bitmap，false不回收
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format,
                               final int quality,
                               final boolean recycle)
    {
        return save(src, new File(filePath), format, quality, recycle);
    }

    /**
     * 保存bitmap.
     *
     * @param src     Bitmap数据源.
     * @param file    File对象
     * @param format  Bitmap.CompressFormat 对象.
     * @param quality 图片质量[0-100]. 0 meaning compress for
     *                small size, 100 meaning compress for max quality. Some
     *                formats, like PNG which is lossless, will ignore the
     *                quality setting
     * @param recycle true:回收Bitmap，false不回收
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final File file,
                               final Bitmap.CompressFormat format,
                               final int quality,
                               final boolean recycle)
    {
        if (isEmptyBitmap(src))
        {
            KLog.e("Can not save a empty bitmap.");
            return false;
        }
        if (src.isRecycled())
        {
            KLog.e("Can not save a recycled bitmap.");
            return false;
        }
        if (!createFileByDeleteOldFile(file))
        {
            KLog.e("Can not save bitmap because create or delete file <" + file + "> failed.");
            return false;
        }
        OutputStream os = null;
        boolean ret = false;
        try
        {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, quality, os);
            if (recycle && !src.isRecycled())
            {
                src.recycle();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {

            try
            {
                if (os != null)
                {
                    os.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * @param src    The source of bitmap.
     * @param format The format of the image.
     * @return the file if save success, otherwise return null.
     */
    @Nullable
    public static File save2Album(final Bitmap src,
                                  final Bitmap.CompressFormat format)
    {
        return save2Album(src, "", format, 100, false);
    }

    /**
     * @param src     The source of bitmap.
     * @param format  The format of the image.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the file if save success, otherwise return null.
     */
    @Nullable
    public static File save2Album(final Bitmap src,
                                  final Bitmap.CompressFormat format,
                                  final boolean recycle)
    {
        return save2Album(src, "", format, 100, recycle);
    }

    /**
     * @param src     The source of bitmap.
     * @param format  The format of the image.
     * @param quality Hint to the compressor, 0-100. 0 meaning compress for
     *                small size, 100 meaning compress for max quality. Some
     *                formats, like PNG which is lossless, will ignore the
     *                quality setting
     * @return the file if save success, otherwise return null.
     */
    @Nullable
    public static File save2Album(final Bitmap src,
                                  final Bitmap.CompressFormat format,
                                  final int quality)
    {
        return save2Album(src, "", format, quality, false);
    }

    /**
     * @param src     The source of bitmap.
     * @param format  The format of the image.
     * @param quality Hint to the compressor, 0-100. 0 meaning compress for
     *                small size, 100 meaning compress for max quality. Some
     *                formats, like PNG which is lossless, will ignore the
     *                quality setting
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the file if save success, otherwise return null.
     */
    @Nullable
    public static File save2Album(final Bitmap src,
                                  final Bitmap.CompressFormat format,
                                  final int quality,
                                  final boolean recycle)
    {
        return save2Album(src, "", format, quality, recycle);
    }

    /**
     * @param src     The source of bitmap.
     * @param dirName The name of directory.
     * @param format  The format of the image.
     * @return the file if save success, otherwise return null.
     */
    @Nullable
    public static File save2Album(final Bitmap src,
                                  final String dirName,
                                  final Bitmap.CompressFormat format)
    {
        return save2Album(src, dirName, format, 100, false);
    }

    /**
     * @param src     The source of bitmap.
     * @param dirName The name of directory.
     * @param format  The format of the image.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the file if save success, otherwise return null.
     */
    @Nullable
    public static File save2Album(final Bitmap src,
                                  final String dirName,
                                  final Bitmap.CompressFormat format,
                                  final boolean recycle)
    {
        return save2Album(src, dirName, format, 100, recycle);
    }

    /**
     * @param src     The source of bitmap.
     * @param dirName The name of directory.
     * @param format  The format of the image.
     * @param quality Hint to the compressor, 0-100. 0 meaning compress for
     *                small size, 100 meaning compress for max quality. Some
     *                formats, like PNG which is lossless, will ignore the
     *                quality setting
     * @return the file if save success, otherwise return null.
     */
    @Nullable
    public static File save2Album(final Bitmap src,
                                  final String dirName,
                                  final Bitmap.CompressFormat format,
                                  final int quality)
    {
        return save2Album(src, dirName, format, quality, false);
    }

    /**
     * @param src     The source of bitmap.
     * @param dirName The name of directory.
     * @param format  The format of the image.
     * @param quality Hint to the compressor, 0-100. 0 meaning compress for
     *                small size, 100 meaning compress for max quality. Some
     *                formats, like PNG which is lossless, will ignore the
     *                quality setting
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the file if save success, otherwise return null.
     */
    @Nullable
    public static File save2Album(final Bitmap src,
                                  final String dirName,
                                  final Bitmap.CompressFormat format,
                                  final int quality,
                                  final boolean recycle)
    {
        String safeDirName = TextUtils.isEmpty(dirName) ? AppUtils.getPackageName() : dirName;
        String suffix = Bitmap.CompressFormat.JPEG.equals(format) ? "JPG" : format.name();
        String fileName = System.currentTimeMillis() + "_" + quality + "." + suffix;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
        {

            if (ActivityCompat.checkSelfPermission(AppContext.get(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                Log.e("ImageUtils", "save to album need storage permission");
                return null;
            }
            File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File destFile = new File(picDir, safeDirName + "/" + fileName);
            if (!save(src, destFile, format, quality, recycle))
            {
                return null;
            }
            FileUtils.notifySystemToScan(destFile);
            return destFile;
        } else
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
            Uri contentUri;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else
            {
                contentUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
            }
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/" + safeDirName);
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1);
            Uri uri = AppContext.get().getContentResolver().insert(contentUri, contentValues);
            if (uri == null)
            {
                return null;
            }
            OutputStream os = null;
            try
            {
                os = AppContext.get().getContentResolver().openOutputStream(uri);
                src.compress(format, quality, os);

                contentValues.clear();
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0);
                AppContext.get().getContentResolver().update(uri, contentValues, null, null);

                return UriUtils.uri2File(uri);
            } catch (Exception e)
            {
                AppContext.get().getContentResolver().delete(uri, null, null);
                e.printStackTrace();
                return null;
            } finally
            {
                try
                {
                    if (os != null)
                    {
                        os.close();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 使用缩放压缩图片.
     *
     * @param src       Bitmap数据源.
     * @param newWidth  最大宽度.
     * @param newHeight 最大高度.
     * @return 压缩后的bitmap
     */
    public static Bitmap compressByScale(final Bitmap src,
                                         final int newWidth,
                                         final int newHeight)
    {
        return scale(src, newWidth, newHeight, false);
    }

    /**
     * 使用缩放压缩图片.
     *
     * @param src       Bitmap数据源.
     * @param newWidth  最大宽度.
     * @param newHeight 最大高度.
     * @param recycle   true:回收Bitmap，false不回收
     * @return 压缩后的bitmap
     */
    public static Bitmap compressByScale(final Bitmap src,
                                         final int newWidth,
                                         final int newHeight,
                                         final boolean recycle)
    {
        return scale(src, newWidth, newHeight, recycle);
    }

    /**
     * 使用缩放压缩图片.
     *
     * @param src         Bitmap数据源.
     * @param scaleWidth  最大宽度.
     * @param scaleHeight 最大高度.
     * @return 压缩后的bitmap
     */
    public static Bitmap compressByScale(final Bitmap src,
                                         final float scaleWidth,
                                         final float scaleHeight)
    {
        return scale(src, scaleWidth, scaleHeight, false);
    }

    /**
     * 使用缩放压缩图片
     *
     * @param src         Bitmap数据源.
     * @param scaleWidth  最大宽度.
     * @param scaleHeight 最大高度.
     * @param recycle     true:回收Bitmap，false不回收
     * @return 压缩后的bitmap
     */
    public static Bitmap compressByScale(final Bitmap src,
                                         final float scaleWidth,
                                         final float scaleHeight,
                                         final boolean recycle)
    {
        return scale(src, scaleWidth, scaleHeight, recycle);
    }

    /**
     * 使用质量压缩图片
     *
     * @param src     Bitmap数据源.
     * @param quality 压缩后的质量
     * @return 压缩后的bitmap
     */
    public static byte[] compressByQuality(final Bitmap src,
                                           @IntRange(from = 0, to = 100) final int quality)
    {
        return compressByQuality(src, quality, false);
    }

    /**
     * 使用质量压缩图片
     *
     * @param src     Bitmap数据源.
     * @param quality 压缩后的质量
     * @param recycle true:回收Bitmap，false不回收
     * @return 压缩后的bitmap
     */
    public static byte[] compressByQuality(final Bitmap src,
                                           @IntRange(from = 0, to = 100) final int quality,
                                           final boolean recycle)
    {
        if (isEmptyBitmap(src))
        {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled())
        {
            src.recycle();
        }
        return bytes;
    }

    /**
     * 使用质量压缩图片
     *
     * @param src         Bitmap数据源.
     * @param maxByteSize 压缩后最大字节大小
     * @return 压缩后的bitmap
     */
    public static byte[] compressByQuality(final Bitmap src, final long maxByteSize)
    {
        return compressByQuality(src, maxByteSize, false);
    }

    /**
     * 使用质量压缩图片
     *
     * @param src         Bitmap数据源.
     * @param maxByteSize 压缩后最大字节大小
     * @param recycle     true:回收Bitmap，false不回收
     * @return 压缩后的bitmap
     */
    public static byte[] compressByQuality(final Bitmap src,
                                           final long maxByteSize,
                                           final boolean recycle)
    {
        if (isEmptyBitmap(src) || maxByteSize <= 0)
        {
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes;
        if (baos.size() <= maxByteSize)
        {
            bytes = baos.toByteArray();
        } else
        {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, 0, baos);
            if (baos.size() >= maxByteSize)
            {
                bytes = baos.toByteArray();
            } else
            {
                // find the best quality using binary search
                int st = 0;
                int end = 100;
                int mid = 0;
                while (st < end)
                {
                    mid = (st + end) / 2;
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, mid, baos);
                    int len = baos.size();
                    if (len == maxByteSize)
                    {
                        break;
                    } else if (len > maxByteSize)
                    {
                        end = mid - 1;
                    } else
                    {
                        st = mid + 1;
                    }
                }
                if (end == mid - 1)
                {
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, st, baos);
                }
                bytes = baos.toByteArray();
            }
        }
        if (recycle && !src.isRecycled())
        {
            src.recycle();
        }
        return bytes;
    }

    /**
     * 使用SampleSize压缩图片
     *
     * @param src        Bitmap数据源.
     * @param sampleSize SampleSize大小
     * @return 压缩后的bitmap
     */

    public static Bitmap compressBySampleSize(final Bitmap src, final int sampleSize)
    {
        return compressBySampleSize(src, sampleSize, false);
    }

    /**
     * 使用SampleSize压缩图片
     *
     * @param src        Bitmap数据源.
     * @param sampleSize SampleSize大小
     * @param recycle    true:回收Bitmap，false不回收
     * @return 压缩后的bitmap
     */
    public static Bitmap compressBySampleSize(final Bitmap src,
                                              final int sampleSize,
                                              final boolean recycle)
    {
        if (isEmptyBitmap(src))
        {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled())
        {
            src.recycle();
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 使用SampleSize压缩图片
     *
     * @param src       Bitmap数据源.
     * @param maxWidth  最大宽度.
     * @param maxHeight 最大高度.
     * @return 压缩后的bitmap
     */
    public static Bitmap compressBySampleSize(final Bitmap src,
                                              final int maxWidth,
                                              final int maxHeight)
    {
        return compressBySampleSize(src, maxWidth, maxHeight, false);
    }

    /**
     * 使用SampleSize压缩图片
     *
     * @param src       Bitmap数据源.
     * @param maxWidth  最大宽度.
     * @param maxHeight 最大高度.
     * @param recycle   true:回收Bitmap，false不回收
     * @return 压缩后的bitmap
     */
    public static Bitmap compressBySampleSize(final Bitmap src,
                                              final int maxWidth,
                                              final int maxHeight,
                                              final boolean recycle)
    {
        if (isEmptyBitmap(src))
        {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        if (recycle && !src.isRecycled())
        {
            src.recycle();
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    private static boolean isEmptyBitmap(final Bitmap src)
    {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    /**
     * 计算图片采样大小
     *
     * @param options   Bitmap配置
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样值
     */
    public static int calculateInSampleSize(final BitmapFactory.Options options,
                                            final int maxWidth,
                                            final int maxHeight)
    {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while (height > maxHeight || width > maxWidth)
        {
            height >>= 1;
            width >>= 1;
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

    /**
     * 缩放图片.
     *
     * @param src       Bitmap数据源.
     * @param newWidth  新图宽度.
     * @param newHeight 新图高度.
     * @return 压缩后的bitmap
     */
    public static Bitmap scale(final Bitmap src, final int newWidth, final int newHeight)
    {
        return scale(src, newWidth, newHeight, false);
    }

    /**
     * 缩放图片.
     *
     * @param src       Bitmap数据源.
     * @param newWidth  新图宽度.
     * @param newHeight 新图高度.
     * @param recycle   true:回收Bitmap，false不回收
     * @return 压缩后的bitmap
     */
    public static Bitmap scale(final Bitmap src,
                               final int newWidth,
                               final int newHeight,
                               final boolean recycle)
    {
        if (isEmptyBitmap(src))
        {
            return null;
        }
        Bitmap ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true);
        if (recycle && !src.isRecycled() && ret != src)
        {
            src.recycle();
        }
        return ret;
    }

    /**
     * 缩放图片
     *
     * @param src         Bitmap数据源.
     * @param scaleWidth  缩放宽度.
     * @param scaleHeight 缩放高度.
     * @return 压缩后的bitmap
     */
    public static Bitmap scale(final Bitmap src, final float scaleWidth, final float scaleHeight)
    {
        return scale(src, scaleWidth, scaleHeight, false);
    }

    /**
     * 缩放图片
     *
     * @param src         Bitmap数据源.
     * @param scaleWidth  缩放宽度.
     * @param scaleHeight 缩放高度.
     * @param recycle     true:回收Bitmap，false不回收
     * @return 压缩后的bitmap
     */
    public static Bitmap scale(final Bitmap src,
                               final float scaleWidth,
                               final float scaleHeight,
                               final boolean recycle)
    {
        if (isEmptyBitmap(src))
        {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled() && ret != src)
        {
            src.recycle();
        }
        return ret;
    }

    /**
     * 裁剪图片
     *
     * @param src    Bitmap数据源.
     * @param x      The x coordinate of the first pixel.
     * @param y      The y coordinate of the first pixel.
     * @param width  The width.
     * @param height The height.
     * @return the clipped bitmap
     */
    public static Bitmap clip(final Bitmap src,
                              final int x,
                              final int y,
                              final int width,
                              final int height)
    {
        return clip(src, x, y, width, height, false);
    }

    /**
     * 裁剪图片
     *
     * @param src     The source of bitmap.
     * @param x       The x coordinate of the first pixel.
     * @param y       The y coordinate of the first pixel.
     * @param width   The width.
     * @param height  The height.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the clipped bitmap
     */
    public static Bitmap clip(final Bitmap src,
                              final int x,
                              final int y,
                              final int width,
                              final int height,
                              final boolean recycle)
    {
        if (isEmptyBitmap(src))
        {
            return null;
        }
        Bitmap ret = Bitmap.createBitmap(src, x, y, width, height);
        if (recycle && !src.isRecycled() && ret != src)
        {
            src.recycle();
        }
        return ret;
    }

    /**
     * Skew变换图片.
     *
     * @param src The source of bitmap.
     * @param kx  The skew factor of x.
     * @param ky  The skew factor of y.
     * @return the skewed bitmap
     */
    public static Bitmap skew(final Bitmap src, final float kx, final float ky)
    {
        return skew(src, kx, ky, 0, 0, false);
    }

    /**
     * Skew变换图片.
     *
     * @param src     The source of bitmap.
     * @param kx      The skew factor of x.
     * @param ky      The skew factor of y.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the skewed bitmap
     */
    public static Bitmap skew(final Bitmap src,
                              final float kx,
                              final float ky,
                              final boolean recycle)
    {
        return skew(src, kx, ky, 0, 0, recycle);
    }

    /**
     * Skew变换图片.
     *
     * @param src The source of bitmap.
     * @param kx  The skew factor of x.
     * @param ky  The skew factor of y.
     * @param px  The x coordinate of the pivot point.
     * @param py  The y coordinate of the pivot point.
     * @return the skewed bitmap
     */
    public static Bitmap skew(final Bitmap src,
                              final float kx,
                              final float ky,
                              final float px,
                              final float py)
    {
        return skew(src, kx, ky, px, py, false);
    }

    /**
     * Skew变换图片.
     *
     * @param src     The source of bitmap.
     * @param kx      The skew factor of x.
     * @param ky      The skew factor of y.
     * @param px      The x coordinate of the pivot point.
     * @param py      The y coordinate of the pivot point.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the skewed bitmap
     */
    public static Bitmap skew(final Bitmap src,
                              final float kx,
                              final float ky,
                              final float px,
                              final float py,
                              final boolean recycle)
    {
        if (isEmptyBitmap(src))
        {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setSkew(kx, ky, px, py);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled() && ret != src)
        {
            src.recycle();
        }
        return ret;
    }

    /**
     * 旋转图片
     *
     * @param src     The source of bitmap.
     * @param degrees The number of degrees.
     * @param px      The x coordinate of the pivot point.
     * @param py      The y coordinate of the pivot point.
     * @return the rotated bitmap
     */
    public static Bitmap rotate(final Bitmap src,
                                final int degrees,
                                final float px,
                                final float py)
    {
        return rotate(src, degrees, px, py, false);
    }

    /**
     * 旋转图片
     *
     * @param src     The source of bitmap.
     * @param degrees The number of degrees.
     * @param px      The x coordinate of the pivot point.
     * @param py      The y coordinate of the pivot point.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the rotated bitmap
     */
    public static Bitmap rotate(final Bitmap src,
                                final int degrees,
                                final float px,
                                final float py,
                                final boolean recycle)
    {
        if (isEmptyBitmap(src))
        {
            return null;
        }
        if (degrees == 0)
        {
            return src;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, px, py);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled() && ret != src)
        {
            src.recycle();
        }
        return ret;
    }

    /**
     * 关闭IO
     *
     * @param closeables closeables
     */
    private static void closeIO(Closeable... closeables)
    {
        if (closeables == null)
        {
            return;
        }
        for (Closeable closeable : closeables)
        {
            if (closeable != null)
            {
                try
                {
                    closeable.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
