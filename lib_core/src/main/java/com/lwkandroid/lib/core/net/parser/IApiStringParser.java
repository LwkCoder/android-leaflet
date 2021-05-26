package com.lwkandroid.lib.core.net.parser;

import java.util.List;

/**
 * 将String类型的网络请求结果转换为具体对象的接口
 *
 * @author LWK
 */
public interface IApiStringParser
{
    /**
     * 将结果解析为某一Object类型数据
     *
     * @param data      待解析字符串
     * @param dataClass 对象的Class
     * @param <T>       对象泛型
     * @return
     */
    <T> T parseCustomDataObject(String data, final Class<T> dataClass)  throws Exception;

    /**
     * 将结果解析为{@link com.lwkandroid.lib.core.net.bean.IApiRestfulResult}，并获取Object类型数据
     *
     * @param data      待解析字符串
     * @param dataClass 对象的Class
     * @param <T>       对象泛型
     */
    <T> T parseRestfulDataObject(String data, final Class<T> dataClass) throws Exception;

    /**
     * 将结果解析为某一Object类型集合数据
     *
     * @param data      待解析字符串
     * @param dataClass 对象的Class
     * @param <T>       对象泛型
     * @return
     */
    <T> List<T> parseCustomDataList(String data, final Class<T> dataClass)  throws Exception;

    /**
     * 将结果解析为{@link com.lwkandroid.lib.core.net.bean.IApiRestfulResult}，并获取Object类型集合数据
     *
     * @param data      待解析字符串
     * @param dataClass 对象的Class
     * @param <T>       对象泛型
     */
    <T> List<T> parseRestfulDataList(String data, final Class<T> dataClass)  throws Exception;

    /**
     * 将结果解析为某一Object类型数组数据
     *
     * @param data      待解析字符串
     * @param dataClass 对象的Class
     * @param <T>       对象泛型
     * @return
     */
    <T> T[] parseCustomDataArray(String data, final Class<T> dataClass)  throws Exception;

    /**
     * 将结果解析为{@link com.lwkandroid.lib.core.net.bean.IApiRestfulResult}，并获取Object类型数组数据
     *
     * @param data      待解析字符串
     * @param dataClass 对象的Class
     * @param <T>       对象泛型
     */
    <T> T[] parseRestfulDataArray(String data, final Class<T> dataClass)  throws Exception;
}
