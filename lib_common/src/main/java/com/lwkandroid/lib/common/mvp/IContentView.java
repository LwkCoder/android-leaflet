package com.lwkandroid.lib.common.mvp;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

/**
 * Description:Activity/Fragment 内容公共方法相关接口层
 *
 * @author LWK
 * @date 2020/2/25
 */
interface IContentView
{
    View getContentView();

    <T extends View> T find(@IdRes int resId);

    void addClick(@IdRes int resId);

    void addClick(@IdRes int resId, View.OnClickListener listener);

    void addClick(@IdRes int... resIds);

    void addClick(View view);

    void addClick(View view, View.OnClickListener listener);

    void addClick(View... views);

    void showShortToast(@StringRes int resId);

    void showShortToast(CharSequence message);

    void showLongToast(@StringRes int resId);

    void showLongToast(CharSequence message);
}
