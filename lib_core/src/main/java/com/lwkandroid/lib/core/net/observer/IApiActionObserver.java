package com.lwkandroid.lib.core.net.observer;

import com.lwkandroid.lib.core.net.bean.ApiException;

/**
 * @description: 网络请求Observer统一接口
 * @author: LWK
 * @date: 2020/6/22 14:17
 */
public interface IApiActionObserver<T>
{
    void onAccept(T t);

    void onError(ApiException e);
}
