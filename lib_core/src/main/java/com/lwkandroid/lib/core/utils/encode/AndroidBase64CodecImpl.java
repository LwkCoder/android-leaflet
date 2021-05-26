package com.lwkandroid.lib.core.utils.encode;

import android.util.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Description: Android上Base64编解码方法实现类
 *
 * @author LWK
 * @date 2019/5/27
 */
public final class AndroidBase64CodecImpl implements IBase64Codec
{
    private static final Charset UTF8 = StandardCharsets.UTF_8;

    @Override
    public byte[] encode(byte[] data)
    {
        return Base64.encode(data, Base64.NO_WRAP);
    }

    @Override
    public String encode2String(byte[] data)
    {
        return encode2String(data, UTF8);
    }

    @Override
    public String encode2String(byte[] data, Charset charset)
    {
        return new String(encode(data), charset);
    }

    @Override
    public String encode2String(String data)
    {
        return encode2String(data, UTF8);
    }

    @Override
    public String encode2String(String data, Charset charset)
    {
        return encode2String(data.getBytes(charset), charset);
    }

    @Override
    public byte[] decode(byte[] base64Data)
    {
        return Base64.decode(base64Data, Base64.NO_WRAP);
    }

    @Override
    public byte[] decodeString(String base64String)
    {
        return decodeString(base64String, UTF8);
    }

    @Override
    public byte[] decodeString(String base64String, Charset charset)
    {
        return decode(base64String.getBytes(charset));
    }

    @Override
    public String decodeString2String(String base64String)
    {
        return decodeString2String(base64String, UTF8);
    }

    @Override
    public String decodeString2String(String base64String, Charset charset)
    {
        return new String(decodeString(base64String, charset), charset);
    }
}
