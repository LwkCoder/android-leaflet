package com.lwkandroid.lib.core.net.requst;

import android.text.TextUtils;

import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.net.ApiService;
import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.constants.ApiRequestType;
import com.lwkandroid.lib.core.net.response.ApiStringResponseImpl;
import com.lwkandroid.lib.core.net.response.IApiStringResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * Get请求
 *
 * @author LWK
 */
public final class ApiGetRequest extends ApiBaseRequest<ApiGetRequest> implements IApiStringResponse
{
    private ApiStringResponseImpl<ApiGetRequest> mStringResponseImpl;

    public ApiGetRequest(String url)
    {
        super(url, ApiRequestType.GET);
        mStringResponseImpl = new ApiStringResponseImpl<>(this);
    }

    @Override
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                     Map<String, Object> formDataMap,
                                                     Object objectRequestBody,
                                                     RequestBody okHttp3RequestBody,
                                                     String jsonBody,
                                                     ApiService service)
    {
        if (objectRequestBody != null)
        {
            KLog.w("RxHttp method GET must not have a request body：\n" + objectRequestBody.toString());
        } else if (okHttp3RequestBody != null)
        {
            KLog.w("RxHttp method GET must not have a request body：\n" + okHttp3RequestBody.toString());
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            KLog.w("RXHttp method GET must not have a request body：\n" + jsonBody);
        }
        return service.get(getSubUrl(), headersMap, formDataMap);
    }

    @Override
    public Observable<ResultCacheWrapper<String>> returnStringWithCache()
    {
        return mStringResponseImpl.returnStringWithCache();
    }

    @Override
    public Observable<String> returnString()
    {
        return mStringResponseImpl.returnString();
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseRestfulObjectWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulObjectWithCache(tOfClass);
    }

    @Override
    public <T> Observable<T> parseRestfulObject(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulObject(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseCustomObjectWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomObjectWithCache(tOfClass);
    }

    @Override
    public <T> Observable<T> parseCustomObject(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomObject(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseRestfulListWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulListWithCache(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseRestfulList(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulList(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseCustomListWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomListWithCache(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseCustomList(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomList(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T[]>> parseRestfulArrayWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomArrayWithCache(tOfClass);
    }

    @Override
    public <T> Observable<T[]> parseRestfulArray(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulArray(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T[]>> parseCustomArrayWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomArrayWithCache(tOfClass);
    }

    @Override
    public <T> Observable<T[]> parseCustomArray(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomArray(tOfClass);
    }
}
