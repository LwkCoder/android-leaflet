package com.lwkandroid.lib.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IdRes;

/**
 * Description:注解实现多个View的点击监听
 *
 * @author LWK
 * @date 2020/4/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ClickViews
{
    @IdRes int[] values();
}
