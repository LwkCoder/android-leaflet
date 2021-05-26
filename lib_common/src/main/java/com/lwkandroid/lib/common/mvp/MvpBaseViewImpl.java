package com.lwkandroid.lib.common.mvp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Description:MVP View层接口实现类
 *
 * @author LWK
 * @date 2020/2/27
 */
public class MvpBaseViewImpl<P extends LifecycleObserver> implements IMvpBaseView<P>
{
    private FragmentActivity mFragmentActivity;
    private P mPresenter;
    private LifecycleOwner mLifecycleOwner;

    public MvpBaseViewImpl(FragmentActivity activity, LifecycleOwner owner)
    {
        this.mFragmentActivity = activity;
        this.mLifecycleOwner = owner;
    }

    @Override
    public FragmentActivity getFragmentActivity()
    {
        return mFragmentActivity;
    }

    @Override
    public LifecycleOwner getLifecycleOwner()
    {
        return mLifecycleOwner;
    }

    @Override
    public void setPresenter(P presenter)
    {
        mPresenter = presenter;
        //绑定生命周期
        if (mLifecycleOwner != null)
        {
            mLifecycleOwner.getLifecycle().addObserver(presenter);
        }
    }

    @Override
    public P getPresenter()
    {
        return mPresenter;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle()
    {
        return mLifecycleOwner.getLifecycle();
    }
}
