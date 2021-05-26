package com.lwkandroid.lib.core.net;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Retrofit请求模版
 *
 * @author LWK
 */
public interface ApiService
{
    @GET()
    Observable<ResponseBody> get(@Url String url, @HeaderMap Map<String, String> headerMap,
                                 @QueryMap Map<String, Object> maps);

    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> post(@Url String url, @HeaderMap Map<String, String> headerMap,
                                  @FieldMap Map<String, Object> maps);

    @POST()
    Observable<ResponseBody> post(@Url String url, @HeaderMap Map<String, String> headerMap, @Body Object object);

    @POST()
    Observable<ResponseBody> post(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody body);

    @DELETE()
    Observable<ResponseBody> delete(@Url String url, @HeaderMap Map<String, String> headerMap,
                                    @QueryMap Map<String, Object> maps);

    @HTTP(method = "DELETE", hasBody = true)
    Observable<ResponseBody> delete(@Url String url, @HeaderMap Map<String, String> headerMap, @Body Object object);

    @HTTP(method = "DELETE", hasBody = true)
    Observable<ResponseBody> delete(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody object);

    @PUT()
    Observable<ResponseBody> put(@Url String url, @HeaderMap Map<String, String> headerMap,
                                 @QueryMap Map<String, Object> maps);

    @PUT()
    Observable<ResponseBody> put(@Url String url, @HeaderMap Map<String, String> headerMap, @Body Object object);

    @PUT()
    Observable<ResponseBody> put(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody body);

    @PATCH()
    Observable<ResponseBody> patch(@Url String url, @HeaderMap Map<String, String> headerMap,
                                   @QueryMap Map<String, Object> maps);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url String url, @HeaderMap Map<String, String> headerMap,
                                         @Part() List<MultipartBody.Part> parts);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl, @HeaderMap Map<String, String> headerMap);

    @Streaming
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> downloadFile(@Url String fileUrl, @HeaderMap Map<String, String> headerMap,
                                          @FieldMap Map<String, Object> maps);

    @Streaming
    @POST()
    Observable<ResponseBody> downloadFile(@Url String url, @HeaderMap Map<String, String> headerMap, @Body Object object);

    @Streaming
    @POST()
    Observable<ResponseBody> downloadFile(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody body);

}
