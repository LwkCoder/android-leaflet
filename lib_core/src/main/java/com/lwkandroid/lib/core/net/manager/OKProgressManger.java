package com.lwkandroid.lib.core.net.manager;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;

import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.net.bean.ProgressRequestBody;
import com.lwkandroid.lib.core.net.bean.ProgressResponseBody;
import com.lwkandroid.lib.core.net.listener.OnProgressListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LWK
 * 基于OkHttp做网络请求的所有过程监听管理类
 * 需要OkHttp添加{@link com.lwkandroid.lib.core.net.interceptor.OkProgressInterceptor}
 */

public final class OKProgressManger
{
    private OKProgressManger()
    {
        mHandler = new Handler(Looper.getMainLooper());
    }

    private static final class Holder
    {
        public static final OKProgressManger INSTANCE = new OKProgressManger();
    }

    public static OKProgressManger get()
    {
        return Holder.INSTANCE;
    }

    //默认的进度刷新时间：150ms
    private static final int DEFAULT_REFRESH_TIME = 150;
    private static final String IDENTIFICATION_NUMBER = "?OkProgressNumber=";
    private static final String IDENTIFICATION_HEADER = "OkProgressHeader";
    private static final String LOCATION_HEADER = "Location";
    //所有监听器在 Handler 中被执行,所以可以保证所有监听器在主线程中被执行
    private final Handler mHandler;
    //进度刷新时间(单位ms),避免高频率调用
    private int mRefreshTime = DEFAULT_REFRESH_TIME;
    /**
     * 存储上传过程监听
     */
    private final Map<String, List<OnProgressListener>> mUploadListeners = new WeakHashMap<>();
    /**
     * 存储下载过程监听
     */
    private final Map<String, List<OnProgressListener>> mDownloadListeners = new WeakHashMap<>();

    /**
     * 设置进度更新刷新时间
     *
     * @param refreshTime 间隔时间,单位毫秒
     */
    public void setRefreshTime(int refreshTime)
    {
        this.mRefreshTime = refreshTime;
    }

    /**
     * 将需要被监听上传进度的 {@code url} 注册到管理器,此操作请在页面初始化时进行,切勿多次注册同一个(内容相同)监听器
     *
     * @param url      {@code url} 作为标识符
     * @param listener 当此 {@code url} 地址存在上传的动作时,此监听器将被调用
     */
    public void addUploadListener(String url, OnProgressListener listener)
    {
        List<OnProgressListener> progressListeners;
        synchronized (OKProgressManger.class)
        {
            progressListeners = mUploadListeners.get(url);
            if (progressListeners == null)
            {
                progressListeners = new LinkedList<>();
                mUploadListeners.put(url, progressListeners);
            }
        }
        progressListeners.add(listener);
    }

    /**
     * 移除某个链接下所有监听
     */
    public void removeUploadListener(String url)
    {
        synchronized (OKProgressManger.class)
        {
            mUploadListeners.remove(url);
        }
    }

    /**
     * 将需要被监听下载进度的 {@code url} 注册到管理器,此操作请在页面初始化时进行,切勿多次注册同一个(内容相同)监听器
     *
     * @param url      {@code url} 作为标识符
     * @param listener 当此 {@code url} 地址存在下载的动作时,此监听器将被调用
     */
    public void addDownloadListener(String url, OnProgressListener listener)
    {
        List<OnProgressListener> progressListeners;
        synchronized (OKProgressManger.class)
        {
            progressListeners = mDownloadListeners.get(url);
            if (progressListeners == null)
            {
                progressListeners = new LinkedList<>();
                mDownloadListeners.put(url, progressListeners);
            }
        }
        progressListeners.add(listener);
    }

    /**
     * 移除某个链接下所有监听
     */
    public void removeDownloadListener(String url)
    {
        synchronized (OKProgressManger.class)
        {
            mDownloadListeners.remove(url);
        }
    }

