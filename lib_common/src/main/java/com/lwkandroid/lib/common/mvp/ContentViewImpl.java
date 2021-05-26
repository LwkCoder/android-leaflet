package com.lwkandroid.lib.common.mvp;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lwkandroid.lib.core.utils.common.ToastUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * Description:Activity/Fragment 内容公共方法相关接口层实现类
 *
 * @author LWK
 * @date 2020/2/25
 */
final class ContentViewImpl implements IContentView, View.OnClickListener
{
    private View mContentView;
    private SparseArray<View> mViews;
    private OnClickListenerDispatcher mClickDispatcher;

    ContentViewImpl(OnClickListenerDispatcher dispatcher)
    {
        this.mViews = new SparseArray<>();
        this.mClickDispatcher = dispatcher;
    }

    View inflateContentView(Context context, @LayoutRes int layoutResId)
    {
        mContentView = LayoutInflater.from(context).inflate(layoutResId, null);
        return mContentView;
    }

    View inflateContentView(Context context, @LayoutRes int layoutResId, @Nullable ViewGroup container)
    {
        mContentView = LayoutInflater.from(context).inflate(layoutResId, container, false);
        return mContentView;
    }

    View inflateContentView(LayoutInflater inflater, @LayoutRes int layoutResId, @Nullable ViewGroup container)
    {
        mContentView = inflater.inflate(layoutResId, container, false);
        return mContentView;
    }

    @Override
    public View getContentView()
    {
        return mContentView;
    }

    @Override
    public <T extends View> T find(int resId)
    {
        if (mContentView == null)
        {
            throw new IllegalStateException("You must invoke inflateContentView() first !");
        }
        View view = mViews.get(resId);
        if (view == null)
        {
            view = mContentView.findViewById(resId);
            if (view != null)
            {
                mViews.put(resId, view);
            }
        }
        return (T) view;
    }

    @Override
    public void addClick(int resId)
    {
        if (mContentView == null)
        {
            return;
        }
        View view = find(resId);
        if (view != null)
        {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void addClick(int resId, View.OnClickListener listener)
    {
        if (mContentView == null)
        {
            return;
        }
        View view = find(resId);
        if (view != null)
        {
            view.setOnClickListener(listener);
        }
    }

    @Override
    public void addClick(int... resIds)
    {
        if (mContentView == null || resIds == null)
        {
            return;
        }
        for (int resId : resIds)
        {
            addClick(resId);
        }
    }

    @Override
    public void addClick(View view)
    {
        if (view != null)
        {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void addClick(View view, View.OnClickListener listener)
    {
        if (view != null)
        {
            view.setOnClickListener(listener);
        }
    }

    @Override
    public void addClick(View... views)
    {
        if (views == null)
        {
            return;
        }
        for (View view : views)
        {
            addClick(view);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (mClickDispatcher != null)
        {
            mClickDispatcher.onClick(v.getId(), v);
        }
    }

    @Override
    public void showShortToast(int resId)
    {
        ToastUtils.showShort(resId);
    }

    @Override
    public void showShortToast(CharSequence message)
    {
        ToastUtils.showShort(message);
    }

    @Override
    public void showLongToast(int resId)
    {
        ToastUtils.showLong(resId);
    }

    @Override
    public void showLongToast(CharSequence message)
    {
        ToastUtils.showLong(message);
    }

    public interface OnClickListenerDispatcher
    {
        void onClick(int id, View view);
    }
}
