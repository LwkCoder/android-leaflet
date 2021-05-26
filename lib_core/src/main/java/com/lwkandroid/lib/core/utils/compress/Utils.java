package com.lwkandroid.lib.core.utils.compress;

import android.graphics.Bitmap;

import com.lwkandroid.lib.core.utils.common.FileUtils;
import com.lwkandroid.lib.core.utils.common.PathUtils;

import java.io.File;

/**
 * @description: 公共工具
 * @author: LWK
 * @date: 2020/6/17 10:41
 */
final class Utils
{
    public static String createOutputFilePath(String originFilePath, String cacheFolderPath,
                                              ICompressImageRename renameImpl, Bitmap.CompressFormat format)
    {
        String newFileName = renameImpl.createImageName(originFilePath);
        if (!FileUtils.createOrExistsDir(cacheFolderPath))
        {
            cacheFolderPath = PathUtils.getExAppDcimPath();
        }
        String targetPath = cacheFolderPath.endsWith(File.separator) ?
                cacheFolderPath + newFileName : cacheFolderPath + File.separator + newFileName;
        if (Bitmap.CompressFormat.JPEG == format)
        {
            return targetPath + ".jpg";
        } else if (Bitmap.CompressFormat.PNG == format)
        {
            return targetPath + ".png";
        } else if (Bitmap.CompressFormat.WEBP == format)
        {
            return targetPath + ".webp";
        } else
        {
            return targetPath;
        }
    }

}
