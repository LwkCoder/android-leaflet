package com.lwkandroid.lib.core.net.response.func;

import io.reactivex.rxjava3.functions.Function;
import okhttp3.ResponseBody;

/**
 * Description:ResponseBody转换类
 *
 * @author LWK
 */
public abstract class AbsResponseFunc<T> implements Function<ResponseBody, T>
{
    @Override
    public T apply(ResponseBody body) throws Exception
    {
        return convert(body);
    }

    public abstract T convert(ResponseBody body) throws Exception;
}
