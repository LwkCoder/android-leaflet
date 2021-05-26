package com.lwkandroid.lib.core.callback;

/**
 * Description:消费者回调
 *
 * @author LWK
 * @date 2020/4/21
 */
public interface WingsConsumer<T>
{
    void accept(T t);
}
