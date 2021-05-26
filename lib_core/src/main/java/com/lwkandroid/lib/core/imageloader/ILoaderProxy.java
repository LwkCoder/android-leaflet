package com.lwkandroid.lib.core.imageloader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.widget.ImageView;

/**
 * 定义给图片参数直接调用图片加载方法的接口
 *
 * @author LWK
 */

public interface ILoaderProxy
{
    void show(Context context, ImageView imageView);

    void show(Activity activity, ImageView imageView);

    void show(Fragment fragment, ImageView imageView);

    void show(androidx.fragment.app.Fragment fragment, ImageView imageView);
}
