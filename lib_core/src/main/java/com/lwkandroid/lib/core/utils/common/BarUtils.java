package com.lwkandroid.lib.core.utils.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lwkandroid.lib.core.context.AppContext;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

/**
 * 状态栏、导航栏相关工具类
 *
 * @author LWK
 */
public final class BarUtils
{
    private static final String FAKE_STATUS_BAR_TAG = "FakeStatusBar";
    private static final int MIN_API = Build.VERSION_CODES.KITKAT;

    /**
     * 默认透明度为1.0f（不透明）
     */
    private static final float DEFAULT_ALPHA = 1.0f;

    private BarUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /******************************************以下代码属于状态栏**********************************************************/

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight()
    {
        int result = 0;
        Resources resources = AppContext.get().getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0)
        {
            result = resources.getDimensionPixelSize(resId);
        }
        return result;
    }

    /**
     * 判断状态栏是否显示
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarVisible(@NonNull final Activity activity)
    {
        int flags = activity.getWindow().getAttributes().flags;
        return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarColor(Activity activity, @ColorInt int color)
    {
        setStatusBarColor(activity, color, DEFAULT_ALPHA, true);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarColor(Activity activity, @ColorInt int color, boolean fitSystemWindow)
    {
        setStatusBarColor(activity, color, DEFAULT_ALPHA, fitSystemWindow);
    }

    /**
     * 改变状态栏颜色的方法
     *
     * @param activity 依附的Activity
     * @param color    状态栏颜色值
     * @param alpha    状态栏透明度【不是颜色透明度】
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color
            , @FloatRange(from = 0.0, to = 1.0) float alpha)
    {
        setStatusBarColor(activity, color, alpha, true);
    }

    /**
     * 改变状态栏颜色的方法
     *
     * @param activity        依附的Activity
     * @param color           状态栏颜色值
     * @param alpha           状态栏透明度【不是颜色透明度】
     * @param firSystemWindow 是否自动调整布局间距
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color
            , @FloatRange(from = 0.0, to = 1.0) float alpha, boolean firSystemWindow)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mixtureColor(color, alpha));
        } else if (Build.VERSION.SDK_INT >= MIN_API)
        {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setStatusBarColorForKitkat((ViewGroup) window.getDecorView(), color, alpha);
        }
        //设置窗口根布局自动调整间距
        fitSystemWindow(activity, firSystemWindow);
    }

    /**
     * 4.4创建假的透明栏
     *
     * @param container
     * @param color
     * @param alpha
     */
    private static void setStatusBarColorForKitkat(ViewGroup container, int color
            , @FloatRange(from = 0.0, to = 1.0) float alpha)
    {
        int mixtureColor = mixtureColor(color, alpha);
        View statusView = container.findViewWithTag(FAKE_STATUS_BAR_TAG);
        if (statusView != null)
        {
            statusView.setBackgroundColor(mixtureColor);
            statusView.setVisibility(View.VISIBLE);
        } else
        {
            statusView = new View(container.getContext());
            statusView.setBackgroundColor(mixtureColor);
            statusView.setTag(FAKE_STATUS_BAR_TAG);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
            container.addView(statusView, lp);
        }
    }

    /**
     * 调配透明度的方法
     *
     * @param color
     * @param alpha
     * @return
     */
    private static int mixtureColor(int color, @FloatRange(from = 0.0, to = 1.0) float alpha)
    {
        if (color == Color.TRANSPARENT)
        {
            return Color.TRANSPARENT;
        }

        int a = (color & 0xff000000) == 0 ? 0xff : color >>> 24;
        return (color & 0x00ffffff) | (((int) (a * alpha)) << 24);
    }

    /**
     * 设置根布局fitSystemWindow属性
     *
     * @param activity
     * @param fitSystemWindow
     */
    private static void fitSystemWindow(Activity activity, boolean fitSystemWindow)
    {
        ViewGroup parent = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        for (int i = 0, count = parent.getChildCount(); i < count; i++)
        {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup)
            {
                childView.setFitsSystemWindows(fitSystemWindow);
                ((ViewGroup) childView).setClipToPadding(fitSystemWindow);
            }
        }
    }

    /**
     * 设置状态栏为深色模式
     */
    public static void setStatusBarDarkMode(@NonNull final Activity activity,
                                            final boolean isDarkMode)
    {
        setStatusBarDarkMode(activity.getWindow(), isDarkMode);
    }

    /**
     * 设置状态栏为深色模式
     */
    public static void setStatusBarDarkMode(@NonNull final Window window,
                                            final boolean isDarkMode)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            View decorView = window.getDecorView();
            int vis = decorView.getSystemUiVisibility();
            if (isDarkMode)
            {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else
            {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(vis);
        }
    }

    /**
     * 判断状态栏是否为深色模式
     */
    public static boolean isStatusBarDarkMode(@NonNull final Activity activity)
    {
        return isStatusBarDarkMode(activity.getWindow());
    }

    /**
     * 判断状态栏是否为深色模式
     */
    public static boolean isStatusBarDarkMode(@NonNull final Window window)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            View decorView = window.getDecorView();
            int vis = decorView.getSystemUiVisibility();
            return (vis & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) != 0;
        }
        return false;
    }

    /**
     * 为了配合状态栏而增加View的paddingTop,增加的值为状态栏高度
     */
    public static void compatPaddingWithStatusBar(final View view)
    {
        if (Build.VERSION.SDK_INT < MIN_API || view == null)
        {
            return;
        }

        final int statusBarHeight = getStatusBarHeight();
        final ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null && lp.height > 0)
        {
            lp.height += statusBarHeight;
            view.setLayoutParams(lp);
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight,
                    view.getPaddingRight(), view.getPaddingBottom());
        } else
        {
            view.post(() -> {
                ViewGroup.LayoutParams lp1 = view.getLayoutParams();
                int height = view.getHeight();
                lp1.height = height + statusBarHeight;
                view.setLayoutParams(lp1);
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight,
                        view.getPaddingRight(), view.getPaddingBottom());
            });
        }
    }

    /**
     * 为了配合状态栏而增加View的marginTop,增加的值为状态栏高度
     * 【一般是给宽度为wrap_content的View设置的】
     */
    public static void compatMarginWithStatusBar(View view)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null && lp instanceof ViewGroup.MarginLayoutParams)
        {
            ((ViewGroup.MarginLayoutParams) lp).topMargin += getStatusBarHeight();
            view.setLayoutParams(lp);
        }
    }

    /*******************************************以下代码属于导航栏*******************************************************************/

    /**
     * 获取导航栏的高度
     */
    public static int getNavigationBarHeight()
    {
        int height = 0;
        Resources rs = AppContext.get().getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0)
        {
            height = rs.getDimensionPixelSize(id);
        }
        return height;
    }

    /**
     * 设置导航栏可见性
     */
    public static void setNavigationBarVisibility(@NonNull final Activity activity, boolean isVisible)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            return;
        }
        setNavNavigationBarVisibility(activity.getWindow(), isVisible);

    }

    /**
     * 设置导航栏可见性
     */
    public static void setNavNavigationBarVisibility(@NonNull final Window window, boolean isVisible)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            return;
        }
        final ViewGroup decorView = (ViewGroup) window.getDecorView();
        for (int i = 0, count = decorView.getChildCount(); i < count; i++)
        {
            final View child = decorView.getChildAt(i);
            final int id = child.getId();
            if (id != View.NO_ID)
            {
                String resourceEntryName = AppContext.get()
                        .getResources()
                        .getResourceEntryName(id);
                if ("navigationBarBackground".equals(resourceEntryName))
                {
                    child.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
                }
            }
        }
        final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (isVisible)
        {
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~uiOptions);
        } else
        {
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | uiOptions);
        }
    }

    /**
     * 判断导航栏是否可见
     * <p>需要在{@link Activity#onWindowFocusChanged(boolean)}内调用，或者界面完全绘制完成后调用</p>
     */
    public static boolean isNavBarVisible(@NonNull final Activity activity)
    {
        return isNavBarVisible(activity.getWindow());
    }

    /**
     * 判断导航栏是否可见
     * <p>需要在{@link Activity#onWindowFocusChanged(boolean)}内调用，或者界面完全绘制完成后调用</p>
     */
    public static boolean isNavBarVisible(@NonNull final Window window)
    {
        boolean isVisible = false;
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        for (int i = 0, count = decorView.getChildCount(); i < count; i++)
        {
            final View child = decorView.getChildAt(i);
            final int id = child.getId();
            if (id != View.NO_ID)
            {
                String resourceEntryName = AppContext.get()
                        .getResources()
                        .getResourceEntryName(id);
                if ("navigationBarBackground".equals(resourceEntryName)
                        && child.getVisibility() == View.VISIBLE)
                {
                    isVisible = true;
                    break;
                }
            }
        }
        if (isVisible)
        {
            // 对于三星手机，android10以下非OneUI2的版本，比如 s8，note8 等设备上，
            // 导航栏显示存在bug："当用户隐藏导航栏时显示输入法的时候导航栏会跟随显示"，会导致隐藏输入法之后判断错误
            // 这个问题在 OneUI 2 & android 10 版本已修复
            if (RomUtils.isSamsung()
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            {
                try
                {
                    return Settings.Global.getInt(AppContext.get().getContentResolver(), "navigationbar_hide_bar_enabled") == 0;
                } catch (Exception ignore)
                {
                }
            }

            int visibility = decorView.getSystemUiVisibility();
            isVisible = (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
        }

        return isVisible;
    }

    /**
     * 5.0以上切换导航栏颜色
     */
    public static void setNavigationBarColor(Activity activity, @ColorInt int color)
    {
        setNavigationBarColor(activity, color, DEFAULT_ALPHA);
    }

    /**
     * 5.0以上切换NavigationBar颜色
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavigationBarColor(Activity activity, @ColorInt int color
            , @FloatRange(from = 0.0, to = 1.0) float alpha)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(mixtureColor(color, alpha));
        }
    }

    /**
     * 为了配合状态栏而增加View的paddingBottom,增加的值为导航栏高度
     */
    public static void compatPaddingWithNavigationBar(View view)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        int navigationBarHeight = getNavigationBarHeight();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null && lp.height > 0)
        {
            lp.height += navigationBarHeight;
        }
        view.setLayoutParams(lp);
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(),
                view.getPaddingRight(), view.getPaddingBottom() + navigationBarHeight);
    }

    /**
     * 为了配合状态栏而增加View的marginBottom,增加的值为导航栏高度
     * 【一般是给宽度为wrap_content的View设置的】
     */
    public static void compatMarginWithNavigationBar(View view)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams)
        {
            ((ViewGroup.MarginLayoutParams) lp).bottomMargin += getNavigationBarHeight();
        }
        view.setLayoutParams(lp);
    }

    /********************************************其他方法****************************************************/

    /**
     * 设置全屏【隐藏状态栏、导航栏半透明】
     */
    //    public static void setFullScreen(Activity activity)
    //    {
    //        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    //        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    //                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    //        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    //    }

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
     * 设置状态栏透明
     * 【xml不要设置"fitsystemwindow=true"】
     */
    public static void setStatusBarTransparent(Activity activity)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //改变状态栏颜色
            window.setStatusBarColor(Color.TRANSPARENT);
        } else
        {
            //设置状态栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏和导航栏全透明
     * 【xml不要设置"fitsystemwindow=true"】
     */
    public static void setAllBarTransparent(Activity activity)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //改变状态栏颜色
            window.setStatusBarColor(Color.TRANSPARENT);
            //改变导航栏颜色
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else
        {
            //设置状态栏和导航栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 设置沉浸式模式
     * <p>需要在{@link Activity#onWindowFocusChanged(boolean)}内调用，或者界面完全绘制完成后调用</p>
     */
    public static void immsiveMode(Activity activity, boolean hasFocus)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        if (hasFocus)
        {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
