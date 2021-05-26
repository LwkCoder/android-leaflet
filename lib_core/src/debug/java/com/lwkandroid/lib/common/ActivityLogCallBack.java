package com.lwkandroid.lib.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lwkandroid.lib.core.log.KLog;

/**
 * Description:Activity生命周期日志记录
 *
 * @author LWK
 * @date 2020/2/15
 */
public class ActivityLogCallBack implements Application.ActivityLifecycleCallbacks
{
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {
        KLog.d("onActivityCreated: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity)
    {
        KLog.d("onActivityStarted: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        KLog.d("onActivityResumed: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityPaused(Activity activity)
    {
        KLog.d("onActivityPaused: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStopped(Activity activity)
    {
        KLog.d("onActivityStopped: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {
        KLog.d("onActivitySaveInstanceState: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        KLog.d("onActivityDestroyed: " + activity.getClass().getSimpleName());
    }
}
