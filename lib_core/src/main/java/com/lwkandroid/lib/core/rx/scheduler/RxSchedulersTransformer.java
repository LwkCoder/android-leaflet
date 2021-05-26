package com.lwkandroid.lib.core.rx.scheduler;

import org.reactivestreams.Publisher;

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
 * Description: 切换线程的Transformer
 *
 * @author LWK
 * @date 2019/4/24
 */
final class RxSchedulersTransformer<T> implements ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer
{
    private Scheduler mUpScheduler;
    private Scheduler mDownScheduler;

    public RxSchedulersTransformer(Scheduler up, Scheduler down)
    {
        this.mUpScheduler = up;
        this.mDownScheduler = down;
    }

    @Override
    public CompletableSource apply(Completable upstream)
    {
        return upstream.subscribeOn(mUpScheduler).observeOn(mDownScheduler);
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream)
    {
        return upstream.subscribeOn(mUpScheduler).observeOn(mDownScheduler);
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream)
    {
        return upstream.subscribeOn(mUpScheduler).observeOn(mDownScheduler);
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream)
    {
        return upstream.subscribeOn(mUpScheduler).observeOn(mDownScheduler);
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream)
    {
        return upstream.subscribeOn(mUpScheduler).observeOn(mDownScheduler);
    }
}
