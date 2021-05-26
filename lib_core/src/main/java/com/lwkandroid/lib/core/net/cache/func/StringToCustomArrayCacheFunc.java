package com.lwkandroid.lib.core.net.cache.func;


import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;

/**
 * 转换缓存包装体内String数据为某一对象集合数据
 *
 * @author LWK
 */
public final class StringToCustomArrayCacheFunc<T> extends AbsArrayCacheFunc<T>
{
    public StringToCustomArrayCacheFunc(IApiStringParser parser, Class<T> classType)
    {
        super(parser, classType);
    }

    @Override
    public ResultCacheWrapper<T[]> apply(ResultCacheWrapper<String> cacheWrapper) throws Exception
    {
        String data = cacheWrapper.getData();
        return new ResultCacheWrapper<>(cacheWrapper.isCache(),
                getParser().parseCustomDataArray(data, getClassType()));
    }
}
