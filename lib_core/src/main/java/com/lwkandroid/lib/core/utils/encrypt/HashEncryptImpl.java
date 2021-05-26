package com.lwkandroid.lib.core.utils.encrypt;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description: Hash加密工具基类
 *
 * @author LWK
 * @date 2019/4/26
 */
public final class HashEncryptImpl extends BaseEncryptImpl implements IHashEncryption
{
    private String mAlgorithm;

    HashEncryptImpl(String algorithm)
    {
        this.mAlgorithm = algorithm;
    }

    @Override
    public byte[] encrypt(byte[] data)
    {
        if (data == null || data.length <= 0)
        {
            return null;
        }
        try
        {
            MessageDigest md = MessageDigest.getInstance(mAlgorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encryptToString(byte[] data)
    {
        return bytesToString(encrypt(data));
    }

    @Override
    public String encryptToString(String data)
    {
        return encryptToString(data, CHARSET_UTF8);
    }

    @Override
    public String encryptToString(String data, Charset charset)
    {
        return bytesToString(encrypt(stringToBytes(data, charset)), charset);
    }

    @Override
    public byte[] encryptToBase64(byte[] data)
    {
        return bytesToBase64(encrypt(data));
    }

    @Override
    public byte[] encryptToBase64(String data)
    {
        return encryptToBase64(data, CHARSET_UTF8);
    }

    @Override
    public byte[] encryptToBase64(String data, Charset charset)
    {
        return bytesToBase64(encrypt(stringToBytes(data, charset)));
    }

    @Override
    public String encryptToBase64String(byte[] data)
    {
        return bytesToBase64String(encrypt(data));
    }

    @Override
    public String encryptToBase64String(String data)
    {
        return encryptToBase64String(data, CHARSET_UTF8);
    }

    @Override
    public String encryptToBase64String(String data, Charset charset)
    {
        return bytesToBase64String(encrypt(stringToBytes(data, charset)), charset);
    }

    @Override
    public String encryptToHexString(byte[] data)
    {
        return bytesToHexString(encrypt(data));
    }

    @Override
    public String encryptToHexString(String data)
    {
        return encryptToHexString(data, CHARSET_UTF8);
    }

    @Override
    public String encryptToHexString(String data, Charset charset)
    {
        return encryptToHexString(stringToBytes(data, charset));
    }
}
