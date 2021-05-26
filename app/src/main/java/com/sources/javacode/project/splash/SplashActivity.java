package com.sources.javacode.project.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.sources.javacode.R;
import com.sources.javacode.project.home.HomeActivity;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class SplashActivity extends MvpBaseActivity<SplashPresenter> implements SplashContract.IView<SplashPresenter>
{

    @Override
    protected SplashPresenter createPresenter()
    {
        return new SplashPresenter(this, new SplashModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_splash;
    }

    @Override
    protected void initUI(View contentView)
    {
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        getPresenter().waitForLaunch();
    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            default:
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        //        super.onBackPressed();
    }

    @Override
    public void toHome()
    {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
