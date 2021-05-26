package com.lwkandroid.lib.core.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.lwkandroid.lib.core.context.AppContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

/**
 * Description:Activity生命周期帮助类
 *
 * @author LWK
 * @date 2020/2/24
 */
public final class ActivityLifecycleHelper implements Application.ActivityLifecycleCallbacks
{
    private ActivityLifecycleHelper()
    {
    }

    public static ActivityLifecycleHelper get()
    {
        return Holder.INSTANCE;
    }

    private static final class Holder
    {
        static final ActivityLifecycleHelper INSTANCE = new ActivityLifecycleHelper();
    }

    private final LinkedList<Activity> mActivityList = new LinkedList<>();
    private final List<OnAppStatusChangedListener> mStatusListeners = new ArrayList<>();
    private final Map<Activity, List<OnActivityLifecycleListener>> mActivityLifecycleListenersMap = new ConcurrentHashMap<>();
    private int mForegroundCount = 0;
    private int mConfigCount = 0;
    private boolean mIsBackground = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public Activity getTopActivity()
    {
        if (!mActivityList.isEmpty())
        {
            for (int i = mActivityList.size() - 1; i >= 0; i--)
            {
                Activity activity = mActivityList.get(i);
                if (activity == null
                        || activity.isFinishing()
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()))
                {
                    continue;
                }
                return activity;
            }
        }
        Activity topActivityByReflect = getTopActivityByReflect();
        if (topActivityByReflect != null)
        {
            setTopActivity(topActivityByReflect);
        }
        return topActivityByReflect;
    }

    public List<Activity> getActivityList()
    {
        return mActivityList;
    }

    public void addOnAppStatusChangedListener(final OnAppStatusChangedListener listener)
    {
        mStatusListeners.add(listener);
    }

    public void removeOnAppStatusChangedListener(final OnAppStatusChangedListener listener)
    {
        mStatusListeners.remove(listener);
    }

    public void addActivityLifecycleListener(final Activity activity,
                                             final OnActivityLifecycleListener listener)
    {
        if (activity == null || listener == null)
        {
            return;
        }
        runOnUiThread(() -> addActivityLifecycleListenerInner(activity, listener));
    }

    public void removeActivityLifecycleListeners(final Activity activity)
    {
        if (activity == null)
        {
            return;
        }
        runOnUiThread(() -> mActivityLifecycleListenersMap.remove(activity));
    }

    public void removeActivityLifecycleListeners(final Activity activity,
                                                 final OnActivityLifecycleListener callbacks)
    {
        if (activity == null || callbacks == null)
        {
            return;
        }
        runOnUiThread(() -> removeActivityLifecycleListenerInner(activity, callbacks));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////    以下是重写方法   /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState)
    {
        setTopActivity(activity);
        invokeActivityLifecycleListener(activity, Lifecycle.Event.ON_CREATE);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity)
    {
        if (!mIsBackground)
        {
            setTopActivity(activity);
        }
        if (mConfigCount < 0)
        {
            ++mConfigCount;
        } else
        {
            ++mForegroundCount;
        }
        invokeActivityLifecycleListener(activity, Lifecycle.Event.ON_START);
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity)
    {
        setTopActivity(activity);
        if (mIsBackground)
        {
            mIsBackground = false;
            postStatus(activity, true);
        }
        processHideSoftInputOnActivityDestroy(activity, false);
        invokeActivityLifecycleListener(activity, Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity)
    {
        invokeActivityLifecycleListener(activity, Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity)
    {
        if (activity.isChangingConfigurations())
        {
            --mConfigCount;
        } else
        {
            --mForegroundCount;
            if (mForegroundCount <= 0)
            {
                mIsBackground = true;
                postStatus(activity, false);
            }
        }
        processHideSoftInputOnActivityDestroy(activity, true);
        invokeActivityLifecycleListener(activity, Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState)
    {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity)
    {
        mActivityList.remove(activity);
        fixSoftInputLeaks(activity.getWindow());
        invokeActivityLifecycleListener(activity, Lifecycle.Event.ON_DESTROY);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////    以下是私有方法   /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void addActivityLifecycleListenerInner(final Activity activity,
                                                   final OnActivityLifecycleListener listener)
    {
        List<OnActivityLifecycleListener> listeners = mActivityLifecycleListenersMap.get(activity);
        if (listeners == null)
        {
            listeners = new ArrayList<>();
            mActivityLifecycleListenersMap.put(activity, listeners);
        } else
        {
            if (listeners.contains(listener))
            {
                return;
            }
        }
        listeners.add(listener);
    }

    private void removeActivityLifecycleListenerInner(final Activity activity,
                                                      final OnActivityLifecycleListener listener)
    {
        List<OnActivityLifecycleListener> listeners = mActivityLifecycleListenersMap.get(activity);
        if (listeners != null && !listeners.isEmpty())
        {
            listeners.remove(listener);
        }
    }

    private void invokeActivityLifecycleListener(Activity activity, Lifecycle.Event event)
    {
        List<OnActivityLifecycleListener> listeners = mActivityLifecycleListenersMap.get(activity);
        if (listeners != null)
        {
            for (OnActivityLifecycleListener listener : listeners)
            {
                listener.onLifecycleChanged(activity, event);
                if (event.equals(Lifecycle.Event.ON_CREATE))
                {
                    listener.onActivityCreated(activity);
                } else if (event.equals(Lifecycle.Event.ON_START))
                {
                    listener.onActivityStarted(activity);
                } else if (event.equals(Lifecycle.Event.ON_RESUME))
                {
                    listener.onActivityResumed(activity);
                } else if (event.equals(Lifecycle.Event.ON_PAUSE))
                {
                    listener.onActivityPaused(activity);
                } else if (event.equals(Lifecycle.Event.ON_STOP))
                {
                    listener.onActivityStopped(activity);
                } else if (event.equals(Lifecycle.Event.ON_DESTROY))
                {
                    listener.onActivityDestroyed(activity);
                }
            }
            if (event.equals(Lifecycle.Event.ON_DESTROY))
            {
                mActivityLifecycleListenersMap.remove(activity);
            }
        }
    }

    private void setTopActivity(final Activity activity)
    {
        if (mActivityList.contains(activity))
        {
            if (!mActivityList.getLast().equals(activity))
            {
                mActivityList.remove(activity);
                mActivityList.addLast(activity);
            }
        } else
        {
            mActivityList.addLast(activity);
        }
    }

    private void postStatus(final Activity activity, final boolean isForeground)
    {
        if (mStatusListeners.isEmpty())
        {
            return;
        }
        for (OnAppStatusChangedListener statusListener : mStatusListeners)
        {
            if (isForeground)
            {
                statusListener.onForeground(activity);
            } else
            {
                statusListener.onBackground(activity);
            }
        }
    }

    private void processHideSoftInputOnActivityDestroy(final Activity activity, boolean isSave)
    {
        if (isSave)
        {
            final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            final int softInputMode = attrs.softInputMode;
            activity.getWindow().getDecorView().setTag(-123, softInputMode);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } else
        {
            final Object tag = activity.getWindow().getDecorView().getTag(-123);
            if (!(tag instanceof Integer))
            {
                return;
            }
            runOnUiThreadDelayed(() -> {
                Window window = activity.getWindow();
                if (window != null)
                {
                    window.setSoftInputMode(((Integer) tag));
                }
            }, 100);
        }
    }

    private void runOnUiThreadDelayed(Runnable runnable, long millSeconds)
    {
        mHandler.postDelayed(runnable, millSeconds);
    }

    private void runOnUiThread(Runnable runnable)
    {
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            runnable.run();
        } else
        {
            mHandler.post(runnable);
        }
    }

    private void fixSoftInputLeaks(final Window window)
    {
        InputMethodManager imm = (InputMethodManager) AppContext.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null)
        {
            return;
        }
        String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
        for (String leakView : leakViews)
        {
            try
            {
                Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                if (!leakViewField.isAccessible())
                {
                    leakViewField.setAccessible(true);
                }
                Object obj = leakViewField.get(imm);
                if (!(obj instanceof View))
                {
                    continue;
                }
                View view = (View) obj;
                if (view.getRootView() == window.getDecorView().getRootView())
                {
                    leakViewField.set(imm, null);
                }
            } catch (Throwable ignore)
            {/**/}
        }
    }

    private Activity getTopActivityByReflect()
    {
        try
        {
            @SuppressLint("PrivateApi")
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Object currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field mActivityListField = activityThreadClass.getDeclaredField("mActivityList");
            mActivityListField.setAccessible(true);
            Map activities = (Map) mActivityListField.get(currentActivityThreadMethod);
            if (activities == null)
            {
                return null;
            }
            for (Object activityRecord : activities.values())
            {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord))
                {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e)
        {
            Log.e("ActivityLifecycleHelper", e.getMessage());
        }
        return null;
    }
}
