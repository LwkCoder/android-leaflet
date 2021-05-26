package com.lwkandroid.lib.core.net.exception;

import com.lwkandroid.lib.core.log.KLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;


/**
 * Description:适用于Observable的重试操作
 *
 * @author LWK
 * @date 2020/4/2
 */
public final class ApiRetryObservableExecutor implements Function<Observable<? extends Throwable>, Observable<?>>
{
    private RetryConfig mRetryConfigProvider;
    private int mCurrentRetryCount = 0;

    public ApiRetryObservableExecutor(RetryConfig retryConfigProvider)
    {
        this.mRetryConfigProvider = retryConfigProvider;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception
    {
        return observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
            Single<Boolean> single = mRetryConfigProvider.getRetryCondition().onRetryCondition(throwable);
            if (++mCurrentRetryCount <= mRetryConfigProvider.getMaxRetryCount())
            {
                return single.flatMapObservable((Function<Boolean, ObservableSource<?>>) needRetry -> {
                    if (needRetry)
                    {
                        KLog.e("Retry RxHttp request after "
                                + mRetryConfigProvider.getRetryDelay() + " milliseconds,"
                                + "current retry count=" + mCurrentRetryCount
                                + " due to exception:\n"
                                + throwable.toString());
                        return Observable.timer(mRetryConfigProvider.getRetryDelay(), TimeUnit.MILLISECONDS);
                    } else
                    {
                        return Observable.error(throwable);
                    }
                });
            } else
            {
                return Observable.error(throwable);
            }
        });
    }

}
