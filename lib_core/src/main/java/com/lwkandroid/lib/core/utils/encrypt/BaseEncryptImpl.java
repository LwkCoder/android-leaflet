package com.lwkandroid.lib.core.utils.encrypt;

import com.lwkandroid.lib.core.utils.common.StringUtils;
import com.lwkandroid.lib.core.utils.encode.EncodeUtils;

import java.nio.charset.Charset;

/**
 * Description: 加密类基类
 *
 * @author LWK
 * @date 2019/4/26
 */
public class BaseEncryptImpl implements IBaseEncryption
{
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    @Override
    public String bytesToString(byte[] data)
    {
        return bytesToString(data, CHARSET_UTF8);
    }

    @Override
    public String bytesToString(byte[] data, Charset charset)
    {
        if (data == null || data.length <= 0)
        {
            return "";
        }
        return new String(data, charset);
    }

    @Override
    public String bytesToHexString(byte[] data)
    {
        if (data == null || data.length <= 0)
        {
            return "";
        }
        int len = data.length;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++)
        {
            ret[j++] = HEX_DIGITS[data[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[data[i] & 0x0f];
        }
        return new String(ret);
    }

    @Override
    public byte[] bytesToBase64(byte[] data)
    {
        return EncodeUtils.base64().encode(data);
    }

    @Override
    public String bytesToBase64String(byte[] data)
    {
        return bytesToBase64String(data, CHARSET_UTF8);
    }

    @Override
    public String bytesToBase64String(byte[] data, Charset charset)
    {
        return new String(bytesToBase64(data), charset);
    }

    @Override
    public byte[] stringToBytes(String string)
    {
        return stringToBytes(string, CHARSET_UTF8);
    }

    @Override
    public byte[] stringToBytes(String string, Charset charset)
    {
        if (StringUtils.isEmpty(string))
        {
            return null;
        }
        return string.getBytes(charset);
    }

    @Override
    public byte[] base64ToBytes(byte[] base64Bytes)
    {
        return EncodeUtils.base64().decode(base64Bytes);
    }

    @Override
    public byte[] base64StringToBytes(String base64String)
    {
        return base64StringToBytes(base64String, CHARSET_UTF8);
    }

    @Override
    public byte[] base64StringToBytes(String base64String, Charset charset)
    {
        return base64ToBytes(base64String.getBytes(charset));
    }

    @Override
    public byte[] hexStringToBytes(String hexString)
    {
        if (StringUtils.isEmpty(hexString))
        {
            return null;
        }
        int len = hexString.length();
        if (len % 2 != 0)
        {
            hexString = HEX_DIGITS[0] + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2)
        {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(final char hexChar)
    {
        if (hexChar >= HEX_DIGITS[0] && hexChar <= HEX_DIGITS[9])
        {
            return hexChar - HEX_DIGITS[0];
        } else if (hexChar >= HEX_DIGITS[10] && hexChar <= HEX_DIGITS[15])
        {
            return hexChar - HEX_DIGITS[10] + 10;
        } else
        {
            throw new IllegalArgumentException("Fail to decrypt hex char:" + hexChar + " is not the hex char which should be one of [0-9][A-F]");
        }
    }

}
