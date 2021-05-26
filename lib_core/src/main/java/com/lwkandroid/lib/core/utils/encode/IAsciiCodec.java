package com.lwkandroid.lib.core.utils.encode;

/**
 * Description: ASCII编解码方法
 *
 * @author LWK
 * @date 2019/5/29
 */
public interface IAsciiCodec
{
    String encode(String data);

    String encode(String data, String splitChar);

    String decode(String asciiString);

    String decode(String asciiString, String splitChar);
}
