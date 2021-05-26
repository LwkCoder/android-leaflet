package com.lwkandroid.lib.core.net.exception;

import com.lwkandroid.lib.core.net.bean.ApiException;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;


/**
 * Description:异常转换为ApiException的方法
 *
 * @author LWK
 */
public final class ApiExceptionConvertFunc<T> implements Function<Throwable, ObservableSource<T>>
{
    @Override
    public ObservableSource<T> apply(Throwable throwable) throws Exception
    {
        return Observable.error(ApiException.handleThrowable(throwable));
    }
}
