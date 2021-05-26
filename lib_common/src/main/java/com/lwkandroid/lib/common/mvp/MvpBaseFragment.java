package com.lwkandroid.lib.common.mvp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Description:Fragment层基类
 *
 * @author LWK
 * @date 2020/2/28
 */
public abstract class MvpBaseFragment<P extends LifecycleObserver> extends Fragment
        implements IContentView, ContentViewImpl.OnClickListenerDispatcher, View.OnClickListener, IMvpBaseView<P>
{
    private ContentViewImpl mContentImpl = new ContentViewImpl(this);
    private MvpBaseViewImpl<P> mViewImpl;

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mViewImpl = new MvpBaseViewImpl<>(getActivity(), this);
        setPresenter(createPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mContentImpl.inflateContentView(inflater, getContentViewId(), container);
        return mContentImpl.getContentView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getArgumentsData(getArguments(), savedInstanceState);
        initUI(getContentView());
        initData(savedInstanceState);
    }

    @Override
    public FragmentActivity getFragmentActivity()
    {
        return mViewImpl.getFragmentActivity();
    }

    @Override
    public LifecycleOwner getLifecycleOwner()
    {
        return mViewImpl.getLifecycleOwner();
    }

    @Override
    public void setPresenter(P presenter)
    {
        mViewImpl.setPresenter(presenter);
    }

    @Override
    public P getPresenter()
    {
        return mViewImpl.getPresenter();
    }

    @Override
    public View getContentView()
    {
        return mContentImpl.getContentView();
    }

    @Override
    public <T extends View> T find(int resId)
    {
        return mContentImpl.find(resId);
    }

    @Override
    public void addClick(int resId)
    {
        mContentImpl.addClick(resId);
    }

    @Override
    public void addClick(int resId, View.OnClickListener listener)
    {
        mContentImpl.addClick(resId, listener);
    }

    @Override
    public void addClick(int... resIds)
    {
        mContentImpl.addClick(resIds);
    }

    @Override
    public void addClick(View view)
    {
        mContentImpl.addClick(view);
    }

    @Override
    public void addClick(View view, View.OnClickListener listener)
    {
        mContentImpl.addClick(view, listener);
    }

    @Override
    public void addClick(View... views)
    {
        mContentImpl.addClick(views);
    }

    @Override
    public void showShortToast(int resId)
    {
        mContentImpl.showShortToast(resId);
    }

    @Override
    public void showShortToast(CharSequence message)
    {
        mContentImpl.showShortToast(message);
    }

    @Override
    public void showLongToast(int resId)
    {
        mContentImpl.showLongToast(resId);
    }

    @Override
    public void showLongToast(CharSequence message)
    {
        mContentImpl.showLongToast(message);
    }

    @Override
    public void onClick(View v)
    {
        onClick(v.getId(), v);
    }

    /**
     * 创建对应Presenter层
     */
    protected abstract P createPresenter();

    /**
     * 子类实现，获取getArgument()传递的数据
     *
     * @param bundle             argument数据
     * @param savedInstanceState
     */
    protected abstract void getArgumentsData(Bundle bundle, Bundle savedInstanceState);

    /**
     * 子类实现，指定布局id
     */
    protected abstract int getContentViewId();

    /**
     * 子类实现，初始化UI
     *
     * @param contentView 内容布局
     */
    protected abstract void initUI(View contentView);

    /**
     * 子类实现，初始化数据
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);
}
