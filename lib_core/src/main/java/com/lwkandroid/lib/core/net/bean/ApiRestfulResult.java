package com.lwkandroid.lib.core.net.bean;

import com.lwkandroid.lib.core.annotation.NotProguard;
import com.lwkandroid.lib.core.net.RxHttp;

/**
 * RestFul风格默认的请求结果
 *
 * @author LWK
 */
@NotProguard
public class ApiRestfulResult implements IApiRestfulResult
{
    private int code;

    private String msg;

    private Object data;

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    @Override
    public int getCode()
    {
        return code;
    }

    @Override
    public String getMessage()
    {
        return msg;
    }

    @Override
    public Object getData()
    {
        return data;
    }

    @Override
    public boolean isResultOK()
    {
        return code == RxHttp.getGlobalOptions().getApiRestfulResultOkCode();
    }

    @Override
    public String toString()
    {
        return "ApiRestfulResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
