package com.lwkandroid.lib.core.net.interceptor;


import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.utils.json.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

/**
 * OkHttp日志请求拦截器
 *
 * @author LWK
 */
public class ApiLogInterceptor implements Interceptor
{
    private static final String TAG = "ApiLogInterceptor";
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String START = "————————————↓ OkHttp ↓————————————————————————————————————————";
    private static final String END = "————————————↑ OkHttp ↑————————————————————————————————————————";
    private static final String SEPARATOR = "| ";
    private static final String NEXT_LINE = "\n";
    private static final String TEXT_TYPE = "text";
    private static final String FORM_URLENCODED = "x-www-form-urlencoded";
    private static final String XML = "xml";
    private static final String JSON = "json";
    private static final String HTML = "html";
    private static final String BRACE = "{";
    private static final String BRACKET = "[";

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        Request request = chain.request();
        logRequest(request, chain.connection(), builder);
        Response response;
        try
        {
            response = chain.proceed(request);
        } catch (Exception e)
        {
            builder.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("--->HttpResponse : Fail to proceed response:")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append(e.toString())
                    .append(NEXT_LINE)
                    .append(END);
            showLog(builder);
            throw e;
        }

        return logResponse(response, builder);
    }

    private void logRequest(Request r, Connection connection, StringBuilder builder)
    {
        builder.append(NEXT_LINE)
                .append(START);
        Request request = r.newBuilder().build();
        String method = request.method();
        String url = request.url().toString();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        RequestBody body = request.body();
        boolean hasBody = body != null;
        MediaType mediaType = hasBody ? body.contentType() : null;

        try
        {
            builder.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("---------->HttpRequest")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Url=")
                    .append(url)
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Method=")
                    .append(method)
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Protocal=")
                    .append(protocol)
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Content-type=")
                    .append(hasBody ? mediaType : "null")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Content-Length=")
                    .append(hasBody ? body.contentLength() : "null");

            Headers headers = request.headers();
            if (headers != null && headers.size() > 0)
            {
                builder.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("Headers=");
                for (int i = 0, size = headers.size(); i < size; i++)
                {
                    builder.append("[").append(headers.name(i)).append("=").append(headers.value(i)).append("] ");
                }
            } else
            {
                builder.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("Headers=null");
            }

            if (hasBody)
            {
                String bodyString = requestBodyToString(mediaType, body);
                if (isPlaintext(mediaType))
                {
                    if (JsonUtils.isJsonData(bodyString))
                    {
                        builder.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("RequestBody:");
                        appendFormatJson(bodyString, builder);
                    } else
                    {
                        builder.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("RequestBody:")
                                .append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append(bodyString);
                    }
                } else
                {
                    if (JsonUtils.isJsonData(bodyString))
                    {
                        builder.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("RequestBody: Binary json data:");
                        appendFormatJson(bodyString, builder);
                    } else
                    {
                        builder.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("RequestBody: Maybe binary body. Ignored logging !");
                    }
                    builder.append(NEXT_LINE)
                            .append(END);
                }
            } else
            {
                builder.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("RequestBody:null");
            }
        } catch (Exception e)
        {
            builder.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Exception occurred during logging for request:")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append(e.toString());
        }
    }

    private Response logResponse(Response response, StringBuilder builder)
    {
        Response clone = response.newBuilder().build();
        ResponseBody body = clone.body();
        boolean hasBody = body != null;
        MediaType mediaType = hasBody ? body.contentType() : null;

        try
        {
            builder.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("---------->HttpResponse")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Content-Type=")
                    .append((hasBody ? mediaType : "null"))
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Content-Length:")
                    .append((hasBody ? body.contentLength() : "null"));

            Headers headers = clone.headers();
            if (headers != null && headers.size() > 0)
            {
                builder.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("Headers=");
            } else
            {
                builder.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("Headers=null");
            }
            for (int i = 0, size = headers.size(); i < size; i++)
            {
                builder.append("[").append(headers.name(i)).append("=").append(headers.value(i)).append("] ");
            }

            if (hasBody && HttpHeaders.hasBody(clone))
            {
                if (isPlaintext(mediaType))
                {
                    byte[] bytes = toByteArray(body.byteStream());
                    Charset charset = mediaType != null ? mediaType.charset(UTF8) : UTF8;
                    String bodyString = new String(bytes, charset);
                    if (JsonUtils.isJsonData(bodyString))
                    {
                        builder.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("ResponseBody:");
                        appendFormatJson(bodyString, builder);
                    } else
                    {
                        builder.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("ResponseBody:")
                                .append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append(bodyString);
                    }
                    builder.append(NEXT_LINE)
                            .append(END);
                    showLog(builder);
                    body = ResponseBody.create(body.contentType(), bytes);
                    return response.newBuilder().body(body).build();
                } else
                {
                    builder.append(NEXT_LINE)
                            .append(SEPARATOR)
                            .append("ResponseBody: Maybe binary body. Ignored logging !")
                            .append(NEXT_LINE)
                            .append(END);
                    showLog(builder);
                    return response.newBuilder().body(body).build();
                }
            } else
            {
                builder.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("ResponseBody:null")
                        .append(NEXT_LINE)
                        .append(END);
                showLog(builder);
                return response;
            }

        } catch (Exception e)
        {
            builder.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("ResponseBody:Exception occurred during logging for response:")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append(e.toString())
                    .append(NEXT_LINE)
                    .append(END);
            showLog(builder);
        }
        return response;
    }

    /**
     * 判断是否为文本类型
     */
    private static boolean isPlaintext(MediaType mediaType)
    {
        if (mediaType == null)
        {
            return false;
        }
        if (mediaType.type() != null && TEXT_TYPE.equals(mediaType.type()))
        {
            return true;
        }

        String subtype = mediaType.subtype();
        if (subtype != null)
        {
            subtype = subtype.toLowerCase();
            return subtype.contains(FORM_URLENCODED) || subtype.contains(JSON)
                    || subtype.contains(XML) || subtype.contains(HTML);
        }
        return false;
    }

    /**
     * 请求体转为String
     */

    private String requestBodyToString(MediaType mediaType, RequestBody body) throws IOException
    {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        Charset charset = mediaType != null ? mediaType.charset(UTF8) : UTF8;
        String strBody = buffer.readString(charset);
        try
        {
            strBody = strBody.replaceAll("%(?![0-9a-fA-F]{2})", "%25").
                    replaceAll("\\+", "%2B");
            strBody = URLDecoder.decode(strBody, UTF8.name());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return strBody;
    }

    /**
     * 将InputStream转为字节数组
     */
    private byte[] toByteArray(InputStream input)
    {
        ByteArrayOutputStream output = null;
        try
        {
            output = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[2048];
            while ((len = input.read(buffer)) != -1)
            {
                output.write(buffer, 0, len);
            }
            output.flush();
            return output.toByteArray();
        } catch (IOException e)
        {
            e.printStackTrace();
            return new byte[0];
        } finally
        {
            try
            {
                if (output != null)
                {
                    output.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拼接格式化的Json数据
     */
    private void appendFormatJson(String json, StringBuilder buffer)
    {
        String message;
        try
        {
            if (BRACE.startsWith(json))
            {
                JSONObject jsonObject = new JSONObject(json);
                message = jsonObject.toString(KLog.JSON_INDENT);
            } else if (BRACKET.startsWith(json))
            {
                JSONArray jsonArray = new JSONArray(json);
                message = jsonArray.toString(KLog.JSON_INDENT);
            } else
            {
                message = json;
            }
        } catch (JSONException e)
        {
            message = json;
        }

        String[] lines = message.split(KLog.LINE_SEPARATOR);
        for (String line : lines)
        {
            buffer.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append(line);
        }
    }

    private void showLog(StringBuilder builder)
    {
        KLog.d(TAG, builder.toString());
    }
}
