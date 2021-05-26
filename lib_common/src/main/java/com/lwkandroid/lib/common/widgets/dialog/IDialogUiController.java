package com.lwkandroid.lib.common.widgets.dialog;

import android.content.DialogInterface;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.lifecycle.LifecycleEventObserver;

/**
 * Description:定义Dialog UI层的操作接口
 *
 * @author LWK
 * @date 2020/4/7
 */
public interface IDialogUiController extends LifecycleEventObserver
{
    @LayoutRes
    int getLayoutId();

    void onCreateView(ViewGroup parentView, WingsDialog dialog);

    void onShow(DialogInterface dialog);

    void onDismiss(DialogInterface dialog);

    void onCancel(DialogInterface dialog);
}
