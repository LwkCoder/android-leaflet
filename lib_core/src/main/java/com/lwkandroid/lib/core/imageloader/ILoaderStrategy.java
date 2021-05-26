package com.lwkandroid.lib.core.imageloader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lwkandroid.lib.core.imageloader.bean.ImageOptions;
import com.lwkandroid.lib.core.imageloader.callback.ImageDownLoadCallBack;

import java.io.File;

/**
 * 定义图片加载方法
 *
 * @author LWK
 */
public interface ILoaderStrategy<T extends ImageOptions>
{
    /**
     * 加载图片
     *
     * @param context   Context环境
     * @param imageView 待加载对象
     * @param options   加载参数
     */
    void show(Context context, ImageView imageView, T options);

    /**
     * 加载图片
     *
     * @param context   Context环境
     * @param imageView 待加载对象
     * @param options   加载参数
     */
    void show(Activity context, ImageView imageView, T options);

    /**
     * 加载图片
     *
     * @param context   Context环境
     * @param imageView 待加载对象
     * @param options   加载参数
     */
    void show(Fragment context, ImageView imageView, T options);

    /**
     * 加载图片
     *
     * @param context   Context环境
     * @param imageView 待加载对象
     * @param options   加载参数
     */
    void show(androidx.fragment.app.Fragment context, ImageView imageView, T options);

    /**
     * 下载图片保存为File
     *
     * @param context  Context环境
     * @param url      下载地址
     * @param callBack 回调
     */
    void downloadFile(Context context, String url, ImageDownLoadCallBack<File> callBack);

    /**
     * 下载图片保存为File
     *
     * @param context   Context环境
     * @param url       下载地址
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @param callBack  回调
     */
    void downloadFile(Context context, String url, int maxWidth, int maxHeight, ImageDownLoadCallBack<File> callBack);

    /**
     * 下载图片转为Bitmap
     *
     * @param context  Context环境
     * @param url      下载地址
     * @param callBack 回调
     */
    void downloadBitmap(Context context, String url, ImageDownLoadCallBack<Bitmap> callBack);

    /**
     * 下载图片转为Bitmap
     *
     * @param context   Context环境
     * @param url       下载地址
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @param callBack  回调
     */
    void downloadBitmap(Context context, String url, int maxWidth, int maxHeight, ImageDownLoadCallBack<Bitmap> callBack);

    /**
     * 暂停加载
     */
    void pause(Context context);

    /**
     * 恢复加载
     */
    void resume(Context context);

    /**
     * 清除缓存
     */
    void clearCache(Context context);

    /**
     * 获取缓存地址
     */
    String getCachePath();
}
