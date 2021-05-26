package com.lwkandroid.lib.common.rx;

import android.content.DialogInterface;

import com.lwkandroid.lib.core.callback.WingsConsumer;
import com.lwkandroid.lib.core.net.observer.ApiFlowableSubscriber;

import org.reactivestreams.Subscription;

import androidx.annotation.StringRes;

/**
 * @description: 带Loading的网络请求FlowableSubscriber
 * @author: LWK
 * @date: 2020/6/23 14:25
 */
public abstract class ApiLoadingFlowableSubscriber<T> extends ApiFlowableSubscriber<T> implements WingsConsumer<DialogInterface>
{
    private LoadingDialogImpl mDialogImpl;
    private Subscription mSubscription;

    public ApiLoadingFlowableSubscriber()
    {
        this.mDialogImpl = new LoadingDialogImpl(this);
    }

    public ApiLoadingFlowableSubscriber<T> setCancelable(boolean cancelable)
    {
        mDialogImpl.getDialogBuilder().setCancelable(cancelable);
        return this;
    }

    public ApiLoadingFlowableSubscriber<T> setCanceledOnTouchOutside(boolean b)
    {
        mDialogImpl.getDialogBuilder().setCanceledOnTouchOutside(b);
        return this;
    }

    public ApiLoadingFlowableSubscriber<T> setMessage(String message)
    {
        mDialogImpl.getDialogController().setMessage(message);
        return this;
    }

    public ApiLoadingFlowableSubscriber<T> setMessage(@StringRes int resId)
    {
        mDialogImpl.getDialogController().setMessage(resId);
        return this;
    }

    public ApiLoadingFlowableSubscriber<T> setDarkWindowDegree(float degree)
    {
        mDialogImpl.getDialogBuilder().setDarkWindowDegree(degree);
        return this;
    }

    public ApiLoadingFlowableSubscriber<T> setFocusable(boolean b)
    {
        mDialogImpl.getDialogBuilder().setFocusable(b);
        return this;
    }

    @Override
    public void onSubscribe(Subscription s)
    {
        super.onSubscribe(s);
        mSubscription = s;
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
        mDialogImpl.dismissDialog();
    }

    @Override
    public void accept(DialogInterface dialogInterface)
    {
        if (mSubscription != null)
        {
            mSubscription.cancel();
            mSubscription = null;
        }
    }
}
