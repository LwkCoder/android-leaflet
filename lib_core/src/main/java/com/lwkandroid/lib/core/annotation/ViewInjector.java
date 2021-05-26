package com.lwkandroid.lib.core.annotation;

import android.app.Activity;
import android.view.View;

import com.lwkandroid.lib.core.log.KLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.fragment.app.Fragment;

/**
 * Description:FindView、ClickView、ClickViews注解绑定类
 *
 * @author LWK
 * @date 2020/4/15
 */
public final class ViewInjector
{
    private ViewInjector()
    {
    }

    public static void with(Activity activity)
    {
        with(activity, activity.getWindow().getDecorView());
    }

    public static void with(Fragment fragment)
    {
        with(fragment, fragment.getView());
    }

    public static void with(Object object, View contentView)
    {
        if (object == null || contentView == null)
        {
            KLog.e("ViewInjector can not inject with null object or null contentView.");
            return;
        }

        Class cls = object.getClass();

        try
        {
            //关联FindView注解
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields)
            {
                FindView finder = field.getAnnotation(FindView.class);
                if (finder != null)
                {
                    int viewId = finder.value();
                    if (viewId == View.NO_ID)
                    {
                        continue;
                    }
                    View view = contentView.findViewById(viewId);
                    field.setAccessible(true);
                    field.set(object, view);
                }
            }
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        //关联ClickView、ClickViews注解
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods)
        {
            ClickView viewClicker = null;
            ClickViews viewsClicker = null;
            if ((viewClicker = method.getAnnotation(ClickView.class)) != null)
            {
                int viewId = viewClicker.value();
                if (viewId == View.NO_ID)
                {
                    continue;
                }
                View view = contentView.findViewById(viewId);
                if (view == null)
                {
                    KLog.e("ViewInjector can not find view with id=" + viewId + " in " + object.getClass().getName());
                    continue;
                }
                view.setOnClickListener(arg0 -> {
                    try
                    {
                        method.setAccessible(true);
                        method.invoke(object, view);
                    } catch (IllegalAccessException | InvocationTargetException e)
                    {
                        e.printStackTrace();
                    }
                });
            } else if ((viewsClicker = method.getAnnotation(ClickViews.class)) != null)
            {
                int[] viewIds = viewsClicker.values();
                for (int viewId : viewIds)
                {
                    if (viewId == View.NO_ID)
                    {
                        continue;
                    }
                    final View view = contentView.findViewById(viewId);
                    if (view == null)
                    {
                        KLog.e("ViewInjector can not find view with id=" + viewId + " in " + object.getClass().getName());
                        continue;
                    }
                    view.setOnClickListener(arg0 -> {
                        try
                        {
                            method.setAccessible(true);
                            method.invoke(object, viewId, view);
                        } catch (IllegalAccessException | InvocationTargetException e)
                        {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }
}
