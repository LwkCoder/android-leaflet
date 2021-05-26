package com.lwkandroid.lib.common;

import android.app.ActivityThread;
import android.content.Context;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.imageloader.glide.GlideOkClient;
import com.lwkandroid.lib.core.net.RxHttp;
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

        //添加Activity生命周期日志记录
        ActivityThread.currentApplication().registerActivityLifecycleCallbacks(new ActivityLogCallBack());
        //启动严格模式
        //        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        //        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        //初始化Chucker
        RxHttp.getGlobalOptions().addInterceptor("Chucker", new ChuckerInterceptor(context.getApplicationContext()));
        //初始化Stetho
        RxHttp.getGlobalOptions().addNetInterceptor("Stetho", new StethoInterceptor());
        Stetho.initialize(Stetho.newInitializerBuilder(context.getApplicationContext())
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context.getApplicationContext()))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context.getApplicationContext()))
                .build());
        GlideOkClient.get().getBuilder().addNetworkInterceptor(new StethoInterceptor());
    }
}
