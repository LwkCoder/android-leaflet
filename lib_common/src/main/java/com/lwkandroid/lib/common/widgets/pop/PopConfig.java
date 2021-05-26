package com.lwkandroid.lib.common.widgets.pop;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.StyleRes;

/**
 * Description:PopupWindow配置
 *
 * @author LWK
 * @date 2020/4/8
 */
final class PopConfig
{
    private int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private boolean mFocusable = true;
    private boolean mCanceledOnTouchOutside = true;
    private boolean mTouchable = true;
    private @StyleRes
    int mAnimStyle = -1;
    private int mInputMethodMode = PopupWindow.INPUT_METHOD_NEEDED;
    private int mSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

    public int getWidth()
    {
        return mWidth;
    }

    public void setWidth(int width)
    {
        this.mWidth = width;
    }

    public int getHeight()
    {
        return mHeight;
    }

    public void setHeight(int height)
    {
        this.mHeight = height;
    }

    public boolean isFocusable()
    {
        return mFocusable;
    }

    public void setFocusable(boolean b)
    {
        this.mFocusable = b;
    }

    public boolean isCanceledOnTouchOutside()
    {
        return mCanceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean b)
    {
        this.mCanceledOnTouchOutside = b;
    }

    public int getAnimStyle()
    {
        return mAnimStyle;
    }

    public void setAnimStyle(int animStyle)
    {
        this.mAnimStyle = animStyle;
    }

    public boolean isTouchable()
    {
        return mTouchable;
    }

    public void setTouchable(boolean b)
    {
        this.mTouchable = b;
    }

    public int getInputMethodMode()
    {
        return mInputMethodMode;
    }

    public void setInputMethodMode(int mode)
    {
        this.mInputMethodMode = mode;
    }

    public int getSoftInputMode()
    {
        return mSoftInputMode;
    }

    public void setSoftInputMode(int mode)
    {
        this.mSoftInputMode = mode;
    }
}
