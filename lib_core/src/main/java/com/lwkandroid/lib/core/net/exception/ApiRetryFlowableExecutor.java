package com.lwkandroid.lib.core.net.exception;

import com.lwkandroid.lib.core.log.KLog;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;


/**
 * Description:适用于Flowable的重试操作
 *
 * @author LWK
 * @date 2020/4/2
 */
public final class ApiRetryFlowableExecutor implements Function<Flowable<? extends Throwable>, Publisher<?>>
{
    private RetryConfig mRetryConfigProvider;
    private int mCurrentRetryCount = 0;

    public ApiRetryFlowableExecutor(RetryConfig retryConfigProvider)
    {
        this.mRetryConfigProvider = retryConfigProvider;
    }

    @Override
    public Publisher<?> apply(Flowable<? extends Throwable> flowable) throws Exception
    {
        return flowable.flatMap((Function<Throwable, Publisher<?>>) throwable -> {
            Single<Boolean> single = mRetryConfigProvider.getRetryCondition().onRetryCondition(throwable);
            if (++mCurrentRetryCount <= mRetryConfigProvider.getMaxRetryCount())
            {
                return single.flatMapPublisher((Function<Boolean, Publisher<?>>) needRetry -> {
                    if (needRetry)
                    {
                        KLog.e("Retry RxHttp request after "
                                + mRetryConfigProvider.getRetryDelay() + " milliseconds,"
                                + "current retry count=" + mCurrentRetryCount
                                + "due to exception:\n"
                                + throwable.toString());
                        return Flowable.timer(mRetryConfigProvider.getRetryDelay(), TimeUnit.MILLISECONDS);
                    } else
                    {
                        return Flowable.error(throwable);
                    }
                });
            } else
            {
                return Flowable.error(throwable);
            }
        });
    }
}
