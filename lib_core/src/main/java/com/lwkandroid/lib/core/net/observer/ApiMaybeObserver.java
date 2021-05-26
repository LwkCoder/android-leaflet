package com.lwkandroid.lib.core.net.observer;

import com.lwkandroid.lib.core.net.bean.ApiException;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * @description: MaybeObserver
 * @author: LWK
 * @date: 2020/6/22 14:14
 */
public abstract class ApiMaybeObserver<T> implements MaybeObserver<T>, IApiActionObserver<T>
{
    @Override
    public void onSubscribe(Disposable d)
    {

    }

    @Override
    public void onSuccess(T t)
    {
        onAccept(t);
    }

    @Override
    public void onError(Throwable e)
    {
        onError(ApiException.handleThrowable(e));
    }

    @Override
    public void onComplete()
    {
        onEmpty();
    }

    abstract void onEmpty();
}
