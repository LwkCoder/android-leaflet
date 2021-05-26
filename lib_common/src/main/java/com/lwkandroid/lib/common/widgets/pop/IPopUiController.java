package com.lwkandroid.lib.common.widgets.pop;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

/**
 * Description:
 *
 * @author LWK
 * @date 2020/4/8
 */
public interface IPopUiController
{
    @LayoutRes
    int getLayoutId();

    void onCreateView(ViewGroup parentView, WingsPopupWindow popupWindow);

    void onDismiss();
}