    /**
     * 当出现需要使用同一个 {@code url} 根据 Post 请求参数的不同而下载不同资源的情况
     * 请使用 {@link #addDiffDownloadListenerOnSameUrl(String, OnProgressListener)} 代替 {@link #addDownloadListener}
     * {@link #addDiffDownloadListenerOnSameUrl(String, OnProgressListener)} 会返回一个加入了时间戳的新的 {@code url}
     * 请使用这个新的 {@code url} 去代替 {@code originUrl} 进行下载的请求即可 (当实际请求时依然使用 {@code originUrl} 进行网络请求)
     * <p>
     * {@link #addDiffDownloadListenerOnSameUrl(String, OnProgressListener)} 与 {@link #addDiffDownloadListenerOnSameUrl(String, String, OnProgressListener)}
     * 的区别在于:
     * {@link #addDiffDownloadListenerOnSameUrl(String, String, OnProgressListener)} 可以使用不同的 {@code key} 来自定义新的 {@code url}
     * {@link #addDiffDownloadListenerOnSameUrl(String, OnProgressListener)} 是直接使用时间戳来生成新的 {@code url}
     * <p>
     *
     * @param originUrl {@code originUrl} 作为基础并结合时间戳用于生成新的 {@code url} 作为标识符
     * @param listener  当加入了时间戳的新的 {@code url} 地址存在下载的动作时,此监听器将被调用
     * @return 加入了时间戳的新的 {@code url}
     */
    public String addDiffDownloadListenerOnSameUrl(String originUrl, OnProgressListener listener)
    {
        return addDiffDownloadListenerOnSameUrl(originUrl, String.valueOf(SystemClock.elapsedRealtime() + listener.hashCode()), listener);
    }

    /**
     * 当出现需要使用同一个 {@code url} 根据 Post 请求参数的不同而下载不同资源的情况
     * 请使用 {@link #addDiffDownloadListenerOnSameUrl(String, String, OnProgressListener)} 代替 {@link #addDownloadListener}
     * 请使用 {@link #addDiffDownloadListenerOnSameUrl(String, String, OnProgressListener)} 会返回一个 {@code originUrl} 结合 {@code key} 生成的新的 {@code url}
     * 请使用这个新的 {@code url} 去代替 {@code originUrl} 进行下载的请求即可 (当实际请求时依然使用 {@code originUrl} 进行网络请求)
     * <p>
     * {@link #addDiffDownloadListenerOnSameUrl(String, OnProgressListener)} 与 {@link #addDiffDownloadListenerOnSameUrl(String, String, OnProgressListener)}
     * 的区别在于:
     * {@link #addDiffDownloadListenerOnSameUrl(String, String, OnProgressListener)} 可以使用不同的 {@code key} 来自定义新的 {@code url}
     * {@link #addDiffDownloadListenerOnSameUrl(String, OnProgressListener)} 是直接使用时间戳来生成新的 {@code url}
     * <p>
     * Example usage:
     * <pre>
     * String newUrl = ProgressManager.get().addDiffDownloadListenerOnSameUrl(DOWNLOAD_URL, "id", getDownloadListener());
     * new Thread(new Runnable() {
     *
     *   public void run() {
     *     try {
     *        Request request = new Request.Builder()
     *        .url(newUrl) // 请一定使用 addDiffDownloadListenerOnSameUrl 返回的 newUrl 做请求
     *        .build();
     *
     *        Response response = mOkHttpClient.newCall(request).execute();
     *      } catch (IOException e) {
     *       e.printStackTrace();
     *       //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法,这里也要使用 newUrl
     *       ProgressManager.get().notifyOnErorr(newUrl, e);
     *     }
     *   }
     * }).start();
     * </pre>
     *
     * @param originUrl {@code originUrl} 作为基础并结合 {@code key} 用于生成新的 {@code url} 作为标识符
     * @param key       {@code originUrl} 作为基础并结合 {@code key} 用于生成新的 {@code url} 作为标识符
     * @param listener  当 {@code originUrl} 结合 {@code key} 生成的新的 {@code url} 地址存在下载的动作时,此监听器将被调用
     * @return {@code originUrl} 结合 {@code key} 生成的新的 {@code url}
     */
    public String addDiffDownloadListenerOnSameUrl(String originUrl, String key, OnProgressListener listener)
    {
        String newUrl = originUrl + IDENTIFICATION_NUMBER + key;
        addDownloadListener(newUrl, listener);
        return newUrl;
    }

