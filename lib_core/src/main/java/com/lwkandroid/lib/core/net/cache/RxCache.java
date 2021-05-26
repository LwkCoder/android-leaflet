package com.lwkandroid.lib.core.net.cache;


import com.lwkandroid.lib.core.net.bean.ApiCacheOptions;
import com.lwkandroid.lib.core.net.bean.ApiDiskCacheBean;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.cache.core.CacheCore;
import com.lwkandroid.lib.core.net.cache.strategy.ApiCacheAfterRemoteDiffStrategy;
import com.lwkandroid.lib.core.net.cache.strategy.ApiCacheAfterRemoteStrategy;
import com.lwkandroid.lib.core.net.cache.strategy.ApiCacheFirstStrategy;
import com.lwkandroid.lib.core.net.cache.strategy.ApiCacheOnlyStrategy;
import com.lwkandroid.lib.core.net.cache.strategy.ApiNoCacheStrategy;
import com.lwkandroid.lib.core.net.cache.strategy.ApiRemoteFirstStrategy;
import com.lwkandroid.lib.core.net.cache.strategy.ApiRemoteOnlyStrategy;
import com.lwkandroid.lib.core.net.cache.strategy.IApiCacheStrategy;
import com.lwkandroid.lib.core.net.constants.ApiCacheMode;
import com.lwkandroid.lib.core.net.constants.ApiExceptionCode;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.exceptions.Exceptions;


/**
 * 缓存功能入口
 *
 * @author LWK
 */

public class RxCache
{
    /**
     * 根据参数添加缓存关联
     */
    public static <T> ObservableTransformer<T, ResultCacheWrapper<T>> transform(final ApiCacheOptions options, final Class<T> clazz)
    {
        final IApiCacheStrategy strategy = getStrategy(options.getCacheMode());
        return upstream -> strategy.execute(options, upstream, clazz);
    }

    /**
     * 读取缓存
     */
    public static <T> Observable<T> loadCache(final CacheCore cacheCore, final Class<T> clazz, final String cacheKey, final long cacheTime)
    {
        return Observable.create(new CacheSubscribe<ApiDiskCacheBean<T>>()
        {
            @Override
            ApiDiskCacheBean<T> execute() throws Throwable
            {
                return cacheCore.load(clazz, cacheKey, cacheTime);
            }
        }).map(ApiDiskCacheBean::getData);
    }

    /**
     * 保存缓存
     */
    public static <T> Observable<Boolean> saveCache(final CacheCore cacheCore, final T value, final String cacheKey, final long cacheTime)
    {
        return Observable.create(new CacheSubscribe<Boolean>()
        {
            @Override
            Boolean execute() throws Throwable
            {
                long time;
                if (cacheTime < -1)
                {
                    time = -1;
                } else
                {
                    time = cacheTime;
                }
                ApiDiskCacheBean<T> entity = new ApiDiskCacheBean<>(value, time);
                entity.setUpdateDate(System.currentTimeMillis());
                return cacheCore.save(cacheKey, entity);
            }
        });
    }

    private static abstract class CacheSubscribe<T> implements ObservableOnSubscribe<T>
    {
        @Override
        public void subscribe(@NonNull ObservableEmitter<T> subscriber) throws Exception
        {
            try
            {
                T data = execute();
                if (!subscriber.isDisposed())
                {
                    if (data instanceof ApiDiskCacheBean)
                    {
                        if (((ApiDiskCacheBean) data).getData() != null)
                        {
                            subscriber.onNext(data);
                        } else
                        {
                            subscriber.onError(new ApiException(ApiExceptionCode.CACHE_EMPTY, "cache is empty"));
                        }
                    } else
                    {
                        if (data != null)
                        {
                            subscriber.onNext(data);
                        } else
                        {
                            subscriber.onError(new ApiException(ApiExceptionCode.CACHE_EMPTY, "cache is empty"));
                        }
                    }
                }
            } catch (Throwable e)
            {
                if (!subscriber.isDisposed())
                {
                    subscriber.onError(e);
                }
                Exceptions.throwIfFatal(e);
                return;
            }

            if (!subscriber.isDisposed())
            {
                subscriber.onComplete();
            }
        }

        abstract T execute() throws Throwable;
    }

    private static IApiCacheStrategy getStrategy(@ApiCacheMode.Mode int mode)
    {
        if (mode == ApiCacheMode.NO_CACHE)
        {
            return new ApiNoCacheStrategy();
        } else if (mode == ApiCacheMode.REMOTE_FIRST_OR_CACHE)
        {
            return new ApiRemoteFirstStrategy();
        } else if (mode == ApiCacheMode.CACHE_FIRST_OR_REMOTE)
        {
            return new ApiCacheFirstStrategy();
        } else if (mode == ApiCacheMode.REMOTE_ONLY)
        {
            return new ApiRemoteOnlyStrategy();
        } else if (mode == ApiCacheMode.CACHE_ONLY)
        {
            return new ApiCacheOnlyStrategy();
        } else if (mode == ApiCacheMode.CACHE_FIRST_AFTER_REMOTE)
        {
            return new ApiCacheAfterRemoteStrategy();
        } else if (mode == ApiCacheMode.CACHE_FIRST_AFTER_REMOTE_IF_DIFF)
        {
            return new ApiCacheAfterRemoteDiffStrategy();
        }
        return null;
    }
}
