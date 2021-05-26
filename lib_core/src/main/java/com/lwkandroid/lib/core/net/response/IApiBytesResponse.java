package com.lwkandroid.lib.core.net.response;

import android.graphics.Bitmap;

import java.io.File;

import io.reactivex.rxjava3.core.Observable;


/**
 * Created by LWK
 * 定义Byte数组网络请求结果的转换方法
 *
 * @author LWK
 */
public interface IApiBytesResponse
{
    /**
     * 直接返回Byte数组网络请求结果
     *
     * @return
     */
    Observable<byte[]> returnByteArrayResponse();

    /**
     * 将Bytes解析为File并保存
     */
    Observable<File> parseAsFileFromBytes();

    /**
     * 将Bytes解析为Bitmap
     *
     * @return
     */
    Observable<Bitmap> parseAsBitmapFromBytes();
}
