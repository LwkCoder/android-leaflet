/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lwkandroid.lib.core.net.cache.core;


import android.text.TextUtils;

import com.lwkandroid.lib.core.net.bean.ApiDiskCacheBean;

import okio.ByteString;


/**
 * <p>描述：缓存核心管理类</p>
 * <p>
 * 1.采用LruDiskCache<br>
 * 2.对Key进行MD5加密<br>
 *
 * @author LWK
 */
public class CacheCore
{
    private static final String TAG = "CacheCore";

    private DiskLruCacheWrapper mDiskLruCache;

    public CacheCore(DiskLruCacheWrapper disk)
    {
        this.mDiskLruCache = disk;
    }


    /**
     * 读取
     */
    public synchronized <T> ApiDiskCacheBean<T> load(Class<T> clazz, String key, long time)
    {
        String cacheKey = TextUtils.isEmpty(key) ? null : ByteString.of(key.getBytes()).md5().hex();
        if (mDiskLruCache != null && cacheKey != null)
        {
            ApiDiskCacheBean<T> result = mDiskLruCache.load(clazz, cacheKey, time);
            if (result != null)
            {
                if (result.getCacheTime() == -1 || result.getUpdateDate() + result.getCacheTime() > System.currentTimeMillis())
                {
                    // 未过期
                    return result;
                }

                // 已过期
                mDiskLruCache.remove(cacheKey);
            }
        }

        return null;
    }

    /**
     * 保存
     */
    public synchronized <T> boolean save(String key, T value)
    {
        String cacheKey = TextUtils.isEmpty(key) ? null : ByteString.of(key.getBytes()).md5().hex();
        return (mDiskLruCache != null && cacheKey != null) && mDiskLruCache.save(cacheKey, value);
    }

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    public synchronized boolean containsKey(String key)
    {
        String cacheKey = TextUtils.isEmpty(key) ? null : ByteString.of(key.getBytes()).md5().hex();
        return (mDiskLruCache != null && cacheKey != null) && mDiskLruCache.containsKey(cacheKey);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public synchronized boolean remove(String key)
    {
        String cacheKey = TextUtils.isEmpty(key) ? null : ByteString.of(key.getBytes()).md5().hex();
        return (mDiskLruCache != null && cacheKey != null) && mDiskLruCache.remove(cacheKey);
    }

    /**
     * 清空缓存
     */
    public synchronized boolean clear()
    {
        if (mDiskLruCache != null)
        {
            return mDiskLruCache.clear();
        }
        return false;
    }

}
