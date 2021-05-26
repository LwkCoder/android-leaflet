package com.lwkandroid.lib.core.net.exception;


import io.reactivex.rxjava3.core.Single;

/**
 * Description:自动重试网络请求配置
 *
 * @author LWK
 * @date 2020/4/3
 */
public final class RetryConfig
{
    private int mMaxRetryCount = 1;
    private long mRetryDelay = 1000;
    private IRetryCondition mRetryCondition;

    public static RetryConfig noRetry()
    {
        return new RetryConfig(throwable -> Single.just(false));
    }

    public RetryConfig(IRetryCondition retryCondition)
    {
        this.mRetryCondition = retryCondition;
    }

    public RetryConfig(int retryCount, long retryDelay, IRetryCondition retryCondition)
    {
        this.mMaxRetryCount = retryCount;
        this.mRetryDelay = retryDelay;
        this.mRetryCondition = retryCondition;
    }

    public int getMaxRetryCount()
    {
        return mMaxRetryCount;
    }

    public void setMaxRetryCount(int maxRetryCount)
    {
        this.mMaxRetryCount = maxRetryCount;
    }

    public long getRetryDelay()
    {
        return mRetryDelay;
    }

    public void setRetryDelay(long retryDelay)
    {
        this.mRetryDelay = retryDelay;
    }

    public IRetryCondition getRetryCondition()
    {
        return mRetryCondition;
    }

    public void setRetryCondition(IRetryCondition retryCondition)
    {
        this.mRetryCondition = retryCondition;
    }

}
