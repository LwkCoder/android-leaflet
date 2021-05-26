package com.lwkandroid.lib.core.utils.compress;

import android.graphics.Bitmap;

import com.lwkandroid.lib.core.utils.common.PathUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: 多样性的压缩配置
 * @author: LWK
 * @date: 2020/6/11 16:30
 */
public class AdvanceCompressOptions
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

    /**
     * 压缩后占用最大字节数
     */
    private long mMaxBytesSize = -1;

    /**
     * 压缩后最大宽度分辨率
     */
    private int mMaxWidth = -1;

    /**
     * 压缩后最大高度分辨率
     */
    private int mMaxHeight = -1;

    public AdvanceCompressOptions setCacheFolderPath(String path)
    {
        this.mCacheFolderPath = path;
        return this;
    }

    public AdvanceCompressOptions setIgnoreBytesSize(long size)
    {
        this.mIgnoreBytesSize = size;
        return this;
    }

    public AdvanceCompressOptions setCopressFormat(Bitmap.CompressFormat format)
    {
        this.mCompressFormat = format;
        return this;
    }

    public AdvanceCompressOptions addFile(File file)
    {
        this.mFileList.add(file);
        return this;
    }

    public AdvanceCompressOptions addFileList(List<File> list)
    {
        this.mFileList.addAll(list);
        return this;
    }

    public AdvanceCompressOptions setFileList(List<File> list)
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

    public AdvanceCompressOptions setMaxBytesSize(long size)
    {
        this.mMaxBytesSize = size;
        return this;
    }

    public AdvanceCompressOptions setMaxWidth(int width)
    {
        this.mMaxWidth = width;
        return this;
    }

    public AdvanceCompressOptions setMaxHeight(int height)
    {
        this.mMaxHeight = height;
        return this;
    }

    public long getMaxBytesSize()
    {
        return mMaxBytesSize;
    }

    public int getMaxWidth()
    {
        return mMaxWidth;
    }

    public int getMaxHeight()
    {
        return mMaxHeight;
    }

    public AdvanceCompressEngine create()
    {
        return new AdvanceCompressEngine(this);
    }
}
