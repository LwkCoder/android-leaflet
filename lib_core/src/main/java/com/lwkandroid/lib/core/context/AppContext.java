package com.lwkandroid.lib.core.context;

import android.app.ActivityThread;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Description:全局Context提供者
 *
 * @author LWK
 * @date 2020/1/6
 */
public final class AppContext
{
    private static Context mContext;

    private AppContext() throws IllegalAccessException
    {
        throw new IllegalAccessException("Can't instantiate this class !");
    }

    public static Context get()
    {
        if (mContext == null)
        {
            mContext = ContextProvider.mContext;
        }
        if (mContext == null)
        {
            mContext = ActivityThread.currentApplication().getApplicationContext();
            if (mContext == null)
            {
                try
                {
                    final Class<?> clazz = Class.forName("android.app.ActivityThread");
                    final Method method = clazz.getDeclaredMethod("currentApplication");
                    mContext = (Context) method.invoke(null);
                } catch (Throwable ex)
                {
                    ex.printStackTrace();
                    Log.e("AppContext", "Can not access ActivityThread.currentApplication !");
                }
            }
        }
        if (mContext == null)
        {
            throw new IllegalArgumentException("Can not get context instance.");
        }
        return mContext;
    }
}
