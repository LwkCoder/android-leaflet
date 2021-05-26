package com.lwkandroid.lib.core.net.bean;


import com.lwkandroid.lib.core.callback.WingsSupplier;
import com.lwkandroid.lib.core.net.cache.operator.IDiskCacheOperator;
import com.lwkandroid.lib.core.net.constants.ApiCacheMode;
import com.lwkandroid.lib.core.net.constants.ApiConstants;
import com.lwkandroid.lib.core.net.cookie.CookieManager;
import com.lwkandroid.lib.core.net.cookie.ICookieJar;
import com.lwkandroid.lib.core.net.exception.ApiErrorConsumer;
import com.lwkandroid.lib.core.net.exception.ApiExceptionMsgParserImpl;
import com.lwkandroid.lib.core.net.exception.IApiExceptionMsgParser;
import com.lwkandroid.lib.core.net.exception.RetryConfig;
import com.lwkandroid.lib.core.net.https.HttpsUtils;
import com.lwkandroid.lib.core.net.parser.ApiStringParser;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;
import com.lwkandroid.lib.core.net.utils.FormDataMap;
import com.lwkandroid.lib.core.utils.common.StringUtils;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;

/**
 * Created by LWK
 * 网络请求全局配置对象
 *
 * @author LWK
 */
public class ApiGlobalOptions implements IApiRequestOptions.Global<ApiGlobalOptions>
{
    private IApiRequestOptions.Common mCommonImpl = new ApiCommonOptionsImpl();
    /**
     * 网络请求正常的状态码
     */
    private int mResultOkCode;
    /**
     * 网络请求结果对象Type
     */
    private Type mApiResultType;
    /**
     * Cookie管理类
     */
    private ICookieJar mCookieJar;
    /**
     * App版本
     */
    private int mCacheVersion = -1;
    /**
     * 缓存路径
     */
    private String mCachePath;
    /**
     * 硬盘缓存大小
     */
    private long mDiskMaxSize = -1;
    /**
     * 缓存转换器
     */
    private IDiskCacheOperator mCacheOperator = null;
    /**
     * 设置错误描述的对象
     */
    private IApiExceptionMsgParser mApiExceptionMsgParser;
    /**
     * 动态参数Map
     */
    private Map<String, WingsSupplier<Object>> mDynamicFormDataMap;
    /**
     * 动态HeaderMap
     */
    private Map<String, WingsSupplier<String>> mDynamicHeaderMap;
    /**
     * 自动重试配置
     */
    private RetryConfig mRetryConfig;
    /**
     * 发生错误后统一处理
     */
    private ApiErrorConsumer mErrorConsumer;

    public ApiGlobalOptions()
    {
        setConnectTimeOut(ApiConstants.CONNECT_TIMEOUT);
        setWriteTimeOut(ApiConstants.WRITE_TIMEOUT);
        setReadTimeOut(ApiConstants.READ_TIMEOUT);
        setApiRestfulResultType(ApiRestfulResult.class);
        setApiRestfulResultOkCode(ApiConstants.RESULT_OK_CODE);
        setApiStringParser(new ApiStringParser());
        setApiExceptionMsgParser(new ApiExceptionMsgParserImpl());
        setCacheMode(ApiCacheMode.NO_CACHE);
        setCookieManager(new CookieManager());
        setRetryConfig(RetryConfig.noRetry());
        setApiErrorConsumer(ApiErrorConsumer.empty());
    }

    @Override
    public ApiGlobalOptions setApiStringParser(IApiStringParser parser)
    {
        mCommonImpl.setApiStringParser(parser);
        return this;
    }

    @Override
    public IApiStringParser getApiStringParser()
    {
        return mCommonImpl.getApiStringParser();
    }

    @Override
    public ApiGlobalOptions setApiRestfulResultType(Type type)
    {
        this.mApiResultType = type;
        return this;
    }

    @Override
    public Type getApiRestfulResultType()
    {
        return mApiResultType;
    }

    @Override
    public ApiGlobalOptions setReadTimeOut(long readTimeOut)
    {
        mCommonImpl.setReadTimeOut(readTimeOut);
        return this;
    }

    @Override
    public long getReadTimeOut()
    {
        return mCommonImpl.getReadTimeOut();
    }

    @Override
    public ApiGlobalOptions setWriteTimeOut(long writeTimeOut)
    {
        mCommonImpl.setWriteTimeOut(writeTimeOut);
        return this;
    }

