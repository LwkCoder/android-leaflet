package com.lwkandroid.lib.core.net.cache.strategy;


import com.lwkandroid.lib.core.net.bean.ApiCacheOptions;
import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;

import io.reactivex.rxjava3.core.Observable;


/**
 * 只请求网络，但数据仍然缓存的策略
 *
 * @author LWK
 */
public class ApiRemoteOnlyStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        return loadRemote(options, clazz, source, false);
    }
}
