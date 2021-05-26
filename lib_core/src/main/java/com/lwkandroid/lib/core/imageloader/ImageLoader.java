package com.lwkandroid.lib.core.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.lwkandroid.lib.core.imageloader.bean.ImageGlobalOptions;
import com.lwkandroid.lib.core.imageloader.callback.ImageDownLoadCallBack;
import com.lwkandroid.lib.core.imageloader.glide.GlideLoader;
import com.lwkandroid.lib.core.imageloader.glide.GlideLoaderOptions;

import java.io.File;

import androidx.annotation.DrawableRes;

/**
 * Created by LWK
 * 向外公布的图片加载类
 *
 * @author LWK
 */
public final class ImageLoader
{
    private static final ImageGlobalOptions GLOBAL_OPTIONS = new ImageGlobalOptions();

    private static ILoaderStrategy mLoaderImpl = new GlideLoader();

    private ImageLoader()
    {
    }

    /**
     * 获取全局配置
     */
    public static ImageGlobalOptions getGlobalOptions()
    {
        return GLOBAL_OPTIONS;
    }

    /**
     * 获取图片加载器对象
     */
    public static ILoaderStrategy getLoader()
    {
        if (mLoaderImpl == null)
        {
            mLoaderImpl = new GlideLoader();
        }
        return mLoaderImpl;
    }

    /**
     * 设置实现类
     *
     * @param loader
     */
    public static void setLoader(ILoaderStrategy loader)
    {
        mLoaderImpl = loader;
    }

    /**
     * 加载网络图片
     *
     * @param url 图片地址
     */
    public static GlideLoaderOptions load(String url)
    {
        return new GlideLoaderOptions().setResource(url);
    }

    /**
     * 加载本地Uri图片
     *
     * @param uri 图片Uri
     */
    public static GlideLoaderOptions load(Uri uri)
    {
        return new GlideLoaderOptions().setResource(uri);
    }

    /**
     * 加载图片Drawable
     *
     * @param drawable 图片Drawable
     */
    public static GlideLoaderOptions load(Drawable drawable)
    {
        return new GlideLoaderOptions().setResource(drawable);
    }

    /***
     * 加载图片资源id
     * @param resId 图片资源id
     */
    public static GlideLoaderOptions load(@DrawableRes int resId)
    {
        return new GlideLoaderOptions().setResource(resId);
    }

    /***
     * 加载图片资源bitmap
     * @param bitmap 图片资源bitmap
     */
    public static GlideLoaderOptions load(Bitmap bitmap)
    {
        return new GlideLoaderOptions().setResource(bitmap);
    }

    /**
     * 加载图片二进制数据
     *
     * @param bytes 图片二进制数据
     */
    public static GlideLoaderOptions load(byte[] bytes)
    {
        return new GlideLoaderOptions().setResource(bytes);
    }

    /**
     * 下载图片保存为File
     *
     * @param context  Context环境
     * @param url      图片地址
     * @param callBack 回调
     */
    public static void downloadFile(Context context, String url, ImageDownLoadCallBack<File> callBack)
    {
        mLoaderImpl.downloadFile(context, url, callBack);
    }

    /**
     * 下载图片保存为File
     *
     * @param context   Context环境
     * @param url       图片地址
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @param callBack  回调
     */
    public static void downloadFile(Context context, String url, int maxWidth, int maxHeight, ImageDownLoadCallBack<File> callBack)
    {
        mLoaderImpl.downloadFile(context, url, maxWidth, maxHeight, callBack);
    }

    /**
     * 下载图片转为Bitmap
     *
     * @param context  Context环境
     * @param url      图片地址
     * @param callBack 回调
     */
    public static void downloadBitmap(Context context, String url, ImageDownLoadCallBack<Bitmap> callBack)
    {
        mLoaderImpl.downloadBitmap(context, url, callBack);
    }

    /**
     * 下载图片转为Bitmap
     *
     * @param context   Context环境
     * @param url       图片地址
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @param callBack  回调
     */
    public static void downloadBitmap(Context context, String url, int maxWidth, int maxHeight, ImageDownLoadCallBack<Bitmap> callBack)
    {
        mLoaderImpl.downloadBitmap(context, url, maxWidth, maxHeight, callBack);
    }
}