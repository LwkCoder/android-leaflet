package com.lwkandroid.lib.core.utils.encode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:Unicode编解码方法实现类
 *
 * @author LWK
 * @date 2020/4/10
 */
public class UnicodeCodecImpl implements IUnicodeCodec
{
    private static final String DEFAULT_SPLIT = " ";
    private static final String EMPTY_STRING = "";
    private static final String UNICODE_ENCODE_PREFIX = "\\u";
    private static final String UNICODE_DECODE_PREFIX = "\\\\u";
    private static final String UNICODE_ENCODE_TEMPLATE = "0000";

    @Override
    public String encode(String data)
    {
        return encode(data, DEFAULT_SPLIT);
    }

    @Override
    public String encode(String data, String splitStr)
    {
        if (data == null || data.length() == 0)
        {
            return EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0, length = data.length(); i < length; i++)
        {
            String hex = Integer.toHexString(data.charAt(i));
            //不够四位进行补0
            if (hex.length() < 4)
            {
                int index = UNICODE_ENCODE_TEMPLATE.length() - hex.length();
                hex = UNICODE_ENCODE_TEMPLATE.substring(0, index) + hex;
            }
            builder.append(UNICODE_ENCODE_PREFIX)
                    .append(hex)
                    .append(i != length - 1 ? splitStr : EMPTY_STRING);
        }
        return builder.toString();
    }

    @Override
    public String decode(String data)
    {
        return decode(data, DEFAULT_SPLIT);
    }

    @Override
    public String decode(String data, String splitStr)
    {
        if (data == null || data.length() == 0)
        {
            return EMPTY_STRING;
        }
        data = data.replaceAll(splitStr, EMPTY_STRING);
        StringBuilder builder = new StringBuilder();
        String[] hexArray = data.split(UNICODE_DECODE_PREFIX);
        for (int i = 1, length = hexArray.length; i < length; i++)
        {
            int c = Integer.parseInt(hexArray[i], 16);
            builder.append((char) c);
        }
        return builder.toString();
    }

    @Override
    public String encodeExceptCharacter(String data)
    {
        char[] charArray = data.toCharArray();

        StringBuilder builder = new StringBuilder();
        for (int i = 0, length = data.length(); i < length; i++)
        {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(charArray[i]);
            if (ub == Character.UnicodeBlock.BASIC_LATIN)
            {
                //英文及数字等
                builder.append(charArray[i]);
            } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
            {
                //全角半角字符
                int j = (int) charArray[i] - 65248;
                builder.append((char) j);
            } else
            {
                //汉字
                builder.append(UNICODE_ENCODE_PREFIX)
                        .append(Integer.toHexString(charArray[i]));
            }
        }
        return builder.toString();
    }

    @Override
    public String decodeExceptCharacter(String data)
    {
        int length = data.length();
        int count = 0;
        //正则匹配条件，可匹配“\\u”1到4位，一般是4位可直接使用 String regex = "\\\\u[a-f0-9A-F]{4}";
        String regex = "\\\\u[a-f0-9A-F]{1,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        StringBuilder sb = new StringBuilder();
        while (matcher.find())
        {
            //原本的Unicode字符
            String oldChar = matcher.group();
            //转换为普通字符
            String newChar = decode(oldChar, EMPTY_STRING);
            // 在遇见重复出现的unicode代码的时候会造成从源字符串获取非unicode编码字符的时候截取索引越界等
            int index = matcher.start();
            //添加前面不是unicode的字符
            sb.append(data.substring(count, index));
            //添加转换后的字符
            sb.append(newChar);
            //统计下标移动的位置
            count = index + oldChar.length();
        }
        //添加末尾不是Unicode的字符
        sb.append(data.substring(count, length));
        return sb.toString();
    }
}
