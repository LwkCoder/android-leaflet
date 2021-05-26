package com.lwkandroid.lib.core.net.interceptor;


import com.lwkandroid.lib.core.log.KLog;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp Headers拦截器，用于对所有请求添加公共动态Header
 * 需要配合RxHttp.getGlobalOptions().addInterceptor(String tag,Interceptor interceptor)使用
 * 【不可使用addNetInterceptor】
 * 推荐使用同样效果的方法：RxHttp.getGlobalOptions.addDynamicHeader(String tag, WingsSupplier<String> supplier);
 *
 * @author LWK
 */
public abstract class ApiHeaderInterceptor implements Interceptor
{
    private static final String TAG = "ApiHeaderInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request.Builder builder = chain.request().newBuilder();
        Map<String, String> headersMap = createHeaders();
        if (headersMap == null || headersMap.isEmpty())
        {
            return chain.proceed(builder.build());
        }

        try
        {
            for (Map.Entry<String, String> entry : headersMap.entrySet())
            {
                builder.addHeader(entry.getKey(), entry.getValue()).build();
            }
        } catch (Exception e)
        {
            KLog.e(TAG, "Exception occured when add Header:" + e.toString());
        }
        return chain.proceed(builder.build());
    }

    public abstract Map<String, String> createHeaders();
}