package com.lwkandroid.lib.core.net.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 网络请求RequestBody集合参数辅助工具
 *
 * @author LWK
 */

public class RequestBodyMap extends HashMap<String, RequestBody>
{
    private static final long serialVersionUID = -1513188462053348607L;

    /**
     * 添加表单文本的参数
     */
    public RequestBodyMap addFormData(String key, Object value)
    {
        put(key, RequestBodyUtils.createFormDataBody(value));
        return this;
    }

    /**
     * 添加普通文本的参数
     */
    public RequestBodyMap addText(String key, String value)
    {
        put(key, RequestBodyUtils.createTextBody(value));
        return this;
    }

    /**
     * 添加Json参数
     */
    public RequestBodyMap addJson(String key, String json)
    {
        put(key, RequestBodyUtils.createJsonBody(json));
        return this;
    }

    /**
     * 添加File参数
     */
    public RequestBodyMap addFile(String key, File file)
    {
        put(key, RequestBodyUtils.createFileBody(file));
        return this;
    }

    /**
     * 添加Bytes参数
     */
    public RequestBodyMap addBytes(String key, byte[] bytes)
    {
        put(key, RequestBodyUtils.createBytesBody(bytes));
        return this;
    }

    /**
     * 添加InputStream参数
     */
    public RequestBodyMap addInputStream(String key, String fileName, InputStream stream)
    {
        put(key, RequestBodyUtils.createInputStreamBody(RequestBodyUtils.guessMimeType(fileName), stream));
        return this;
    }

    /**
     * 添加InputStream参数
     */
    public RequestBodyMap addInputStream(String key, MediaType mediaType, InputStream stream)
    {
        put(key, RequestBodyUtils.createInputStreamBody(mediaType, stream));
        return this;
    }
}
