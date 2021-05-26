package com.lwkandroid.lib.common;

import android.app.ActivityThread;
import android.content.Context;

import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.utils.common.CrashUtils;

/**
 * Description:工具初始化入口
 *
 * @author LWK
 * @date 2020/3/6
 */
public final class WingsTools
{
    private WingsTools()
    {
    }

    public static void initTools(Context context)
    {
        //崩溃日志统计
        CrashUtils.init();
        //Activity栈管理
        ActivityThread.currentApplication().registerActivityLifecycleCallbacks(ActivityLifecycleHelper.get());
    }
}
