package com.lwkandroid.lib.core.net.response.func;

import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Description:ResponseBody转InputStream的方法
 *
 * @author LWK
 */
public final class ResponseToInputStreamFunc extends AbsResponseFunc<InputStream>
{
    @Override
    public InputStream convert(ResponseBody body) throws Exception
    {
        return body.byteStream();
    }
}
