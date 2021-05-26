package com.lwkandroid.lib.core.net.bean;


import com.lwkandroid.lib.core.annotation.NotProguard;

import java.io.Serializable;

/**
 * 实际缓存的类，将传入的data包裹在此类下，用以设置缓存时长等
 *
 * @author LWK
 */
@NotProguard
public class ApiDiskCacheBean<T> implements Serializable
{
    private static final long serialVersionUID = -464078693785762945L;
    /**
     * 缓存的时间，以ms为单位
     */
    private long cacheTime;
    /**
     * 实际需要缓存的数据
     */
    private T data;
    /**
     * 缓存开始的时间
     */
    private long updateDate;

    public ApiDiskCacheBean(T data, long cacheTime)
    {
        this.cacheTime = cacheTime;
        this.data = data;
    }

    public long getCacheTime()
    {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime)
    {
        this.cacheTime = cacheTime;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public long getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(long updateDate)
    {
        this.updateDate = updateDate;
    }

    @Override
    public String toString()
    {
        return "ApiDiskCacheBean{" +
                "cacheTime=" + cacheTime +
                ", data=" + data +
                ", updateDate=" + updateDate +
                '}';
    }
}
