package com.lwkandroid.lib.core.net.response.func;

import okhttp3.ResponseBody;

/**
 * Description:ResponseBody转Bytes的方法
 *
 * @author LWK
 */
public final class ResponseToBytesFunc extends AbsResponseFunc<byte[]>
{
    @Override
    public byte[] convert(ResponseBody body) throws Exception
    {
        return body.bytes();
    }
}
