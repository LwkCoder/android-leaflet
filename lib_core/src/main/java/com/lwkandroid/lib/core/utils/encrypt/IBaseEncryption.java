package com.lwkandroid.lib.core.utils.encrypt;

import java.nio.charset.Charset;

/**
 * Description:加密公共方法接口
 *
 * @author LWK
 * @date 2019/4/26
 */
public interface IBaseEncryption
{
    Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    String bytesToString(byte[] data);

    String bytesToString(byte[] data, Charset charset);

    String bytesToHexString(byte[] data);

    byte[] bytesToBase64(byte[] data);

    String bytesToBase64String(byte[] data);

    String bytesToBase64String(byte[] data, Charset charset);

    byte[] stringToBytes(String string);

    byte[] stringToBytes(String string, Charset charset);

    byte[] base64ToBytes(byte[] base64Bytes);

    byte[] base64StringToBytes(String base64String);

    byte[] base64StringToBytes(String base64String, Charset charset);

    byte[] hexStringToBytes(String hexString);
}
