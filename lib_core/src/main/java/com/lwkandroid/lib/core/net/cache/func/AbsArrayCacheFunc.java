package com.lwkandroid.lib.core.net.cache.func;


import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;

import io.reactivex.rxjava3.functions.Function;


/**
 * Description:缓存转换为对象集合数据的基类
 *
 * @author LWK
 * @date 2019/6/6
 */
public abstract class AbsArrayCacheFunc<T> implements Function<ResultCacheWrapper<String>,
        ResultCacheWrapper<T[]>>
{
    private IApiStringParser mParser;
    private Class<T> mClassType;

    AbsArrayCacheFunc(IApiStringParser parser, Class<T> classType)
    {
        this.mParser = parser;
        this.mClassType = classType;
    }

    public IApiStringParser getParser()
    {
        return mParser;
    }

    public Class<T> getClassType()
    {
        return mClassType;
    }
}
