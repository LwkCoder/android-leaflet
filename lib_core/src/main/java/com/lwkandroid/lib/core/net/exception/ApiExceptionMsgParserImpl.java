package com.lwkandroid.lib.core.net.exception;

import com.lwkandroid.lib.core.R;
import com.lwkandroid.lib.core.net.constants.ApiExceptionCode;
import com.lwkandroid.lib.core.utils.common.ResourceUtils;

/**
 * 设置ApiException错误描述的方法
 *
 * @author LWK
 */
public class ApiExceptionMsgParserImpl implements IApiExceptionMsgParser
{
    @Override
    public String parserMessageByCode(int errorCode, String throwMessage)
    {
        if (errorCode == ApiExceptionCode.PARSE_ERROR)
        {
            return ResourceUtils.getString(R.string.rxhttp_error_parse_data);
        } else if (errorCode == ApiExceptionCode.CAST_ERROR)
        {
            return ResourceUtils.getString(R.string.rxhttp_error_cast_data);
        } else if (errorCode == ApiExceptionCode.CONNECT_ERROR)
        {
            return ResourceUtils.getString(R.string.rxhttp_error_connect_fail);
        } else if (errorCode == ApiExceptionCode.SSL_ERROR)
        {
            return ResourceUtils.getString(R.string.rxhttp_error_ssl_invalid);
        } else if (errorCode == ApiExceptionCode.TIMEOUT_ERROR)
        {
            return ResourceUtils.getString(R.string.rxhttp_error_connect_timeout);
        } else if (errorCode == ApiExceptionCode.UNKNOWN_HOST_ERROR)
        {
            return ResourceUtils.getString(R.string.rxhttp_error_unknow_host);
        } else if (errorCode == ApiExceptionCode.NULL_POINTER_EXCEPTION)
        {
            return ResourceUtils.getString(R.string.rxhttp_error_nullpointer);
        } else if (errorCode == ApiExceptionCode.IO_EXCEPTION)
        {
            return ResourceUtils.getString(R.string.rxhttp_error_io);
        } else if (errorCode == ApiExceptionCode.RESPONSE_EMPTY)
        {
            return ResourceUtils.getString(R.string.rxhttp_error_response_empty);
        } else if (errorCode == ApiExceptionCode.CACHE_EMPTY)
        {
            return ResourceUtils.getString(R.string.rxhttp_error_cache_empty);
        } else
        {
            return parseDisplayMessage(errorCode, throwMessage);
        }
    }

    /**
     * 根据错误码设置错误提示语
     * 子类可继承该类完成补全
     *
     * @param errCode      错误码
     * @param throwMessage 服务器返回的异常提示
     * @return 自定义错误提示语
     */
    public String parseDisplayMessage(int errCode, String throwMessage)
    {
        return throwMessage;
    }
}
