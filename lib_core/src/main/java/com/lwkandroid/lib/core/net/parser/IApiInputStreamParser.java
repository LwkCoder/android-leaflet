package com.lwkandroid.lib.core.net.parser;

import android.graphics.Bitmap;

import java.io.File;
import java.io.InputStream;

import io.reactivex.rxjava3.core.ObservableTransformer;


/**
 * Created by LWK
 * 将InputStream类型的网络请求结果转换为其他对象类型的接口
 *
 * @author LWK
 */

public interface IApiInputStreamParser
{
    interface FileParser
    {
        ObservableTransformer<InputStream, File> parseAsFile();
    }

    interface BitmapParser
    {
        ObservableTransformer<InputStream, Bitmap> parseAsBitmap();
    }
}
