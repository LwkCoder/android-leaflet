package com.sources.javacode.utils;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.lwkandroid.lib.core.imageloader.ImageLoader;
import com.lwkandroid.lib.core.utils.common.SDCardUtils;

/**
 * 自定义GlideModule
 * 【该类必须放在Application模块中】
 *
 * @author LWK
 */

@GlideModule
public final class GlideAppModule extends AppGlideModule
{
    /**
     * 默认缓存里的倍数
     */
    private static final float MEMORY_CACHE_COUNT = 1.2f;
    /**
     * 磁盘缓存容量最大值,300M
     */
    private static final int MAX_DISK_CACHE_SIZE = 314572800;

    @Override
    public void applyOptions(Context context, GlideBuilder builder)
    {
        super.applyOptions(context, builder);
        //修改内存容量和位图缓存池大小
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        int customMemoryCacheSize = (int) (MEMORY_CACHE_COUNT * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (MEMORY_CACHE_COUNT * defaultBitmapPoolSize);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
        //设置磁盘缓存
        long availableSize = SDCardUtils.getFreeSpace();
        builder.setDiskCache(new DiskLruCacheFactory(ImageLoader.getLoader().getCachePath(),
                availableSize < MAX_DISK_CACHE_SIZE ? (int) availableSize : MAX_DISK_CACHE_SIZE));
    }

    @Override
    public boolean isManifestParsingEnabled()
    {
        //v4版本不需要解析清单文件，加快速度
        return false;
    }
}
