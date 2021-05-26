package com.lwkandroid.lib.core.net.bean;

import java.io.Serializable;

/**
 * 请求结果的缓存封装
 *
 * @author LWK
 */
public class ResultCacheWrapper<T> implements Serializable
{
    private static final long serialVersionUID = -5458415801107285006L;
    private boolean isFromCache;
    private T data;

    public ResultCacheWrapper()
    {
    }

    public ResultCacheWrapper(boolean isFromCache)
    {
        this.isFromCache = isFromCache;
    }

    public ResultCacheWrapper(boolean isFromCache, T data)
    {
        this.isFromCache = isFromCache;
        this.data = data;
    }

    public boolean isCache()
    {
        return isFromCache;
    }

    public void setCache(boolean cache)
    {
        isFromCache = cache;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public T getData()
    {
        return data;
    }

    @Override
    public String toString()
    {
        return "ResultCacheWrapper{" +
                "isCache=" + isFromCache +
                ", data=" + data +
                '}';
    }
}
