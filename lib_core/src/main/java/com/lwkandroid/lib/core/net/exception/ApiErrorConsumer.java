package com.lwkandroid.lib.core.net.exception;

import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.bean.ApiException;

import io.reactivex.rxjava3.functions.Consumer;


/**
 * Description:统一处理错误的消费者
 *
 * @author LWK
 * @date 2020/4/3
 */
public abstract class ApiErrorConsumer implements Consumer<Throwable>
{
    /**
     * 空实现
     */
    public static ApiErrorConsumer empty()
    {
        return new ApiErrorConsumer()
        {
            @Override
            public void onAcceptedException(ApiException e)
            {

            }
        };
    }

    @Override
    public void accept(Throwable throwable) throws Exception
    {
        ApiException e = ApiException.handleThrowable(throwable);
        String displayMessage = RxHttp.getGlobalOptions().getApiExceptionMsgParser().parserMessageByCode(e.getCode(), e.getThrowMessage());
        e.setDisplayMessage(displayMessage);
        onAcceptedException(e);
    }

    /**
     * 子类实现，预先处理Exception
     * 通常用作全局处理的操作
     *
     * @param e
     */
    public abstract void onAcceptedException(ApiException e);
}
