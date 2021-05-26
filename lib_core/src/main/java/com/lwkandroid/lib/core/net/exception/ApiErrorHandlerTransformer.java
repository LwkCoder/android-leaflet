package com.lwkandroid.lib.core.net.exception;

import org.reactivestreams.Publisher;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.CompletableTransformer;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.MaybeTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.core.SingleTransformer;

/**
 * Description:全局错误处理操作
 *
 * @author LWK
 * @date 2020/4/2
 */
public class ApiErrorHandlerTransformer<T> implements ObservableTransformer<T, T>,
        FlowableTransformer<T, T>, SingleTransformer<T, T>,
        MaybeTransformer<T, T>, CompletableTransformer
{
    private Scheduler mHandlerScheduler;
    private RetryConfig mRetryConfigProvider;
    private ApiErrorConsumer mErrorConsumer;

    public ApiErrorHandlerTransformer(RetryConfig retryConfigProvider, ApiErrorConsumer errorConsumer)
    {
        this(AndroidSchedulers.mainThread(), retryConfigProvider, errorConsumer);
    }

    public ApiErrorHandlerTransformer(Scheduler handlerScheduler, RetryConfig retryConfigProvider, ApiErrorConsumer errorConsumer)
    {
        this.mHandlerScheduler = handlerScheduler;
        this.mRetryConfigProvider = retryConfigProvider;
        this.mErrorConsumer = errorConsumer;
    }

    @Override
    public CompletableSource apply(Completable upstream)
    {
        return upstream
                .observeOn(mHandlerScheduler)
                .onErrorResumeNext(new ApiExceptionConvertFunc())
                .retryWhen(new ApiRetryFlowableExecutor(mRetryConfigProvider))
                .doOnError(mErrorConsumer);
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream)
    {
        return upstream
                .observeOn(mHandlerScheduler)
                .onErrorResumeNext(new ApiExceptionConvertFunc())
                .retryWhen(new ApiRetryFlowableExecutor(mRetryConfigProvider))
                .doOnError(mErrorConsumer);
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream)
    {
        return upstream
                .observeOn(mHandlerScheduler)
                .onErrorResumeNext(new ApiExceptionConvertFunc())
                .retryWhen(new ApiRetryFlowableExecutor(mRetryConfigProvider))
                .doOnError(mErrorConsumer);
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream)
    {
        return upstream
                .observeOn(mHandlerScheduler)
                .onErrorResumeNext(new ApiExceptionConvertFunc())
                .retryWhen(new ApiRetryObservableExecutor(mRetryConfigProvider))
                .doOnError(mErrorConsumer);
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream)
    {
        return upstream
                .observeOn(mHandlerScheduler)
                .onErrorResumeNext(new ApiExceptionConvertFunc())
                .retryWhen(new ApiRetryFlowableExecutor(mRetryConfigProvider))
                .doOnError(mErrorConsumer);
    }
}
