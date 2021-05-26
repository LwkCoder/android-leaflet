package com.lwkandroid.lib.core.net.observer;

import com.lwkandroid.lib.core.net.bean.ApiException;

/**
 * @description: CompleteObserver统一接口
 * @author: LWK
 * @date: 2020/6/22 14:17
 */
public interface IApiCompleteObserver
{
    void onCompleted();

    void onError(ApiException e);
}
