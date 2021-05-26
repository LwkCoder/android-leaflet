package com.lwkandroid.lib.common.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Description:Activity基类
 *
 * @author LWK
 * @date 2020/2/27
 */
public abstract class MvpBaseActivity<P extends LifecycleObserver> extends AppCompatActivity
        implements IContentView, ContentViewImpl.OnClickListenerDispatcher, View.OnClickListener, IMvpBaseView<P>
{
    private ContentViewImpl mContentImpl = new ContentViewImpl(this);
    private MvpBaseViewImpl<P> mViewImpl = new MvpBaseViewImpl<>(this, this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //初始化Presenter
        setPresenter(createPresenter());
        getIntentData(getIntent(), false);
        setContentView(mContentImpl.inflateContentView(this, getContentViewId()));
        initUI(getContentView());
        initData(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        getIntentData(intent, true);
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
     * 子类实现，接收Intent数据
     *
     * @param intent    传递的Intent
     * @param newIntent 是否onNewIntent()调用
     */
    protected abstract void getIntentData(Intent intent, boolean newIntent);

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
