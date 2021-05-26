package com.sources.javacode.project.splash;

import com.lwkandroid.lib.common.mvp.IMvpBaseModel;
import com.lwkandroid.lib.common.mvp.IMvpBasePresenter;
import com.lwkandroid.lib.common.mvp.IMvpBaseView;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Description:契约层
 *
 * @author
 * @date
 */
interface SplashContract
{
    interface IView<P extends LifecycleObserver> extends IMvpBaseView<P>
    {
        void toHome();
    }

    interface IModel extends IMvpBaseModel
    {

    }

    interface IPresenter<V extends LifecycleOwner, M> extends IMvpBasePresenter<V, M>
    {
        void waitForLaunch();
    }
}
