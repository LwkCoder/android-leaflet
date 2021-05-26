package com.lwkandroid.lib.core.net.interceptor;


import com.lwkandroid.lib.core.net.utils.MultipartBodyUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LWK
 * OkHttp参数拦截器，用于对所有请求添加公共动态参数
 * 需要配合RxHttp.getGlobalOptions().addInterceptor(String tag,Interceptor interceptor)使用
 * 【不可使用addNetInterceptor】
 * 推荐使用同样效果的方法：RxHttp.getGlobalOptions.addDynamicFormData(String key, WingsSupplier<Object> supplier);
 */
public abstract class ApiParamsInterceptor implements Interceptor
{
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private HttpUrl mHttpUrl;
    private final String GET = "GET";
    private final String POST = "POST";

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        if (GET.equals(request.method()))
        {
            this.mHttpUrl = HttpUrl.parse(parseUrl(request.url().url().toString()));
            request = addGetParamsSign(request);
        } else if (POST.equals(request.method()))
        {
            this.mHttpUrl = request.url();
            request = addPostParamsSign(request);
        }
        return chain.proceed(request);
    }

    protected HttpUrl getHttpUrl()
    {
        return mHttpUrl;
    }

    //解析get请求的原始url
    private String parseUrl(String url)
    {
        if (!"".equals(url) && url.contains("?"))
        {
            url = url.substring(0, url.indexOf('?'));
        }
        return url;
    }

    //get 对参数进行动态修改
    private Request addGetParamsSign(Request request) throws UnsupportedEncodingException
    {
        HttpUrl httpUrl = request.url();
        HttpUrl.Builder newBuilder = httpUrl.newBuilder();

        //获取原有的参数
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>(nameSet);
        TreeMap<String, Object> oldParams = new TreeMap<>();
        for (int i = 0; i < nameList.size(); i++)
        {
            List<String> valueList = httpUrl.queryParameterValues(nameList.get(i));
            String value = valueList.size() > 0 ? valueList.get(0) : "";
            oldParams.put(nameList.get(i), value);
        }
        String nameKeys = Collections.singletonList(nameList).toString();
        //组装新的参数
        TreeMap<String, Object> newParams = addParams(oldParams);
        for (Map.Entry<String, Object> entry : newParams.entrySet())
        {
            String urlValue = URLEncoder.encode(String.valueOf(entry.getValue()), UTF8.name());
            if (!nameKeys.contains(entry.getKey()))
            {
                newBuilder.addQueryParameter(entry.getKey(), urlValue);
            }
        }

        httpUrl = newBuilder.build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    //post 对参数进行动态修改
    private Request addPostParamsSign(Request request) throws UnsupportedEncodingException
    {
        if (request.body() instanceof FormBody)
        {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            //原有的参数
            TreeMap<String, Object> oldParams = new TreeMap<>();
            for (int i = 0; i < formBody.size(); i++)
            {
                oldParams.put(formBody.encodedName(i), formBody.encodedValue(i));
            }

            //拼装新的参数
            TreeMap<String, Object> newParams = addParams(oldParams);
            for (Map.Entry<String, Object> entry : newParams.entrySet())
            {
                String value = URLDecoder.decode(String.valueOf(entry.getValue()), UTF8.name());
                bodyBuilder.addEncoded(entry.getKey(), value);
            }
            formBody = bodyBuilder.build();
            request = request.newBuilder().post(formBody).build();
        } else if (request.body() instanceof MultipartBody)
        {
            MultipartBody multipartBody = (MultipartBody) request.body();
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            List<MultipartBody.Part> oldParts = multipartBody.parts();

            //拼装新的参数
            List<MultipartBody.Part> newParts = new ArrayList<>(oldParts);
            TreeMap<String, Object> oldParams = new TreeMap<>();
            TreeMap<String, Object> newParams = addParams(oldParams);
            for (Map.Entry<String, Object> entry : newParams.entrySet())
            {
                MultipartBody.Part part = MultipartBodyUtils.createFormDataPart(entry.getKey(), entry.getValue());
                newParts.add(part);
            }
            for (MultipartBody.Part part : newParts)
            {
                bodyBuilder.addPart(part);
            }
            multipartBody = bodyBuilder.build();
            request = request.newBuilder().post(multipartBody).build();
        }
        return request;
    }

    /**
     * 子类实现该方法对参数进行动态修改
     */
    public abstract TreeMap<String, Object> addParams(TreeMap<String, Object> oldParams);
}
