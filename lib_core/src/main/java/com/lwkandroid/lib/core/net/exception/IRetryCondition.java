package com.lwkandroid.lib.core.net.exception;


import io.reactivex.rxjava3.core.Single;

/**
 * Description:定义需要重试的条件的接口
 *
 * @author LWK
 * @date 2020/4/2
 */
public interface IRetryCondition
{
    Single<Boolean> onRetryCondition(Throwable throwable);
}
