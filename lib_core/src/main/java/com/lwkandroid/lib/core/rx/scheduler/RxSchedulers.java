package com.lwkandroid.lib.core.rx.scheduler;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * RxJava 工具类
 *
 * @author LWK
 */

public final class RxSchedulers
{
    /**
     * 绑定某个Dispose到IO_MAIN调度中
     */
    public static <T> RxSchedulersTransformer<T> applyIo2Main()
    {
        return new RxSchedulersTransformer<T>(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到COMPUTATION_MAIN调度中
     */
    public static <T> RxSchedulersTransformer<T> applyComputation2Main()
    {
        return new RxSchedulersTransformer<T>(Schedulers.computation(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到NEWTHREAD_MAIN调度中
     */
    public static <T> RxSchedulersTransformer<T> applyNewThread2Main()
    {
        return new RxSchedulersTransformer<T>(Schedulers.newThread(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到SINGLE_MAIN调度中
     */
    public static <T> RxSchedulersTransformer<T> applySingleThread2Main()
    {
        return new RxSchedulersTransformer<T>(Schedulers.single(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到TRAMPOLINE_MAIN调度中
     */
    public static <T> RxSchedulersTransformer<T> applyTrampoline2Main()
    {
        return new RxSchedulersTransformer<T>(Schedulers.trampoline(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到任意两个线程
     */
    public static <T> RxSchedulersTransformer<T> applySchedulers(Scheduler up, Scheduler down)
    {
        return new RxSchedulersTransformer<T>(up, down);
    }
}
