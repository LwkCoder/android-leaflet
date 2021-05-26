package com.lwkandroid.lib.core.utils.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.lwkandroid.lib.core.context.AppContext;
import com.lwkandroid.lib.core.log.KLog;

import java.lang.reflect.Method;

import androidx.annotation.RequiresPermission;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.MODIFY_PHONE_STATE;
import static android.content.Context.WIFI_SERVICE;

/**
 * 网络相关工具类【需要网络权限】
 *
 * @author LWK
 */
public final class NetworkUtils
{
    private NetworkUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    private static NetworkInfo getActiveNetworkInfo()
    {
        ConnectivityManager manager = (ConnectivityManager) AppContext.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo();
    }

    /**
     * 判断网络是否可用
     *
     * @return true:是 false：否
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            ConnectivityManager manager = (ConnectivityManager) AppContext.get().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkCapabilities capabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());
            return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        } else
        {
            NetworkInfo info = getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
    }

    /**
     * 判断Wifi是否打开
     *
     * @return true:是 false：否
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static boolean isWifiEnabled()
    {
        WifiManager manager = (WifiManager) AppContext.get().getApplicationContext().getSystemService(WIFI_SERVICE);
        return manager != null && manager.isWifiEnabled();
    }

    /**
     * 打开/关闭Wifi
     *
     * @param enable true：打开 false：关闭
     */
    @RequiresPermission(CHANGE_WIFI_STATE)
    public static void setWifiEnabled(boolean enable)
    {
        WifiManager manager = (WifiManager) AppContext.get().getApplicationContext().getSystemService(WIFI_SERVICE);
        if (manager == null)
        {
            return;
        }
        if (enable == manager.isWifiEnabled())
        {
            return;
        }
        manager.setWifiEnabled(enable);
    }

    /**
     * 判断Wifi是否可用
     *
     * @return true:是 false：否
     */
    public static boolean isWifiAvailable()
    {
        return isWifiEnabled() && isNetworkAvailable();
    }

    /**
     * 判断移动数据是否打开
     *
     * @return true:是 false：否
     */
    @RequiresPermission(allOf = {MODIFY_PHONE_STATE, ACCESS_NETWORK_STATE})
    public static boolean isMobileDataEnabled()
    {
        try
        {
            TelephonyManager tm = (TelephonyManager) AppContext.get().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null)
            {
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                return tm.isDataEnabled();
            }
            @SuppressLint("PrivateApi")
            Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod)
            {
                return (boolean) getMobileDataEnabledMethod.invoke(tm);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            KLog.e("getMobileDataEnabled: ", e);
        }
        return false;
    }

    /**
     * 打开/关闭移动数据
     *
     * @param enabled true：打开 false：关闭
     * @return 操作结果：true：成功 false：失败
     */
    @RequiresPermission(MODIFY_PHONE_STATE)
    public static boolean setMobileDataEnabled(boolean enabled)
    {
        try
        {
            TelephonyManager tm = (TelephonyManager) AppContext.get().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null)
            {
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                tm.setDataEnabled(enabled);
                return true;
            }
            Method setDataEnabledMethod =
                    tm.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            if (null != setDataEnabledMethod)
            {
                return (boolean) setDataEnabledMethod.invoke(tm, enabled);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            KLog.e("setMobileDataEnabled: ", e);
        }
        return false;
    }

    /**
     * 是否正在使用移动网络数据
     *
     * @return true:是 false：否
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isUsingMobileData()
    {
        NetworkInfo info = getActiveNetworkInfo();
        return null != info
                && info.isAvailable()
                && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 是否正在使用Wifi数据
     *
     * @return true:是 false：否
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isUsingWifi()
    {
        NetworkInfo info = getActiveNetworkInfo();
        return null != info
                && info.isAvailable()
                && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 跳转到Wifi设置界面
     */
    public static void goToWifiSettingDetail()
    {
        AppContext.get().startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
