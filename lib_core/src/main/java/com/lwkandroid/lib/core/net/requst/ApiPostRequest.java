package com.lwkandroid.lib.core.net.requst;


import com.lwkandroid.lib.core.net.ApiService;
import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.constants.ApiConstants;
import com.lwkandroid.lib.core.net.constants.ApiRequestType;
import com.lwkandroid.lib.core.net.response.ApiStringResponseImpl;
import com.lwkandroid.lib.core.net.response.IApiStringResponse;
import com.lwkandroid.lib.core.net.utils.RequestBodyUtils;
import com.lwkandroid.lib.core.utils.json.JsonUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * Post请求
 *
 * @author LWK
 */
public final class ApiPostRequest extends ApiBaseRequest<ApiPostRequest> implements IApiStringResponse
{
    private ApiStringResponseImpl<ApiPostRequest> mStringResponseImpl;
    private boolean mIsFormDataJson = false;

    public ApiPostRequest(String url)
    {
        super(url, ApiRequestType.POST);
        mStringResponseImpl = new ApiStringResponseImpl<>(this);
    }

    public ApiPostRequest convertToJsonRequest()
    {
        mIsFormDataJson = true;
        return this;
    }

    public boolean isConvertToJsonRequest()
    {
        return mIsFormDataJson;
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
            return service.post(getSubUrl(), headersMap, objectRequestBody);
        } else if (okHttp3RequestBody != null)
        {
            return service.post(getSubUrl(), headersMap, okHttp3RequestBody);
        } else if (jsonBody != null)
        {
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonBody);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.post(getSubUrl(), headersMap, jsonRequestBody);
        } else if (mIsFormDataJson)
        {
            String jsonString = JsonUtils.toJson(formDataMap);
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonString);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.post(getSubUrl(), headersMap, jsonRequestBody);
        }
        {
            return service.post(getSubUrl(), headersMap, formDataMap);
        }
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
