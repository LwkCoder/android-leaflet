package com.lwkandroid.lib.common.widgets.ui;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.widgets.dialog.IDialogUiController;
import com.lwkandroid.lib.common.widgets.dialog.WingsDialog;
import com.lwkandroid.lib.common.widgets.view.RTextView;
import com.lwkandroid.lib.core.utils.common.ResourceUtils;
import com.lwkandroid.lib.core.utils.common.StringUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * Description:通用Dialog样式控制层
 *
 * @author LWK
 * @date 2020/4/7
 */
public class CommonDialogController implements IDialogUiController
{
    private final int DEFAULT_TEXT_COLOR = ResourceUtils.getColor(R.color.text_black_normal);
    private String mTitle;
    private String mContent;
    private String mPositive;
    private String mNegative;
    private int mTitleColor = DEFAULT_TEXT_COLOR;
    private int mContentColor = DEFAULT_TEXT_COLOR;
    private int mNegativeColor = DEFAULT_TEXT_COLOR;
    private int mPositiveColor = DEFAULT_TEXT_COLOR;
    private RTextView mTvTitle;
    private RTextView mTvContent;
    private RTextView mTvPositive;
    private RTextView mTvNegative;

    public CommonDialogController setTitle(String title)
    {
        this.mTitle = title;
        return this;
    }

    public CommonDialogController setTitle(int resId)
    {
        this.mTitle = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogController setContent(int resId)
    {
        this.mContent = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogController setContent(String content)
    {
        this.mContent = content;
        return this;
    }

    public CommonDialogController setPositive(String positive)
    {
        this.mPositive = positive;
        return this;
    }

    public CommonDialogController setPositive(int resId)
    {
        this.mPositive = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogController setNegative(int resId)
    {
        this.mNegative = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogController setNegative(String negative)
    {
        this.mNegative = negative;
        return this;
    }

    public CommonDialogController setTitleTextColor(@ColorInt int color)
    {
        this.mTitleColor = color;
        return this;
    }

    public CommonDialogController setTitleTextColorRes(@ColorRes int color)
    {
        this.mTitleColor = ResourceUtils.getColor(color);
        return this;
    }

    public CommonDialogController setContentTextColor(@ColorInt int color)
    {
        this.mContentColor = color;
        return this;
    }

    public CommonDialogController setContentTextColorRes(@ColorRes int color)
    {
        this.mContentColor = ResourceUtils.getColor(color);
        return this;
    }

    public CommonDialogController setNegativeColor(@ColorInt int color)
    {
        this.mNegativeColor = color;
        return this;
    }

    public CommonDialogController setNegativeColorRes(@ColorRes int color)
    {
        this.mNegativeColor = ResourceUtils.getColor(color);
        return this;
    }

    public CommonDialogController setPositiveColor(@ColorInt int color)
    {
        this.mPositiveColor = color;
        return this;
    }

    public CommonDialogController setPositiveColorRes(@ColorRes int color)
    {
        this.mPositiveColor = ResourceUtils.getColor(color);
        return this;
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.dialog_content_template;
    }

    @Override
    public void onCreateView(ViewGroup parentView, WingsDialog dialog)
    {
        mTvTitle = parentView.findViewById(R.id.tv_dialog_template_title);
        mTvContent = parentView.findViewById(R.id.tv_dialog_template_content);
        mTvPositive = parentView.findViewById(R.id.tv_dialog_template_positive);
        mTvNegative = parentView.findViewById(R.id.tv_dialog_template_negative);

        mTvTitle.getHelper().setTextColorNormal(mTitleColor);
        mTvContent.getHelper().setTextColorNormal(mContentColor);
        mTvNegative.getHelper().setTextColorNormal(mNegativeColor);
        mTvPositive.getHelper().setTextColorNormal(mPositiveColor);

        mTvTitle.setText(mTitle);
        mTvContent.setText(mContent);
        mTvPositive.setText(mPositive);
        mTvNegative.setText(mNegative);

        mTvTitle.setVisibility(StringUtils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);
        mTvContent.setVisibility(StringUtils.isEmpty(mContent) ? View.GONE : View.VISIBLE);
        mTvPositive.setVisibility(StringUtils.isEmpty(mPositive) ? View.GONE : View.VISIBLE);
        mTvNegative.setVisibility(StringUtils.isEmpty(mNegative) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onShow(DialogInterface dialog)
    {

    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {

    }

    @Override
    public void onCancel(DialogInterface dialog)
    {

    }

    public int getPositiveButtonId()
    {
        return R.id.tv_dialog_template_positive;
    }

    public int getNegativeButtonId()
    {
        return R.id.tv_dialog_template_negative;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event)
    {

    }
}
