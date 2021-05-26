package com.lwkandroid.lib.core.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;

import java.io.InputStream;

/**
 * 自定义GlideModule
 *
 * @author LWK
 */

@GlideModule
public final class GlideLibraryModule extends LibraryGlideModule
{

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry)
    {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(GlideOkClient.get().getClient()));
    }
}
