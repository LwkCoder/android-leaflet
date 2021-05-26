package com.lwkandroid.lib.core.net.constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * 缓存类型
 *
 * @author LWK
 */
public final class ApiCacheMode
{
    /**
     * 不缓存
     */
    public static final int NO_CACHE = 0x00000001;

    /**
     * 先请求网络数据，失败后尝试获取本地缓存
     */
    public static final int REMOTE_FIRST_OR_CACHE = 0x00000002;

    /**
     * 先请求本地缓存，失败或为空获取网络数据
     */
    public static final int CACHE_FIRST_OR_REMOTE = 0x00000003;

    /**
     * 只请求网络数据，但仍然会缓存
     */
    public static final int REMOTE_ONLY = 0x00000004;

    /**
     * 只加载本地缓存
     */
    public static final int CACHE_ONLY = 0x00000005;

    /**
     * 先返回本地缓存，然后获取网络数据，回调2次
     */
    public static final int CACHE_FIRST_AFTER_REMOTE = 0x00000006;

    /**
     * 先返回本地缓存，然后获取网络数据，如果数据一样就忽略
     */
    public static final int CACHE_FIRST_AFTER_REMOTE_IF_DIFF = 0x00000007;


    @IntDef({NO_CACHE, REMOTE_FIRST_OR_CACHE, CACHE_FIRST_OR_REMOTE
            , REMOTE_ONLY, CACHE_ONLY, CACHE_FIRST_AFTER_REMOTE, CACHE_FIRST_AFTER_REMOTE_IF_DIFF})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode
    {
    }
}
