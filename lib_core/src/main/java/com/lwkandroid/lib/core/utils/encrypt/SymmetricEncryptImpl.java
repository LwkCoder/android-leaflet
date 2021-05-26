package com.lwkandroid.lib.core.utils.encrypt;


import com.lwkandroid.lib.core.utils.encode.EncodeUtils;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Description: 对称加密实现类
 * 有关对称加密知识：https://blog.csdn.net/zhugewendu/article/details/72972518
 *
 * @author LWK
 * @date 2019/4/26
 */
public final class SymmetricEncryptImpl extends BaseEncryptImpl implements ISymmetricEncryption
{
    //AES默认密钥位数
    private static final int AES_DEFAULT_KEY_BIT = 128;
    //DES默认密钥位数
    private static final int DES_DEFAULT_KEY_BIT = 56;
    //3DES默认密钥位数
    private static final int TRIPLE_DES_DEFAULT_KEY_BIT = 168;

    private String mAlgorithm;
    private String mDefaultTransformation;
    private String mDefaultIV;

    SymmetricEncryptImpl(String algorithm, String defaultTransformation, String iv)
    {
        this.mAlgorithm = algorithm;
        this.mDefaultTransformation = defaultTransformation;
        this.mDefaultIV = iv;
    }

    public String getAlgorithm()
    {
        return mAlgorithm;
    }

    public String getDefaultTransformation()
    {
        return mDefaultTransformation;
    }

    public String getDefaultIV()
    {
        return mDefaultIV;
    }

