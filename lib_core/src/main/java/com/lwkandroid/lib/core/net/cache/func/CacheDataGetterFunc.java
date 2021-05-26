package com.lwkandroid.lib.core.net.cache.func;


import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;

import io.reactivex.rxjava3.functions.Function;


/**
 * Created by LWK
 * 获取缓存包装体内数据的方法
 */
public final class CacheDataGetterFunc<T> implements Function<ResultCacheWrapper<T>, T>
{
    @Override
    public T apply(ResultCacheWrapper<T> result) throws Exception
    {
        return result.getData();
    }
}
