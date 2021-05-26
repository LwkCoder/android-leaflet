package com.lwkandroid.lib.core.net.utils;

import com.lwkandroid.lib.core.callback.WingsSupplier;
import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.bean.ApiGlobalOptions;
import com.lwkandroid.lib.core.net.interceptor.ApiHeaderInterceptor;
import com.lwkandroid.lib.core.net.interceptor.RetrofitFormDataInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * Created by LWK
 * Retrofit辅助工具
 */

public final class RetrofitUtils
{
    /**
     * 根据RxHttp全局配置创建Retrofit对象
     * 【全局参数和Header通过添加拦截器实现】
     *
     * @return Retrofit对象
     */
    public Retrofit createWithGlobalOptions()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        ApiGlobalOptions globalOptions = RxHttp.getGlobalOptions();
        /*设置超时时间*/
        builder.readTimeout(globalOptions.getReadTimeOut(), TimeUnit.MILLISECONDS);
        builder.writeTimeout(globalOptions.getWriteTimeOut(), TimeUnit.MILLISECONDS);
        builder.connectTimeout(globalOptions.getConnectTimeOut(), TimeUnit.MILLISECONDS);

        /*设置HostnameVerifier*/
        if (globalOptions.getHostnameVerifier() != null)
        {
            builder.hostnameVerifier(globalOptions.getHostnameVerifier());
        }

        /*设置Https证书*/
        if (globalOptions.getHttpsSSLParams() != null)
        {
            builder.sslSocketFactory(globalOptions.getHttpsSSLParams().getSSLSocketFactory(),
                    globalOptions.getHttpsSSLParams().getTrustManager());
        }

        /*添加全局参数和Header*/
        FormDataMap globalFormDataMap = RxHttp.getGlobalOptions().getFormDataMap();
        Map<String, WingsSupplier<Object>> globalDynamicFormDataMap = RxHttp.getGlobalOptions().getDynamicFormDataMap();
        for (Map.Entry<String, WingsSupplier<Object>> entry : globalDynamicFormDataMap.entrySet())
        {
            globalFormDataMap.addParam(entry.getKey(), entry.getValue().get());
        }
        if (globalFormDataMap != null && globalFormDataMap.size() > 0)
        {
            builder.addInterceptor(new RetrofitFormDataInterceptor(globalFormDataMap));
        }

        Map<String, String> globalHeaderMap = RxHttp.getGlobalOptions().getHeadersMap();
        Map<String, WingsSupplier<String>> globalDynamicHeaderMap = RxHttp.getGlobalOptions().getDynamicHeaderMap();
        for (Map.Entry<String, WingsSupplier<String>> entry : globalDynamicHeaderMap.entrySet())
        {
            globalHeaderMap.put(entry.getKey(), entry.getValue().get());
        }
        if (globalHeaderMap != null && globalHeaderMap.size() > 0)
        {
            builder.addInterceptor(new HeaderInterceptor(globalHeaderMap));
        }

        /*添加全局拦截器*/
        Map<String, Interceptor> interceptorMap = globalOptions.getInterceptorMap();
        Map<String, Interceptor> netInterceptorMap = globalOptions.getNetInterceptorMap();
        if (interceptorMap != null && interceptorMap.size() > 0)
        {
            for (Interceptor interceptor : interceptorMap.values())
            {
                builder.addInterceptor(interceptor);
            }
        }
        if (netInterceptorMap != null && netInterceptorMap.size() > 0)
        {
            for (Interceptor interceptor : netInterceptorMap.values())
            {
                builder.addNetworkInterceptor(interceptor);
            }
        }
        builder.addInterceptor(RxHttp.getProgressInterceptor());
        if (RxHttp.isDebugMode())
        {
            builder.addInterceptor(RxHttp.getApiLogInterceptor());
        }

        //添加Cookie管理类
        builder.cookieJar(RxHttp.getGlobalOptions().getCookieManager());

        return create(globalOptions.getBaseUrl(), builder.build());
    }

    /**
     * 创建Retrofit对象
     *
     * @param baseUrl  请求域名
     * @param okClient OkHttpClient对象
     * @return Retrofit对象
     */
    public Retrofit create(String baseUrl, OkHttpClient okClient)
    {
        Retrofit.Builder retroBuilder = new Retrofit.Builder();
        retroBuilder
                .baseUrl(baseUrl)
                .client(okClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());

        return retroBuilder.build();
    }

    /**
     * 用于配合Retrofit添加全局Header的拦截器
     */
    private static class HeaderInterceptor extends ApiHeaderInterceptor
    {
        private Map<String, String> mHeadersMap;

        HeaderInterceptor(Map<String, String> headersMap)
        {
            this.mHeadersMap = headersMap;
        }

        @Override
        public Map<String, String> createHeaders()
        {
            return mHeadersMap;
        }
    }
}
