package com.lwkandroid.lib.core.net.bean;


import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.constants.ApiRequestType;
import com.lwkandroid.lib.core.net.https.HttpsUtils;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;
import com.lwkandroid.lib.core.net.utils.FormDataMap;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;

import androidx.annotation.NonNull;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.RequestBody;

/**
 * 单次网络请求的配置
 *
 * @author LWK
 */
public class ApiBaseRequestOptions<T extends ApiBaseRequestOptions> implements IApiRequestOptions.Custom<ApiBaseRequestOptions>
{
    private IApiRequestOptions.Common mCommonImpl = new ApiCommonOptionsImpl();
    /**
     * 请求类型
     */
    @ApiRequestType.Type
    private int mRequestType;

    /**
     * 请求链接
     */
    private String mSubUrl;

    /**
     * 单次请求需要排除的全局拦截器TAG集合
     */
    private Set<String> mIgnoreInterceptorSet;

    /**
     * 单次请求需要排除的全局网络拦截器TAG集合
     */
    private Set<String> mIgnoreNetInterceptorSet;

    /**
     * 单次请求需要排除的全局表单参数Key集合
     */
    private Set<String> mIgnoreFormDataSet;

    /**
     * 单次请求需要排除的全局Header参数Key集合
     */
    private Set<String> mIgnoreHeaderSet;

    /**
     * 单次请求是否去除所有全局拦截器
     */
    private boolean mIsIgnoreAllGlobalInterceptors = false;

    /**
     * 单次请求是否去除所有网络拦截器
     */
    private boolean mIsIgnoreAllGlobalNetInterceptors = false;

    /**
     * 单次请求是否去除所有全局参数
     */
    private boolean mIsIgnoreAllGlobalFormDatas = false;

    /**
     * 单次请求是否去除所有全局Header
     */
    private boolean mIsIgnoreAllGlobalHeaders = false;

    /**
     * 单次请求的ObjectBody
     */
    private Object mObjectRequestBody;

    /**
     * 单次请求的RequestBody
     */
    private RequestBody mOkHttp3RequestBody;

    /**
     * 单次请求的JsonBody
     */
    private String mJsonRequestBody;

    /**
     * Cookie
     */
    private List<Cookie> mCookieList;

    /**
     * 缓存key
     */
    private String mCacheKey;

    public ApiBaseRequestOptions()
    {
        //初始化的时候保持和全局配置一样
        setApiStringParser(RxHttp.getGlobalOptions().getApiStringParser());
        setConnectTimeOut(RxHttp.getGlobalOptions().getConnectTimeOut());
        setWriteTimeOut(RxHttp.getGlobalOptions().getWriteTimeOut());
        setReadTimeOut(RxHttp.getGlobalOptions().getReadTimeOut());
        setBaseUrl(RxHttp.getGlobalOptions().getBaseUrl());
        setCacheMode(RxHttp.getGlobalOptions().getCacheMode());
        setCacheTime(RxHttp.getGlobalOptions().getCacheTime());
        setHttpsSSLParams(RxHttp.getGlobalOptions().getHttpsSSLParams());
        setHostnameVerifier(RxHttp.getGlobalOptions().getHostnameVerifier());
    }

    @Override
    public T setApiStringParser(IApiStringParser parser)
    {
        mCommonImpl.setApiStringParser(parser);
        return (T) this;
    }

    @Override
    public IApiStringParser getApiStringParser()
    {
        return mCommonImpl.getApiStringParser();
    }

    @Override
    public T setReadTimeOut(long readTimeOut)
    {
        mCommonImpl.setReadTimeOut(readTimeOut);
        return (T) this;
    }

    @Override
    public long getReadTimeOut()
    {
        return mCommonImpl.getReadTimeOut();
    }

    @Override
    public T setWriteTimeOut(long writeTimeOut)
    {
        mCommonImpl.setWriteTimeOut(writeTimeOut);
        return (T) this;
    }

