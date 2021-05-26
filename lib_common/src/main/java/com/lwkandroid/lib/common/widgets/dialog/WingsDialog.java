package com.lwkandroid.lib.common.widgets.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.lwkandroid.lib.core.log.KLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Description:定制的Dialog
 *
 * @author LWK
 * @date 2020/4/7
 */
public final class WingsDialog extends DialogFragment implements DialogInterface.OnShowListener
{
    private static final String BUNDLE_KEY = "config";
    private DialogConfig mConfig;
    private FrameLayout mRealContentView;
    private DialogInterface.OnDismissListener mDismissListener;
    private DialogInterface.OnCancelListener mCancelListener;
    private DialogInterface.OnShowListener mShowListener;
    private DialogInterface.OnKeyListener mKeyListener;
    private IDialogUiController mUiController;
    private SparseArray<OnDialogChildClickListener> mChildClickArray = new SparseArray<>();

    private WingsDialog()
    {
    }

    static WingsDialog create(DialogConfig dialogConfig)
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY, dialogConfig);
        WingsDialog dialog = new WingsDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public WingsDialog setDialogDismissListener(DialogInterface.OnDismissListener listener)
    {
        this.mDismissListener = listener;
        return this;
    }

    public WingsDialog setDialogCancelListener(DialogInterface.OnCancelListener listener)
    {
        this.mCancelListener = listener;
        return this;
    }

    public WingsDialog setDialogShowListener(DialogInterface.OnShowListener listener)
    {
        this.mShowListener = listener;
        return this;
    }

    public WingsDialog setDialogKeyListener(DialogInterface.OnKeyListener listener)
    {
        this.mKeyListener = listener;
        return this;
    }

    public WingsDialog addOnChildClickListener(int viewId, OnDialogChildClickListener listener)
    {
        this.mChildClickArray.put(viewId, listener);
        return this;
    }

    public WingsDialog setUiController(IDialogUiController controller)
    {
        this.mUiController = controller;
        return this;
    }

    public WingsDialog show(FragmentActivity activity)
    {
        return show(activity, null);
    }

    public WingsDialog show(FragmentActivity activity, String tag)
    {
        show(activity.getSupportFragmentManager(), tag);
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mConfig = getArguments().getParcelable(BUNDLE_KEY);
        if (mConfig == null)
        {
            KLog.w("WingsDialog will use the default config instead of null.");
            mConfig = new DialogConfig();
        }
        if (mUiController != null)
        {
            getLifecycle().addObserver(mUiController);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (mUiController == null)
        {
            throw new IllegalArgumentException("You must to set a IDialogUiController with nonNull layout resource for WingsDialog !");
        }
        //设置Theme
        setStyle(DialogFragment.STYLE_NO_TITLE, mConfig.getThemeStyle());
        //初始化配置
        Window window = getDialog().getWindow();
        if (window != null)
        {
            window.setGravity(mConfig.getLayoutGravity());
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setDimAmount(mConfig.getDarkWindowDegree());
            window.setWindowAnimations(mConfig.getAnimStyle());
        }
        setCancelable(mConfig.isCancelable());
        getDialog().setCanceledOnTouchOutside(mConfig.isCanceledOnTouchOutside());
        getDialog().setOnShowListener(this);
        //初始化布局
        mRealContentView = new FrameLayout(getActivity());
        View view = inflater.inflate(mUiController.getLayoutId(), mRealContentView, false);
        mRealContentView.addView(view);
        //关联布局和控制层
        mUiController.onCreateView(mRealContentView, this);
        //绑定点击监听
        for (int i = 0, size = mChildClickArray.size(); i < size; i++)
        {
            final int index = i;
            final int viewId = mChildClickArray.keyAt(index);
            View child = mRealContentView.findViewById(viewId);
            if (child != null)
            {
                child.setOnClickListener(v -> {
                    OnDialogChildClickListener listener = mChildClickArray.valueAt(index);
                    if (listener != null)
                    {
                        listener.onDialogChildClicked(v.getId(), v, mRealContentView, WingsDialog.this);
                    }
                });
            }
        }
        return mRealContentView;
    }


    @Override
    public void onStart()
    {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null)
        {
            window.setLayout(mConfig.getLayoutWidth(), mConfig.getLayoutHeight());
        }
        if (mKeyListener != null)
        {
            getDialog().setOnKeyListener(mKeyListener);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (getDialog() != null && getDialog().isShowing())
        {
            dismiss();
        }
        if (mUiController != null)
        {
            getLifecycle().removeObserver(mUiController);
        }
    }

    @Override
    public void onShow(DialogInterface dialog)
    {
        if (mUiController != null)
        {
            mUiController.onShow(dialog);
        }
        if (mShowListener != null)
        {
            mShowListener.onShow(dialog);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog)
    {
        if (mUiController != null)
        {
            mUiController.onDismiss(dialog);
        }
        super.onDismiss(dialog);
        if (mChildClickArray != null)
        {
            mChildClickArray.clear();
        }
        if (mDismissListener != null)
        {
            mDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog)
    {
        if (mUiController != null)
        {
            mUiController.onCancel(dialog);
        }
        super.onCancel(dialog);
        if (mCancelListener != null)
        {
            mCancelListener.onCancel(dialog);
        }
    }
}
