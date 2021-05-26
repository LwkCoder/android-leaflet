package com.sources.javacode.app;

import android.app.Application;
import android.os.Build;

import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.utils.common.AppUtils;
import com.lwkandroid.widget.leaflet.LeafletMap;
import com.sources.javacode.BuildConfig;
import com.sources.javacode.net.ApiURL;

/**
 * Application入口
 *
 * @author LWK
 */

public class AppApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        ApiURL.BASEURL = BuildConfig.baseUrl;
        AppConfig.CHANNEL_NAME = BuildConfig.APP_CHANNEL;

        //初始化网络请求库
        RxHttp.init(BuildConfig.DEBUG, ApiURL.BASEURL);

        if (BuildConfig.DEBUG)
        {
            KLog.i(new StringBuilder()
                    .append("\n********************************************************\n")
                    .append("| AppName=").append(AppUtils.getAppName()).append("\n")
                    .append("| VersionName=").append(AppUtils.getAppVersionName()).append("\n")
                    .append("| VersionCode=").append(AppUtils.getAppVersionCode()).append("\n")
                    .append("| ChannelName=").append(AppConfig.CHANNEL_NAME).append("\n")
                    .append("| PackageName=").append(AppUtils.getPackageName()).append("\n")
                    .append("| KeyStoreSHA1=").append(AppUtils.getAppSignatureSHA1()).append("\n")
                    .append("| KeyStoreSHA256=").append(AppUtils.getAppSignatureSHA256()).append("\n")
                    .append("| ApiURL.HOST=").append(ApiURL.BASEURL).append("\n")
                    .append("| DeviceManufacturer=").append(Build.MANUFACTURER).append("\n")
                    .append("| DeviceModel=").append(Build.MODEL).append("\n")
                    .append("| AndroidVersion=").append(Build.VERSION.RELEASE).append("\n")
                    .append("| AndroidSdk=").append(Build.VERSION.SDK_INT).append("\n")
                    .append("********************************************************"));
        }

        //注册Activity回调
        registerActivityLifecycleCallbacks(new AppLifecycleCallBack());

        LeafletMap.init(this);
    }
}
