package com.lwkandroid.lib.core.rx.life;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * 执行生命周期Lifecycle自动绑定的方法
 *
 * @author LWK
 * 2020/2/12
 */
public class RxLifeProvider implements LifecycleEventObserver
{
    private PublishSubject<Lifecycle.Event> mEventSubject = PublishSubject.create();
    private Lifecycle mLifecycle;

    public RxLifeProvider(LifecycleOwner owner)
    {
        this(owner.getLifecycle());
    }

    public RxLifeProvider(Lifecycle lifecycle)
    {
        this.mLifecycle = lifecycle;
        mLifecycle.addObserver(this);
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event)
    {
        mEventSubject.onNext(event);
        if (mLifecycle != null && mLifecycle.getCurrentState() == Lifecycle.State.DESTROYED)
        {
            release();
        }
    }

    private void release()
    {
        mEventSubject.onComplete();
        if (mLifecycle != null)
        {
            mLifecycle.removeObserver(this);
        }
    }

    public <T> RxLifeTransformer<T> bindUtilOnDestroy()
    {
        return bindUtilEvent(Lifecycle.Event.ON_DESTROY);
    }

    public <T> RxLifeTransformer<T> bindUtilEvent(Lifecycle.Event event)
    {
        return new RxLifeTransformer<T>(getBindUtilObservable(event));
    }

    private Observable<Lifecycle.Event> getBindUtilObservable(Lifecycle.Event lifeEvent)
    {
        return mEventSubject.filter(event -> event.equals(lifeEvent));
    }

}
