package com.lwkandroid.lib.core.utils.compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.lwkandroid.lib.core.utils.common.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;


/**
 * @description: Luban算法实现的压缩功能
 * @author: LWK
 * @date: 2020/6/11 16:36
 */
public final class LubanCompressEngine
{
    private LubanCompressOptions mOptions;

    LubanCompressEngine(LubanCompressOptions options)
    {
        this.mOptions = options;
    }

    public List<File> compress() throws IOException
    {
        checkOptionNonNull();
        List<File> fileList = mOptions.getFileList();
        List<File> resultList = new LinkedList<>();
        for (File file : fileList)
        {
            resultList.add(compressStep1(file));
        }
        return resultList;
    }

    public Observable<List<File>> compressByRxJava()
    {
        checkOptionNonNull();
        List<File> fileList = mOptions.getFileList();
        return Observable.fromIterable(fileList)
                .map(this::compressStep1)
                .collect((Supplier<List<File>>) LinkedList::new, List::add)
                .flatMapObservable((Function<List<File>, ObservableSource<List<File>>>) Observable::just);
    }

    private File compressStep1(File file) throws IOException
    {
        if (file.length() <= mOptions.getIgnoreBytesSize())
        {
            return file;
        }

        String filePath = file.getAbsolutePath();
        String targetPath = Utils.createOutputFilePath(filePath, mOptions.getCacheFolderPath(),
                mOptions.getRenameImpl(), mOptions.getCompressFormat());

        double targetSize;
        int[] imgSize = ImageUtils.getImageSize(filePath);
        int width = imgSize[0];
        int height = imgSize[1];
        int degree = ImageUtils.getRotateDegree(filePath);

        boolean flip = width > height;
        int thumbW = width % 2 == 1 ? width + 1 : width;
        int thumbH = height % 2 == 1 ? height + 1 : height;

        width = Math.min(thumbW, thumbH);
        height = Math.max(thumbW, thumbH);

        double scale = ((double) width / height);

        if (scale <= 1 && scale > 0.5625)
        {
            if (height < 1664)
            {
                if (file.length() / 1024 < 150)
                {
                    return file;
                }

                targetSize = (width * height) / Math.pow(1664, 2) * 150;
                targetSize = Math.max(60, targetSize);
            } else if (height < 4990)
            {
                thumbW = width / 2;
                thumbH = height / 2;
                targetSize = (thumbW * thumbH) / Math.pow(2495, 2) * 300;
                targetSize = Math.max(60, targetSize);
            } else if (height < 10240)
            {
                thumbW = width / 4;
                thumbH = height / 4;
                targetSize = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
                targetSize = Math.max(100, targetSize);
            } else
            {
                int multiple = height / 1280;
                thumbW = width / multiple;
                thumbH = height / multiple;
                targetSize = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
                targetSize = Math.max(100, targetSize);
            }
        } else if (scale <= 0.5625 && scale > 0.5)
        {
            if (height < 1280 && file.length() / 1024 < 200)
            {
                return file;
            }

            int multiple = height / 1280 == 0 ? 1 : height / 1280;
            thumbW = width / multiple;
            thumbH = height / multiple;
            targetSize = (thumbW * thumbH) / (1440.0 * 2560.0) * 400;
            targetSize = Math.max(100, targetSize);
        } else
        {
            int multiple = (int) Math.ceil(height / (1280.0 / scale));
            thumbW = width / multiple;
            thumbH = height / multiple;
            targetSize = ((thumbW * thumbH) / (1280.0 * (1280 / scale))) * 500;
            targetSize = Math.max(100, targetSize);
        }

        return compressStep2(filePath, targetPath,
                flip ? thumbH : thumbW,
                flip ? thumbW : thumbH,
                degree, targetSize);
    }

    private File compressStep2(String filePath, String targetPath,
                               int targetWidth, int targetHeight,
                               int degree, double targetSize) throws IOException
    {
        Bitmap thumbBitmap = compressStep3(filePath, targetWidth, targetHeight);
        thumbBitmap = rotatingImage(thumbBitmap, degree);
        return saveImage(targetPath, thumbBitmap, targetSize);
    }

    private Bitmap compressStep3(String filePath, int targetWidth, int targetHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        int outH = options.outHeight;
        int outW = options.outWidth;
        int inSampleSize = 1;

        while (outH / inSampleSize > targetHeight || outW / inSampleSize > targetWidth)
        {
            inSampleSize *= 2;
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    private File saveImage(String filePath, Bitmap bitmap, double targetSize) throws IOException
    {
        ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());

        int quality = 100;
        bitmap.compress(mOptions.getCompressFormat(), quality, mByteArrayOutputStream);

        while (mByteArrayOutputStream.size() / 1024 > targetSize && quality > 6)
        {
            mByteArrayOutputStream.reset();
            quality -= 6;
            bitmap.compress(mOptions.getCompressFormat(), quality, mByteArrayOutputStream);
        }
        bitmap.recycle();

        FileOutputStream fos = new FileOutputStream(filePath);
        mByteArrayOutputStream.writeTo(fos);
        fos.close();

        return new File(filePath);
    }

    private Bitmap rotatingImage(Bitmap bitmap, int degree)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void checkOptionNonNull()
    {
        if (mOptions == null)
        {
            throw new IllegalArgumentException("CompressOption can not be null.");
        }
    }
}
