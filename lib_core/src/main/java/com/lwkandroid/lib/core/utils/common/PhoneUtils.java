package com.lwkandroid.lib.core.utils.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.lwkandroid.lib.core.context.AppContext;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.annotation.RequiresPermission;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * 手机设备相关工具类
 *
 * @author LWK
 */
public final class PhoneUtils
{

    private PhoneUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 判断设备是否root
     *
     * @return the boolean{@code true}: 是<br>{@code false}: 否
     */
    public static boolean isRooted()
    {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/"
                , "/sbin/", "/system/sd/xbin/"
                , "/system/bin/failsafe/", "/data/local/xbin/"
                , "/data/local/bin/", "/data/local/"};
        for (String location : locations)
        {
            if (new File(location + su).exists())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static int getSDKVersion()
    {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备AndroidID
     *
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID()
    {
        return Settings.Secure.getString(AppContext.get().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备厂商
     *
     * @return 设备厂商
     */
    public static String getManufacturer()
    {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     *
     * @return 设备型号
     */
    public static String getModel()
    {
        String model = Build.MODEL;
        if (model != null)
        {
            model = model.trim().replaceAll("\\s*", "");
        } else
        {
            model = "";
        }
        return model;
    }

    private static TelephonyManager getTeleManager()
    {
        return (TelephonyManager) AppContext.get().getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 判断设置是否是手机
     */
    public static boolean isPhone()
    {
        TelephonyManager manager = getTeleManager();
        return manager != null && manager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * Return the unique device id.
     * <p>If the version of SDK is greater than 28, it will return an empty string.</p>
     * <p>Must hold {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return the unique device id
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(READ_PHONE_STATE)
    public static String getDeviceId()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            return "";
        }
        TelephonyManager tm = getTeleManager();
        String deviceId = tm.getDeviceId();
        if (!TextUtils.isEmpty(deviceId))
        {
            return deviceId;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String imei = tm.getImei();
            if (!TextUtils.isEmpty(imei))
            {
                return imei;
            }
            String meid = tm.getMeid();
            return TextUtils.isEmpty(meid) ? "" : meid;
        }
        return "";
    }

    /**
     * Return the IMEI.
     * <p>If the version of SDK is greater than 28, it will return an empty string.</p>
     * <p>Must hold {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return the IMEI
     */
    @RequiresPermission(READ_PHONE_STATE)
    public static String getIMEI()
    {
        return getImeiOrMeid(true);
    }

    /**
     * Return the MEID.
     * <p>If the version of SDK is greater than 28, it will return an empty string.</p>
     * <p>Must hold {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return the MEID
     */
    @RequiresPermission(READ_PHONE_STATE)
    public static String getMEID()
    {
        return getImeiOrMeid(false);
    }

    @SuppressLint("HardwareIds")
    @RequiresPermission(READ_PHONE_STATE)
    public static String getImeiOrMeid(boolean isImei)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            return "";
        }
        TelephonyManager tm = getTeleManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            if (isImei)
            {
                return getMinOne(tm.getImei(0), tm.getImei(1));
            } else
            {
                return getMinOne(tm.getMeid(0), tm.getMeid(1));
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            String ids = getSystemPropertyByReflect(isImei ? "ril.gsm.imei" : "ril.cdma.meid");
            if (!TextUtils.isEmpty(ids))
            {
                String[] idArr = ids.split(",");
                if (idArr.length == 2)
                {
                    return getMinOne(idArr[0], idArr[1]);
                } else
                {
                    return idArr[0];
                }
            }

            String id0 = tm.getDeviceId();
            String id1 = "";
            try
            {
                Method method = tm.getClass().getMethod("getDeviceId", int.class);
                id1 = (String) method.invoke(tm,
                        isImei ? TelephonyManager.PHONE_TYPE_GSM
                                : TelephonyManager.PHONE_TYPE_CDMA);
            } catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            } catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
            if (isImei)
            {
                if (id0 != null && id0.length() < 15)
                {
                    id0 = "";
                }
                if (id1 != null && id1.length() < 15)
                {
                    id1 = "";
                }
            } else
            {
                if (id0 != null && id0.length() == 14)
                {
                    id0 = "";
                }
                if (id1 != null && id1.length() == 14)
                {
                    id1 = "";
                }
            }
            return getMinOne(id0, id1);
        } else
        {
            String deviceId = tm.getDeviceId();
            if (isImei)
            {
                if (deviceId != null && deviceId.length() >= 15)
                {
                    return deviceId;
                }
            } else
            {
                if (deviceId != null && deviceId.length() == 14)
                {
                    return deviceId;
                }
            }
        }
        return "";
    }

    private static String getSystemPropertyByReflect(String key)
    {
        try
        {
            @SuppressLint("PrivateApi")
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method getMethod = clz.getMethod("get", String.class, String.class);
            return (String) getMethod.invoke(clz, key, "");
        } catch (Exception e)
        {/**/}
        return "";
    }

    private static String getMinOne(String s0, String s1)
    {
        boolean empty0 = TextUtils.isEmpty(s0);
        boolean empty1 = TextUtils.isEmpty(s1);
        if (empty0 && empty1)
        {
            return "";
        }
        if (!empty0 && !empty1)
        {
            if (s0.compareTo(s1) <= 0)
            {
                return s0;
            } else
            {
                return s1;
            }
        }
        if (!empty0)
        {
            return s0;
        }
        return s1;
    }

    /**
     * 拨打电话【调用前需要申请权限android.permission.CALL_PHONE】
     * <p>Must hold {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber 电话号码
     * @return true:成功 false:失败
     */
    public static boolean call(String phoneNumber)
    {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (AppContext.get().getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(AppContext.get(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                {
                    AppContext.get().startActivity(intent);
                    return true;
                } else
                {
                    return false;
                }
            } else
            {
                AppContext.get().startActivity(intent);
                return true;
            }
        }
        return false;
    }

    /**
     * 跳转拨号盘
     *
     * @param teleNumber 电话号码
     * @return true:成功 false:失败
     */
    public static boolean skipDial(String teleNumber)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + teleNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (AppContext.get().getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0)
        {
            AppContext.get().startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 发送短信【调用前需要申请权限android.permission.SEND_SMS】
     *
     * @param teleNumner 电话号码
     * @param message    短信内容
     * @return true:成功 false:失败
     */
    public static boolean sendSMS(String teleNumner, String message)
    {
        Uri uri = Uri.parse("smsto:" + teleNumner);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        if (AppContext.get().getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0)
        {

            intent.putExtra("sms_body", message);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppContext.get().startActivity(intent);
            return true;
        }
        return false;
    }
}
