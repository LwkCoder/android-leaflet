package com.lwkandroid.lib.common.widgets.view.iface;

import android.graphics.Canvas;

/**
 * @description: 裁剪布局
 * @author: LWK
 * @date: 2020/8/19 14:56
 */
public interface IClip
{
    void dispatchDraw(Canvas canvas);

    void onLayout(boolean changed, int left, int top, int right, int bottom);
}
