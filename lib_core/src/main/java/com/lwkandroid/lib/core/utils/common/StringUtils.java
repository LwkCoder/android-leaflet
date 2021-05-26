package com.lwkandroid.lib.core.utils.common;

import com.lwkandroid.lib.core.utils.constants.RegexConstants;

import java.util.IllegalFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;

/**
 * 字符串相关工具类
 *
 * @author LWK
 */
public final class StringUtils
{
    private StringUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s)
    {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否不为null或者长度大于0
     *
     * @param s 待校验字符串
     * @return {@code true}: 不为空<br> {@code false}: 空
     */
    public static boolean isNotEmpty(CharSequence s)
    {
        return s != null && s.length() > 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isTrimEmpty(String s)
    {
        return s == null || s.trim().length() == 0;
    }

    /**
     * 判断字符串是否不为null或不全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: 不为null且不全空格<br> {@code false}: null或全空格
     */
    public static boolean isTrimNotEmpty(String s)
    {
        return s != null && s.trim().length() > 0;
    }

    /**
     * 判断字符串是否为null或全为空白字符(空白符包含：空格、tab键、换行符)
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空白字符<br> {@code false}: 不为null且不全空白字符
     */
    public static boolean isSpace(String s)
    {
        if (s == null)
        {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i)
        {
            if (!Character.isWhitespace(s.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean isEquals(CharSequence a, CharSequence b)
    {
        if (a == b)
        {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length())
        {
            if (a instanceof String && b instanceof String)
            {
                return a.equals(b);
            } else
            {
                for (int i = 0; i < length; i++)
                {
                    if (a.charAt(i) != b.charAt(i))
                    {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean isEqualsIgnoreCase(String a, String b)
    {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s)
    {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s)
    {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0)))
        {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s)
    {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0)))
        {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s)
    {
        int len = length(s);
        if (len <= 1)
        {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i)
        {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toHalfWidthCharacters(String s)
    {
        if (isEmpty(s))
        {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++)
        {
            if (chars[i] == 12288)
            {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374)
            {
                chars[i] = (char) (chars[i] - 65248);
            } else
            {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toFullWidthCharacters(String s)
    {
        if (isEmpty(s))
        {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++)
        {
            if (chars[i] == ' ')
            {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126)
            {
                chars[i] = (char) (chars[i] + 65248);
            } else
            {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 去掉所有空白字符
     *
     * @param str
     * @return
     */
    public static String replaceAllBlank(String str)
    {
        String dest = "";
        if (str != null)
        {
            Matcher m = Pattern.compile(RegexConstants.REGEX_BLANK_LINE).matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * Format the string.
     *
     * @param str  The string.
     * @param args The args.
     * @return a formatted string.
     */
    public static String format(@Nullable String str, Object... args)
    {
        String text = str;
        if (text != null)
        {
            if (args != null && args.length > 0)
            {
                try
                {
                    text = String.format(str, args);
                } catch (IllegalFormatException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return text;
    }
}