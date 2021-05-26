package com.lwkandroid.lib.common.mvp.list;

import com.lwkandroid.lib.common.widgets.ptr.PTRLayout;

/**
 * Description:PTRLayout实现的下拉刷新状态实现
 *
 * @author LWK
 * @date 2020/3/5
 */
public final class PTRLayoutWrapper implements IRefreshWrapper<PTRLayout>, PTRLayout.OnPullListener
{
    private PTRLayout mPtrLayout;
    private IRefreshWrapper.OnRefreshRequestedListener mListener;

    public PTRLayoutWrapper(PTRLayout layout)
    {
        wrapRefreshView(layout);
    }

    @Override
    public void wrapRefreshView(PTRLayout view)
    {
        this.mPtrLayout = view;
    }

    @Override
    public PTRLayout getRefreshView()
    {
        return mPtrLayout;
    }

    @Override
    public void enableRefresh(boolean enable)
    {
        if (mPtrLayout != null)
        {
            mPtrLayout.setEnabled(true);
        }
    }

    @Override
    public void autoRefresh()
    {
        if (mPtrLayout != null)
        {
            mPtrLayout.autoRefresh();
        }
    }

    @Override
    public void callRefreshSuccess()
    {
        //PTRLayout没有区分成功或失败的状态
        if (mPtrLayout != null)
        {
            mPtrLayout.finishRefresh();
        }
    }

    @Override
    public void callRefreshFail(Throwable throwable)
    {
        //PTRLayout没有区分成功或失败的状态
        if (mPtrLayout != null)
        {
            mPtrLayout.finishRefresh();
        }
    }

    @Override
    public void setOnRefreshRequestedListener(OnRefreshRequestedListener listener)
    {
        this.mListener = listener;
        if (mPtrLayout != null)
        {
            mPtrLayout.setOnPullListener(this);
        }
    }

    @Override
    public void onMoveTarget(int offset)
    {

    }

    @Override
    public void onMoveRefreshView(int offset)
    {

    }

    @Override
    public void onRefresh()
    {
        if (mListener != null)
        {
            mListener.onRefreshRequested();
        }
    }
}
