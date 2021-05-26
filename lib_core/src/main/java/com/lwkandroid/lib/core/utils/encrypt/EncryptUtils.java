package com.lwkandroid.lib.core.utils.encrypt;

/**
 * Description:加密算法入口
 *
 * @author LWK
 * @date 2019/4/26
 */
public final class EncryptUtils
{
    private EncryptUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 对称加密默认Transformation
     * ECB模式下不用偏移量
     * <p>
     * 算法/模式/填充                16字节加密后数据长度        不满16字节加密后长度
     * AES/CBC/NoPadding             16                          不支持
     * AES/CBC/PKCS5Padding          32                          16
     * AES/CBC/ISO10126Padding       32                          16
     * AES/CFB/NoPadding             16                          原始数据长度
     * AES/CFB/PKCS5Padding          32                          16
     * AES/CFB/ISO10126Padding       32                          16
     * AES/ECB/NoPadding             16                          不支持
     * AES/ECB/PKCS5Padding          32                          16
     * AES/ECB/ISO10126Padding       32                          16
     * AES/OFB/NoPadding             16                          原始数据长度
     * AES/OFB/PKCS5Padding          32                          16
     * AES/OFB/ISO10126Padding       32                          16
     * AES/PCBC/NoPadding            16                          不支持
     * AES/PCBC/PKCS5Padding         32                          16
     * AES/PCBC/ISO10126Padding      32                          16
     */
    public static final String AES_DEFAULT_TRANSFORMATION = "AES/CBC/PKCS7Padding";
    public static final String DES_DEFAULT_TRANSFORMATION = "DES/CBC/PKCS7Padding";
    public static final String TRIPLE_DES_DEFAULT_TRANSFORMATION = "DESede/CBC/PKCS7Padding";
    //对称加密默认偏移量
    public static final String DES_DEFAULT_IV = "12345678";
    public static final String TRIPLE_DES_DEFAULT_IV = "12345678";
    public static final String AES_DEFAULT_IV = "123456789abcdefg";
    //RSA加密默认Transformation
    public static final String RSA_DEFAULT_TRANSFORMATION = "RSA/NONE/PKCS1Padding";

    /**
     * MD2加密
     * 似乎无效，会报warning
     * java.security.NoSuchAlgorithmException: MD2 MessageDigest not available
     */
    public static IHashEncryption md2()
    {
        return new HashEncryptImpl("MD2");
    }

    /**
     * MD5加密
     */
    public static IHashEncryption md5()
    {
        return new HashEncryptImpl("MD5");
    }

    /**
     * SHA1加密
     */
    public static IHashEncryption sha1()
    {
        return new HashEncryptImpl("SHA-1");
    }

    /**
     * SHA224加密
     */
    public static IHashEncryption sha224()
    {
        return new HashEncryptImpl("SHA-224");
    }

    /**
     * SHA256加密
     */
    public static IHashEncryption sha256()
    {
        return new HashEncryptImpl("SHA-256");
    }

    /**
     * SHA384加密
     */
    public static IHashEncryption sha384()
    {
        return new HashEncryptImpl("SHA-384");
    }

    /**
     * SHA512加密
     */
    public static IHashEncryption sha512()
    {
        return new HashEncryptImpl("SHA-512");
    }

    /**
     * HmacMD5加密
     */
    public static IHmacEncryption hmac_md5()
    {
        return new HmacEncryptImpl("HmacMD5");
    }

    /**
     * HmacSHA1加密
     */
    public static IHmacEncryption hmac_sha1()
    {
        return new HmacEncryptImpl("HmacSHA1");
    }

    /**
     * HmacSHA224加密
     */
    public static IHmacEncryption hmac_sha224()
    {
        return new HmacEncryptImpl("HmacSHA224");
    }

    /**
     * HmacSHA256加密
     */
    public static IHmacEncryption hmac_sha256()
    {
        return new HmacEncryptImpl("HmacSHA256");
    }

    /**
     * HmacSHA384加密
     */
    public static IHmacEncryption hmac_sha384()
    {
        return new HmacEncryptImpl("HmacSHA384");
    }

    /**
     * HmacSHA512加密
     */
    public static IHmacEncryption hmac_sha512()
    {
        return new HmacEncryptImpl("HmacSHA512");
    }

    /**
     * AES加密
     * 工作模式为："AES/CBC/PKCS7Padding"
     * 偏移量为："123456789abcdefg"
     */
    public static ISymmetricEncryption aes()
    {
        return new SymmetricEncryptImpl("AES", AES_DEFAULT_TRANSFORMATION, AES_DEFAULT_IV);
    }

    /**
     * AES加密
     *
     * @param transformation 工作模式
     * @param iv             偏移量
     */
    public static ISymmetricEncryption aes(String transformation, String iv)
    {
        return new SymmetricEncryptImpl("AES", transformation, iv);
    }

    /**
     * AES加密
     * 工作模式为："DES/CBC/PKCS7Padding"
     * 偏移量为："12345678"
     */
    public static ISymmetricEncryption des()
    {
        return new SymmetricEncryptImpl("DES", DES_DEFAULT_TRANSFORMATION, DES_DEFAULT_IV);
    }

    /**
     * DES加密
     *
     * @param transformation 工作模式
     * @param iv             偏移量
     */
    public static ISymmetricEncryption des(String transformation, String iv)
    {
        return new SymmetricEncryptImpl("DES", transformation, iv);
    }

    /**
     * 3DES加密
     * 工作模式为："DESede/CBC/PKCS7Padding"
     * 偏移量为："12345678"
     */
    public static ISymmetricEncryption tripleDes()
    {
        return new SymmetricEncryptImpl("DESede", TRIPLE_DES_DEFAULT_TRANSFORMATION, TRIPLE_DES_DEFAULT_IV);
    }

    /**
     * 3DES加密
     *
     * @param transformation 工作模式
     * @param iv             偏移量
     */
    public static ISymmetricEncryption tripleDes(String transformation, String iv)
    {
        return new SymmetricEncryptImpl("DESede", transformation, iv);
    }

    /**
     * RSA加密
     * 工作模式为："RSA/NONE/PKCS1Padding"
     */
    public static IRsaEncryption rsa()
    {
        return new RsaEncryptionImpl(RSA_DEFAULT_TRANSFORMATION);
    }

    /**
     * RSA加密
     *
     * @param transformation 工作模式
     */
    public static IRsaEncryption rsa(String transformation)
    {
        return new RsaEncryptionImpl(transformation);
    }
}
