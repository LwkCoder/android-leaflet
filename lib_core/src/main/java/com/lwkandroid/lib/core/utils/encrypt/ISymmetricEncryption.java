package com.lwkandroid.lib.core.utils.encrypt;

import java.nio.charset.Charset;

/**
 * Description:对称加密接口
 *
 * @author LWK
 * @date 2019/4/26
 */
public interface ISymmetricEncryption
{
    /**
     * 对称加密/解密模版
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 填充模式
     * @param iv             偏移量
     * @param isEncrypt      是否为加密
     * @return 结果
     */
    byte[] symmetricTemplate(byte[] data,
                             byte[] key,
                             String transformation,
                             byte[] iv,
                             boolean isEncrypt);

    /**
     * 加密算法
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    byte[] encrypt(byte[] data, byte[] key);

    /**
     * 加密算法
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] encrypt(byte[] data, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转字符串
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    String encryptToString(byte[] data, byte[] key);

    /**
     * 加密算法，结果转字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToString(byte[] data, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转字符串
     *
     * @param data     待加密数据
     * @param charsets 字符集
     * @param key      密钥
     * @return 结果
     */
    String encryptToString(byte[] data, Charset charsets, byte[] key);

    /**
     * 加密算法，结果转字符串
     *
     * @param data           待加密数据
     * @param charsets       字符集
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToString(byte[] data, Charset charsets, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转字符串
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    String encryptToString(String data, byte[] key);

    /**
     * 加密算法，结果转字符串
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    String encryptToString(String data, Charset charset, byte[] key);

    /**
     * 加密算法，结果转字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToString(String data, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToString(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    byte[] encryptToBase64(byte[] data, byte[] key);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] encryptToBase64(byte[] data, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    byte[] encryptToBase64(String data, byte[] key);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    byte[] encryptToBase64(String data, Charset charset, byte[] key);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] encryptToBase64(String data, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转Base64编码
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] encryptToBase64(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    String encryptToBase64String(byte[] data, byte[] key);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    String encryptToBase64String(byte[] data, Charset charset, byte[] key);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToBase64String(byte[] data, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToBase64String(byte[] data, Charset charset, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    String encryptToBase64String(String data, byte[] key);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    String encryptToBase64String(String data, Charset charset, byte[] key);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToBase64String(String data, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转Base64编码的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToBase64String(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    String encryptToHexString(byte[] data, byte[] key);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToHexString(byte[] data, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    String encryptToHexString(String data, byte[] key);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 结果
     */
    String encryptToHexString(String data, Charset charset, byte[] key);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToHexString(String data, byte[] key, String transformation, byte[] iv);

    /**
     * 加密算法，结果转16进制的字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String encryptToHexString(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    /**
     * 解密算法
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 结果
     */
    byte[] decrypt(byte[] data, byte[] key);

    /**
     * 解密算法
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] decrypt(byte[] data, byte[] key, String transformation, byte[] iv);

    /**
     * 解密字符串算法
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 结果
     */
    byte[] decryptString(String data, byte[] key);

    /**
     * 解密字符串算法
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 结果
     */
    byte[] decryptString(String data, Charset charset, byte[] key);

    /**
     * 解密字符串算法
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] decryptString(String data, byte[] key, String transformation, byte[] iv);

    /**
     * 解密字符串算法
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] decryptString(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    /**
     * 解密字符串算法，结果转字符串
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 结果
     */
    String decryptStringToString(String data, byte[] key);

    /**
     * 解密字符串算法，结果转字符串
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 结果
     */
    String decryptStringToString(String data, Charset charset, byte[] key);

    /**
     * 解密字符串算法，结果转字符串
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String decryptStringToString(String data, byte[] key, String transformation, byte[] iv);

    /**
     * 解密字符串算法，结果转字符串
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String decryptStringToString(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    /**
     * 解密Base64编码数据
     *
     * @param base64Data 待解密数据
     * @param key        密钥
     * @return 结果
     */
    byte[] decryptBase64(byte[] base64Data, byte[] key);

    /**
     * 解密Base64编码数据
     *
     * @param base64Data     待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] decryptBase64(byte[] base64Data, byte[] key, String transformation, byte[] iv);

    /**
     * 解密Base64编码字符串
     *
     * @param base64String 待解密数据
     * @param key          密钥
     * @return 结果
     */
    byte[] decryptBase64String(String base64String, byte[] key);

    /**
     * 解密Base64编码字符串
     *
     * @param base64String 待解密数据
     * @param key          密钥
     * @return 结果
     */
    byte[] decryptBase64String(String base64String, Charset charset, byte[] key);

    /**
     * 解密Base64编码字符串
     *
     * @param base64String   待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] decryptBase64String(String base64String, byte[] key, String transformation, byte[] iv);

    /**
     * 解密Base64编码字符串
     *
     * @param base64String   待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] decryptBase64String(String base64String, Charset charset, byte[] key, String transformation, byte[] iv);

    /**
     * 解密Base64编码字符串，结果转字符串
     *
     * @param base64String 待解密数据
     * @param key          密钥
     * @return 结果
     */
    String decryptBase64StringToString(String base64String, byte[] key);

    /**
     * 解密Base64编码字符串，结果转字符串
     *
     * @param base64String 待解密数据
     * @param key          密钥
     * @return 结果
     */
    String decryptBase64StringToString(String base64String, Charset charset, byte[] key);

    /**
     * 解密Base64编码字符串，结果转字符串
     *
     * @param base64String   待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String decryptBase64StringToString(String base64String, byte[] key, String transformation, byte[] iv);

    /**
     * 解密Base64编码字符串，结果转字符串
     *
     * @param base64String   待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String decryptBase64StringToString(String base64String, Charset charset, byte[] key, String transformation, byte[] iv);

    /**
     * 解密16进制字符串
     *
     * @param hexString 待解密数据
     * @param key       密钥
     * @return 结果
     */
    byte[] decryptHexString(String hexString, byte[] key);

    /**
     * 解密16进制字符串
     *
     * @param hexString      待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    byte[] decryptHexString(String hexString, byte[] key, String transformation, byte[] iv);

    /**
     * 解密16进制字符串，结果转字符串
     *
     * @param hexString 待解密数据
     * @param key       密钥
     * @return 结果
     */
    String decryptHexStringToString(String hexString, byte[] key);

    /**
     * 解密16进制字符串，结果转字符串
     *
     * @param hexString      待解密数据
     * @param key            密钥
     * @param transformation 加密模式
     * @param iv             填充量
     * @return 结果
     */
    String decryptHexStringToString(String hexString, byte[] key, String transformation, byte[] iv);

    /**
     * 生成密钥，使用默认密钥长度
     *
     * @return 密钥
     */
    byte[] generateKey();

    /**
     * 生成密钥
     *
     * @param keyBit 密钥长度
     * @return 密钥
     */
    byte[] generateKey(int keyBit);

    /**
     * 生成密钥,结果转Base64编码的字符串，使用默认密钥长度
     *
     * @return 密钥
     */
    String generateKeyAsBase64String();

    /**
     * 生成密钥,结果转Base64编码的字符串
     *
     * @param keyBit 密钥长度
     * @return 密钥
     */
    String generateKeyAsBase64String(int keyBit);

    /**
     * 生成密钥,结果转16进制的字符串，使用默认密钥长度
     *
     * @return 密钥
     */
    String generateKeyAsHexString();

    /**
     * 生成密钥,结果转16进制的字符串
     *
     * @param keyBit 密钥长度
     * @return 密钥
     */
    String generateKeyAsHexString(int keyBit);
}
