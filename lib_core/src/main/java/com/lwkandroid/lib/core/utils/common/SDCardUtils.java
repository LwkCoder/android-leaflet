package com.lwkandroid.lib.core.utils.common;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.lwkandroid.lib.core.context.AppContext;

import java.io.File;

/**
 * SD卡相关工具类
 */
public final class SDCardUtils
{

    private SDCardUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 判断SD卡是否可用
     */
    public static boolean isSDCardEnable()
    {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径（一般是：/storage/emulated/0/）
     *
     * @deprecated use{@link com.lwkandroid.lib.core.utils.common.PathUtils}
     */
    @Deprecated
    public static String getSDCardPath()
    {
        if (!isSDCardEnable())
        {
            return null;
        }
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        return path.endsWith(File.separator) ? path : path + File.separatorChar;
    }

    /**
     * 获取默认缓存路径（一般是：/storage/emulated/0/Android/data/package-name/data/）
     *
     * @return 缓存路径
     * @deprecated use{@link com.lwkandroid.lib.core.utils.common.PathUtils}
     */
    @Deprecated
    public static String getExternalCachePath()
    {
        if (!isSDCardEnable())
        {
            return null;
        }

        File file = AppContext.get().getExternalCacheDir();
        if (file == null)
        {
            //部分ROM获取不到地址就自行拼接
            String path = getSDCardPath() +
                    "Android/data/" +
                    AppUtils.getPackageName() +
                    "/cache/";
            file = new File(path);
            return file.mkdirs() ? file.getAbsolutePath() : null;
        }

        String path = file.getAbsolutePath();
        return path.endsWith(File.separator) ? path : path + File.separatorChar;
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间（单位：byte）
     */
    public static long getFreeSpace()
    {
        if (!isSDCardEnable())
        {
            return 0;
        }
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            availableBlocks = stat.getAvailableBlocksLong();
            blockSize = stat.getBlockSizeLong();
        } else
        {
            availableBlocks = stat.getAvailableBlocks();
            blockSize = stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }
}