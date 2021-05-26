package com.lwkandroid.lib.core.net.utils;


import com.lwkandroid.lib.core.utils.common.StringUtils;

import java.util.HashMap;

/**
 * 网络请求QueryMap、FieldMap参数辅助工具
 *
 * @author LWK
 */
public class FormDataMap extends HashMap<String, Object>
{
    private static final String TAG = "FormDataMap";
    private static final long serialVersionUID = 1124339485844644063L;

    public FormDataMap addParam(String key, byte value)
    {
        checkKeyNotNull(key);
        put(key, value);
        return this;
    }

    public FormDataMap addParam(String key, short value)
    {
        checkKeyNotNull(key);
        put(key, value);
        return this;
    }

    public FormDataMap addParam(String key, float value)
    {
        checkKeyNotNull(key);
        put(key, value);
        return this;
    }

    public FormDataMap addParam(String key, int value)
    {
        checkKeyNotNull(key);
        put(key, value);
        return this;
    }

    public FormDataMap addParam(String key, long value)
    {
        checkKeyNotNull(key);
        put(key, value);
        return this;
    }

    public FormDataMap addParam(String key, double value)
    {
        checkKeyNotNull(key);
        put(key, value);
        return this;
    }

    public FormDataMap addParam(String key, boolean value)
    {
        checkKeyNotNull(key);
        put(key, value);
        return this;
    }

    public FormDataMap addParam(String key, Object value)
    {
        checkKeyNotNull(key);
        //        if (null == value)
        //        {
        //            KLog.e(TAG, "RxHttp query param's value can not be null , Ignore key=" + key);
        //            return this;
        //        }
        put(key, value);
        return this;
    }

    public FormDataMap addParam(String key, String value)
    {
        checkKeyNotNull(key);
        //        if (StringUtils.isEmpty(value))
        //        {
        //            KLog.e(TAG, "RxHttp query param's value can not be null , Ignore key=" + key);
        //            return this;
        //        }
        put(key, value);
        return this;
    }

    private void checkKeyNotNull(String key)
    {
        if (StringUtils.isTrimEmpty(key))
        {
            throw new IllegalArgumentException("RxHttp query param's key can not be trim empty !");
        }
    }
}
