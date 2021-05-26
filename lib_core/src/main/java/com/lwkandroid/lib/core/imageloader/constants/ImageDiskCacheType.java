package com.lwkandroid.lib.core.imageloader.constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * 硬盘缓存策略
 *
 * @author LWK
 */

public class ImageDiskCacheType
{
    /**
     * 不缓存
     */
    public static final int NONE = 0X00000001;
    /**
     * 全尺寸缓存
     */
    public static final int ALL = 0X00000002;
    /**
     * 缓存原始数据（原始尺寸的数据）
     */
    public static final int DATA = 0X00000003;
    /**
     * 缓存最终数据（最终尺寸的数据）
     */
    public static final int RESOURCE = 0X00000004;
    /**
     * 默认缓存
     */
    public static final int DEFAULT = 0X00000005;

    @IntDef({NONE, ALL, DATA, RESOURCE, DEFAULT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type
    {
    }
}
