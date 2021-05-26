package com.sources.javacode.project.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.core.annotation.ClickViews;
import com.lwkandroid.lib.core.annotation.ViewInjector;
import com.lwkandroid.lib.core.callback.WingsConsumer;
import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.widget.leaflet.LeafletWebView;
import com.sources.javacode.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class HomeActivity extends MvpBaseActivity<HomePresenter> implements HomeContract.IView<HomePresenter>
{
    LeafletWebView mMapView;

    @Override
    protected HomePresenter createPresenter()
    {
        return new HomePresenter(this, new HomeModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_home;
    }

    @Override
    protected void initUI(View contentView)
    {
        ViewInjector.with(this);

        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>()
                {
                    @Override
                    public void onAction(List<String> data)
                    {
                        ViewStub view = find(R.id.viewStub);
                        view.inflate();
                        mMapView = find(R.id.mapView);
                        mMapView.setOnMapInflatedConsumer(new WingsConsumer<String>()
                        {
                            @Override
                            public void accept(String s)
                            {
                                KLog.e("地图初始化完成:" + s);
//                                mMapView.loadUrl("javascript:showGaodeSatellite(true)");
                            }
                        });
                    }
                }).start();
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {

    }

    @Override
    @ClickViews(values = {})
    public void onClick(int id, View view)
    {
        switch (id)
        {
            default:
                break;
        }
    }
}
