package com.lwkandroid.lib.core.utils.encrypt;

import java.nio.charset.Charset;

/**
 * Description: Hash加密接口
 *
 * @author LWK
 * @date 2019/4/26
 */
public interface IHashEncryption
{
    byte[] encrypt(byte[] data);

    String encryptToString(byte[] data);

    String encryptToString(String data);

    String encryptToString(String data, Charset charset);

    byte[] encryptToBase64(byte[] data);

    byte[] encryptToBase64(String data);

    byte[] encryptToBase64(String data, Charset charset);

    String encryptToBase64String(byte[] data);

    String encryptToBase64String(String data);

    String encryptToBase64String(String data, Charset charset);

    String encryptToHexString(byte[] data);

    String encryptToHexString(String data);

    String encryptToHexString(String data, Charset charset);
}
