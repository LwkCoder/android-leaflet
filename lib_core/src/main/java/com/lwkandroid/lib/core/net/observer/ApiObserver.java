package com.lwkandroid.lib.core.net.observer;

import com.lwkandroid.lib.core.net.bean.ApiException;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * @description: 网络请求Observer
 * @author: LWK
 * @date: 2020/6/22 14:14
 */
public abstract class ApiObserver<T> implements Observer<T>, IApiActionObserver<T>
{
    @Override
    public void onSubscribe(Disposable d)
    {

    }

    @Override
    public void onNext(T t)
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
        
    }
}
