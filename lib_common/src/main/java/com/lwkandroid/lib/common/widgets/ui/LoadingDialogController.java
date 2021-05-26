package com.lwkandroid.lib.common.widgets.ui;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.widgets.dialog.IDialogUiController;
import com.lwkandroid.lib.common.widgets.dialog.WingsDialog;
import com.lwkandroid.lib.core.utils.common.ResourceUtils;
import com.lwkandroid.lib.core.utils.common.StringUtils;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * Description:Loading弹窗控制层
 *
 * @author LWK
 * @date 2020/4/7
 */
public class LoadingDialogController implements IDialogUiController
{
    private String mMessage;
    private ViewGroup mViewGroupRoot;
    private LoadingView mLoadingView;
    private TextView mTvMessage;

    public LoadingDialogController()
    {
    }

    public LoadingDialogController(String message)
    {
        this.mMessage = message;
    }

    public String getMessage()
    {
        return mMessage;
    }

    public void setMessage(@StringRes int resId)
    {
        setMessage(ResourceUtils.getString(resId));
    }

    public void setMessage(String message)
    {
        this.mMessage = message;
        //没有文字显示的时候，padding大一点
        //有文字显示的时候，padding左右稍大，上下稍小
        if (StringUtils.isTrimEmpty(mMessage))
        {
            if (mTvMessage != null)
            {
                mTvMessage.setText(null);
                mTvMessage.setVisibility(View.GONE);
            }
            if (mViewGroupRoot != null)
            {
                int padding = ResourceUtils.getDimenPixelSize(R.dimen.spacing_large);
                mViewGroupRoot.setPadding(padding, padding, padding, padding);
            }
        } else
        {
            if (mTvMessage != null)
            {
                mTvMessage.setText(mMessage);
                mTvMessage.setVisibility(View.VISIBLE);
            }
            if (mViewGroupRoot != null)
            {
                int padding = ResourceUtils.getDimenPixelSize(R.dimen.spacing_normal);
                mViewGroupRoot.setPadding(padding * 2, padding, padding * 2, padding);
            }
        }
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.dialog_loading;
    }

    @Override
    public void onCreateView(ViewGroup parentView, WingsDialog dialog)
    {
        mViewGroupRoot = parentView.findViewById(R.id.ll_loading_dialog_root);
        mLoadingView = parentView.findViewById(R.id.lv_loading_dialog);
        mTvMessage = parentView.findViewById(R.id.tv_loading_dialog);
        setMessage(mMessage);
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

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event)
    {

    }
}
