package com.lwkandroid.lib.core.net.interceptor;


import com.lwkandroid.lib.core.net.manager.OKProgressManger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * OkHttp 上传/下载过程拦截器
 *
 * @author LWK
 */
public final class OkProgressInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        return OKProgressManger.get().wrapResponseBody(
                chain.proceed(OKProgressManger.get().wrapRequestBody(chain.request())));
    }
}
