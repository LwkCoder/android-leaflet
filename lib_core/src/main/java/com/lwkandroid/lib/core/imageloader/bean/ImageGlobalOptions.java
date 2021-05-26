package com.lwkandroid.lib.core.imageloader.bean;

import android.graphics.drawable.Drawable;

import com.lwkandroid.lib.core.imageloader.constants.ImageDiskCacheType;

import androidx.annotation.DrawableRes;

/**
 * 全局设置
 *
 * @author LWK
 */

public class ImageGlobalOptions
{
    /**
     * 全局加载占位图
     */
    @DrawableRes
    private int placeHolder = -1;

    /**
     * 全局加载占位图
     */
    private Drawable placeHolderDrawable;

    /**
     * 全局失败占位图
     */
    @DrawableRes
    private int errorHolder = -1;

    /**
     * 全局失败占位图
     */
    private Drawable errorHolderDrawable;

    /**
     * 全局外部缓存策略
     */
    @ImageDiskCacheType.Type
    private int diskCacheType = ImageDiskCacheType.RESOURCE;

    /**
     * 全局内存是否缓存
     */
    private boolean skipMemoryCache = false;

    /**
     * 全局是否显示动画
     */
    private boolean crossFade = false;

    /**
     * 全剧动画时间
     */
    private int crossFadeDuration = 200;

    /**
     * 缓存地址
     */
    private String cachePath;

    public int getPlaceHolder()
    {
        return placeHolder;
    }

    public ImageGlobalOptions setPlaceHolder(int placeHolder)
    {
        this.placeHolder = placeHolder;
        return this;
    }

    public Drawable getPlaceHolderDrawable()
    {
        return placeHolderDrawable;
    }

    public ImageGlobalOptions setPlaceHolderDrawable(Drawable placeHolderDrawable)
    {
        this.placeHolderDrawable = placeHolderDrawable;
        return this;
    }

    public int getErrorHolder()
    {
        return errorHolder;
    }

    public ImageGlobalOptions setErrorHolder(int errorHolder)
    {
        this.errorHolder = errorHolder;
        return this;
    }

    public Drawable getErrorHolderDrawable()
    {
        return errorHolderDrawable;
    }

    public ImageGlobalOptions setErrorHolderDrawable(Drawable errorHolderDrawable)
    {
        this.errorHolderDrawable = errorHolderDrawable;
        return this;
    }

    public int getDiskCacheType()
    {
        return diskCacheType;
    }

    public ImageGlobalOptions setDiskCacheType(int diskCacheType)
    {
        this.diskCacheType = diskCacheType;
        return this;
    }

    public boolean isSkipMemoryCache()
    {
        return skipMemoryCache;
    }

    public ImageGlobalOptions setSkipMemoryCache(boolean skipMemoryCache)
    {
        this.skipMemoryCache = skipMemoryCache;
        return this;
    }

    public boolean isCrossFade()
    {
        return crossFade;
    }

    public ImageGlobalOptions setCrossFade(boolean crossFade)
    {
        this.crossFade = crossFade;
        return this;
    }

    public int getCrossFadeDuration()
    {
        return crossFadeDuration;
    }

    public ImageGlobalOptions setCrossFadeDuration(int crossFadeDuration)
    {
        this.crossFadeDuration = crossFadeDuration;
        return this;
    }

    public String getCachePath()
    {
        return cachePath;
    }

    public ImageGlobalOptions setCachePath(String cachePath)
    {
        this.cachePath = cachePath;
        return this;
    }
}
