package com.lwkandroid.widget.leaflet;

import android.content.Context;

import com.lwkandroid.lib.core.log.KLog;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

/**
 * @description:
 * @author: 20180004
 * @date: 2021/3/11 14:26
 */
public final class LeafletMap
{
    private LeafletMap()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Context context)
    {
        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map = new HashMap(2);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback()
        {
            @Override
            public void onViewInitFinished(boolean success)
            {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                KLog.i("X5 QbSdkPreInitCallback.onViewInitFinished:" + success);
            }

            @Override
            public void onCoreInitFinished()
            {
                KLog.i("X5 QbSdkPreInitCallback.onCoreInitFinished");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(context.getApplicationContext(), cb);
    }
}
