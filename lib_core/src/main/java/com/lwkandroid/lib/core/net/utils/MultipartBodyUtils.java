package com.lwkandroid.lib.core.net.utils;

import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * 网络请求MultipartBody参数辅助工具
 *
 * @author LWK
 */
public class MultipartBodyUtils
{
    private MultipartBodyUtils()
    {
    }

    /**
     * 创建单独的Multipart.Body.Part对象
     */
    public static okhttp3.MultipartBody.Part createPart(RequestBody requestBody)
    {
        return okhttp3.MultipartBody.Part.create(requestBody);
    }

    /**
     * 创建单独的Multipart.Body.Part对象
     */
    public static okhttp3.MultipartBody.Part createPart(Headers headers, RequestBody requestBody)
    {
        return okhttp3.MultipartBody.Part.create(headers, requestBody);
    }

    //    /**
    //     * 创建单独的Multipart.Body.Part对象
    //     */
    //    public static okhttp3.MultipartBody.Part createFormDataPart(String key, String value)
    //    {
    //        return okhttp3.MultipartBody.Part.createFormData(key, value);
    //    }

    public static okhttp3.MultipartBody.Part createFormDataPart(String key, Object value)
    {
        return okhttp3.MultipartBody.Part.createFormData(key, null,
                RequestBody.create(String.valueOf(value), null));
    }

    /**
     * 创建单独的Multipart.Body.Part对象
     */
    public static okhttp3.MultipartBody.Part createFormDataPart(String key, String fileName, RequestBody requestBody)
    {
        return okhttp3.MultipartBody.Part.createFormData(key, fileName, requestBody);
    }
}
