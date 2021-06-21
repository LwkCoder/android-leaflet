package com.lwkandroid.widget.leaflet;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.lwkandroid.lib.core.annotation.NotProguard;
import com.lwkandroid.lib.core.callback.WingsConsumer;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @description:
 * @author: LWK
 * @date: 2021/3/11 14:49
 */
@NotProguard
public class LeafletWebView extends WebView
{
    private static final String MAP_LOCATION = "file:///android_asset/index.html";
    private boolean mIsMapInflated = false;
    private WingsConsumer<String> mMapInflateConsumer;

    public LeafletWebView(Context context, boolean b)
    {
        super(context, b);
    }

    public LeafletWebView(Context context)
    {
        this(context, null);
    }

    public LeafletWebView(Context context, AttributeSet attributeSet)
    {
        this(context, attributeSet, -1);
    }

    public LeafletWebView(Context context, AttributeSet attributeSet, int i)
    {
        super(context, attributeSet, i);
        init();
    }

    private void init()
    {
        this.setWebViewClient(WEBVIEW_CLIENT);
        initWebViewSettings();
        this.getView().setClickable(true);
        initMapPageContent();
    }

    private void initWebViewSettings()
    {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    private final WebViewClient WEBVIEW_CLIENT = new WebViewClient()
    {
        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap)
        {
            super.onPageStarted(webView, s, bitmap);
        }

        @Override
        public void onPageFinished(WebView webView, String s)
        {
            super.onPageFinished(webView, s);
            mIsMapInflated = true;
            if (mMapInflateConsumer != null)
            {
                mMapInflateConsumer.accept(s);
            }
        }
    };

    public void setOnMapInflatedConsumer(WingsConsumer<String> consumer)
    {
        this.mMapInflateConsumer = consumer;
        if (mIsMapInflated && mMapInflateConsumer != null)
        {
            mMapInflateConsumer.accept(getUrl());
        }
    }

    /**
     * 加载地图html文件
     */
    public void initMapPageContent()
    {
        loadUrl(MAP_LOCATION);
    }
}
