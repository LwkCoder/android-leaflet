package com.lwkandroid.lib.common.mvp;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Description:MVP View层接口类
 *
 * @author LWK
 * @date 2020/2/27
 */
public interface IMvpBaseView<P extends LifecycleObserver> extends LifecycleOwner
{
    FragmentActivity getFragmentActivity();

    LifecycleOwner getLifecycleOwner();

    void setPresenter(P presenter);

    P getPresenter();
}
