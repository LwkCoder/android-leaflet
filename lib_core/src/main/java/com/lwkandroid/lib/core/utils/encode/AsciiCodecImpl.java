package com.lwkandroid.lib.core.utils.encode;

/**
 * Description:ASCII编解码实现类
 *
 * @author LWK
 * @date 2019/5/29
 */
public final class AsciiCodecImpl implements IAsciiCodec
{
    private static final String DEFAULT_SPLIT = " ";
    private static final String EMPTY_STRING = "";

    @Override
    public String encode(String data)
    {
        return encode(data, DEFAULT_SPLIT);
    }

    @Override
    public String encode(String data, String splitChar)
    {
        if (data == null || data.length() == 0)
        {
            return EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0, length = data.length(); i < length; i++)
        {
            char c = data.charAt(i);
            builder.append(Integer.valueOf(c))
                    .append(splitChar);
        }

        return builder.toString();
    }

    @Override
    public String decode(String asciiString)
    {
        return decode(asciiString, DEFAULT_SPLIT);
    }

    @Override
    public String decode(String asciiString, String splitChar)
    {
        if (asciiString == null || asciiString.length() == 0)
        {
            return EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        String[] valueChars = asciiString.split(splitChar);
        for (String valueChar : valueChars)
        {
            builder.append(((char) Integer.parseInt(valueChar)));
        }
        return builder.toString();
    }
}
