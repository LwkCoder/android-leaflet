package com.lwkandroid.lib.core.net.cache.strategy;


import com.lwkandroid.lib.core.net.bean.ApiCacheOptions;
import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;

import io.reactivex.rxjava3.core.Observable;


/**
 *  无缓存策略
 *
 * @author LWK
 */
public class ApiNoCacheStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        return source.map(t -> new ResultCacheWrapper<T>(false, t));
    }
}
