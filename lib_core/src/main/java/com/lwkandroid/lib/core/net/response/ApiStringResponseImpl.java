package com.lwkandroid.lib.core.net.response;


import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.cache.RxCache;
import com.lwkandroid.lib.core.net.cache.func.CacheDataGetterFunc;
import com.lwkandroid.lib.core.net.cache.func.StringToCustomArrayCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringToCustomListCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringToCustomObjectCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringToRestfulArrayCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringToRestfulListCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringToRestfulObjectCacheFunc;
import com.lwkandroid.lib.core.net.exception.ApiErrorHandlerTransformer;
import com.lwkandroid.lib.core.net.requst.ApiBaseRequest;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;


/**
 * Description: 字符串网络请求结果的转换方法的实现类
 *
 * @author LWK
 * @date 2019/5/15
 */
public final class ApiStringResponseImpl<R extends ApiBaseRequest<R>> implements IApiStringResponse
{
    private ApiBaseRequest<R> mRequest;

    public ApiStringResponseImpl(ApiBaseRequest<R> request)
    {
        this.mRequest = request;
    }

    @Override
    public Observable<ResultCacheWrapper<String>> returnStringWithCache()
    {
        return mRequest.invokeRequest()
                .map(ApiResponseBodyConverter.convertToString())
                .compose(RxCache.transform(mRequest.getFinalCacheOptions(), String.class))
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public Observable<String> returnString()
    {
        return returnStringWithCache()
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseRestfulObjectWithCache(Class<T> tOfClass)
    {
        return mRequest.invokeRequest()
                .map(ApiResponseBodyConverter.convertToString())
                .compose(RxCache.transform(mRequest.getFinalCacheOptions(), String.class))
                .map(new StringToRestfulObjectCacheFunc<>(mRequest.getApiStringParser(), tOfClass))
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public <T> Observable<T> parseRestfulObject(Class<T> tOfClass)
    {
        return parseRestfulObjectWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseCustomObjectWithCache(Class<T> tOfClass)
    {
        return mRequest.invokeRequest()
                .map(ApiResponseBodyConverter.convertToString())
                .compose(RxCache.transform(mRequest.getFinalCacheOptions(), String.class))
                .map(new StringToCustomObjectCacheFunc<>(mRequest.getApiStringParser(), tOfClass))
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public <T> Observable<T> parseCustomObject(Class<T> tOfClass)
    {
        return parseCustomObjectWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseRestfulListWithCache(Class<T> tOfClass)
    {
        return mRequest.invokeRequest()
                .map(ApiResponseBodyConverter.convertToString())
                .compose(RxCache.transform(mRequest.getFinalCacheOptions(), String.class))
                .map(new StringToRestfulListCacheFunc<>(mRequest.getApiStringParser(), tOfClass))
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public <T> Observable<List<T>> parseRestfulList(Class<T> tOfClass)
    {
        return parseRestfulListWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseCustomListWithCache(Class<T> tOfClass)
    {
        return mRequest.invokeRequest()
                .map(ApiResponseBodyConverter.convertToString())
                .compose(RxCache.transform(mRequest.getFinalCacheOptions(), String.class))
                .map(new StringToCustomListCacheFunc<>(mRequest.getApiStringParser(), tOfClass))
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public <T> Observable<List<T>> parseCustomList(Class<T> tOfClass)
    {
        return parseCustomListWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T[]>> parseRestfulArrayWithCache(Class<T> tOfClass)
    {
        return mRequest.invokeRequest()
                .map(ApiResponseBodyConverter.convertToString())
                .compose(RxCache.transform(mRequest.getFinalCacheOptions(), String.class))
                .map(new StringToRestfulArrayCacheFunc<>(mRequest.getApiStringParser(), tOfClass))
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public <T> Observable<T[]> parseRestfulArray(Class<T> tOfClass)
    {
        return parseRestfulArrayWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T[]>> parseCustomArrayWithCache(Class<T> tOfClass)
    {
        return mRequest.invokeRequest()
                .map(ApiResponseBodyConverter.convertToString())
                .compose(RxCache.transform(mRequest.getFinalCacheOptions(), String.class))
                .map(new StringToCustomArrayCacheFunc<>(mRequest.getApiStringParser(), tOfClass))
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public <T> Observable<T[]> parseCustomArray(Class<T> tOfClass)
    {
        return parseCustomArrayWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }
}
