package com.lwkandroid.lib.core.net.cache.func;

import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;

import java.util.List;

/**
 * 转换缓存包装体内String数据为Restful风格中对象集合数据
 *
 * @author LWK
 */
public final class StringToRestfulListCacheFunc<T> extends AbsListCacheFunc<T>
{

    public StringToRestfulListCacheFunc(IApiStringParser parser, Class<T> classType)
    {
        super(parser, classType);
    }

    @Override
    public ResultCacheWrapper<List<T>> apply(ResultCacheWrapper<String> cacheWrapper) throws Exception
    {
        String data = cacheWrapper.getData();
        return new ResultCacheWrapper<>(cacheWrapper.isCache(),
                getParser().parseRestfulDataList(data, getClassType()));
    }
}
