package com.lwkandroid.lib.core.net.utils;


import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.utils.common.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * 网络请求RequestBody参数辅助工具
 *
 * @author LWK
 */

public class RequestBodyUtils
{
    private RequestBodyUtils()
    {
    }

    /**
     * 创建表单参数格式的RequestBody
     *
     * @param content 参数
     * @return 请求体
     */
    public static RequestBody createFormDataBody(Object content)
    {
        if (null == content)
        {
            KLog.e("Can not invoke 'createFormDataBody()' because of a null object");
            return null;
        }
        return RequestBody.create(String.valueOf(content), MediaType.parse("application/x-www-form-urlencoded"));
    }

    /**
     * 创建普通文本格式的RequestBody
     *
     * @param text 普通文本
     * @return 请求体
     */
    public static RequestBody createTextBody(String text)
    {
        if (StringUtils.isEmpty(text))
        {
            KLog.e("Can not invoke 'createTextBody()' because of a null text");
            return null;
        }
        return RequestBody.create(text, MediaType.parse("text/plain"));
    }

    /**
     * 创建Json格式的RequestBody
     *
     * @param json json文本
     * @return 请求体
     */
    public static RequestBody createJsonBody(String json)
    {
        if (StringUtils.isEmpty(json))
        {
            KLog.e("Can not invoke 'createJsonBody()' because of a null json");
            return null;
        }
        return RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
    }

    /**
     * 创建byte格式的RequestBody
     *
     * @param bytes 数据字节
     * @return 请求体
     */
    public static RequestBody createBytesBody(byte[] bytes)
    {
        if (bytes == null)
        {
            KLog.e("Can not invoke 'createBytesBody()' because of a null bytes");
            return null;
        }
        return RequestBody.create(bytes, MediaType.parse("application/octet-stream"));
    }

    /**
     * 创建File格式的RequestBody
     *
     * @param file 文件File
     * @return 请求体
     */
    public static RequestBody createFileBody(File file)
    {
        if (file == null)
        {
            KLog.e("Can not invoke 'createFileBody()' because of a null file");
            return null;
        }
        return RequestBody.create(file, guessMimeType(file.getName()));
    }

    /**
     * 创建InputStream格式的RequestBody
     *
     * @param contentType MediaType类型
     * @param inputStream 流
     * @return 请求体
     */
    public static RequestBody createInputStreamBody(final MediaType contentType, final InputStream inputStream)
    {
        if (inputStream == null)
        {
            KLog.e("Can not invoke 'createInputStreamBody()' because of a null inputStream");
            return null;
        }
        return new RequestBody()
        {
            @Override
            public MediaType contentType()
            {
                return contentType;
            }

            @Override
            public long contentLength()
            {
                try
                {
                    return inputStream.available();
                } catch (IOException e)
                {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException
            {
                Source source = null;
                try
                {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally
                {
                    Util.closeQuietly(source);
                }
            }
        };
    }

    /**
     * 根据文件名字获取对应的MediaType
     */
    public static MediaType guessMimeType(String fileName)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        fileName = fileName.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(fileName);
        if (contentType == null)
        {
            contentType = "application/octet-stream";
        }
        return MediaType.parse(contentType);
    }
}