    @Override
    public long getWriteTimeOut()
    {
        return mCommonImpl.getWriteTimeOut();
    }

    @Override
    public T setConnectTimeOut(long connectTimeOut)
    {
        mCommonImpl.setConnectTimeOut(connectTimeOut);
        return (T) this;
    }

    @Override
    public long getConnectTimeOut()
    {
        return mCommonImpl.getConnectTimeOut();
    }

    @Override
    public T setBaseUrl(String baseUrl)
    {
        mCommonImpl.setBaseUrl(baseUrl);
        return (T) this;
    }

    @Override
    public String getBaseUrl()
    {
        return mCommonImpl.getBaseUrl();
    }

    @Override
    public Map<String, Interceptor> getInterceptorMap()
    {
        return mCommonImpl.getInterceptorMap();
    }

    @Override
    public T setInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        mCommonImpl.setInterceptorMap(interceptorMap);
        return (T) this;
    }

    @Override
    public T addInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        mCommonImpl.addInterceptorMap(interceptorMap);
        return (T) this;
    }

    @Override
    public T addInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        mCommonImpl.addInterceptor(tag, interceptor);
        return (T) this;
    }

    @Override
    public Map<String, Interceptor> getNetInterceptorMap()
    {
        return mCommonImpl.getNetInterceptorMap();
    }

    @Override
    public T setNetInterceptorMap(Map<String, Interceptor> netInterceptorMap)
    {
        mCommonImpl.setNetInterceptorMap(netInterceptorMap);
        return (T) this;
    }

    @Override
    public T addNetInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        mCommonImpl.addNetInterceptorMap(interceptorMap);
        return (T) this;
    }

    @Override
    public T addNetInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        mCommonImpl.addNetInterceptor(tag, interceptor);
        return (T) this;
    }

    @Override
    public FormDataMap getFormDataMap()
    {
        return mCommonImpl.getFormDataMap();
    }

    @Override
    public T setFormDataMap(FormDataMap formDataMap)
    {
        mCommonImpl.setFormDataMap(formDataMap);
        return (T) this;
    }

    @Override
    public T addFormData(@NonNull String key, byte value)
    {
        mCommonImpl.addFormData(key, value);
        return (T) this;
    }

    @Override
    public T addFormData(@NonNull String key, int value)
    {
        mCommonImpl.addFormData(key, value);
        return (T) this;
    }

    @Override
    public T addFormData(@NonNull String key, float value)
    {
        mCommonImpl.addFormData(key, value);
        return (T) this;
    }

    @Override
    public T addFormData(@NonNull String key, short value)
    {
        mCommonImpl.addFormData(key, value);
        return (T) this;
    }

    @Override
    public T addFormData(@NonNull String key, long value)
    {
        mCommonImpl.addFormData(key, value);
        return (T) this;
    }

    @Override
    public T addFormData(@NonNull String key, double value)
    {
        mCommonImpl.addFormData(key, value);
        return (T) this;
    }

    @Override
    public T addFormData(@NonNull String key, boolean value)
    {
        mCommonImpl.addFormData(key, value);
        return (T) this;
    }

    @Override
    public T addFormData(@NonNull String key, String value)
    {
        mCommonImpl.addFormData(key, value);
        return (T) this;
    }

    @Override
    public T addFormData(@NonNull String key, Object value)
    {
        mCommonImpl.addFormData(key, value);
        return (T) this;
    }

    @Override
    public Map<String, String> getHeadersMap()
    {
        return mCommonImpl.getHeadersMap();
    }

    @Override
    public T setHeadersMap(Map<String, String> headersMap)
    {
        mCommonImpl.setHeadersMap(headersMap);
        return (T) this;
    }

    @Override
    public T addHeadersMap(Map<String, String> headersMap)
    {
        mCommonImpl.addHeadersMap(headersMap);
        return (T) this;
    }

    @Override
    public T addHeader(@NonNull String tag, String value)
    {
        mCommonImpl.addHeader(tag, value);
        return (T) this;
    }

    @Override
    public T setCacheMode(int mode)
    {
        mCommonImpl.setCacheMode(mode);
        return (T) this;
    }

    @Override
    public int getCacheMode()
    {
        return mCommonImpl.getCacheMode();
    }

    @Override
    public T setCacheTime(long cacheTime)
    {
        mCommonImpl.setCacheTime(cacheTime);
        return (T) this;
    }

    @Override
    public long getCacheTime()
    {
        return mCommonImpl.getCacheTime();
    }

    @Override
    public T setRequestType(int type)
    {
        this.mRequestType = type;
        return (T) this;
    }

    @Override
    public int getRequestType()
    {
        return mRequestType;
    }

    @Override
    public T setSubUrl(String url)
    {
        this.mSubUrl = url;
        return (T) this;
    }

    @Override
    public String getSubUrl()
    {
        return mSubUrl;
    }

    @Override
    public Set<String> getIgnoreInterceptors()
    {
        if (mIgnoreInterceptorSet == null)
        {
            mIgnoreInterceptorSet = new HashSet<>();
        }
        return mIgnoreInterceptorSet;
    }

    @Override
    public T setIgnoreInterceptors(Set<String> tags)
    {
        this.mIgnoreInterceptorSet = tags;
        return (T) this;
    }

    @Override
    public T addIgnoreInterceptors(Set<String> tags)
    {
        getIgnoreInterceptors().addAll(tags);
        return (T) this;
    }

    @Override
    public T addIgnoreInterceptors(String tag)
    {
        getIgnoreInterceptors().add(tag);
        return (T) this;
    }

    @Override
    public Set<String> getIgnoreNetInterceptors()
    {
        if (mIgnoreNetInterceptorSet == null)
        {
            mIgnoreNetInterceptorSet = new HashSet<>();
        }
        return mIgnoreNetInterceptorSet;
    }

    @Override
    public T setIgnoreNetInterceptors(Set<String> tags)
    {
        this.mIgnoreNetInterceptorSet = tags;
        return (T) this;
    }

    @Override
    public T addIgnoreNetInterceptors(Set<String> tagList)
    {
        getIgnoreNetInterceptors().addAll(tagList);
        return (T) this;
    }

    @Override
    public T addIgnoreNetInterceptor(String tag)
    {
        getIgnoreNetInterceptors().add(tag);
        return (T) this;
    }

    @Override
    public Set<String> getIgnoreFormDataSet()
    {
        if (mIgnoreFormDataSet == null)
        {
            mIgnoreFormDataSet = new HashSet<>();
        }
        return mIgnoreFormDataSet;
    }

    @Override
    public T setIgnoreFormDataList(Set<String> keys)
    {
        this.mIgnoreFormDataSet = keys;
        return (T) this;
    }

    @Override
    public T addIgnoreFormData(Set<String> keys)
    {
        getIgnoreFormDataSet().addAll(keys);
        return (T) this;
    }

    @Override
    public T addIgnoreFormData(String key)
    {
        getIgnoreFormDataSet().add(key);
        return (T) this;
    }

    @Override
    public Set<String> getIgnoreHeaders()
    {
        if (mIgnoreHeaderSet == null)
        {
            mIgnoreHeaderSet = new HashSet<>();
        }
        return mIgnoreFormDataSet;
    }

    @Override
    public T setIgnoreHeaderList(Set<String> tags)
    {
        this.mIgnoreHeaderSet = tags;
        return (T) this;
    }

    @Override
    public T addIgnoreHeaderList(Set<String> tags)
    {
        getIgnoreHeaders().addAll(tags);
        return (T) this;
    }

    @Override
    public T addIgnoreHeader(String tag)
    {
        getIgnoreHeaders().add(tag);
        return (T) this;
    }

    @Override
    public T ignoreAllGlobalInterceptors()
    {
        mIsIgnoreAllGlobalInterceptors = true;
        return (T) this;
    }

    @Override
    public boolean isIgnoreAllGlobalInterceptors()
    {
        return mIsIgnoreAllGlobalInterceptors;
    }

    @Override
    public T ignoreAllGlobalNetInterceptors()
    {
        mIsIgnoreAllGlobalNetInterceptors = true;
        return (T) this;
    }

    @Override
    public boolean isIgnoreAllGlobalNetInterceptors()
    {
        return mIsIgnoreAllGlobalNetInterceptors;
    }

    @Override
    public T ignoreAllGlobalFormData()
    {
        mIsIgnoreAllGlobalFormDatas = true;
        return (T) this;
    }

    @Override
    public boolean isIgnoreAllGlobalFormData()
    {
        return mIsIgnoreAllGlobalFormDatas;
    }

    @Override
    public T ignoreAllGlobalHeaders()
    {
        mIsIgnoreAllGlobalHeaders = true;
        return (T) this;
    }

    @Override
    public boolean isIgnoreAllGlobalHeaders()
    {
        return mIsIgnoreAllGlobalHeaders;
    }

    @Override
    public T setObjectRequestBody(Object body)
    {
        this.mObjectRequestBody = body;
        return (T) this;
    }

    @Override
    public Object getObjectRequestBody()
    {
        return mObjectRequestBody;
    }

    @Override
    public T setOkHttp3RequestBody(RequestBody body)
    {
        this.mOkHttp3RequestBody = body;
        return (T) this;
    }

    @Override
    public RequestBody getOkHttp3RequestBody()
    {
        return mOkHttp3RequestBody;
    }

    @Override
    public T setJsonRequestBody(String body)
    {
        this.mJsonRequestBody = body;
        return (T) this;
    }

    @Override
    public String getJsonRequestBody()
    {
        return mJsonRequestBody;
    }

    @Override
    public List<Cookie> getCookieList()
    {
        if (mCookieList == null)
        {
            mCookieList = new ArrayList<>();
        }
        return mCookieList;
    }

    @Override
    public T addCookie(String name, String value)
    {
        HttpUrl httpUrl = HttpUrl.parse(getBaseUrl());
        Cookie.Builder builder = new Cookie.Builder();
        Cookie cookie = builder.name(name).value(value).domain(httpUrl.host()).build();
        getCookieList().add(cookie);
        return (T) this;
    }

    @Override
    public T addCookie(Cookie cookie)
    {
        getCookieList().add(cookie);
        return (T) this;
    }

    @Override
    public T addCookies(List<Cookie> cookieList)
    {
        getCookieList().addAll(cookieList);
        return (T) this;
    }

    @Override
    public T removeCookie(Cookie cookie)
    {
        getCookieList().remove(cookie);
        return (T) this;
    }

    @Override
    public T clearAllCookies()
    {
        getCookieList().clear();
        return (T) this;
    }

    @Override
    public T setHttpsCertificates(InputStream... certificates)
    {
        mCommonImpl.setHttpsCertificates(certificates);
        return (T) this;
    }

    @Override
    public T setHttpsCertificates(InputStream bksFile, String password, InputStream... certificates)
    {
        mCommonImpl.setHttpsCertificates(bksFile, password, certificates);
        return (T) this;
    }

    @Override
    public T setHttpsSSLParams(HttpsUtils.SSLParams params)
    {
        mCommonImpl.setHttpsSSLParams(params);
        return (T) this;
    }

    @Override
    public HttpsUtils.SSLParams getHttpsSSLParams()
    {
        return mCommonImpl.getHttpsSSLParams();
    }

    @Override
    public T setHostnameVerifier(HostnameVerifier verifier)
    {
        mCommonImpl.setHostnameVerifier(verifier);
        return (T) this;
    }

    @Override
    public HostnameVerifier getHostnameVerifier()
    {
        return mCommonImpl.getHostnameVerifier();
    }

    @Override
    public T setCacheKey(String key)
    {
        this.mCacheKey = key;
        return (T) this;
    }

    @Override
    public String getCacheKey()
    {
        return mCacheKey;
    }
}
