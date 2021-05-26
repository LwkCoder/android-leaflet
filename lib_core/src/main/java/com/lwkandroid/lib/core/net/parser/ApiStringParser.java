package com.lwkandroid.lib.core.net.parser;


import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.net.bean.IApiRestfulResult;
import com.lwkandroid.lib.core.net.constants.ApiExceptionCode;
import com.lwkandroid.lib.core.utils.common.StringUtils;
import com.lwkandroid.lib.core.utils.json.JsonUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 将String类型的网络请求结果转换为{@link IApiRestfulResult}的实现类
 *
 * @author LWK
 */
public class ApiStringParser implements IApiStringParser
{
    @Override
    public <T> T parseCustomDataObject(String data, Class<T> dataClass)
    {
        return JsonUtils.fromJson(data, dataClass);
    }

    @Override
    public <T> T parseRestfulDataObject(String data, Class<T> dataClass) throws Exception
    {
        String dataJsonString = parseRestfulDataJson(data);
        return StringUtils.isNotEmpty(dataJsonString) ?
                JsonUtils.fromJson(dataJsonString, dataClass) : dataClass.newInstance();
    }

    @Override
    public <T> List<T> parseCustomDataList(String data, Class<T> dataClass) throws Exception
    {
        return JsonUtils.fromJson(data, JsonUtils.getListType(dataClass));
    }

    @Override
    public <T> List<T> parseRestfulDataList(String data, Class<T> dataClass) throws Exception
    {
        String dataJsonString = parseRestfulDataJson(data);
        return StringUtils.isNotEmpty(dataJsonString) ?
                JsonUtils.fromJson(dataJsonString, JsonUtils.getListType(dataClass)) : new ArrayList<>();
    }

    @Override
    public <T> T[] parseCustomDataArray(String data, Class<T> dataClass) throws Exception
    {
        return JsonUtils.fromJson(data, JsonUtils.getArrayType(dataClass));
    }

    @Override
    public <T> T[] parseRestfulDataArray(String data, Class<T> dataClass) throws Exception
    {
        String dataJsonString = parseRestfulDataJson(data);
        return StringUtils.isNotEmpty(dataJsonString) ?
                JsonUtils.fromJson(dataJsonString, JsonUtils.getArrayType(dataClass)) : (T[]) Array.newInstance(dataClass, 0);
    }

    /**
     * 将获取Restful中Data数据的Json
     *
     * @param response 网络请求String结果
     * @return
     * @throws ApiException
     */
    private String parseRestfulDataJson(String response) throws ApiException
    {
        IApiRestfulResult result = JsonUtils.fromJson(response, RxHttp.getGlobalOptions().getApiRestfulResultType());
        if (result == null)
        {
            throw new ApiException(ApiExceptionCode.RESPONSE_EMPTY, "Could not get any Response");
        }
        if (!result.isResultOK())
        {
            throw new ApiException(result.getCode(), result.getMessage());
        }
        Object resultData = result.getData();
        return resultData != null ? JsonUtils.toJson(result.getData()) : null;
    }
}
