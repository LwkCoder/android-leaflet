package com.sources.javacode.project.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.widgets.dialog.DialogBuilder;
import com.lwkandroid.lib.common.widgets.dialog.IDialogUiController;
import com.lwkandroid.lib.common.widgets.dialog.WingsDialog;
import com.lwkandroid.lib.core.annotation.ClickViews;
import com.lwkandroid.lib.core.annotation.FindView;
import com.lwkandroid.lib.core.annotation.ViewInjector;
import com.lwkandroid.lib.core.callback.WingsConsumer;
import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.rcvadapter.RcvSingleAdapter;
import com.lwkandroid.rcvadapter.holder.RcvHolder;
import com.lwkandroid.rcvadapter.utils.RcvLinearDecoration;
import com.lwkandroid.widget.ComActionBar;
import com.lwkandroid.widget.leaflet.LeafletWebView;
import com.sources.javacode.R;
import com.sources.javacode.project.constants.AppConstants;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class HomeActivity extends MvpBaseActivity<HomePresenter> implements HomeContract.IView<HomePresenter>
{
    @FindView(R.id.actionBar)
    private ComActionBar mActionBar;

    private LeafletWebView mMapView;
    private List<MethodOptions> mMethodList = new LinkedList<>();

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
        mActionBar.setRightOnItemClickListener01((viewId, textView, dividerLine) -> {
            CheckMapTileContent content = new CheckMapTileContent(mMethodList, HomeActivity.this::invoke);
            DialogBuilder.with(content)
                    .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .build()
                    .show(HomeActivity.this);
        });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        mMethodList.add(new MethodOptions("天地图卫星图", "showTiandituSatellite", AppConstants.TIAN_DI_TU_TOKEN, true));
        mMethodList.add(new MethodOptions("天地图矢量图", "showTiandituVector", AppConstants.TIAN_DI_TU_TOKEN, true));
        mMethodList.add(new MethodOptions("天地图地形图", "showTiandituTerrain", AppConstants.TIAN_DI_TU_TOKEN, true));
        mMethodList.add(new MethodOptions("高德卫星图", "showGaodeSatellite", true));
        mMethodList.add(new MethodOptions("高德矢量图", "showGaodeVector"));
        mMethodList.add(new MethodOptions("腾讯卫星图", "showTencentSatellite", true));
        mMethodList.add(new MethodOptions("腾讯矢量图", "showTencentVector"));
        mMethodList.add(new MethodOptions("腾讯地形图", "showTencentTerrain", true));
        mMethodList.add(new MethodOptions("百度卫星图", "showBaiduSatellite", true));
        mMethodList.add(new MethodOptions("百度矢量图", "showBaiduVector"));
        mMethodList.add(new MethodOptions("百度自定义Light矢量图", "showBaiduCustomLight"));
        mMethodList.add(new MethodOptions("百度自定义Dark矢量图", "showBaiduCustomDark"));
        mMethodList.add(new MethodOptions("百度自定义MidNight矢量图", "showBaiduCustomMidNight"));
        mMethodList.add(new MethodOptions("百度自定义GrayScale矢量图", "showBaiduCustomGrayScale"));
        mMethodList.add(new MethodOptions("百度自定义HardEdge矢量图", "showBaiduCustomHardEdge"));
        mMethodList.add(new MethodOptions("百度自定义RedAlert矢量图", "showBaiduCustomRedAlert"));
        mMethodList.add(new MethodOptions("百度自定义GoogleLite矢量图", "showBaiduCustomGoogleLite"));
        mMethodList.add(new MethodOptions("百度自定义GrassGreen矢量图", "showBaiduCustomGrassGreen"));
        mMethodList.add(new MethodOptions("百度自定义Pink矢量图", "showBaiduCustomPink"));
        mMethodList.add(new MethodOptions("百度自定义DarkGreen矢量图", "showBaiduCustomDarkGreen"));
        mMethodList.add(new MethodOptions("百度自定义Bluish矢量图", "showBaiduCustomBluish"));
        mMethodList.add(new MethodOptions("OSM矢量图", "showOSMVector"));
        mMethodList.add(new MethodOptions("智图矢量图", "showGeoqNormalVector"));
        mMethodList.add(new MethodOptions("智图暖色矢量图", "showGeoqWarmVector", true));
        mMethodList.add(new MethodOptions("智图灰色矢量图", "showGeoqGrayVector", true));
        mMethodList.add(new MethodOptions("智图蓝黑矢量图", "showGeoqPurplishBlueVector", true));
        mMethodList.add(new MethodOptions("智图英文矢量图", "showGeoqEnglishVector", true));
        mMethodList.add(new MethodOptions("智图中国行政边界矢量图", "showGeoqCNBoundaryLineVector", true));
        mMethodList.add(new MethodOptions("智图水系图层矢量图", "showGeoqWorldHydroVector", true));
        mMethodList.add(new MethodOptions("谷歌卫星图（需要翻墙）", "showGoogleSatellite", true));
        mMethodList.add(new MethodOptions("谷歌矢量图（需要翻墙）", "showGoogleNormalVector"));
        mMethodList.add(new MethodOptions("谷歌简洁矢量图（需要翻墙）", "showGoogleSimpleVector"));
        mMethodList.add(new MethodOptions("谷歌浅灰矢量图（需要翻墙）", "showGoogleGrayLightVector"));
        mMethodList.add(new MethodOptions("谷歌深灰矢量图（需要翻墙）", "showGoogleGrayDarkVector"));
        mMethodList.add(new MethodOptions("谷歌复古矢量图（需要翻墙）", "showGoogleRetroVector"));

        XXPermissions.with(this)
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback()
                {
                    @Override
                    public void onGranted(List<String> permissions, boolean all)
                    {
                        ViewStub view = find(R.id.viewStub);
                        view.inflate();
                        mMapView = find(R.id.mapView);
                        mMapView.setOnMapInflatedConsumer(s -> {
                            KLog.i("地图初始化完成:" + s);
                            invoke(mMethodList.get(0));
                            MethodOptions options = new MethodOptions("坐标标记", "testMarker");
                            invoke(options);
                        });
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never)
                    {
                        showShortToast("缺少权限");
                    }
                });
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


    private void invoke(MethodOptions options)
    {
        String methodName = options.getMethodName();
        List<Object> params = options.getParamsList();

        StringBuilder builder = new StringBuilder();
        builder.append(AppConstants.JS_PREFIX)
                .append(methodName)
                .append(AppConstants.LEFT_BRACKET);
        if (params != null)
        {
            for (int index = 0; index < params.size(); index++)
            {
                builder.append(params.get(index));
                if (index < params.size() - 1)
                {
                    builder.append(AppConstants.COMMA);
                }
            }
        }
        builder.append(AppConstants.RIGHT_BRACKET);
        String jsMethod = builder.toString();
        KLog.i(getClass().getSimpleName(), "--->" + jsMethod);
        if (mMapView != null)
        {
            mMapView.loadUrl(jsMethod);
        }
    }

    private static class CheckMapTileContent implements IDialogUiController
    {
        private RecyclerView recyclerView;
        private List<MethodOptions> methodOptionsList;
        private ListAdapter adapter;
        private WingsConsumer<MethodOptions> consumer;

        public CheckMapTileContent(List<MethodOptions> list, WingsConsumer<MethodOptions> consumer)
        {
            this.methodOptionsList = list;
            this.consumer = consumer;
        }

        @Override
        public int getLayoutId()
        {
            return R.layout.dialog_check_map_tile;
        }

        @Override
        public void onCreateView(ViewGroup parentView, WingsDialog dialog)
        {
            recyclerView = parentView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(parentView.getContext()));
            recyclerView.addItemDecoration(RcvLinearDecoration.createDefaultVertical(parentView.getContext()));
            adapter = new ListAdapter(parentView.getContext(), methodOptionsList);
            adapter.setOnItemClickListener((holder, methodOptions, position) -> {
                if (consumer != null)
                {
                    consumer.accept(methodOptions);
                }
                dialog.dismiss();
            });
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onShow(DialogInterface dialog)
        {

        }

        @Override
        public void onDismiss(DialogInterface dialog)
        {

        }

        @Override
        public void onCancel(DialogInterface dialog)
        {

        }

        @Override
        public void onStateChanged(@NonNull @NotNull LifecycleOwner source, @NonNull @NotNull Lifecycle.Event event)
        {

        }
    }


    private static class ListAdapter extends RcvSingleAdapter<MethodOptions>
    {

        public ListAdapter(Context context, List<MethodOptions> datas)
        {
            super(context, R.layout.adapter_tile_list, datas);
        }

        @Override
        public void onBindView(RcvHolder holder, MethodOptions itemData, int position)
        {
            holder.setTvText(R.id.textView, itemData.getDescription());
        }
    }

    private static class MethodOptions
    {
        private String description;
        private String methodName;
        private java.util.List<Object> paramsList = new LinkedList<>();

        public MethodOptions(String description, String methodName, java.util.List<Object> paramsList)
        {
            this.description = description;
            this.methodName = methodName;
            this.paramsList.addAll(paramsList);
        }

        public MethodOptions(String description, String methodName, Object... params)
        {
            this.description = description;
            this.methodName = methodName;
            this.paramsList.addAll(Arrays.asList(params));
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public String getMethodName()
        {
            return methodName;
        }

        public void setMethodName(String methodName)
        {
            this.methodName = methodName;
        }

        public List<Object> getParamsList()
        {
            return paramsList;
        }

        public void setParamsList(List<Object> paramsList)
        {
            this.paramsList = paramsList;
        }

        public void addParam(Object param)
        {
            paramsList.add(param);
        }
    }
}
