package com.lwkandroid.lib.common.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.rxjava3.core.ObservableTransformer;

/**
 * Description:MVP Presenter层接口类
 * 【建议所有Presenter层接口类继承该类】
 *
 * @author LWK
 * @date 2020/2/27
 */
public interface IMvpBasePresenter<V extends LifecycleOwner, M> extends LifecycleObserver
{
    V getViewImpl();

    M getModelImpl();

    void setViewImpl(V viewImpl);

    void setModelImpl(M modelImpl);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    <T> ObservableTransformer<T, T> applyIo2MainUntilOnDestroy();

    <T> ObservableTransformer<T, T> applyIo2MainUntilLifeCycle(Lifecycle.Event event);

    <T> ObservableTransformer<T, T> applyComputation2MainUntilOnDestroy();

    <T> ObservableTransformer<T, T> applyComputation2MainUntilLifeCycle(Lifecycle.Event event);

    <T> ObservableTransformer<T, T> applyNewThread2MainUntilOnDestroy();

    <T> ObservableTransformer<T, T> applyNewThread2MainUntilLifeCycle(Lifecycle.Event event);

    <T> ObservableTransformer<T, T> applySingle2MainUntilOnDestroy();

    <T> ObservableTransformer<T, T> applySingle2MainUntilLifeCycle(Lifecycle.Event event);
}
