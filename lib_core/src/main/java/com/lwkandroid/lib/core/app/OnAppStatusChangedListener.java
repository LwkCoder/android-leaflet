package com.lwkandroid.lib.core.app;

import android.app.Activity;

/**
 * Description:APP状态更改监听
 *
 * @author LWK
 * @date 2020/4/9
 */
public interface OnAppStatusChangedListener
{
    void onForeground(Activity activity);

    void onBackground(Activity activity);
}
