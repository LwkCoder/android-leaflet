package com.lwkandroid.lib.common.widgets.pop;

import android.view.View;
import android.view.ViewGroup;

/**
 * Description:Pop子控件点击事件
 *
 * @author LWK
 * @date 2019/6/10
 */
public interface OnPopChildClickListener
{
    void onPopChildClicked(int viewId, View view, ViewGroup contentView, WingsPopupWindow popupWindow);
}
