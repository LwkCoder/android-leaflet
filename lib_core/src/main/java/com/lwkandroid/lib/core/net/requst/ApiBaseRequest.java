package com.lwkandroid.lib.core.net.requst;


import com.lwkandroid.lib.core.callback.WingsSupplier;
import com.lwkandroid.lib.core.net.ApiService;
import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.bean.ApiBaseRequestOptions;
import com.lwkandroid.lib.core.net.bean.ApiCacheOptions;
import com.lwkandroid.lib.core.net.constants.ApiRequestType;
import com.lwkandroid.lib.core.net.utils.FormDataMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by LWK
 * 请求构造体基类
 *
 * @author LWK
 */
public abstract class ApiBaseRequest<T extends ApiBaseRequestOptions> extends ApiBaseRequestOptions<T>
{
    ApiBaseRequest(String url, @ApiRequestType.Type int type)
    {
        setSubUrl(url);
        setRequestType(type);
    }

    /**
     * 执行请求的过程
     */
    public Observable<ResponseBody> invokeRequest()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(getReadTimeOut(), TimeUnit.MILLISECONDS);
        builder.writeTimeout(getWriteTimeOut(), TimeUnit.MILLISECONDS);
        builder.connectTimeout(getConnectTimeOut(), TimeUnit.MILLISECONDS);

        /*设置HostnameVerifier*/
        if (getHostnameVerifier() != null)
        {
            builder.hostnameVerifier(getHostnameVerifier());
        }

        /*设置Https证书*/
        if (getHttpsSSLParams() != null)
        {
            builder.sslSocketFactory(getHttpsSSLParams().getSSLSocketFactory(), getHttpsSSLParams().getTrustManager());
        }

        //设置拦截器
        Map<String, Interceptor> allInterceptorMap = mergeParams(
                RxHttp.getGlobalOptions().getInterceptorMap(),
                getInterceptorMap(), getIgnoreInterceptors(),
                isIgnoreAllGlobalInterceptors());
        if (allInterceptorMap.size() > 0)
        {
            for (Interceptor interceptor : allInterceptorMap.values())
            {
                builder.addInterceptor(interceptor);
            }
        }
        builder.addInterceptor(RxHttp.getProgressInterceptor());
        if (RxHttp.isDebugMode())
        {
            builder.addInterceptor(RxHttp.getApiLogInterceptor());
        }

        //设置网络拦截器
        Map<String, Interceptor> allNetInterceptorMap = mergeParams(
                RxHttp.getGlobalOptions().getNetInterceptorMap(),
                getNetInterceptorMap(), getIgnoreNetInterceptors(),
                isIgnoreAllGlobalNetInterceptors());
        if (allNetInterceptorMap.size() > 0)
        {
            for (Interceptor interceptor : allNetInterceptorMap.values())
            {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        //获取Headers
        Map<String, String> globalHeaderMap = RxHttp.getGlobalOptions().getHeadersMap();
        Map<String, WingsSupplier<String>> globalDynamicHeaderMap = RxHttp.getGlobalOptions().getDynamicHeaderMap();
        for (Map.Entry<String, WingsSupplier<String>> entry : globalDynamicHeaderMap.entrySet())
        {
            globalHeaderMap.put(entry.getKey(), entry.getValue().get());
        }
        Map<String, String> allHeadersMap = mergeParams(
                globalHeaderMap, getHeadersMap(),
                getIgnoreHeaders(), isIgnoreAllGlobalHeaders());

        //获取表单参数
        FormDataMap globalFormDataMap = RxHttp.getGlobalOptions().getFormDataMap();
        Map<String, WingsSupplier<Object>> globalDynamicFormDataMap = RxHttp.getGlobalOptions().getDynamicFormDataMap();
        for (Map.Entry<String, WingsSupplier<Object>> entry : globalDynamicFormDataMap.entrySet())
        {
            globalFormDataMap.addParam(entry.getKey(), entry.getValue().get());
        }
        Map<String, Object> allFormDataMap = mergeParams(
                globalFormDataMap,
                getFormDataMap(), getIgnoreFormDataSet(),
                isIgnoreAllGlobalFormData());

        //添加Cookie管理类
        RxHttp.getGlobalOptions().getCookieManager().add(getCookieList());
        builder.cookieJar(RxHttp.getGlobalOptions().getCookieManager());

        //创建Retrofit对象
        Retrofit retrofit = RxHttp.RETROFIT().create(getBaseUrl(), builder.build());
        ApiService apiService = retrofit.create(ApiService.class);

        //执行请求
        return buildResponse(allHeadersMap, allFormDataMap, getObjectRequestBody(),
                getOkHttp3RequestBody(), getJsonRequestBody(), apiService);
    }

    /**
     * 合并全局参数和自定义参数
     *
     * @param globalParams 全局参数
     * @param customParams 单次请求参数
     * @param ignoreParams 忽略的参数
     * @param ignoreGlobal 是否忽略全局参数
     * @param <P>          参数泛型
     * @return 最终合并后的参数
     */
    private <P> Map<String, P> mergeParams(Map<String, P> globalParams,
                                           Map<String, P> customParams,
                                           Set<String> ignoreParams,
                                           boolean ignoreGlobal)
    {
        Map<String, P> resultMap = new HashMap<>(8);
        //添加全局参数
        if (!ignoreGlobal && globalParams != null)
        {
            resultMap.putAll(globalParams);
        }
        //添加自定义参数
        if (customParams != null)
        {
            resultMap.putAll(customParams);
        }
        //去除忽略的参数
        if (ignoreParams != null)
        {
            for (String param : ignoreParams)
            {
                resultMap.remove(param);
            }
        }
        return resultMap;
    }

    /**
     * 获取最终缓存参数
     */
    public ApiCacheOptions getFinalCacheOptions()
    {
        ApiCacheOptions.Builder cacheBuilder = new ApiCacheOptions.Builder();
        cacheBuilder.appVersion(RxHttp.getGlobalOptions().getCacheVersion());
        cacheBuilder.cachePath(RxHttp.getGlobalOptions().getCachePath());
        cacheBuilder.diskMaxSize(RxHttp.getGlobalOptions().getCacheDiskMaxSize());
        cacheBuilder.cacheOpeartor(RxHttp.getGlobalOptions().getCacheOperator());
        cacheBuilder.cacheMode(getCacheMode());
        cacheBuilder.cacheTime(getCacheTime());
        cacheBuilder.cacheKey(getCacheKey());

        return cacheBuilder.build();
    }

    /**
     * 子类实现该方法执行网络请求过程
     *
     * @param headersMap         所有Header的Map
     * @param formDataMap        所有QueryParams的Map
     * @param objectRequestBody  Object对象的请求体
     * @param okHttp3RequestBody OK3RequestBody对象的请求体
     * @param jsonBody           Json格式String对象的请求体
     * @param service            Retrofit请求模版Service对象
     * @return Observable
     */
    protected abstract Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                              Map<String, Object> formDataMap,
                                                              Object objectRequestBody,
                                                              RequestBody okHttp3RequestBody,
                                                              String jsonBody,
                                                              ApiService service);
}
