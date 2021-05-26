package com.lwkandroid.lib.core.utils.encrypt;

import java.nio.charset.Charset;

/**
 * Description: Hmac加密接口
 *
 * @author LWK
 * @date 2019/4/26
 */
public interface IHmacEncryption
{
    byte[] encrypt(byte[] data, byte[] key);

    String encryptToString(byte[] data, byte[] key);

    String encryptToString(String data, byte[] key);

    String encryptToString(String data, Charset charset, byte[] key);

    byte[] encryptToBase64(byte[] data, byte[] key);

    byte[] encryptToBase64(String data, byte[] key);

    byte[] encryptToBase64(String data, Charset charset, byte[] key);

    String encryptToBase64String(byte[] data, byte[] key);

    String encryptToBase64String(String data, byte[] key);

    String encryptToBase64String(String data, Charset charset, byte[] key);

    String encryptToHexString(byte[] data, byte[] key);

    String encryptToHexString(String data, final byte[] key);

    String encryptToHexString(String data, Charset charset, final byte[] key);
}
