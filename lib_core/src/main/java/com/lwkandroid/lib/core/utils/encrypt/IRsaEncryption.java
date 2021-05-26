package com.lwkandroid.lib.core.utils.encrypt;

import android.util.Pair;

import java.nio.charset.Charset;

/**
 * Description:非对称加密接口
 *
 * @author LWK
 * @date 2019/4/28
 */
public interface IRsaEncryption
{
    /**
     * RSA加密/解密模版
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 填充模式
     * @param isEncrypt      是否加密
     * @return 结果
     */
    byte[] rsaTemplate(byte[] data,
                       byte[] key,
                       boolean isPublicKey,
                       String transformation,
                       boolean isEncrypt);

    /**
     * 加密算法，使用默认填充模式
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    byte[] encrypt(byte[] data, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，使用默认填充模式
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] encrypt(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转字符串
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToString(byte[] data, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToString(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转字符串
     *
     * @param data        待加密数据
     * @param charsets    字符集
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToString(byte[] data, Charset charsets, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转字符串
     *
     * @param data           待加密数据
     * @param charsets       字符集
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToString(byte[] data, Charset charsets, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转字符串
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToString(String data, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToString(String data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转字符串
     *
     * @param data        待加密数据
     * @param charsets    字符集
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToString(String data, Charset charsets, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转字符串
     *
     * @param data           待加密数据
     * @param charsets       字符集
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToString(String data, Charset charsets, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    byte[] encryptToBase64(byte[] data, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] encryptToBase64(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    byte[] encryptToBase64(String data, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] encryptToBase64(String data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data        待加密数据
     * @param charset     字符集
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    byte[] encryptToBase64(String data, Charset charset, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data           待加密数据
     * @param charset        字符集
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] encryptToBase64(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToBase64String(byte[] data, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data        待加密数据
     * @param charset     字符集
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToBase64String(byte[] data, Charset charset, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToBase64String(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data           待加密数据
     * @param charset        字符集
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToBase64String(byte[] data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToBase64String(String data, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data        待加密数据
     * @param charset     字符集
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToBase64String(String data, Charset charset, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToBase64String(String data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data           待加密数据
     * @param charset        字符集
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToBase64String(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToHexString(byte[] data, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToHexString(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToHexString(String data, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data        待加密数据
     * @param charset     字符集
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String encryptToHexString(String data, Charset charset, byte[] key, boolean isPublicKey);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToHexString(String data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data           待加密数据
     * @param charset        字符集
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String encryptToHexString(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密算法
     *
     * @param data        待解密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    byte[] decrypt(byte[] data, byte[] key, boolean isPublicKey);

    /**
     * 解密算法
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] decrypt(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密字符串数据
     *
     * @param data        待解密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    byte[] decryptString(String data, byte[] key, boolean isPublicKey);

    /**
     * 解密字符串数据
     *
     * @param data        待解密数据
     * @param charset     字符集
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    byte[] decryptString(String data, Charset charset, byte[] key, boolean isPublicKey);

    /**
     * 解密字符串数据
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] decryptString(String data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密字符串数据
     *
     * @param data           待解密数据
     * @param charset        字符集
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] decryptString(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密字符串数据，结果转字符串
     *
     * @param data        待解密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String decryptStringToString(String data, byte[] key, boolean isPublicKey);

    /**
     * 解密字符串数据，结果转字符串
     *
     * @param data        待解密数据
     * @param charset     字符集
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String decryptStringToString(String data, Charset charset, byte[] key, boolean isPublicKey);

    /**
     * 解密字符串数据，结果转字符串
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String decryptStringToString(String data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密字符串数据，结果转字符串
     *
     * @param data           待解密数据
     * @param charset        字符集
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String decryptStringToString(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密Base64数据
     *
     * @param base64Data  待解密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    byte[] decryptBase64(byte[] base64Data, byte[] key, boolean isPublicKey);

    /**
     * 解密Base64数据
     *
     * @param base64Data     待解密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] decryptBase64(byte[] base64Data, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密Base64字符串数据
     *
     * @param base64String 待解密数据
     * @param key          密钥
     * @param isPublicKey  密钥是否为公钥
     * @return 结果
     */
    byte[] decryptBase64String(String base64String, byte[] key, boolean isPublicKey);

