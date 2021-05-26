package com.lwkandroid.lib.common.widgets.dialog;

import androidx.annotation.FloatRange;
import androidx.annotation.StyleRes;

/**
 * Description:Dialog快速创建入口
 *
 * @author LWK
 * @date 2020/4/7
 */
public final class DialogBuilder
{
    private DialogConfig mDialogConfig;
    private IDialogUiController mController;

    private DialogBuilder()
    {
        this.mDialogConfig = new DialogConfig();
    }

    public static DialogBuilder with(IDialogUiController controller)
    {
        return new DialogBuilder().setUiController(controller);
    }

    public DialogBuilder setUiController(IDialogUiController controller)
    {
        this.mController = controller;
        return this;
    }

    public DialogBuilder setLayoutParams(int width, int height)
    {
        this.mDialogConfig.setLayoutWidth(width);
        this.mDialogConfig.setLayoutHeight(height);
        return this;
    }

    public DialogBuilder setFocusable(boolean b)
    {
        this.mDialogConfig.setFocusable(b);
        return this;
    }

    public DialogBuilder setCancelable(boolean b)
    {
        this.mDialogConfig.setCancelable(b);
        return this;
    }

    public DialogBuilder setCanceledOnTouchOutside(boolean b)
    {
        this.mDialogConfig.setCanceledOnTouchOutside(b);
        return this;
    }

    public DialogBuilder setThemeStyle(@StyleRes int style)
    {
        this.mDialogConfig.setThemeStyle(style);
        return this;
    }

    public DialogBuilder setDarkWindowDegree(@FloatRange(from = 0.0, to = 1.0f) float degree)
    {
        this.mDialogConfig.setDarkWindowDegree(degree);
        return this;
    }

    public DialogBuilder setAnimStyle(@StyleRes int style)
    {
        this.mDialogConfig.setAnimStyle(style);
        return this;
    }

    public WingsDialog build()
    {
        return WingsDialog.create(mDialogConfig).setUiController(mController);
    }
}
