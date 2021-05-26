package com.lwkandroid.lib.common.widgets.view.iface;

import android.view.MotionEvent;
import android.view.View;

/**
 * TextView特性功能接口
 */
public interface ITextViewFeature
{
    void setEnabled(boolean enabled);

    void onTouchEvent(MotionEvent event);

    void setSelected(boolean selected);

    void onVisibilityChanged(View changedView, int visibility);
}