    /**
     * 解密Base64字符串数据
     *
     * @param base64String 待解密数据
     * @param charset      字符集
     * @param key          密钥
     * @param isPublicKey  密钥是否为公钥
     * @return 结果
     */
    byte[] decryptBase64String(String base64String, Charset charset, byte[] key, boolean isPublicKey);

    /**
     * 解密Base64字符串数据
     *
     * @param base64String   待解密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] decryptBase64String(String base64String, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密Base64字符串数据
     *
     * @param base64String   待解密数据
     * @param charset        字符集
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] decryptBase64String(String base64String, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密Base64字符串数据,结果转字符串
     *
     * @param base64String 待解密数据
     * @param key          密钥
     * @param isPublicKey  密钥是否为公钥
     * @return 结果
     */
    String decryptBase64StringToString(String base64String, byte[] key, boolean isPublicKey);

    /**
     * 解密Base64字符串数据,结果转字符串
     *
     * @param base64String 待解密数据
     * @param charset      字符集
     * @param key          密钥
     * @param isPublicKey  密钥是否为公钥
     * @return 结果
     */
    String decryptBase64StringToString(String base64String, Charset charset, byte[] key, boolean isPublicKey);

    /**
     * 解密Base64字符串数据,结果转字符串
     *
     * @param base64String   待解密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String decryptBase64StringToString(String base64String, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密Base64字符串数据,结果转字符串
     *
     * @param base64String   待解密数据
     * @param charset        字符集
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String decryptBase64StringToString(String base64String, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密16进制字符串数据
     *
     * @param hexString   待解密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    byte[] decryptHexString(String hexString, byte[] key, boolean isPublicKey);

    /**
     * 解密16进制字符串数据
     *
     * @param hexString      待解密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    byte[] decryptHexString(String hexString, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 解密16进制字符串数据,结果转字符串
     *
     * @param hexString   待解密数据
     * @param key         密钥
     * @param isPublicKey 密钥是否为公钥
     * @return 结果
     */
    String decryptHexStringToString(String hexString, byte[] key, boolean isPublicKey);

    /**
     * 解密16进制字符串数据,结果转字符串
     *
     * @param hexString      待解密数据
     * @param key            密钥
     * @param isPublicKey    密钥是否为公钥
     * @param transformation 自定义填充模式
     * @return 结果
     */
    String decryptHexStringToString(String hexString, byte[] key, boolean isPublicKey, String transformation);

    /**
     * 生成RSA的密钥对，使用默认密钥长度
     *
     * @return 密钥对Pair
     */
    Pair<byte[], byte[]> generateRSAKeys();

    /**
     * 生成RSA的密钥对
     *
     * @param keyBit 密钥长度
     * @return 密钥对Pair
     */
    Pair<byte[], byte[]> generateRSAKeys(int keyBit);

    /**
     * 生成RSA的密钥对，结果转Base64字符串，使用默认密钥长度
     *
     * @return 密钥对Pair
     */
    Pair<String, String> generateRSAKeysAsBase64String();

    /**
     * 生成RSA的密钥对，结果转Base64字符串，使用默认密钥长度
     *
     * @param keyBit 密钥长度
     * @return 密钥对Pair
     */
    Pair<String, String> generateRSAKeysAsBase64String(int keyBit);

    /**
     * 生成RSA的密钥对，结果转16进制字符串，使用默认密钥长度
     *
     * @return 密钥对Pair
     */
    Pair<String, String> generateRSAKeysAsHexString();

    /**
     * 生成RSA的密钥对，结果转16进制字符串，使用默认密钥长度
     *
     * @param keyBit 密钥长度
     * @return 密钥对Pair
     */
    Pair<String, String> generateRSAKeysAsHexString(int keyBit);
}
