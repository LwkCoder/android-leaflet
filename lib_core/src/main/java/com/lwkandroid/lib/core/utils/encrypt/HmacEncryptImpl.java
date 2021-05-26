package com.lwkandroid.lib.core.utils.encrypt;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Description: Hmac加密工具基类
 *
 * @author LWK
 * @date 2019/4/26
 */
public final class HmacEncryptImpl extends BaseEncryptImpl implements IHmacEncryption
{
    private String mAlgorithm;

    HmacEncryptImpl(String algorithm)
    {
        this.mAlgorithm = algorithm;
    }

    @Override
    public byte[] encrypt(byte[] data, byte[] key)
    {
        if (data == null || data.length == 0 || key == null || key.length == 0)
        {
            return null;
        }
        try
        {
            SecretKeySpec secretKey = new SecretKeySpec(key, mAlgorithm);
            Mac mac = Mac.getInstance(mAlgorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encryptToString(byte[] data, byte[] key)
    {
        return bytesToString(encrypt(data, key));
    }

    @Override
    public String encryptToString(String data, byte[] key)
    {
        return encryptToString(data, CHARSET_UTF8, key);
    }

    @Override
    public String encryptToString(String data, Charset charset, byte[] key)
    {
        return bytesToString(encrypt(stringToBytes(data, charset), key), charset);
    }

    @Override
    public byte[] encryptToBase64(byte[] data, byte[] key)
    {
        return bytesToBase64(encrypt(data, key));
    }

    @Override
    public byte[] encryptToBase64(String data, byte[] key)
    {
        return encryptToBase64(data, CHARSET_UTF8, key);
    }

    @Override
    public byte[] encryptToBase64(String data, Charset charset, byte[] key)
    {
        return bytesToBase64(encrypt(stringToBytes(data, charset), key));
    }

    @Override
    public String encryptToBase64String(byte[] data, byte[] key)
    {
        return bytesToBase64String(encrypt(data, key));
    }

    @Override
    public String encryptToBase64String(String data, byte[] key)
    {
        return encryptToBase64String(data, CHARSET_UTF8, key);
    }

    @Override
    public String encryptToBase64String(String data, Charset charset, byte[] key)
    {
        return bytesToBase64String(encrypt(stringToBytes(data, charset), key), charset);
    }

    @Override
    public String encryptToHexString(byte[] data, byte[] key)
    {
        return bytesToHexString(encrypt(data, key));
    }

    @Override
    public String encryptToHexString(String data, byte[] key)
    {
        return encryptToHexString(data, CHARSET_UTF8, key);
    }

    @Override
    public String encryptToHexString(String data, Charset charset, byte[] key)
    {
        return encryptToHexString(stringToBytes(data, charset), key);
    }
}
