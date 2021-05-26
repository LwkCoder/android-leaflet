package com.lwkandroid.lib.common.mvp.list;

/**
 * Description:下拉刷新控件包装类
 *
 * @author LWK
 * @date 2020/3/2
 */
public interface IRefreshWrapper<RV>
{
    void wrapRefreshView(RV view);

    RV getRefreshView();

    void enableRefresh(boolean enable);

    void autoRefresh();

    void callRefreshSuccess();

    void callRefreshFail(Throwable throwable);

    void setOnRefreshRequestedListener(OnRefreshRequestedListener listener);

    interface OnRefreshRequestedListener
    {
        void onRefreshRequested();
    }
}
