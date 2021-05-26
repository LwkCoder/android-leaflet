package com.lwkandroid.lib.core.utils.encode;

/**
 * Description: Url编解码方法
 *
 * @author LWK
 * @date 2019/5/27
 */
public interface IUrlCodec
{
    String encode(String data);

    String encode(String data, String charset);

    String decode(String data);

    String decode(String data, String charset);
}
