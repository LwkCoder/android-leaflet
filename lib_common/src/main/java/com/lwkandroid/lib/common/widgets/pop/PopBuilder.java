package com.lwkandroid.lib.common.widgets.pop;

import androidx.annotation.StyleRes;

/**
 * Description:
 *
 * @author LWK
 * @date 2020/4/8
 */
public final class PopBuilder
{
    private PopConfig mConfig;
    private IPopUiController mController;

    private PopBuilder()
    {
        mConfig = new PopConfig();
    }

    public static PopBuilder with(IPopUiController controller)
    {
        return new PopBuilder().setUiController(controller);
    }

    public PopBuilder setUiController(IPopUiController controller)
    {
        this.mController = controller;
        return this;
    }

    public PopBuilder setLayoutParams(int width, int height)
    {
        mConfig.setWidth(width);
        mConfig.setHeight(height);
        return this;
    }

    public PopBuilder setFocusable(boolean focusable)
    {
        mConfig.setFocusable(focusable);
        return this;
    }

    public PopBuilder setCanceledOnTouchOutside(boolean b)
    {
        mConfig.setCanceledOnTouchOutside(b);
        return this;
    }

    public PopBuilder setAnimStyle(@StyleRes int style)
    {
        mConfig.setAnimStyle(style);
        return this;
    }

    public PopBuilder setTouchable(boolean b)
    {
        mConfig.setTouchable(b);
        return this;
    }

    public PopBuilder setSoftInputMode(int mode)
    {
        mConfig.setSoftInputMode(mode);
        return this;
    }

    public PopBuilder setInputMethodMode(int mode)
    {
        mConfig.setInputMethodMode(mode);
        return this;
    }

    public WingsPopupWindow build()
    {
        return WingsPopupWindow.create(mConfig, mController);
    }
}
