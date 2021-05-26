package com.lwkandroid.lib.core.utils.common;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.lwkandroid.lib.core.context.AppContext;
import com.lwkandroid.lib.core.log.KLog;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ArrayRes;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

/**
 * Created by LWK
 * 资源工具类
 */

public final class ResourceUtils
{
    private static final int BUFFER_SIZE = 8192;

    public static boolean getBoolean(@BoolRes int resId)
    {
        return AppContext.get().getResources().getBoolean(resId);
    }

    public static String getString(@StringRes int resId)
    {
        return AppContext.get().getResources().getString(resId);
    }

    public static String getString(@StringRes int resId, Object... formatArgs)
    {
        return AppContext.get().getResources().getString(resId, formatArgs);
    }

    public static String[] getStringArray(@ArrayRes int resId)
    {
        return AppContext.get().getResources().getStringArray(resId);
    }

    public static int getColor(@ColorRes int resId)
    {
        return ContextCompat.getColor(AppContext.get(), resId);
    }

    public static ColorStateList getColorStateList(@ColorRes int resId)
    {
        return ContextCompat.getColorStateList(AppContext.get(), resId);
    }

    public static Drawable getDrawable(@DrawableRes int resId)
    {
        return ContextCompat.getDrawable(AppContext.get(), resId);
    }

    /**
     * 获取某个dimen的值,如果是dp或sp的单位,将其乘以density,如果是px,则不乘
     * 返回float
     */
    public static float getDimen(@DimenRes int resId)
    {
        return AppContext.get().getResources().getDimension(resId);
    }

    /**
     * 获取某个dimen的值,如果是dp或sp的单位,将其乘以density,如果是px,则不乘
     * 返回int
     */
    public static int getDimenPixelOffset(@DimenRes int resId)
    {
        return AppContext.get().getResources().getDimensionPixelOffset(resId);
    }

    /**
     * 则不管写的是dp还是sp还是px,都会乘以denstiy.
     */
    public static int getDimenPixelSize(@DimenRes int resId)
    {
        return AppContext.get().getResources().getDimensionPixelSize(resId);
    }

    public static int getInteger(@IntegerRes int resId)
    {
        return AppContext.get().getResources().getInteger(resId);
    }

    public static int[] getIntegerArray(@ArrayRes int resId)
    {
        return AppContext.get().getResources().getIntArray(resId);
    }

    public static Typeface getTypeface(@FontRes int resId)
    {
        return ResourcesCompat.getFont(AppContext.get(), resId);
    }

    public static float getAttrFloatValue(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        AppContext.get().getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.getFloat();
    }

    public static int getAttrColor(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        AppContext.get().getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.data;
    }

    public static ColorStateList getAttrColorStateList(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        AppContext.get().getTheme().resolveAttribute(attrRes, typedValue, true);
        return ContextCompat.getColorStateList(AppContext.get(), typedValue.resourceId);
    }

    public static Drawable getAttrDrawable(int attrRes)
    {
        int[] attrs = new int[]{attrRes};
        TypedArray ta = AppContext.get().obtainStyledAttributes(attrs);
        Drawable drawable = getAttrDrawable(ta, 0);
        ta.recycle();
        return drawable;
    }

    public static Drawable getAttrDrawable(TypedArray typedArray, int index)
    {
        TypedValue value = typedArray.peekValue(index);
        if (value != null && value.type != TypedValue.TYPE_ATTRIBUTE && value.resourceId != 0)
        {
            return AppCompatResources.getDrawable(AppContext.get(), value.resourceId);
        }
        return null;
    }

    public static int getAttrDimen(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        AppContext.get().getTheme().resolveAttribute(attrRes, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, AppContext.get().getResources().getDisplayMetrics());
    }

    /**
     * 获取Raw文件夹下资源
     *
     * @param resId R.raw.xxx形式的资源id
     * @return 文件输入流
     */
    public static InputStream getRaw(int resId)
    {
        return AppContext.get().getResources().openRawResource(resId);
    }

    public static InputStream getAsset(String fileName)
    {
        try
        {
            return AppContext.get().getAssets().open(fileName);
        } catch (IOException e)
        {
            KLog.e("Fail to open assets file:" + e.toString());
        }
        return null;
    }

    /**
     * Copy the file from assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @param destFilePath   The path of destination file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean copyFileFromAssets(final String assetsFilePath, final String destFilePath)
    {
        boolean res = true;
        try
        {
            String[] assets = AppContext.get().getAssets().list(assetsFilePath);
            if (assets != null && assets.length > 0)
            {
                for (String asset : assets)
                {
                    res &= copyFileFromAssets(assetsFilePath + "/" + asset, destFilePath + "/" + asset);
                }
            } else
            {
                res = writeFileFromIS(
                        destFilePath,
                        AppContext.get().getAssets().open(assetsFilePath),
                        false
                );
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    /**
     * Return the content of assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @return the content of assets
     */
    public static String readAssets2String(final String assetsFilePath)
    {
        return readAssets2String(assetsFilePath, null);
    }

