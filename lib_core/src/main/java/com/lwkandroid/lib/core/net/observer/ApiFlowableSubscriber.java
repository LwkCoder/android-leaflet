package com.lwkandroid.lib.core.net.observer;


import com.lwkandroid.lib.core.net.bean.ApiException;

import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.core.FlowableSubscriber;


/**
 * @description: 网络请求FlowableSubscriber
 * @author: LWK
 * @date: 2020/6/23 9:59
 */
public abstract class ApiFlowableSubscriber<T> implements FlowableSubscriber<T>, IApiActionObserver<T>
{
    @Override
    public void onSubscribe(Subscription s)
    {

    }

    @Override
    public void onNext(T t)
    {
        onAccept(t);
    }

    @Override
    public void onError(Throwable t)
    {
        onError(ApiException.handleThrowable(t));
    }
}
