package com.lwkandroid.lib.core.imageloader.glide;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lwkandroid.lib.core.imageloader.callback.ImageDownLoadCallBack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 下载Target
 *
 * @author LWK
 */
public class DownLoadTarget<R> extends CustomTarget<R>
{
    private ImageDownLoadCallBack<R> mCallBack;

    public DownLoadTarget(ImageDownLoadCallBack<R> callBack)
    {
        super();
        this.mCallBack = callBack;
    }

    public DownLoadTarget(int width, int height, ImageDownLoadCallBack<R> callBack)
    {
        super(width, height);
        this.mCallBack = callBack;
    }

    private ImageDownLoadCallBack<R> getCallBack()
    {
        return mCallBack;
    }

    @Override
    public void onLoadStarted(@Nullable Drawable placeholder)
    {
        if (getCallBack() != null)
        {
            getCallBack().onImageDownloadStarted();
        }
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable)
    {
        if (getCallBack() != null)
        {
            getCallBack().onImageDownloadFailed();
        }
    }

    @Override
    public void onResourceReady(@NonNull R resource, @Nullable Transition<? super R> transition)
    {
        if (getCallBack() != null)
        {
            getCallBack().onImageDownloadSuccess(resource);
        }
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder)
    {

    }

    @Override
    public void onDestroy()
    {
        if (mCallBack != null)
        {
            mCallBack = null;
        }
    }
}
