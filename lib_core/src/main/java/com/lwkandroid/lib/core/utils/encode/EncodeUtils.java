package com.lwkandroid.lib.core.utils.encode;

import androidx.annotation.RequiresApi;

import static android.os.Build.VERSION_CODES.O;

/**
 * Created by LWK
 * 编解码工具类
 *
 * @author LWK
 */
public final class EncodeUtils
{
    private EncodeUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Android上Base64实现
     */
    private static final IBase64Codec ANDROID_BASE64_CODEC = new AndroidBase64CodecImpl();

    /**
     * Java上Base64实现
     */
    @RequiresApi(O)
    private static final IBase64Codec JAVA_BASE64_CODEC = new JavaBase64CodecImpl();

    /**
     * Url编解码实现
     */
    private static final IUrlCodec URL_CODEC = new UrlCodecImpl();

    /**
     * 二进制编解码实现
     */
    private static final IBinCodec BIN_CODEC = new BinCodecImpl();

    /**
     * 16进制编解码实现
     */
    private static final IHexStringCodec HEX_STRING_CODEC = new HexStringCodecImpl();

    /**
     * ASCII编解码实现
     */
    private static final IAsciiCodec ASCII_CODEC = new AsciiCodecImpl();

    /**
     * Unicode编解码实现
     */
    private static final IUnicodeCodec UNICODE_CODEC = new UnicodeCodecImpl();

    /**
     * Base64编解码入口【默认使用Android实现】
     */
    public static IBase64Codec base64()
    {
        return ANDROID_BASE64_CODEC;
    }

    /**
     * Android Base64编解码入口
     */
    public static IBase64Codec androidBase64()
    {
        return ANDROID_BASE64_CODEC;
    }

    /**
     * Java Base64编解码入口
     */
    @RequiresApi(O)
    public static IBase64Codec javaBase64()
    {
        return JAVA_BASE64_CODEC;
    }

    /**
     * Url编解码入口
     */
    public static IUrlCodec url()
    {
        return URL_CODEC;
    }

    /**
     * 二进制编解码入口
     */
    public static IBinCodec bin()
    {
        return BIN_CODEC;
    }

    /**
     * 16进制编解码入口
     */
    public static IHexStringCodec hex()
    {
        return HEX_STRING_CODEC;
    }

    /**
     * ASCII编解码入口
     */
    public static IAsciiCodec ascii()
    {
        return ASCII_CODEC;
    }

    /**
     * Unicode编解码入口
     */
    public static IUnicodeCodec unicode()
    {
        return UNICODE_CODEC;
    }
}
