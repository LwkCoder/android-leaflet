package com.lwkandroid.lib.core.net.response;


import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;


/**
 * 定义字符串网络请求结果的转换方法
 *
 * @author LWK
 */

public interface IApiStringResponse
{
    /**
     * 获取缓存结果包装的字符串类型的网络请求结果
     */
    Observable<ResultCacheWrapper<String>> returnStringWithCache();

    /**
     * 直接获取字符串类型的网络请求结果
     */
    Observable<String> returnString();

    /**
     * 获取缓存结果包装的ApiRestfulResult对象内数据的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<T>> parseRestfulObjectWithCache(Class<T> tOfClass);

    /**
     * 直接获取ApiRestfulResult对象内数据的网络请求结果
     */
    <T> Observable<T> parseRestfulObject(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的某一个对象的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<T>> parseCustomObjectWithCache(Class<T> tOfClass);

    /**
     * 直接获取某一个对象的网络请求结果
     */
    <T> Observable<T> parseCustomObject(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的ApiRestfulResult对象内数据集合的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<List<T>>> parseRestfulListWithCache(Class<T> tOfClass);

    /**
     * 直接获取ApiRestfulResult对象内数据集合的网络请求结果
     */
    <T> Observable<List<T>> parseRestfulList(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的某个一对象的集合的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<List<T>>> parseCustomListWithCache(Class<T> tOfClass);

    /**
     * 直接获取某一个对象的集合的网络请求结果
     */
    <T> Observable<List<T>> parseCustomList(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的ApiRestfulResult对象数组的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<T[]>> parseRestfulArrayWithCache(Class<T> tOfClass);

    /**
     * 直接获取ApiRestfulResult对象内数据数组的网络请求结果
     */
    <T> Observable<T[]> parseRestfulArray(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的某一对象数组的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<T[]>> parseCustomArrayWithCache(Class<T> tOfClass);

    /**
     * 获取某一对象数组的网络请求结果
     */
    <T> Observable<T[]> parseCustomArray(Class<T> tOfClass);
}
