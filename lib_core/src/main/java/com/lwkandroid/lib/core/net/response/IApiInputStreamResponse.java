package com.lwkandroid.lib.core.net.response;

import android.graphics.Bitmap;

import java.io.File;
import java.io.InputStream;

import io.reactivex.rxjava3.core.Observable;


/**
 * Created by LWK
 * 定义InputStream网络请求结果的转换方法
 *
 * @author LWK
 */

public interface IApiInputStreamResponse
{
    /**
     * 直接返回InputStream网络请求结果
     *
     * @return
     */
    Observable<InputStream> returnISResponse();

    /**
     * 将InputStream解析为File并保存
     */
    Observable<File> parseAsFileFromIS();

    /**
     * 将InputStream解析为Bitmap
     */
    Observable<Bitmap> parseAsBitmapFromIS();
}
