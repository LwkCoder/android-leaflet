package com.lwkandroid.lib.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IdRes;

/**
 * Description:注解实现findViewById
 *
 * @author LWK
 * @date 2020/4/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FindView
{
    @IdRes int value();
}
