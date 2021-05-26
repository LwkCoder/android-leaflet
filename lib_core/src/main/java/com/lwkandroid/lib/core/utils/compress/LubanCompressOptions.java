package com.lwkandroid.lib.core.utils.compress;

import android.graphics.Bitmap;

import com.lwkandroid.lib.core.utils.common.PathUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: 基础压缩配置
 * @author: LWK
 * @date: 2020/6/11 10:13
 */
public class LubanCompressOptions
{
    /**
     * 压缩过程中缓存路径
     */
    private String mCacheFolderPath = PathUtils.getExAppDcimPath();

    /**
     * 待压缩图片
     */
    private List<File> mFileList = new LinkedList<>();

    /**
     * 符合无需压缩的图片最小字节量，单位byte，默认100KB
     */
    private long mIgnoreBytesSize = 102400;

    /**
     * 压缩格式，默认使用JPEG
     */
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;

    /**
     * 压缩后文件命名方式
     */
    private ICompressImageRename mRename = new DefaultImageRenameImpl();

    public LubanCompressOptions setCacheFolderPath(String path)
    {
        this.mCacheFolderPath = path;
        return this;
    }

    public LubanCompressOptions setIgnoreBytesSize(long size)
    {
        this.mIgnoreBytesSize = size;
        return this;
    }

    public LubanCompressOptions setCopressFormat(Bitmap.CompressFormat format)
    {
        this.mCompressFormat = format;
        return this;
    }

    public LubanCompressOptions addFile(File file)
    {
        this.mFileList.add(file);
        return this;
    }

    public LubanCompressOptions addFileList(List<File> list)
    {
        this.mFileList.addAll(list);
        return this;
    }

    public LubanCompressOptions setFileList(List<File> list)
    {
        this.mFileList = list;
        return this;
    }

    public String getCacheFolderPath()
    {
        return mCacheFolderPath;
    }

    public List<File> getFileList()
    {
        return mFileList;
    }

    public long getIgnoreBytesSize()
    {
        return mIgnoreBytesSize;
    }

    public Bitmap.CompressFormat getCompressFormat()
    {
        return mCompressFormat;
    }

    public ICompressImageRename getRenameImpl()
    {
        return mRename;
    }

    public void setRenameImpl(ICompressImageRename renameImpl)
    {
        this.mRename = renameImpl;
    }

    public LubanCompressEngine create()
    {
        return new LubanCompressEngine(this);
    }
}
