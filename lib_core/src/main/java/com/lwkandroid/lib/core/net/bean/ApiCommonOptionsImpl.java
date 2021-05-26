package com.lwkandroid.lib.core.net.bean;


import com.lwkandroid.lib.core.net.constants.ApiCacheMode;
import com.lwkandroid.lib.core.net.https.HttpsUtils;
import com.lwkandroid.lib.core.net.parser.ApiStringParser;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;
import com.lwkandroid.lib.core.net.utils.FormDataMap;
import com.lwkandroid.lib.core.utils.common.StringUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;

/**
 * Created by LWK
 * 配置请求参数的公共方法实现类
 *
 * @author LWK
 */
public class ApiCommonOptionsImpl implements IApiRequestOptions.Common<ApiCommonOptionsImpl>
{
    /**
     * 读取超时时间
     */
    private long mReadTimeOut = -1;
    /**
     * 写入超时时间
     */
    private long mWriteTimeOut = -1;
    /**
     * 连接超时时间
     */
    private long mConnectTimeOut = -1;
    /**
     * 请求域名
     */
    private String mBaseUrl;
    /**
     * 全局请求需要添加的拦截器
     */
    private Map<String, Interceptor> mInterceptorMap;
    /**
     * 全局请求需要添加的网络拦截器
     */
    private Map<String, Interceptor> mNetInterceptorMap;
    /**
     * 全局表单参数
     */
    private FormDataMap mFormDataMap;
    /**
     * 全局Header
     */
    private Map<String, String> mHeadersMap;
    /**
     * 全局String类型网络请求结果的解析器
     */
    private IApiStringParser mStringParser;
    /**
     * 缓存类型
     */
    private @ApiCacheMode.Mode
    int mCacheMode;
    /**
     * 缓存时长
     */
    private long mCacheTime = -1;
    /**
     * Https证书
     */
    private HttpsUtils.SSLParams mSslParams;

    /**
     * Https全局访问规则
     */
    private HostnameVerifier mHostnameVerifier;

    @Override
    public ApiCommonOptionsImpl setApiStringParser(IApiStringParser parser)
    {
        mStringParser = parser;
        return this;
    }

    @Override
    public IApiStringParser getApiStringParser()
    {
        if (mStringParser == null)
        {
            mStringParser = new ApiStringParser();
        }
        return mStringParser;
    }

    @Override
    public ApiCommonOptionsImpl setReadTimeOut(long readTimeOut)
    {
        this.mReadTimeOut = readTimeOut;
        return this;
    }

    @Override
    public long getReadTimeOut()
    {
        return mReadTimeOut;
    }

    @Override
    public ApiCommonOptionsImpl setWriteTimeOut(long writeTimeOut)
    {
        this.mWriteTimeOut = writeTimeOut;
        return this;
    }

    @Override
    public long getWriteTimeOut()
    {
        return mWriteTimeOut;
    }

    @Override
    public ApiCommonOptionsImpl setConnectTimeOut(long connectTimeOut)
    {
        this.mConnectTimeOut = connectTimeOut;
        return this;
    }

    @Override
    public long getConnectTimeOut()
    {
        return mConnectTimeOut;
    }

    @Override
    public ApiCommonOptionsImpl setBaseUrl(String baseUrl)
    {
        this.mBaseUrl = baseUrl;
        return this;
    }

    @Override
    public String getBaseUrl()
    {
        return StringUtils.isTrimNotEmpty(mBaseUrl) ? mBaseUrl : "http://127.0.0/";
    }

    @Override
    public Map<String, Interceptor> getInterceptorMap()
    {
        if (mInterceptorMap == null)
        {
            mInterceptorMap = new HashMap<>(4);
        }
        return mInterceptorMap;
    }

