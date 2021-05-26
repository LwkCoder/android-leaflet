package com.lwkandroid.lib.common.rx;

import android.content.DialogInterface;

import com.lwkandroid.lib.core.callback.WingsConsumer;
import com.lwkandroid.lib.core.net.observer.ApiCompletableObserver;

import androidx.annotation.StringRes;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @description: 带Loading的CompletableObserver
 * @author: LWK
 * @date: 2020/6/23 14:25
 */
public abstract class ApiLoadingCompletableObserver extends ApiCompletableObserver implements WingsConsumer<DialogInterface>
{
    private LoadingDialogImpl mDialogImpl;
    private Disposable mDisposable;

    public ApiLoadingCompletableObserver()
    {
        this.mDialogImpl = new LoadingDialogImpl(this);
    }

    public ApiLoadingCompletableObserver setCancelable(boolean cancelable)
    {
        mDialogImpl.getDialogBuilder().setCancelable(cancelable);
        return this;
    }

    public ApiLoadingCompletableObserver setCanceledOnTouchOutside(boolean b)
    {
        mDialogImpl.getDialogBuilder().setCanceledOnTouchOutside(b);
        return this;
    }

    public ApiLoadingCompletableObserver setMessage(String message)
    {
        mDialogImpl.getDialogController().setMessage(message);
        return this;
    }

    public ApiLoadingCompletableObserver setMessage(@StringRes int resId)
    {
        mDialogImpl.getDialogController().setMessage(resId);
        return this;
    }

    public ApiLoadingCompletableObserver setDarkWindowDegree(float degree)
    {
        mDialogImpl.getDialogBuilder().setDarkWindowDegree(degree);
        return this;
    }

    public ApiLoadingCompletableObserver setFocusable(boolean b)
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
    public void onError(Throwable e)
    {
        super.onError(e);
        mDialogImpl.dismissDialog();
    }

    @Override
    public void onComplete()
    {
        super.onComplete();
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
