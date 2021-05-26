package com.lwkandroid.lib.core.utils.compress;

import java.io.File;
import java.util.List;

/**
 * @description: 图片压缩工具类
 * @author: LWK
 * @date: 2020/6/11 10:12
 */
public final class ImageCompressor
{
    private ImageCompressor()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    //    public static LubanCompressOptions lubanCompress(File file)
    //    {
    //        List<File> list = new LinkedList<>();
    //        list.add(file);
    //        return lubanCompress(list);
    //    }

    public static LubanCompressOptions lubanCompress(List<File> files)
    {
        LubanCompressOptions options = new LubanCompressOptions();
        options.setFileList(files);
        return options;
    }

    //    public static AdvanceCompressOptions advanceCompress(File file)
    //    {
    //        List<File> list = new LinkedList<>();
    //        list.add(file);
    //        return advanceCompress(list);
    //    }

    public static AdvanceCompressOptions advanceCompress(List<File> files)
    {
        AdvanceCompressOptions options = new AdvanceCompressOptions();
        options.setFileList(files);
        return options;
    }
}
