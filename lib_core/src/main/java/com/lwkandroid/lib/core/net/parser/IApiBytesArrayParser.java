package com.lwkandroid.lib.core.net.parser;

import android.graphics.Bitmap;

import java.io.File;

import io.reactivex.rxjava3.core.ObservableTransformer;


/**
 * 将Byte数组类型的网络请求结果转换为其他对象类型的接口
 *
 * @author LWK
 */
public interface IApiBytesArrayParser
{
    interface FileParser
    {
        ObservableTransformer<byte[], File> parseAsFile();
    }

    interface BitmapParser
    {
        ObservableTransformer<byte[], Bitmap> parseAsBitmap();
    }
}
