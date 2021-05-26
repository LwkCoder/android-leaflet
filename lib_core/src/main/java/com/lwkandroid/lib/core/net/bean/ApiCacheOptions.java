package com.lwkandroid.lib.core.net.bean;

import android.text.TextUtils;

import com.lwkandroid.lib.core.net.cache.core.CacheCore;
import com.lwkandroid.lib.core.net.cache.core.DiskLruCacheWrapper;
import com.lwkandroid.lib.core.net.cache.operator.GsonDiskOperator;
import com.lwkandroid.lib.core.net.cache.operator.IDiskCacheOperator;
import com.lwkandroid.lib.core.net.constants.ApiCacheMode;
import com.lwkandroid.lib.core.net.constants.ApiConstants;
import com.lwkandroid.lib.core.utils.common.PathUtils;
import com.lwkandroid.lib.core.utils.common.SDCardUtils;

import java.io.File;

/**
 * 缓存参数
 *
 * @author LWK
 */
public class ApiCacheOptions
{
    /**
     * 缓存类型
     */
    private @ApiCacheMode.Mode
    int cacheMode = ApiCacheMode.NO_CACHE;

    /**
     * App版本
     */
    private int appVersion = 1;

    /**
     * 缓存路径
     */
    private String cachePath;

    /**
     * 缓存key
     */
    private String cacheKey;

    /**
     * 缓存时长
     */
    private long cacheTime = -1;

    /**
     * 硬盘缓存大小
     */
    private long diskMaxSize = -1;

    /**
     * 缓存转换器
     */
    private IDiskCacheOperator cacheOperator = null;

    /**
     * 缓存核心管理类
     */
    private CacheCore cacheCore;

    private ApiCacheOptions()
    {
    }

    public int getCacheMode()
    {
        return cacheMode;
    }

    public void setCacheMode(@ApiCacheMode.Mode int cacheMode)
    {
        this.cacheMode = cacheMode;
    }

    public int getAppVersion()
    {
        return appVersion;
    }

    public void setAppVersion(int appVersion)
    {
        this.appVersion = appVersion;
    }

    public String getCachePath()
    {
        return cachePath;
    }

    public void setCachePath(String cachePath)
    {
        this.cachePath = cachePath;
    }

    public String getCacheKey()
    {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey)
    {
        this.cacheKey = cacheKey;
    }

    public long getCacheTime()
    {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime)
    {
        this.cacheTime = cacheTime;
    }

    public long getDiskMaxSize()
    {
        return diskMaxSize;
    }

    public void setDiskMaxSize(long diskMaxSize)
    {
        this.diskMaxSize = diskMaxSize;
    }

    public IDiskCacheOperator getCacheOperator()
    {
        return cacheOperator;
    }

    public void setCacheOperator(IDiskCacheOperator cacheOperator)
    {
        this.cacheOperator = cacheOperator;
    }

    public CacheCore getCacheCore()
    {
        return cacheCore;
    }

    public void setCacheCore(CacheCore cacheCore)
    {
        this.cacheCore = cacheCore;
    }


    /********************************* 建造模式 *********************************************************************************************************/
    public static final class Builder
    {
        private ApiCacheOptions options;

        public Builder()
        {
            options = new ApiCacheOptions();
        }

        public Builder cacheMode(int mode)
        {
            options.setCacheMode(mode);
            return this;
        }

        public Builder appVersion(int version)
        {
            options.setAppVersion(version);
            return this;
        }

        public Builder cachePath(String cachePath)
        {
            options.setCachePath(cachePath);
            return this;
        }

        public Builder cacheKey(String cacheKey)
        {
            options.setCacheKey(cacheKey);
            return this;
        }

        public Builder diskMaxSize(long maxSize)
        {
            options.setDiskMaxSize(maxSize);
            return this;
        }

        public Builder cacheTime(long cacheTime)
        {
            options.setCacheTime(cacheTime);
            return this;
        }

        public Builder cacheOpeartor(IDiskCacheOperator opeartor)
        {
            options.setCacheOperator(opeartor);
            return this;
        }

        public ApiCacheOptions build()
        {
            //检查app版本
            if (options.getAppVersion() < 1)
            {
                options.setAppVersion(1);
            }
            //检查缓存地址
            if (TextUtils.isEmpty(options.getCachePath()))
            {
                options.setCachePath(new StringBuffer()
                        .append(PathUtils.getExAppCachePath())
                        .append("/net/")
                        .toString());
            }
            File cacheFile = new File(options.getCachePath());
            if (!cacheFile.exists())
            {
                cacheFile.mkdirs();
            }
            //检查转换器
            if (options.getCacheOperator() == null)
            {
                options.setCacheOperator(new GsonDiskOperator());
            }
            //检查缓存磁盘容量
            if (options.getDiskMaxSize() < ApiConstants.DISK_CACHE_MIN_SIZE)
            {
                options.setDiskMaxSize(ApiConstants.DISK_CACHE_MIN_SIZE);
            }
            //设置缓存核心管理
            DiskLruCacheWrapper diskLruCache = new DiskLruCacheWrapper(
                    options.getCacheOperator(),
                    cacheFile,
                    options.getAppVersion(),
                    Math.min(SDCardUtils.getFreeSpace(), options.getDiskMaxSize()));
            options.setCacheCore(new CacheCore(diskLruCache));
            return options;
        }
    }
}
