package com.lwkandroid.lib.common.rx;

import android.content.DialogInterface;

import com.lwkandroid.lib.core.callback.WingsConsumer;
import com.lwkandroid.lib.core.net.observer.ApiSingleObserver;

import androidx.annotation.StringRes;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @description: 带Loading的网络请求SingleObserver
 * @author: LWK
 * @date: 2020/6/23 14:25
 */
public abstract class ApiLoadingSingleObserver<T> extends ApiSingleObserver<T> implements WingsConsumer<DialogInterface>
{
    private LoadingDialogImpl mDialogImpl;
    private Disposable mDisposable;

    public ApiLoadingSingleObserver()
    {
        this.mDialogImpl = new LoadingDialogImpl(this);
    }

    public ApiLoadingSingleObserver<T> setCancelable(boolean cancelable)
    {
        mDialogImpl.getDialogBuilder().setCancelable(cancelable);
        return this;
    }

    public ApiLoadingSingleObserver<T> setCanceledOnTouchOutside(boolean b)
    {
        mDialogImpl.getDialogBuilder().setCanceledOnTouchOutside(b);
        return this;
    }

    public ApiLoadingSingleObserver<T> setMessage(String message)
    {
        mDialogImpl.getDialogController().setMessage(message);
        return this;
    }

    public ApiLoadingSingleObserver<T> setMessage(@StringRes int resId)
    {
        mDialogImpl.getDialogController().setMessage(resId);
        return this;
    }

    public ApiLoadingSingleObserver<T> setDarkWindowDegree(float degree)
    {
        mDialogImpl.getDialogBuilder().setDarkWindowDegree(degree);
        return this;
    }

    public ApiLoadingSingleObserver<T> setFocusable(boolean b)
    {
        mDialogImpl.getDialogBuilder().setFocusable(b);
        return this;
    }

    @Override
    public void onSubscribe(Disposable d)
    {
        super.onSubscribe(d);
        mDisposable = d;
        mDialogImpl.showDialog();
    }

    @Override
    public void onSuccess(T t)
    {
        super.onSuccess(t);
        mDialogImpl.dismissDialog();
    }

    @Override
    public void onError(Throwable e)
    {
        super.onError(e);
        mDialogImpl.dismissDialog();
    }

    @Override
    public void accept(DialogInterface dialogInterface)
    {
        if (mDisposable != null && !mDisposable.isDisposed())
        {
            mDisposable.dispose();
            mDisposable = null;
        }
    }
}
