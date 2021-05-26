package com.lwkandroid.lib.common.mvp;

import com.lwkandroid.lib.core.rx.life.RxLife;
import com.lwkandroid.lib.core.rx.scheduler.RxSchedulers;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.rxjava3.core.ObservableTransformer;

/**
 * Description:MVP Presenter层接口实现类
 * 【建议所有Presenter层实现类继承该类】
 *
 * @author LWK
 * @date 2020/2/27
 */
public class MvpBasePresenterImpl<V extends LifecycleOwner, M> implements IMvpBasePresenter<V, M>
{
    private V mViewImpl;
    private M mModelImpl;

    public MvpBasePresenterImpl(V viewImpl, M modelImpl)
    {
        setViewImpl(viewImpl);
        setModelImpl(modelImpl);
    }

    @Override
    public V getViewImpl()
    {
        return mViewImpl;
    }

    @Override
    public M getModelImpl()
    {
        return mModelImpl;
    }

    @Override
    public void setViewImpl(V viewImpl)
    {
        this.mViewImpl = viewImpl;
    }

    @Override
    public void setModelImpl(M modelImpl)
    {
        this.mModelImpl = modelImpl;
    }

    @Override
    public void onCreate()
    {

    }

    @Override
    public void onDestroy()
    {

    }

    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainUntilOnDestroy()
    {
        return applyIo2MainUntilLifeCycle(Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainUntilLifeCycle(Lifecycle.Event event)
    {
        return upstream -> upstream.compose(RxSchedulers.applyIo2Main())
                .compose(RxLife.with(getViewImpl().getLifecycle()).bindUtilEvent(event));
    }

    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainUntilOnDestroy()
    {
        return applyComputation2MainUntilLifeCycle(Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainUntilLifeCycle(Lifecycle.Event event)
    {
        return upstream -> upstream.compose(RxSchedulers.applyComputation2Main())
                .compose(RxLife.with(getViewImpl().getLifecycle()).bindUtilEvent(event));
    }

    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainUntilOnDestroy()
    {
        return applyNewThread2MainUntilLifeCycle(Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainUntilLifeCycle(Lifecycle.Event event)
    {
        return upstream -> upstream.compose(RxSchedulers.applyNewThread2Main())
                .compose(RxLife.with(getViewImpl().getLifecycle()).bindUtilEvent(event));
    }

    @Override
    public <T> ObservableTransformer<T, T> applySingle2MainUntilOnDestroy()
    {
        return applySingle2MainUntilLifeCycle(Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public <T> ObservableTransformer<T, T> applySingle2MainUntilLifeCycle(Lifecycle.Event event)
    {
        return upstream -> upstream.compose(RxSchedulers.applySingleThread2Main())
                .compose(RxLife.with(getViewImpl().getLifecycle()).bindUtilEvent(event));
    }
}
