package com.lwkandroid.lib.common.mvp.list;

/**
 * Description:自动加载控件封装类
 *
 * @author LWK
 * @date 2020/3/2
 */
public interface ILoadMoreWrapper<LV>
{
    void wrapLoadMoreView(LV view);

    LV getLoadMoreView();

    void enableLoadMore(boolean enable);

    void callLoadMoreSuccess();

    void callLoadMoreFail(Throwable throwable);

    void callLoadMoreNoMoreData();

    void setOnLoadMoreRequestedListener(OnLoadMoreRequestedListener listener);

    interface OnLoadMoreRequestedListener
    {
        void onLoadMoreRequested();
    }
}
