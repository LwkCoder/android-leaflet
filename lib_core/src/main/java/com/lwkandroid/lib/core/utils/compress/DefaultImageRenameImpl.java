package com.lwkandroid.lib.core.utils.compress;

import com.lwkandroid.lib.core.utils.encrypt.EncryptUtils;

/**
 * @description: 默认重命名的实现类
 * @author: LWK
 * @date: 2020/6/11 10:37
 */
final class DefaultImageRenameImpl implements ICompressImageRename
{
    @Override
    public String createImageName(String originFilePath)
    {
        return "IMG_" + EncryptUtils.md5().encryptToHexString(originFilePath + System.currentTimeMillis());
    }
}
