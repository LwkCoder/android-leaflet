package com.lwkandroid.lib.core.net;


import com.lwkandroid.lib.core.net.bean.ApiGlobalOptions;
import com.lwkandroid.lib.core.net.interceptor.ApiLogInterceptor;
import com.lwkandroid.lib.core.net.interceptor.OkProgressInterceptor;
import com.lwkandroid.lib.core.net.requst.ApiDeleteRequest;
import com.lwkandroid.lib.core.net.requst.ApiDownloadRequest;
import com.lwkandroid.lib.core.net.requst.ApiGetRequest;
import com.lwkandroid.lib.core.net.requst.ApiPatchRequest;
import com.lwkandroid.lib.core.net.requst.ApiPostRequest;
import com.lwkandroid.lib.core.net.requst.ApiPutRequest;
import com.lwkandroid.lib.core.net.requst.ApiUploadRequest;
import com.lwkandroid.lib.core.net.utils.RetrofitUtils;

import okhttp3.Interceptor;

/**
 * Created by LWK
 * 向外提供功能的入口
 *
 * @author LWK
 */
public final class RxHttp
{
    static
    {
        DEFAULT_GLOBAL_OPTIONS = new ApiGlobalOptions();
        RETROFIT = new RetrofitUtils();
        LOG_INTERCEPTOR = new ApiLogInterceptor();
        PROGRESS_INTERCEPTOR = new OkProgressInterceptor();
    }

    private RxHttp()
    {
    }

    private static boolean mDebugMode;
    private static final ApiGlobalOptions DEFAULT_GLOBAL_OPTIONS;
    private static final RetrofitUtils RETROFIT;
    private static final Interceptor LOG_INTERCEPTOR;
    private static final Interceptor PROGRESS_INTERCEPTOR;

    /**
     * 获取请求参数日志拦截器
     */
    public static Interceptor getApiLogInterceptor()
    {
        return LOG_INTERCEPTOR;
    }

    /**
     * 获取请求过程拦截器
     */
    public static Interceptor getProgressInterceptor()
    {
        return PROGRESS_INTERCEPTOR;
    }

    /**
     * 初始化公共配置
     *
     * @param debugMode 是否调试模式
     * @param baseUrl   网络请求域名，用来配置Retrofit，结尾必须是“/”
     * @return 公共配置对象
     */
    public static ApiGlobalOptions init(boolean debugMode, String baseUrl)
    {
        mDebugMode = debugMode;
        DEFAULT_GLOBAL_OPTIONS.setBaseUrl(baseUrl);
        return DEFAULT_GLOBAL_OPTIONS;
    }

    public static boolean isDebugMode()
    {
        return mDebugMode;
    }

    /**
     * 获取公共配置
     */
    public static ApiGlobalOptions getGlobalOptions()
    {
        return DEFAULT_GLOBAL_OPTIONS;
    }

    /**
     * 发起Get请求
     */
    public static ApiGetRequest GET(String url)
    {
        return new ApiGetRequest(url);
    }

    /**
     * 发起Post请求
     */
    public static ApiPostRequest POST(String url)
    {
        return new ApiPostRequest(url);
    }

    /**
     * 发起Put请求
     */
    public static ApiPutRequest PUT(String url)
    {
        return new ApiPutRequest(url);
    }

    /**
     * 发起Delete请求
     */
    public static ApiDeleteRequest DELETE(String url)
    {
        return new ApiDeleteRequest(url);
    }

    /**
     * 发起Patch请求
     */
    public static ApiPatchRequest PATCH(String url)
    {
        return new ApiPatchRequest(url);
    }

    /**
     * 发起Download请求
     */
    public static ApiDownloadRequest DOWNLOAD(String url)
    {
        return new ApiDownloadRequest(url);
    }

    /**
     * 发起Upload请求
     */
    public static ApiUploadRequest UPLOAD(String url)
    {
        return new ApiUploadRequest(url);
    }

    /**
     * 获取RetrofitUtils对象，便于自定义接口
     */
    public static RetrofitUtils RETROFIT()
    {
        return RETROFIT;
    }
}
