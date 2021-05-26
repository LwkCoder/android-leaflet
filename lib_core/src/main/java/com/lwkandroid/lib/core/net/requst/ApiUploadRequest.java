package com.lwkandroid.lib.core.net.requst;

import android.text.TextUtils;

import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.net.ApiService;
import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.constants.ApiRequestType;
import com.lwkandroid.lib.core.net.response.ApiStringResponseImpl;
import com.lwkandroid.lib.core.net.response.IApiStringResponse;
import com.lwkandroid.lib.core.net.utils.MultipartBodyList;
import com.lwkandroid.lib.core.net.utils.MultipartBodyUtils;
import com.lwkandroid.lib.core.net.utils.RequestBodyUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * Upload请求
 *
 * @author LWK
 */

public final class ApiUploadRequest extends ApiBaseRequest<ApiUploadRequest> implements IApiStringResponse
{
    /**
     * 单次请求的文件参数
     */
    private MultipartBodyList mBodyList;

    private ApiStringResponseImpl<ApiUploadRequest> mStringResponseImpl;

    public ApiUploadRequest(String url)
    {
        super(url, ApiRequestType.UPLOAD);
        mStringResponseImpl = new ApiStringResponseImpl<>(this);
    }

    /**
     * 获取该次请求的参数
     */
    public MultipartBodyList getPartBodyList()
    {
        return mBodyList;
    }

    /**
     * 添加该次请求的文件
     */
    public ApiUploadRequest addFiles(String key, List<File> fileList)
    {
        checkBodyListNotNull();
        mBodyList.addFiles(key, fileList);
        return this;
    }

    /**
     * 添加该次请求的文件
     */
    public ApiUploadRequest addFile(String key, File file)
    {
        checkBodyListNotNull();
        mBodyList.addFile(key, file);
        return this;
    }

    /**
     * 添加该次请求的字节数据
     */
    public ApiUploadRequest addBytes(String key, String fileName, byte[] bytes)
    {
        checkBodyListNotNull();
        mBodyList.addBytes(key, fileName, bytes);
        return this;
    }

    /**
     * 添加该次请求的流数据
     * 会发生Stream Close的错误，推荐使用其他方法
     */
    @Deprecated
    public ApiUploadRequest addInputStream(String key, String fileName, InputStream stream)
    {
        checkBodyListNotNull();
        mBodyList.addInputStream(key, fileName, stream);
        return this;
    }

    private void checkBodyListNotNull()
    {
        if (mBodyList == null)
        {
            mBodyList = new MultipartBodyList();
        }
    }

    @Override
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                     Map<String, Object> formDataMap,
                                                     Object objectRequestBody,
                                                     RequestBody okHttp3RequestBody,
                                                     String jsonBody,
                                                     ApiService service)
    {
        checkBodyListNotNull();
        if (objectRequestBody != null)
        {
            KLog.w("RxHttp method UPLOAD must not have a Object body：\n" + objectRequestBody.toString());
        } else if (okHttp3RequestBody != null)
        {
            checkBodyListNotNull();
            mBodyList.add(MultipartBodyUtils.createPart(okHttp3RequestBody));
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            checkBodyListNotNull();
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonBody);
            mBodyList.add(MultipartBodyUtils.createPart(jsonRequestBody));
        } else
        {
            checkBodyListNotNull();
            for (Map.Entry<String, Object> entry : formDataMap.entrySet())
            {
                mBodyList.addFormData(entry.getKey(), entry.getValue());
            }
        }

        return service.uploadFiles(getSubUrl(), headersMap, mBodyList);
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