    /**
     * Return the content of assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @param charsetName    The name of charset.
     * @return the content of assets
     */
    public static String readAssets2String(final String assetsFilePath, final String charsetName)
    {
        InputStream is;
        try
        {
            is = AppContext.get().getAssets().open(assetsFilePath);
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        byte[] bytes = is2Bytes(is);
        if (bytes == null)
        {
            return null;
        }
        if (StringUtils.isSpace(charsetName))
        {
            return new String(bytes);
        } else
        {
            try
            {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * Return the content of file in assets.
     *
     * @param assetsPath The path of file in assets.
     * @return the content of file in assets
     */
    public static List<String> readAssets2List(final String assetsPath)
    {
        return readAssets2List(assetsPath, null);
    }

    /**
     * Return the content of file in assets.
     *
     * @param assetsPath  The path of file in assets.
     * @param charsetName The name of charset.
     * @return the content of file in assets
     */
    public static List<String> readAssets2List(final String assetsPath,
                                               final String charsetName)
    {
        try
        {
            return is2List(AppContext.get().getResources().getAssets().open(assetsPath), charsetName);
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Copy the file from raw.
     *
     * @param resId        The resource id.
     * @param destFilePath The path of destination file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean copyFileFromRaw(@RawRes final int resId, final String destFilePath)
    {
        return writeFileFromIS(
                destFilePath,
                AppContext.get().getResources().openRawResource(resId),
                false
        );
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId The resource id.
     * @return the content of resource in raw
     */
    public static String readRaw2String(@RawRes final int resId)
    {
        return readRaw2String(resId, null);
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId       The resource id.
     * @param charsetName The name of charset.
     * @return the content of resource in raw
     */
    public static String readRaw2String(@RawRes final int resId, final String charsetName)
    {
        InputStream is = AppContext.get().getResources().openRawResource(resId);
        byte[] bytes = is2Bytes(is);
        if (bytes == null)
        {
            return null;
        }
        if (isSpace(charsetName))
        {
            return new String(bytes);
        } else
        {
            try
            {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId The resource id.
     * @return the content of file in assets
     */
    public static List<String> readRaw2List(@RawRes final int resId)
    {
        return readRaw2List(resId, null);
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId       The resource id.
     * @param charsetName The name of charset.
     * @return the content of file in assets
     */
    public static List<String> readRaw2List(@RawRes final int resId,
                                            final String charsetName)
    {
        return is2List(AppContext.get().getResources().openRawResource(resId), charsetName);
    }

    ///////////////////////////////////////////////////////////////////////////
    // other utils methods
    ///////////////////////////////////////////////////////////////////////////

    private static boolean writeFileFromIS(final String filePath,
                                           final InputStream is,
                                           final boolean append)
    {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    private static boolean writeFileFromIS(final File file,
                                           final InputStream is,
                                           final boolean append)
    {
        if (!createOrExistsFile(file) || is == null)
        {
            return false;
        }
        OutputStream os = null;
        try
        {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte[] data = new byte[BUFFER_SIZE];
            int len;
            while ((len = is.read(data, 0, BUFFER_SIZE)) != -1)
            {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        } finally
        {
            try
            {
                is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
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

    private static File getFileByPath(final String filePath)
    {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean createOrExistsFile(final File file)
    {
        if (file == null)
        {
            return false;
        }
        if (file.exists())
        {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile()))
        {
            return false;
        }
        try
        {
            return file.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file)
    {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s)
    {
        if (s == null)
        {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i)
        {
            if (!Character.isWhitespace(s.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    private static byte[] is2Bytes(final InputStream is)
    {
        if (is == null)
        {
            return null;
        }
        ByteArrayOutputStream os = null;
        try
        {
            os = new ByteArrayOutputStream();
            byte[] b = new byte[BUFFER_SIZE];
            int len;
            while ((len = is.read(b, 0, BUFFER_SIZE)) != -1)
            {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
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

    private static List<String> is2List(final InputStream is,
                                        final String charsetName)
    {
        BufferedReader reader = null;
        try
        {
            List<String> list = new ArrayList<>();
            if (isSpace(charsetName))
            {
                reader = new BufferedReader(new InputStreamReader(is));
            } else
            {
                reader = new BufferedReader(new InputStreamReader(is, charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null)
            {
                list.add(line);
            }
            return list;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