    @Override
    public byte[] symmetricTemplate(byte[] data,
                                    byte[] key,
                                    String transformation,
                                    byte[] iv,
                                    boolean isEncrypt)
    {
        if (data == null || data.length == 0 || key == null || key.length == 0)
        {
            return null;
        }
        try
        {
            SecretKey secretKey;
            if ("DES".equals(mAlgorithm))
            {
                DESKeySpec desKey = new DESKeySpec(key);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(mAlgorithm);
                secretKey = keyFactory.generateSecret(desKey);
            } else
            {
                secretKey = new SecretKeySpec(key, mAlgorithm);
            }
            Cipher cipher = Cipher.getInstance(transformation);
            if (iv == null || iv.length == 0)
            {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
            } else
            {
                AlgorithmParameterSpec params = new IvParameterSpec(iv);
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, params);
            }
            return cipher.doFinal(data);
        } catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] encrypt(byte[] data, byte[] key)
    {
        return encrypt(data, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public byte[] encrypt(byte[] data, byte[] key, String transformation, byte[] iv)
    {
        return symmetricTemplate(data, key, transformation, iv, true);
    }

    @Override
    public String encryptToString(byte[] data, byte[] key)
    {
        return encryptToString(data, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public String encryptToString(byte[] data, byte[] key, String transformation, byte[] iv)
    {
        return encryptToString(data, CHARSET_UTF8, key, transformation, iv);
    }

    @Override
    public String encryptToString(byte[] data, Charset charsets, byte[] key)
    {
        return encryptToString(data, charsets, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public String encryptToString(byte[] data, Charset charsets, byte[] key, String transformation, byte[] iv)
    {
        return bytesToString(encrypt(data, key, transformation, iv), charsets);
    }

    @Override
    public String encryptToString(String data, byte[] key)
    {
        return encryptToString(data, CHARSET_UTF8, key);
    }

    @Override
    public String encryptToString(String data, Charset charset, byte[] key)
    {
        return encryptToString(data, charset, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public String encryptToString(String data, byte[] key, String transformation, byte[] iv)
    {
        return encryptToString(data, CHARSET_UTF8, key, transformation, iv);
    }

    @Override
    public String encryptToString(String data, Charset charset, byte[] key, String transformation, byte[] iv)
    {
        return encryptToString(stringToBytes(data, charset), charset, key, transformation, iv);
    }

    @Override
    public byte[] encryptToBase64(byte[] data, byte[] key)
    {
        return encryptToBase64(data, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public byte[] encryptToBase64(byte[] data, byte[] key, String transformation, byte[] iv)
    {
        return bytesToBase64(encrypt(data, key, transformation, iv));
    }

    @Override
    public byte[] encryptToBase64(String data, byte[] key)
    {
        return encryptToBase64(data, CHARSET_UTF8, key);
    }

    @Override
    public byte[] encryptToBase64(String data, Charset charset, byte[] key)
    {
        return encryptToBase64(data, charset, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public byte[] encryptToBase64(String data, byte[] key, String transformation, byte[] iv)
    {
        return encryptToBase64(data, CHARSET_UTF8, key, transformation, iv);
    }

    @Override
    public byte[] encryptToBase64(String data, Charset charset, byte[] key, String transformation, byte[] iv)
    {
        return encryptToBase64(stringToBytes(data, charset), key, transformation, iv);
    }

    @Override
    public String encryptToBase64String(byte[] data, byte[] key)
    {
        return encryptToBase64String(data, CHARSET_UTF8, key);
    }

    @Override
    public String encryptToBase64String(byte[] data, Charset charset, byte[] key)
    {
        return encryptToBase64String(data, CHARSET_UTF8, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public String encryptToBase64String(byte[] data, byte[] key, String transformation, byte[] iv)
    {
        return encryptToBase64String(data, CHARSET_UTF8, key, transformation, iv);
    }

    @Override
    public String encryptToBase64String(byte[] data, Charset charset, byte[] key, String transformation, byte[] iv)
    {
        return bytesToString(encryptToBase64(data, key, transformation, iv), charset);
    }

    @Override
    public String encryptToBase64String(String data, byte[] key)
    {
        return encryptToBase64String(data, CHARSET_UTF8, key);
    }

    @Override
    public String encryptToBase64String(String data, Charset charset, byte[] key)
    {
        return encryptToBase64String(data, charset, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public String encryptToBase64String(String data, byte[] key, String transformation, byte[] iv)
    {
        return encryptToBase64String(data, CHARSET_UTF8, key, transformation, iv);
    }

    @Override
    public String encryptToBase64String(String data, Charset charset, byte[] key, String transformation, byte[] iv)
    {
        return encryptToBase64String(stringToBytes(data, charset), charset, key, transformation, iv);
    }

    @Override
    public String encryptToHexString(byte[] data, byte[] key)
    {
        return encryptToHexString(data, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public String encryptToHexString(byte[] data, byte[] key, String transformation, byte[] iv)
    {
        return bytesToHexString(symmetricTemplate(data, key, transformation, iv, true));
    }

    @Override
    public String encryptToHexString(String data, byte[] key)
    {
        return encryptToHexString(data, CHARSET_UTF8, key);
    }

    @Override
    public String encryptToHexString(String data, Charset charset, byte[] key)
    {
        return encryptToHexString(data, charset, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public String encryptToHexString(String data, byte[] key, String transformation, byte[] iv)
    {
        return encryptToHexString(data, CHARSET_UTF8, key, transformation, iv);
    }

    @Override
    public String encryptToHexString(String data, Charset charset, byte[] key, String transformation, byte[] iv)
    {
        return encryptToHexString(stringToBytes(data, charset), key, transformation, iv);
    }

    @Override
    public byte[] decrypt(byte[] data, byte[] key)
    {
        return decrypt(data, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public byte[] decrypt(byte[] data, byte[] key, String transformation, byte[] iv)
    {
        return symmetricTemplate(data, key, transformation, iv, false);
    }

    @Override
    public byte[] decryptString(String data, byte[] key)
    {
        return decryptString(data, CHARSET_UTF8, key);
    }

    @Override
    public byte[] decryptString(String data, Charset charset, byte[] key)
    {
        return decryptString(data, CHARSET_UTF8, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public byte[] decryptString(String data, byte[] key, String transformation, byte[] iv)
    {
        return decryptString(data, CHARSET_UTF8, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public byte[] decryptString(String data, Charset charset, byte[] key, String transformation, byte[] iv)
    {
        return decrypt(stringToBytes(data, charset), key, transformation, iv);
    }

    @Override
    public String decryptStringToString(String data, byte[] key)
    {
        return decryptStringToString(data, CHARSET_UTF8, key);
    }

    @Override
    public String decryptStringToString(String data, Charset charset, byte[] key)
    {
        return decryptStringToString(data, charset, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public String decryptStringToString(String data, byte[] key, String transformation, byte[] iv)
    {
        return decryptStringToString(data, CHARSET_UTF8, key, transformation, iv);
    }

    @Override
    public String decryptStringToString(String data, Charset charset, byte[] key, String transformation, byte[] iv)
    {
        return bytesToString(decryptString(data, charset, key, transformation, iv), charset);
    }

    @Override
    public byte[] decryptBase64(byte[] base64Data, byte[] key)
    {
        return decryptBase64(base64Data, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public byte[] decryptBase64(byte[] base64Data, byte[] key, String transformation, byte[] iv)
    {
        return decrypt(base64ToBytes(base64Data), key, transformation, iv);
    }

    @Override
    public byte[] decryptBase64String(String base64Data, byte[] key)
    {
        return decryptBase64String(base64Data, CHARSET_UTF8, key);
    }

    @Override
    public byte[] decryptBase64String(String base64String, Charset charset, byte[] key)
    {
        return decryptBase64String(base64String, charset, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public byte[] decryptBase64String(String base64String, byte[] key, String transformation, byte[] iv)
    {
        return decryptBase64String(base64String, CHARSET_UTF8, key, transformation, iv);
    }

    @Override
    public byte[] decryptBase64String(String base64String, Charset charset, byte[] key, String transformation, byte[] iv)
    {
        return decryptBase64(stringToBytes(base64String, charset), key, transformation, iv);
    }

    @Override
    public String decryptBase64StringToString(String base64String, byte[] key)
    {
        return decryptBase64StringToString(base64String, CHARSET_UTF8, key);
    }

    @Override
    public String decryptBase64StringToString(String base64String, Charset charset, byte[] key)
    {
        return decryptBase64StringToString(base64String, charset, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public String decryptBase64StringToString(String base64String, byte[] key, String transformation, byte[] iv)
    {
        return decryptBase64StringToString(base64String, CHARSET_UTF8, key, transformation, iv);
    }

    @Override
    public String decryptBase64StringToString(String base64String, Charset charset, byte[] key, String transformation, byte[] iv)
    {
        return bytesToString(decryptBase64String(base64String, charset, key, transformation, iv), charset);
    }

    @Override
    public byte[] decryptHexString(String hexString, byte[] key)
    {
        return decryptHexString(hexString, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public byte[] decryptHexString(String hexString, byte[] key, String transformation, byte[] iv)
    {
        return decrypt(hexStringToBytes(hexString), key, transformation, iv);
    }

    @Override
    public String decryptHexStringToString(String hexString, byte[] key)
    {
        return decryptHexStringToString(hexString, key, mDefaultTransformation, stringToBytes(mDefaultIV));
    }

    @Override
    public String decryptHexStringToString(String hexString, byte[] key, String transformation, byte[] iv)
    {
        return bytesToString(decryptHexString(hexString, key, transformation, iv));
    }

    @Override
    public byte[] generateKey()
    {
        return generateKey(getKeyBit(mAlgorithm));
    }

    @Override
    public byte[] generateKey(int keyBit)
    {
        try
        {
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            String key = EncodeUtils.hex().encode(uuid);
            KeyGenerator kg = KeyGenerator.getInstance(mAlgorithm);
            kg.init(keyBit, new SecureRandom(key.getBytes(CHARSET_UTF8)));
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), mAlgorithm);
            return keySpec.getEncoded();
        } catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String generateKeyAsBase64String()
    {
        return generateKeyAsBase64String(getKeyBit(mAlgorithm));
    }

    @Override
    public String generateKeyAsBase64String(int keyBit)
    {
        return bytesToBase64String(generateKey(keyBit), CHARSET_UTF8);
    }

    @Override
    public String generateKeyAsHexString()
    {
        return generateKeyAsHexString(getKeyBit(mAlgorithm));
    }

    @Override
    public String generateKeyAsHexString(int keyBit)
    {
        return bytesToHexString(generateKey(keyBit));
    }

    private int getKeyBit(String algorithm)
    {
        if ("AES".equals(algorithm))
        {
            return AES_DEFAULT_KEY_BIT;
        } else if ("DES".equals(algorithm))
        {
            return DES_DEFAULT_KEY_BIT;
        } else if ("DESede".equals(algorithm))
        {
            return TRIPLE_DES_DEFAULT_KEY_BIT;
        }
        return AES_DEFAULT_KEY_BIT;
    }
}
