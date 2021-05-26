package com.lwkandroid.lib.core.net.exception;

/**
 * 定义ApiException错误描述文案的方法
 *
 * @author LWK
 */

public interface IApiExceptionMsgParser
{
    String parserMessageByCode(int errorCode, String throwMessage);
}
