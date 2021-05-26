package com.lwkandroid.lib.core.utils.encode;

/**
 * Description:Unicode编解码方法
 *
 * @author LWK
 * @date 2020/4/10
 */
public interface IUnicodeCodec
{
    String encode(String data);

    String encode(String data, String splitStr);

    String decode(String data);

    String decode(String data, String splitStr);

    /**
     * Unicode编码，字母、数字、半角符号除外
     */
    String encodeExceptCharacter(String data);

    /**
     * Unicode解码，只解码属于Unicode的字符
     */
    String decodeExceptCharacter(String data);
}
