package com.lwkandroid.lib.core.utils.encrypt;

import android.util.Pair;

import com.lwkandroid.lib.core.log.KLog;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Description:RSA非对称加密实现类
 *
 * @author LWK
 * @date 2019/4/28
 */
public final class RsaEncryptionImpl extends BaseEncryptImpl implements IRsaEncryption
{
    //生成RSA密钥位数
    private static final int RSA_DEFAULT_KEY_BIT = 1024;
    //RSA最大加密明文大小
    private static final int RSA_MAX_ENCRYPT_BLOCK = 117;
    //RSA最大解密密文大小
    private static final int RSA_MAX_DECRYPT_BLOCK = 128;

    private String mDefaultTransformation;

    RsaEncryptionImpl(String transformation)
    {
        this.mDefaultTransformation = transformation;
    }

    @Override
    public byte[] rsaTemplate(byte[] data, byte[] key, boolean isPublicKey, String transformation, boolean isEncrypt)
    {
        if (data == null || data.length == 0 || key == null || key.length == 0)
        {
            return null;
        }
        try
        {
            Key rsaKey;
            if (isPublicKey)
            {
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
                rsaKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
            } else
            {
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
                rsaKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
            }
            if (rsaKey == null)
            {
                KLog.e("Can not parse RSA key !");
                return null;
            }
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, rsaKey);
            int len = data.length;
            int maxLen = isEncrypt ? RSA_MAX_ENCRYPT_BLOCK : RSA_MAX_DECRYPT_BLOCK;
            int count = len / maxLen;
            if (count > 0)
            {
                byte[] ret = new byte[0];
                byte[] buff = new byte[maxLen];
                int index = 0;
                for (int i = 0; i < count; i++)
                {
                    System.arraycopy(data, index, buff, 0, maxLen);
                    ret = joins(ret, cipher.doFinal(buff));
                    index += maxLen;
                }
                if (index != len)
                {
                    int restLen = len - index;
                    buff = new byte[restLen];
                    System.arraycopy(data, index, buff, 0, restLen);
                    ret = joins(ret, cipher.doFinal(buff));
                }
                return ret;
            } else
            {
                return cipher.doFinal(data);
            }
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        } catch (InvalidKeyException e)
        {
            e.printStackTrace();
        } catch (BadPaddingException e)
        {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        } catch (InvalidKeySpecException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] encrypt(byte[] data, byte[] key, boolean isPublicKey)
    {
        return encrypt(data, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public byte[] encrypt(byte[] data, byte[] key, boolean isPublicKey, String transformation)
    {
        return rsaTemplate(data, key, isPublicKey, transformation, true);
    }

    @Override
    public String encryptToString(byte[] data, byte[] key, boolean isPublicKey)
    {
        return encryptToString(data, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String encryptToString(byte[] data, byte[] key, boolean isPublicKey, String transformation)
    {
        return encryptToString(data, CHARSET_UTF8, key, isPublicKey, transformation);
    }

    @Override
    public String encryptToString(byte[] data, Charset charsets, byte[] key, boolean isPublicKey)
    {
        return encryptToString(data, charsets, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String encryptToString(byte[] data, Charset charsets, byte[] key, boolean isPublicKey, String transformation)
    {
        return bytesToString(encrypt(data, key, false, transformation), charsets);
    }

    @Override
    public String encryptToString(String data, byte[] key, boolean isPublicKey)
    {
        return encryptToString(data, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String encryptToString(String data, byte[] key, boolean isPublicKey, String transformation)
    {
        return encryptToString(data, CHARSET_UTF8, key, isPublicKey, transformation);
    }

    @Override
    public String encryptToString(String data, Charset charsets, byte[] key, boolean isPublicKey)
    {
        return encryptToString(data, charsets, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String encryptToString(String data, Charset charsets, byte[] key, boolean isPublicKey, String transformation)
    {
        return encryptToString(stringToBytes(data, charsets), charsets, key, isPublicKey, transformation);
    }

    @Override
    public byte[] encryptToBase64(byte[] data, byte[] key, boolean isPublicKey)
    {
        return encryptToBase64(data, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public byte[] encryptToBase64(byte[] data, byte[] key, boolean isPublicKey, String transformation)
    {
        return bytesToBase64(encrypt(data, key, isPublicKey, transformation));
    }

    @Override
    public byte[] encryptToBase64(String data, byte[] key, boolean isPublicKey)
    {
        return encryptToBase64(data, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public byte[] encryptToBase64(String data, byte[] key, boolean isPublicKey, String transformation)
    {
        return encryptToBase64(data, CHARSET_UTF8, key, isPublicKey, transformation);
    }

    @Override
    public byte[] encryptToBase64(String data, Charset charset, byte[] key, boolean isPublicKey)
    {
        return encryptToBase64(data, charset, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public byte[] encryptToBase64(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation)
    {
        return encryptToBase64(stringToBytes(data, charset), key, isPublicKey, transformation);
    }

    @Override
    public String encryptToBase64String(byte[] data, byte[] key, boolean isPublicKey)
    {
        return encryptToBase64String(data, CHARSET_UTF8, key, isPublicKey);
    }

    @Override
    public String encryptToBase64String(byte[] data, Charset charset, byte[] key, boolean isPublicKey)
    {
        return encryptToBase64String(data, charset, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String encryptToBase64String(byte[] data, byte[] key, boolean isPublicKey, String transformation)
    {
        return encryptToBase64String(data, CHARSET_UTF8, key, isPublicKey, transformation);
    }

    @Override
    public String encryptToBase64String(byte[] data, Charset charset, byte[] key, boolean isPublicKey, String transformation)
    {
        return bytesToString(encryptToBase64(data, key, isPublicKey, transformation), charset);
    }

    @Override
    public String encryptToBase64String(String data, byte[] key, boolean isPublicKey)
    {
        return encryptToBase64String(data, CHARSET_UTF8, key, isPublicKey);
    }

    @Override
    public String encryptToBase64String(String data, Charset charset, byte[] key, boolean isPublicKey)
    {
        return encryptToBase64String(data, charset, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String encryptToBase64String(String data, byte[] key, boolean isPublicKey, String transformation)
    {
        return encryptToBase64String(data, CHARSET_UTF8, key, isPublicKey, transformation);
    }

    @Override
    public String encryptToBase64String(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation)
    {
        return encryptToBase64String(stringToBytes(data, charset), charset, key, isPublicKey, transformation);
    }

    @Override
    public String encryptToHexString(byte[] data, byte[] key, boolean isPublicKey)
    {
        return encryptToHexString(data, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String encryptToHexString(byte[] data, byte[] key, boolean isPublicKey, String transformation)
    {
        return bytesToHexString(encrypt(data, key, isPublicKey, transformation));
    }

    @Override
    public String encryptToHexString(String data, byte[] key, boolean isPublicKey)
    {
        return encryptToHexString(data, CHARSET_UTF8, key, isPublicKey);
    }

    @Override
    public String encryptToHexString(String data, Charset charset, byte[] key, boolean isPublicKey)
    {
        return encryptToHexString(data, CHARSET_UTF8, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String encryptToHexString(String data, byte[] key, boolean isPublicKey, String transformation)
    {
        return encryptToHexString(data, CHARSET_UTF8, key, isPublicKey, transformation);
    }

    @Override
    public String encryptToHexString(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation)
    {
        return encryptToHexString(stringToBytes(data, charset), key, isPublicKey, transformation);
    }

    @Override
    public byte[] decrypt(byte[] data, byte[] key, boolean isPublicKey)
    {
        return decrypt(data, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public byte[] decrypt(byte[] data, byte[] key, boolean isPublicKey, String transformation)
    {
        return rsaTemplate(data, key, isPublicKey, transformation, false);
    }

    @Override
    public byte[] decryptString(String data, byte[] key, boolean isPublicKey)
    {
        return decryptString(data, CHARSET_UTF8, key, isPublicKey);
    }

    @Override
    public byte[] decryptString(String data, Charset charset, byte[] key, boolean isPublicKey)
    {
        return decryptString(data, charset, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public byte[] decryptString(String data, byte[] key, boolean isPublicKey, String transformation)
    {
        return decryptString(data, CHARSET_UTF8, key, isPublicKey, transformation);
    }

    @Override
    public byte[] decryptString(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation)
    {
        return decrypt(stringToBytes(data, charset), key, isPublicKey, transformation);
    }

    @Override
    public String decryptStringToString(String data, byte[] key, boolean isPublicKey)
    {
        return decryptStringToString(data, CHARSET_UTF8, key, isPublicKey);
    }

    @Override
    public String decryptStringToString(String data, Charset charset, byte[] key, boolean isPublicKey)
    {
        return decryptStringToString(data, charset, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String decryptStringToString(String data, byte[] key, boolean isPublicKey, String transformation)
    {
        return decryptStringToString(data, CHARSET_UTF8, key, isPublicKey, transformation);
    }

    @Override
    public String decryptStringToString(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation)
    {
        return bytesToString(decryptString(data, charset, key, isPublicKey, transformation), charset);
    }

    @Override
    public byte[] decryptBase64(byte[] base64Data, byte[] key, boolean isPublicKey)
    {
        return decryptBase64(base64Data, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public byte[] decryptBase64(byte[] base64Data, byte[] key, boolean isPublicKey, String transformation)
    {
        return decrypt(base64ToBytes(base64Data), key, isPublicKey, transformation);
    }

    @Override
    public byte[] decryptBase64String(String base64String, byte[] key, boolean isPublicKey)
    {
        return decryptBase64String(base64String, CHARSET_UTF8, key, isPublicKey);
    }

    @Override
    public byte[] decryptBase64String(String base64String, Charset charset, byte[] key, boolean isPublicKey)
    {
        return decryptBase64String(base64String, charset, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public byte[] decryptBase64String(String base64String, byte[] key, boolean isPublicKey, String transformation)
    {
        return decryptBase64String(base64String, CHARSET_UTF8, key, isPublicKey, transformation);
    }

    @Override
    public byte[] decryptBase64String(String base64String, Charset charset, byte[] key, boolean isPublicKey, String transformation)
    {
        return decryptBase64(stringToBytes(base64String, charset), key, isPublicKey, transformation);
    }

    @Override
    public String decryptBase64StringToString(String base64String, byte[] key, boolean isPublicKey)
    {
        return decryptBase64StringToString(base64String, CHARSET_UTF8, key, isPublicKey);
    }

    @Override
    public String decryptBase64StringToString(String base64String, Charset charset, byte[] key, boolean isPublicKey)
    {
        return decryptBase64StringToString(base64String, charset, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String decryptBase64StringToString(String base64String, byte[] key, boolean isPublicKey, String transformation)
    {
        return decryptBase64StringToString(base64String, CHARSET_UTF8, key, isPublicKey, transformation);
    }

    @Override
    public String decryptBase64StringToString(String base64String, Charset charset, byte[] key, boolean isPublicKey, String transformation)
    {
        return bytesToString(decryptBase64String(base64String, charset, key, isPublicKey, transformation), charset);
    }

    @Override
    public byte[] decryptHexString(String hexString, byte[] key, boolean isPublicKey)
    {
        return decryptHexString(hexString, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public byte[] decryptHexString(String hexString, byte[] key, boolean isPublicKey, String transformation)
    {
        return decrypt(hexStringToBytes(hexString), key, isPublicKey, transformation);
    }

    @Override
    public String decryptHexStringToString(String hexString, byte[] key, boolean isPublicKey)
    {
        return decryptHexStringToString(hexString, key, isPublicKey, mDefaultTransformation);
    }

    @Override
    public String decryptHexStringToString(String hexString, byte[] key, boolean isPublicKey, String transformation)
    {
        return bytesToString(decryptHexString(hexString, key, isPublicKey, transformation));
    }

    @Override
    public Pair<byte[], byte[]> generateRSAKeys()
    {
        return generateRSAKeys(RSA_DEFAULT_KEY_BIT);
    }

    @Override
    public Pair<byte[], byte[]> generateRSAKeys(int keyBit)
    {
        try
        {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(keyBit);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            return new Pair<>(publicKey.getEncoded(), privateKey.getEncoded());
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return new Pair<>(null, null);
    }

    @Override
    public Pair<String, String> generateRSAKeysAsBase64String()
    {
        return generateRSAKeysAsBase64String(RSA_DEFAULT_KEY_BIT);
    }

    @Override
    public Pair<String, String> generateRSAKeysAsBase64String(int keyBit)
    {
        Pair<byte[], byte[]> pair = generateRSAKeys(keyBit);
        if (pair.first != null && pair.second != null)
        {
            return new Pair<>(bytesToBase64String(pair.first), bytesToBase64String(pair.second));
        }
        return new Pair<>(null, null);
    }

    @Override
    public Pair<String, String> generateRSAKeysAsHexString()
    {
        return generateRSAKeysAsBase64String(RSA_DEFAULT_KEY_BIT);
    }

    @Override
    public Pair<String, String> generateRSAKeysAsHexString(int keyBit)
    {
        Pair<byte[], byte[]> pair = generateRSAKeys(keyBit);
        if (pair.first != null && pair.second != null)
        {
            return new Pair<>(bytesToHexString(pair.first), bytesToHexString(pair.second));
        }
        return new Pair<>(null, null);
    }

    private static byte[] joins(final byte[] prefix, final byte[] suffix)
    {
        byte[] ret = new byte[prefix.length + suffix.length];
        System.arraycopy(prefix, 0, ret, 0, prefix.length);
        System.arraycopy(suffix, 0, ret, prefix.length, suffix.length);
        return ret;
    }
}
