package com.lwkandroid.lib.core.net.response;

import com.lwkandroid.lib.core.net.response.func.ResponseToBytesFunc;
import com.lwkandroid.lib.core.net.response.func.ResponseToInputStreamFunc;
import com.lwkandroid.lib.core.net.response.func.ResponseToStringFunc;

import java.io.InputStream;

import io.reactivex.rxjava3.functions.Function;
import okhttp3.ResponseBody;

/**
 * 网络请求返回ResponseBody的转换类入口
 *
 * @author LWK
 */

public final class ApiResponseBodyConverter
{
    private static final ResponseToStringFunc STRING_CONVERTER = new ResponseToStringFunc();
    private static final ResponseToBytesFunc BYTE_ARRAY_CONVERTER = new ResponseToBytesFunc();
    private static final ResponseToInputStreamFunc INPUT_STREAM_CONVERTER = new ResponseToInputStreamFunc();

    /**
     * 将ResponseBody转换为String
     */
    public static Function<ResponseBody, String> convertToString()
    {
        return STRING_CONVERTER;
    }

    /**
     * 将ResponseBody转换为bytes
     */
    public static Function<ResponseBody, byte[]> convertToBytes()
    {
        return BYTE_ARRAY_CONVERTER;
    }

    /**
     * 将ResponseBody转换为inputStream
     */
    public static Function<ResponseBody, InputStream> convertToInputStream()
    {
        return INPUT_STREAM_CONVERTER;
    }
}
