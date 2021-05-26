package com.lwkandroid.lib.core.rx.life;

import org.reactivestreams.Publisher;

import java.util.concurrent.CancellationException;

import io.reactivex.rxjava3.core.BackpressureStrategy;
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
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.functions.Function;


/**
 * Created by LWK
 * TODO 配合RxJava使用的生命周期自动绑定Transformer
 * 2020/2/12
 */
public final class RxLifeTransformer<T> implements ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        MaybeTransformer<T, T>,
        SingleTransformer<T, T>,
        CompletableTransformer
{
    private final Observable<?> mObservable;

    public RxLifeTransformer(Observable<?> observable)
    {
        this.mObservable = observable;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream)
    {
        return upstream.takeUntil(mObservable);
    }

    @Override
    public CompletableSource apply(Completable upstream)
    {
        return Completable.ambArray(upstream,
                mObservable.flatMapCompletable((Function<Object, Completable>) o -> Completable.error(new CancellationException())));
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream)
    {
        return upstream.takeUntil(mObservable.toFlowable(BackpressureStrategy.LATEST));
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream)
    {
        return upstream.takeUntil(mObservable.firstElement());
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream)
    {
        return upstream.takeUntil(mObservable.firstOrError());
    }
}
