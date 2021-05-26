package com.lwkandroid.lib.core.net.interceptor;

import java.util.Map;
import java.util.TreeMap;

/**
 * 自定义Retrofit对象时用来添加全局参数的拦截器
 *
 * @author LWK
 */

public final class RetrofitFormDataInterceptor extends ApiParamsInterceptor
{
    private Map<String, Object> mNewParamsMap;

    public RetrofitFormDataInterceptor(Map<String, Object> newParamsMap)
    {
        this.mNewParamsMap = newParamsMap;
    }

    @Override
    public TreeMap<String, Object> addParams(TreeMap<String, Object> oldParams)
    {
        if (mNewParamsMap != null && mNewParamsMap.size() > 0)
        {
            oldParams.putAll(mNewParamsMap);
        }
        return oldParams;
    }
}