    /**
     * 当出现需要使用同一个 {@code url} 根据 Post 请求参数的不同而上传不同资源的情况
     * 请使用 {@link #addDiffUploadListenerOnSameUrl(String, OnProgressListener)} 代替 {@link #addUploadListener}
     * {@link #addDiffUploadListenerOnSameUrl(String, OnProgressListener)} 会返回一个加入了时间戳的新的 {@code url}
     * 请使用这个新的 {@code url} 去代替 {@code originUrl} 进行上传的请求即可 (当实际请求时依然使用 {@code originUrl} 进行网络请求)
     * <p>
     * {@link #addDiffUploadListenerOnSameUrl(String, OnProgressListener)} 与 {@link #addDiffUploadListenerOnSameUrl(String, String, OnProgressListener)}
     * 的区别在于:
     * {@link #addDiffUploadListenerOnSameUrl(String, String, OnProgressListener)} 可以使用不同的 {@code key} 来自定义新的 {@code url}
     * {@link #addDiffUploadListenerOnSameUrl(String, OnProgressListener)} 是直接使用时间戳来生成新的 {@code url}
     * <p>
     *
     * @param originUrl {@code originUrl} 作为基础并结合时间戳用于生成新的 {@code url} 作为标识符
     * @param listener  当加入了时间戳的新的 {@code url} 地址存在上传的动作时,此监听器将被调用
     * @return 加入了时间戳的新的 {@code url}
     */
    public String addDiffUploadListenerOnSameUrl(String originUrl, OnProgressListener listener)
    {
        return addDiffUploadListenerOnSameUrl(originUrl, String.valueOf(SystemClock.elapsedRealtime() + listener.hashCode()), listener);
    }


    /**
     * 当出现需要使用同一个 {@code url} 根据 Post 请求参数的不同而上传不同资源的情况
     * 请使用 {@link #addDiffUploadListenerOnSameUrl(String, OnProgressListener)} 代替 {@link #addUploadListener}
     * {@link #addDiffUploadListenerOnSameUrl(String, OnProgressListener)} 会返回一个 {@code originUrl} 结合 {@code key} 生成的新的 {@code url}
     * 请使用这个新的 {@code url} 去代替 {@code originUrl} 进行上传的请求即可 (当实际请求时依然使用 {@code originUrl} 进行网络请求)
     * <p>
     * {@link #addDiffUploadListenerOnSameUrl(String, OnProgressListener)} 与 {@link #addDiffUploadListenerOnSameUrl(String, String, OnProgressListener)}
     * 的区别在于:
     * {@link #addDiffUploadListenerOnSameUrl(String, String, OnProgressListener)} 可以使用不同的 {@code key} 来自定义新的 {@code url}
     * {@link #addDiffUploadListenerOnSameUrl(String, OnProgressListener)} 是直接使用时间戳来生成新的 {@code url}
     * <p>
     * Example usage:
     * <pre>
     * String newUrl = ProgressManager.get().addDiffUploadListenerOnSameUrl(UPLOAD_URL, "id", getUploadListener());
     * new Thread(new Runnable() {
     *
     *    public void run() {
     *    try {
     *       File file = new File(getCacheDir(), "cache");
     *
     *       Request request = new Request.Builder()
     *       .url(newUrl) // 请一定使用 addDiffUploadListenerOnSameUrl 返回的 newUrl 做请求
     *       .post(RequestBody.create(MediaType.parse("multipart/form-data"), file))
     *       .build();
     *
     *       Response response = mOkHttpClient.newCall(request).execute();
     *     } catch (IOException e) {
     *      e.printStackTrace();
     *      //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法,这里也要使用 newUrl
     *      ProgressManager.get().notifyOnErorr(newUrl, e);
     *    }
     *  }
     * }).start();
     * </pre>
     *
     * @param originUrl {@code originUrl} 作为基础并结合 {@code key} 用于生成新的 {@code url} 作为标识符
     * @param key       {@code originUrl} 作为基础并结合 {@code key} 用于生成新的 {@code url} 作为标识符
     * @param listener  当 {@code originUrl} 结合 {@code key} 生成的新的 {@code url} 地址存在上传的动作时,此监听器将被调用
     * @return {@code originUrl} 结合 {@code key} 生成的新的 {@code url}
     */
    public String addDiffUploadListenerOnSameUrl(String originUrl, String key, OnProgressListener listener)
    {
        String newUrl = originUrl + IDENTIFICATION_NUMBER + key;
        addUploadListener(newUrl, listener);
        return newUrl;
    }

    /**
     * 包装原始请求体
     */
    public Request wrapRequestBody(Request request)
    {
        if (request == null)
        {
            return request;
        }

        String key = request.url().toString();
        KLog.d("OkHttpManager.wrapRequestBody.Url=" + key);
        request = pruneIdentification(key, request);

        if (request.body() == null)
        {
            return request;
        }
        if (mUploadListeners.containsKey(key))
        {
            List<OnProgressListener> listeners = mUploadListeners.get(key);
            return request.newBuilder()
                    .method(request.method(), new ProgressRequestBody(mHandler, request.body(), listeners, mRefreshTime))
                    .build();
        }
        return request;
    }

