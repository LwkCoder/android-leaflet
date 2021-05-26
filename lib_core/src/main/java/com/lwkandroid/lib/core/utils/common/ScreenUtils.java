package com.lwkandroid.lib.core.utils.common;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lwkandroid.lib.core.context.AppContext;

import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * 屏幕相关工具类
 *
 * @author LWK
 */
public final class ScreenUtils
{

    private ScreenUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    public static int getScreenWidth()
    {
        WindowManager windowManager = (WindowManager) AppContext.get().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高px
     */
    public static int getScreenHeight()
    {
        WindowManager windowManager = (WindowManager) AppContext.get().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕密度
     *
     * @return 屏幕密度
     */
    public static float getScreenDensity()
    {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕密度Dpi
     *
     * @return 屏幕密度Dpi
     */
    public static int getScreenDensityDpi()
    {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    /**
     * 设置屏幕为横屏
     *
     * @param activity activity
     */
    public static void setLandscape(Activity activity)
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public static void setPortrait(Activity activity)
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLandscape()
    {
        return AppContext.get().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPortrait()
    {
        return AppContext.get().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(Activity activity)
    {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation())
        {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * 截取当前屏幕为图片
     *
     * @param activity 当前屏幕Activity
     * @return 图片Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity)
    {
        return screenShot(activity, false);
    }

    /**
     * 截取当前屏幕为图片
     *
     * @param activity          当前屏幕Activity
     * @param isDeleteStatusBar 是否去除状态栏，true：是 false：否
     * @return 图片Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar)
    {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.setWillNotCacheDrawing(false);
        Bitmap bmp = decorView.getDrawingCache();
        if (bmp == null)
        {
            return null;
        }
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar)
        {
            int statusBarHeight = BarUtils.getStatusBarHeight();
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else
        {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * 判断是否锁屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLock()
    {
        KeyguardManager km = (KeyguardManager) AppContext.get().getSystemService(Context.KEYGUARD_SERVICE);
        return km.isKeyguardLocked();
    }

    /**
     * Set full screen.
     *
     * @param activity The activity.
     */
    public static void setFullScreen(@NonNull final Activity activity)
    {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Set non full screen.
     *
     * @param activity The activity.
     */
    public static void setNonFullScreen(@NonNull final Activity activity)
    {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Toggle full screen.
     *
     * @param activity The activity.
     */
    public static void toggleFullScreen(@NonNull final Activity activity)
    {
        boolean isFullScreen = isFullScreen(activity);
        Window window = activity.getWindow();
        if (isFullScreen)
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else
        {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * Return whether screen is full.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFullScreen(@NonNull final Activity activity)
    {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
    }

    /**
     * Return whether horizontal layout direction of views are from Right to Left.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLayoutRtl()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            Locale primaryLocale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                primaryLocale = AppContext.get().getResources().getConfiguration().getLocales().get(0);
            } else
            {
                primaryLocale = AppContext.get().getResources().getConfiguration().locale;
            }
            return TextUtils.getLayoutDirectionFromLocale(primaryLocale) == View.LAYOUT_DIRECTION_RTL;
        }
        return false;
    }

}