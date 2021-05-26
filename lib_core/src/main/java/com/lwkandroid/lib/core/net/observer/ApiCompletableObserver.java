package com.lwkandroid.lib.core.net.observer;

import com.lwkandroid.lib.core.net.bean.ApiException;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * @description: CompletableObserver
 * @author: LWK
 * @date: 2020/8/10 15:01
 */
public abstract class ApiCompletableObserver implements CompletableObserver, IApiCompleteObserver
{
    @Override
    public void onSubscribe(Disposable d)
    {
    }

    @Override
    public void onComplete()
    {
        onCompleted();
    }

    @Override
    public void onError(Throwable e)
    {
        onError(ApiException.handleThrowable(e));
    }
}