    private Request pruneIdentification(String url, Request request)
    {
        boolean needPrune = url.contains(IDENTIFICATION_NUMBER);
        if (!needPrune)
        {
            return request;
        }

        return request.newBuilder()
                //删除掉标识符
                .url(url.substring(0, url.indexOf(IDENTIFICATION_NUMBER)))
                //将有标识符的 url 加入 header中, 便于wrapResponseBody(Response) 做处理
                .header(IDENTIFICATION_HEADER, url)
                .build();
    }

    /**
     * 包装原始响应体
     */
    public Response wrapResponseBody(Response response)
    {
        if (response == null)
        {
            return response;
        }

        String key = response.request().url().toString();
        KLog.d("OkHttpManager.wrapResponseBody.Url=" + key);
        if (!TextUtils.isEmpty(response.request().header(IDENTIFICATION_HEADER)))
        { //从 header 中拿出有标识符的 url
            key = response.request().header(IDENTIFICATION_HEADER);
        }

        if (response.isRedirect())
        {
            resolveRedirect(mUploadListeners, response, key);
            String location = resolveRedirect(mDownloadListeners, response, key);
            response = modifyLocation(response, location);
            return response;
        }

        if (response.body() == null)
        {
            return response;
        }

        if (mDownloadListeners.containsKey(key))
        {
            List<OnProgressListener> listeners = mDownloadListeners.get(key);
            return response.newBuilder()
                    .body(new ProgressResponseBody(mHandler, response.body(), listeners, mRefreshTime))
                    .build();
        }
        return response;
    }

    /**
     * 解决请求地址重定向后的兼容问题
     *
     * @param map      {@link #mUploadListeners} 或者 {@link #mDownloadListeners}
     * @param response 原始的 {@link Response}
     * @param url      {@code url} 地址
     */
    private String resolveRedirect(Map<String, List<OnProgressListener>> map, Response response, String url)
    {
        String location = null;
        //查看此重定向 url ,是否已经注册过监听器
        List<OnProgressListener> progressListeners = map.get(url);
        if (progressListeners != null && progressListeners.size() > 0)
        {
            // 重定向地址
            location = response.header(LOCATION_HEADER);
            if (!TextUtils.isEmpty(location))
            {
                if (url.contains(IDENTIFICATION_NUMBER) && !location.contains(IDENTIFICATION_NUMBER))
                {
                    //如果 url 有标识符,那也将标识符加入用于重定向的 location
                    location += url.substring(url.indexOf(IDENTIFICATION_NUMBER), url.length());
                }
                if (!map.containsKey(location))
                {
                    //将需要重定向地址的监听器,提供给重定向地址,保证重定向后也可以监听进度
                    map.put(location, progressListeners);
                } else
                {
                    List<OnProgressListener> locationListener = map.get(location);
                    for (OnProgressListener listener : progressListeners)
                    {
                        if (!locationListener.contains(listener))
                        {
                            locationListener.add(listener);
                        }
                    }
                }
            }
        }
        return location;
    }

    private Response modifyLocation(Response response, String location)
    {
        if (!TextUtils.isEmpty(location) && location.contains(IDENTIFICATION_NUMBER))
        {
            //将被加入标识符的新的 location 放入 header 中
            response = response.newBuilder()
                    .header(LOCATION_HEADER, location)
                    .build();
        }
        return response;
    }

    /**
     * 当在 {@link ProgressRequestBody} 和 {@link ProgressResponseBody} 内部处理二进制流时发生错误
     * 会主动调用 {@link OnProgressListener#onError(long, Exception)},但是有些错误并不是在它们内部发生的
     * 但同样会引起网络请求的失败,所以向外面提供{@link OKProgressManger#notifyOnErorr},当外部发生错误时
     * 手动调用此方法,以通知所有的监听器
     *
     * @param url {@code url} 作为标识符
     * @param e   错误
     */
    public void notifyOnErorr(String url, Exception e)
    {
        forEachListenersOnError(mUploadListeners, url, e);
        forEachListenersOnError(mDownloadListeners, url, e);
    }

    private void forEachListenersOnError(Map<String, List<OnProgressListener>> map, String url, Exception e)
    {
        if (map.containsKey(url))
        {
            List<OnProgressListener> progressListeners = map.get(url);
            OnProgressListener[] array = progressListeners.toArray(new OnProgressListener[progressListeners.size()]);
            for (int i = 0; i < array.length; i++)
            {
                array[i].onError(-1, e);
            }
        }
    }
}