    @Override
    public ApiCommonOptionsImpl setInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        this.mInterceptorMap = interceptorMap;
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        getInterceptorMap().putAll(interceptorMap);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        getInterceptorMap().put(tag, interceptor);
        return this;
    }

    @Override
    public Map<String, Interceptor> getNetInterceptorMap()
    {
        if (mNetInterceptorMap == null)
        {
            mNetInterceptorMap = new HashMap<>(4);
        }
        return mNetInterceptorMap;
    }

    @Override
    public ApiCommonOptionsImpl setNetInterceptorMap(Map<String, Interceptor> netInterceptorMap)
    {
        this.mNetInterceptorMap = netInterceptorMap;
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addNetInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        getNetInterceptorMap().putAll(interceptorMap);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addNetInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        getNetInterceptorMap().put(tag, interceptor);
        return this;
    }

    @Override
    public FormDataMap getFormDataMap()
    {
        if (mFormDataMap == null)
        {
            mFormDataMap = new FormDataMap();
        }
        return mFormDataMap;
    }

    @Override
    public ApiCommonOptionsImpl setFormDataMap(FormDataMap formDataMap)
    {
        this.mFormDataMap = formDataMap;
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addFormData(@NonNull String key, byte value)
    {
        getFormDataMap().addParam(key, value);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addFormData(@NonNull String key, int value)
    {
        getFormDataMap().addParam(key, value);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addFormData(@NonNull String key, float value)
    {
        getFormDataMap().addParam(key, value);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addFormData(@NonNull String key, short value)
    {
        getFormDataMap().addParam(key, value);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addFormData(@NonNull String key, long value)
    {
        getFormDataMap().addParam(key, value);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addFormData(@NonNull String key, double value)
    {
        getFormDataMap().addParam(key, value);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addFormData(@NonNull String key, boolean value)
    {
        getFormDataMap().addParam(key, value);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addFormData(@NonNull String key, String value)
    {
        getFormDataMap().addParam(key, value);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addFormData(@NonNull String key, Object value)
    {
        getFormDataMap().addParam(key, value);
        return this;
    }

    @Override
    public Map<String, String> getHeadersMap()
    {
        if (mHeadersMap == null)
        {
            mHeadersMap = new HashMap<>(4);
        }
        return mHeadersMap;
    }

    @Override
    public ApiCommonOptionsImpl setHeadersMap(Map<String, String> headersMap)
    {
        this.mHeadersMap = headersMap;
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addHeadersMap(Map<String, String> headersMap)
    {
        getHeadersMap().putAll(headersMap);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl addHeader(@NonNull String tag, String value)
    {
        getHeadersMap().put(tag, value);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl setCacheMode(int mode)
    {
        this.mCacheMode = mode;
        return this;
    }

    @Override
    public int getCacheMode()
    {
        return mCacheMode;
    }

    @Override
    public ApiCommonOptionsImpl setCacheTime(long cacheTime)
    {
        this.mCacheTime = cacheTime;
        return this;
    }

    @Override
    public long getCacheTime()
    {
        return mCacheTime;
    }

    @Override
    public ApiCommonOptionsImpl setHttpsCertificates(InputStream... certificates)
    {
        this.mSslParams = HttpsUtils.createSSLParams(null, null, certificates);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl setHttpsCertificates(InputStream bksFile, String password, InputStream... certificates)
    {
        this.mSslParams = HttpsUtils.createSSLParams(bksFile, password, certificates);
        return this;
    }

    @Override
    public ApiCommonOptionsImpl setHttpsSSLParams(HttpsUtils.SSLParams params)
    {
        this.mSslParams = params;
        return this;
    }

    @Override
    public HttpsUtils.SSLParams getHttpsSSLParams()
    {
        return mSslParams;
    }

    @Override
    public ApiCommonOptionsImpl setHostnameVerifier(HostnameVerifier verifier)
    {
        this.mHostnameVerifier = verifier;
        return this;
    }

    @Override
    public HostnameVerifier getHostnameVerifier()
    {
        return mHostnameVerifier;
    }
}
