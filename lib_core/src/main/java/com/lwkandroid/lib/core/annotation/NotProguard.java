package com.lwkandroid.lib.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，标识避免Proguard混淆
 * 【需在proguard-rules.pro下添加如下指令】
 * -keepattributes *Annotation*
 * -keep @com.lwkandroid.lib.core.annotation.NotProguard class * {*;}
 * -keep class * {@com.lwkandroid.lib.core.java.annotation.NotProguard <fields>;}
 * -keepclassmembers class * {@com.lwkandroid.lib.core.java.annotation.NotProguard <methods>;}
 *
 * @author LWK
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface NotProguard
{

}


