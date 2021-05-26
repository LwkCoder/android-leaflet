package com.lwkandroid.lib.core.net.requst;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.lwkandroid.lib.core.net.ApiService;
import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.constants.ApiConstants;
import com.lwkandroid.lib.core.net.constants.ApiRequestType;
import com.lwkandroid.lib.core.net.exception.ApiErrorHandlerTransformer;
import com.lwkandroid.lib.core.net.parser.ApiBytes2BitmapParser;
import com.lwkandroid.lib.core.net.parser.ApiBytes2FileParser;
import com.lwkandroid.lib.core.net.parser.ApiIS2BitmapParser;
import com.lwkandroid.lib.core.net.parser.ApiIS2FileParser;
import com.lwkandroid.lib.core.net.response.ApiResponseBodyConverter;
import com.lwkandroid.lib.core.net.response.IApiBytesResponse;
import com.lwkandroid.lib.core.net.response.IApiInputStreamResponse;
import com.lwkandroid.lib.core.net.utils.RequestBodyUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * Download请求
 *
 * @author LWK
 */

public final class ApiDownloadRequest extends ApiBaseRequest<ApiDownloadRequest> implements IApiInputStreamResponse,
        IApiBytesResponse
{
    /**
     * 存储文件夹
     */
    private String mSaveFolder;
    /**
     * 存储名称
     */
    private String mFileName;
    /**
     * Bitmap最大宽度
     */
    private int mBitmapMaxWidth = Integer.MAX_VALUE;
    /**
     * Bitmap最大高度
     */
    private int mBitmapMaxHeight = Integer.MAX_VALUE;

    public ApiDownloadRequest(String url)
    {
        super(url, ApiRequestType.DOWNLOAD);
    }

    public ApiDownloadRequest setSaveFolderPath(String path)
    {
        this.mSaveFolder = path;
        return this;
    }

    public ApiDownloadRequest setFileName(String fileName)
    {
        this.mFileName = fileName;
        return this;
    }

    public ApiDownloadRequest setBitmapMaxSize(int maxWidth, int maxHeight)
    {
        this.mBitmapMaxWidth = maxWidth;
        this.mBitmapMaxHeight = maxHeight;
        return this;
    }

    public int getBitmapMaxHeight()
    {
        return mBitmapMaxHeight;
    }

    public int getBitmapMaxWidth()
    {
        return mBitmapMaxWidth;
    }

    public String getSaveFolderPath()
    {
        return mSaveFolder;
    }

    public String getFileName()
    {
        return mFileName;
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
            return service.downloadFile(getSubUrl(), headersMap, objectRequestBody);
        } else if (okHttp3RequestBody != null)
        {
            return service.downloadFile(getSubUrl(), headersMap, okHttp3RequestBody);
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonBody);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.downloadFile(getSubUrl(), headersMap, jsonRequestBody);
        } else if (formDataMap != null && formDataMap.size() > 0)
        {
            return service.downloadFile(getSubUrl(), headersMap, formDataMap);
        } else
        {
            return service.downloadFile(getSubUrl(), headersMap);
        }
    }

    @Override
    public Observable<InputStream> returnISResponse()
    {
        return invokeRequest()
                .map(ApiResponseBodyConverter.convertToInputStream())
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public Observable<File> parseAsFileFromIS()
    {
        return invokeRequest()
                .map(ApiResponseBodyConverter.convertToInputStream())
                .compose(new ApiIS2FileParser(getSaveFolderPath(), getFileName()).parseAsFile())
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public Observable<Bitmap> parseAsBitmapFromIS()
    {
        return invokeRequest()
                .map(ApiResponseBodyConverter.convertToInputStream())
                .compose(new ApiIS2BitmapParser(getBitmapMaxWidth(), getBitmapMaxHeight()).parseAsBitmap())
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public Observable<byte[]> returnByteArrayResponse()
    {
        return invokeRequest()
                .map(ApiResponseBodyConverter.convertToBytes())
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public Observable<File> parseAsFileFromBytes()
    {
        return invokeRequest()
                .map(ApiResponseBodyConverter.convertToBytes())
                .compose(new ApiBytes2FileParser(getSaveFolderPath(), getFileName()).parseAsFile())
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }

    @Override
    public Observable<Bitmap> parseAsBitmapFromBytes()
    {
        return invokeRequest()
                .map(ApiResponseBodyConverter.convertToBytes())
                .compose(new ApiBytes2BitmapParser(getBitmapMaxWidth(), getBitmapMaxHeight()).parseAsBitmap())
                .compose(new ApiErrorHandlerTransformer<>(RxHttp.getGlobalOptions().getRetryConfig(),
                        RxHttp.getGlobalOptions().getApiErrorConsumer()));
    }
}
