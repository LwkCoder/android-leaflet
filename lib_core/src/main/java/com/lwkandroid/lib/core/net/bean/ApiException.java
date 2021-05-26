package com.lwkandroid.lib.core.net.bean;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.lwkandroid.lib.core.net.constants.ApiExceptionCode;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 自定义异常：网络请求错误
 *
 * @author LWK
 */
public class ApiException extends Exception
{
    private static final long serialVersionUID = 4966919777463155346L;
    /**
     * 错误Id
     */
    private int code;

    /**
     * 错误抛出的异常描述
     */
    private String throwMessage;

    /**
     * 自定义的异常描述，通常可以用来直接Toast
     */
    private String displayMessage;

    public ApiException(int code, String throwMessage)
    {
        this(code, throwMessage, throwMessage);
    }

    public ApiException(int code, String throwMessage, String displayMessage)
    {
        super(throwMessage);
        this.code = code;
        this.throwMessage = throwMessage;
        this.displayMessage = displayMessage;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getThrowMessage()
    {
        return throwMessage;
    }

    public void setThrowMessage(String throwMessage)
    {
        this.throwMessage = throwMessage;
    }

    public String getDisplayMessage()
    {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage)
    {
        this.displayMessage = displayMessage;
    }

    @Override
    public String toString()
    {
        return "ApiException{" +
                "code=" + code +
                ", throwMessage='" + throwMessage + '\'' +
                ", displayMessage='" + displayMessage + '\'' +
                '}';
    }

    /**
     * 统一处理异常
     */
    public static ApiException handleThrowable(Throwable e)
    {
        if (e instanceof ApiException)
        {
            return (ApiException) e;
        } else if (e instanceof HttpException)
        {
            HttpException httpException = (HttpException) e;
            httpException.printStackTrace();
            return new ApiException(httpException.code(), httpException.getMessage());
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.PARSE_ERROR, e.getMessage());
        } else if (e instanceof ClassCastException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.CAST_ERROR, e.getMessage());
        } else if (e instanceof ConnectException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.CONNECT_ERROR, e.getMessage());
        } else if (e instanceof javax.net.ssl.SSLHandshakeException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.SSL_ERROR, e.getMessage());
        } else if (e instanceof ConnectTimeoutException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.TIMEOUT_ERROR, e.getMessage());
        } else if (e instanceof java.net.SocketTimeoutException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.TIMEOUT_ERROR, e.getMessage());
        } else if (e instanceof UnknownHostException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.UNKNOWN_HOST_ERROR, e.getMessage());
        } else if (e instanceof NullPointerException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.NULL_POINTER_EXCEPTION, e.getMessage());
        } else
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.UNDEFINE, e.getMessage());
        }
    }
}
