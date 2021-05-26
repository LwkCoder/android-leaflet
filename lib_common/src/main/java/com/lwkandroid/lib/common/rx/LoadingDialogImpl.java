package com.lwkandroid.lib.common.rx;


import android.content.DialogInterface;

import com.lwkandroid.lib.common.widgets.dialog.DialogBuilder;
import com.lwkandroid.lib.common.widgets.dialog.WingsDialog;
import com.lwkandroid.lib.common.widgets.ui.LoadingDialogController;
import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.callback.WingsConsumer;

import androidx.fragment.app.FragmentActivity;

/**
 * @description: Loading弹窗实现类
 * @author: LWK
 * @date: 2020/6/23 14:27
 */
class LoadingDialogImpl implements DialogInterface.OnDismissListener
{
    private WingsDialog mDialog;
    private DialogBuilder mDialogBuilder;
    private LoadingDialogController mDialogController;
    private WingsConsumer<DialogInterface> mDismissConsumer;

    public LoadingDialogImpl(WingsConsumer<DialogInterface> dismissConsumer)
    {
        this.mDismissConsumer = dismissConsumer;
        init();
    }

    void init()
    {
        mDialogController = new LoadingDialogController();
        mDialogBuilder = DialogBuilder.with(mDialogController);
    }

    public DialogBuilder getDialogBuilder()
    {
        if (mDialogBuilder == null)
        {
            throw new IllegalArgumentException("You should call function init() first.");
        }
        return mDialogBuilder;
    }

    public LoadingDialogController getDialogController()
    {
        return mDialogController;
    }

    public void showDialog()
    {
        dismissDialog();

        mDialog = mDialogBuilder.build();
        mDialog.setDialogDismissListener(this);
        mDialog.show((FragmentActivity) ActivityLifecycleHelper.get().getTopActivity());
    }

    public void dismissDialog()
    {
        if (mDialog != null)
        {
            mDialog.dismiss();
        }
        mDialog = null;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        if (mDismissConsumer != null)
        {
            mDismissConsumer.accept(dialog);
        }
    }
}
