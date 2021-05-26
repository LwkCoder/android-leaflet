package com.sources.javacode.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lwkandroid.lib.core.utils.common.BarUtils;
import com.lwkandroid.lib.core.utils.common.ResourceUtils;
import com.sources.javacode.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description:App模块内Activity的回调
 *
 * @author LWK
 * @date 2020/3/6
 */
public class AppLifecycleCallBack implements Application.ActivityLifecycleCallbacks
{
    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState)
    {

    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState)
    {
        //在此设置状态栏、导航栏颜色
        BarUtils.setStatusBarColor(activity, ResourceUtils.getColor(R.color.statusbar));
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState)
    {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity)
    {

    }
}