    @Override
    public long getWriteTimeOut()
    {
        return mCommonImpl.getWriteTimeOut();
    }

    @Override
    public ApiGlobalOptions setConnectTimeOut(long connectTimeOut)
    {
        mCommonImpl.setConnectTimeOut(connectTimeOut);
        return this;
    }

    @Override
    public long getConnectTimeOut()
    {
        return mCommonImpl.getConnectTimeOut();
    }

    @Override
    public ApiGlobalOptions setBaseUrl(String baseUrl)
    {
        mCommonImpl.setBaseUrl(baseUrl);
        return this;
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
    public ApiGlobalOptions setInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        mCommonImpl.setInterceptorMap(interceptorMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        mCommonImpl.addInterceptorMap(interceptorMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        mCommonImpl.addInterceptor(tag, interceptor);
        return this;
    }

    @Override
    public Map<String, Interceptor> getNetInterceptorMap()
    {
        return mCommonImpl.getNetInterceptorMap();
    }

    @Override
    public ApiGlobalOptions setNetInterceptorMap(Map<String, Interceptor> netInterceptorMap)
    {
        mCommonImpl.setNetInterceptorMap(netInterceptorMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addNetInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        mCommonImpl.addNetInterceptorMap(interceptorMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addNetInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        mCommonImpl.addNetInterceptor(tag, interceptor);
        return this;
    }

    @Override
    public FormDataMap getFormDataMap()
    {
        return mCommonImpl.getFormDataMap();
    }

    @Override
    public ApiGlobalOptions setFormDataMap(FormDataMap formDataMap)
    {
        mCommonImpl.setFormDataMap(formDataMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, byte value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, int value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, float value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, short value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, long value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, double value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, boolean value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, String value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, Object value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public Map<String, String> getHeadersMap()
    {
        return mCommonImpl.getHeadersMap();
    }

    @Override
    public ApiGlobalOptions setHeadersMap(Map<String, String> headersMap)
    {
        mCommonImpl.setHeadersMap(headersMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addHeadersMap(Map<String, String> headersMap)
    {
        mCommonImpl.addHeadersMap(headersMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addHeader(@NonNull String tag, String value)
    {
        mCommonImpl.addHeader(tag, value);
        return this;
    }

    @Override
    public ApiGlobalOptions setCacheMode(@ApiCacheMode.Mode int mode)
    {
        mCommonImpl.setCacheMode(mode);
        return this;
    }

    @Override
    public int getCacheMode()
    {
        return mCommonImpl.getCacheMode();
    }

    @Override
    public ApiGlobalOptions setCacheTime(long cacheTime)
    {
        mCommonImpl.setCacheTime(cacheTime);
        return this;
    }

    @Override
    public long getCacheTime()
    {
        return mCommonImpl.getCacheTime();
    }

    @Override
    public ApiGlobalOptions setHttpsCertificates(InputStream... certificates)
    {
        mCommonImpl.setHttpsCertificates(certificates);
        return this;
    }

    @Override
    public ApiGlobalOptions setHttpsCertificates(InputStream bksFile, String password, InputStream... certificates)
    {
        mCommonImpl.setHttpsCertificates(bksFile, password, certificates);
        return this;
    }

    @Override
    public ApiGlobalOptions setHttpsSSLParams(HttpsUtils.SSLParams params)
    {
        mCommonImpl.setHttpsSSLParams(params);
        return this;
    }

    @Override
    public HttpsUtils.SSLParams getHttpsSSLParams()
    {
        return mCommonImpl.getHttpsSSLParams();
    }

    @Override
    public ApiGlobalOptions setHostnameVerifier(HostnameVerifier verifier)
    {
        mCommonImpl.setHostnameVerifier(verifier);
        return this;
    }

    @Override
    public HostnameVerifier getHostnameVerifier()
    {
        return mCommonImpl.getHostnameVerifier();
    }

    @Override
    public ApiGlobalOptions setApiRestfulResultOkCode(int code)
    {
        this.mResultOkCode = code;
        return this;
    }

    @Override
    public int getApiRestfulResultOkCode()
    {
        return mResultOkCode;
    }

    @Override
    public ApiGlobalOptions removeFormData(String key)
    {
        getFormDataMap().remove(key);
        return this;
    }

    @Override
    public ApiGlobalOptions clearFormData()
    {
        getFormDataMap().clear();
        return this;
    }

    @Override
    public ApiGlobalOptions removeHeader(String key)
    {
        getHeadersMap().remove(key);
        return this;
    }

    @Override
    public ApiGlobalOptions clearHeaders()
    {
        getHeadersMap().clear();
        return this;
    }

    @Override
    public ApiGlobalOptions addDynamicHeader(String tag, WingsSupplier<String> supplier)
    {
        if (StringUtils.isTrimEmpty(tag))
        {
            throw new IllegalArgumentException("RxHttp addDynamicHeader() tag can not be trim empty !");
        }
        if (supplier == null)
        {
            throw new IllegalArgumentException("RxHttp addDynamicHeader() callback can not be null !");
        }
        getDynamicHeaderMap().put(tag, supplier);
        return this;
    }

    @Override
    public ApiGlobalOptions removeDynamicHeader(String tag)
    {
        getDynamicHeaderMap().remove(tag);
        return this;
    }

    @Override
    public ApiGlobalOptions clearDynamicHeader()
    {
        getDynamicHeaderMap().clear();
        return this;
    }

    @Override
    public Map<String, WingsSupplier<String>> getDynamicHeaderMap()
    {
        if (mDynamicHeaderMap == null)
        {
            mDynamicHeaderMap = new TreeMap<>();
        }
        return mDynamicHeaderMap;
    }

    @Override
    public ApiGlobalOptions setCookieManager(ICookieJar cookieJarImpl)
    {
        this.mCookieJar = cookieJarImpl;
        return this;
    }

    @Override
    public ICookieJar getCookieManager()
    {
        return mCookieJar;
    }

    @Override
    public ApiGlobalOptions setCacheVersion(int version)
    {
        this.mCacheVersion = version;
        return this;
    }

    @Override
    public int getCacheVersion()
    {
        return mCacheVersion;
    }

    @Override
    public ApiGlobalOptions setCachePath(String path)
    {
        this.mCachePath = path;
        return this;
    }

    @Override
    public String getCachePath()
    {
        return mCachePath;
    }

    @Override
    public ApiGlobalOptions setCacheDiskMaxSize(long maxSize)
    {
        this.mDiskMaxSize = maxSize;
        return this;
    }

    @Override
    public long getCacheDiskMaxSize()
    {
        return mDiskMaxSize;
    }

    @Override
    public ApiGlobalOptions setCacheOperator(IDiskCacheOperator operator)
    {
        this.mCacheOperator = operator;
        return this;
    }

    @Override
    public IDiskCacheOperator getCacheOperator()
    {
        return mCacheOperator;
    }

    @Override
    public ApiGlobalOptions setApiExceptionMsgParser(IApiExceptionMsgParser parser)
    {
        this.mApiExceptionMsgParser = parser;
        return this;
    }

    @Override
    public IApiExceptionMsgParser getApiExceptionMsgParser()
    {
        return mApiExceptionMsgParser;
    }

    @Override
    public ApiGlobalOptions addDynamicFormData(String key, WingsSupplier<Object> supplier)
    {
        if (StringUtils.isTrimEmpty(key))
        {
            throw new IllegalArgumentException("RxHttp query param's key can not be trim empty !");
        }
        if (supplier == null)
        {
            throw new IllegalArgumentException("RxHttp addParams FormData's callback can not be null !");
        }
        getDynamicFormDataMap().put(key, supplier);
        return this;
    }

    @Override
    public ApiGlobalOptions removeDynamicFormData(String key)
    {
        getDynamicFormDataMap().remove(key);
        return this;
    }

    @Override
    public ApiGlobalOptions clearDynamicFormData()
    {
        getDynamicFormDataMap().clear();
        return this;
    }

    @Override
    public Map<String, WingsSupplier<Object>> getDynamicFormDataMap()
    {
        if (mDynamicFormDataMap == null)
        {
            mDynamicFormDataMap = new TreeMap<>();
        }
        return mDynamicFormDataMap;
    }

    @Override
    public ApiGlobalOptions setRetryConfig(RetryConfig configProvider)
    {
        this.mRetryConfig = configProvider;
        return this;
    }

    @Override
    public RetryConfig getRetryConfig()
    {
        return mRetryConfig;
    }

    @Override
    public ApiGlobalOptions setApiErrorConsumer(ApiErrorConsumer consumer)
    {
        this.mErrorConsumer = consumer;
        return this;
    }

    @Override
    public ApiErrorConsumer getApiErrorConsumer()
    {
        return mErrorConsumer;
    }
}
