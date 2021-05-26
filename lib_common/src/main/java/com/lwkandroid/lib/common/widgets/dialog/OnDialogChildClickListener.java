package com.lwkandroid.lib.common.widgets.dialog;

import android.view.View;
import android.view.ViewGroup;

/**
 * Description:Dialog内子控件点击监听
 *
 * @author LWK
 * @date 2020/4/7
 */
public interface OnDialogChildClickListener
{
    void onDialogChildClicked(int viewId, View view, ViewGroup contentView, WingsDialog dialog);
}
