package com.lwkandroid.lib.core.utils.encode;

import java.nio.charset.Charset;

/**
 * Description:Base64编解码方法
 *
 * @author LWK
 * @date 2019/5/27
 */
public interface IBase64Codec
{
    byte[] encode(byte[] data);

    String encode2String(byte[] data);

    String encode2String(byte[] data, Charset charset);

    String encode2String(String data);

    String encode2String(String data, Charset charset);

    byte[] decode(byte[] base64Data);

    byte[] decodeString(String base64String);

    byte[] decodeString(String base64String, Charset charset);

    String decodeString2String(String base64String);

    String decodeString2String(String base64String, Charset charset);
}
