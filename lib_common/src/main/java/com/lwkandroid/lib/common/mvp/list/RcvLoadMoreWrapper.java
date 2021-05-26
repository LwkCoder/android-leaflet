package com.lwkandroid.lib.common.mvp.list;

import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.rcvadapter.listener.RcvLoadMoreListener;

/**
 * Description:RcvMultiAdapter实现的自动加载状态实现
 *
 * @author LWK
 * @date 2020/3/3
 */
public final class RcvLoadMoreWrapper implements ILoadMoreWrapper<RcvMultiAdapter>, RcvLoadMoreListener
{
    private RcvMultiAdapter mAdapter;
    private OnLoadMoreRequestedListener mListener;

    public RcvLoadMoreWrapper(RcvMultiAdapter adapter)
    {
        wrapLoadMoreView(adapter);
    }

    @Override
    public void wrapLoadMoreView(RcvMultiAdapter adapter)
    {
        this.mAdapter = adapter;
    }

    @Override
    public RcvMultiAdapter getLoadMoreView()
    {
        return mAdapter;
    }

    @Override
    public void enableLoadMore(boolean enable)
    {
        if (mAdapter != null)
        {
            mAdapter.enableLoadMore(enable);
        }
    }

    @Override
    public void callLoadMoreSuccess()
    {
        //没有单独显示状态的方法
    }

    @Override
    public void callLoadMoreFail(Throwable throwable)
    {
        if (mAdapter != null)
        {
            mAdapter.notifyLoadMoreFail();
        }
    }

    @Override
    public void callLoadMoreNoMoreData()
    {
        if (mAdapter != null)
        {
            mAdapter.notifyLoadMoreHasNoMoreData();
        }
    }

    @Override
    public void setOnLoadMoreRequestedListener(OnLoadMoreRequestedListener listener)
    {
        this.mListener = listener;
        if (mAdapter != null)
        {
            mAdapter.setOnLoadMoreListener(this);
        }
    }

    @Override
    public void onLoadMoreRequest()
    {
        if (mListener != null)
        {
            mListener.onLoadMoreRequested();
        }
    }
}
