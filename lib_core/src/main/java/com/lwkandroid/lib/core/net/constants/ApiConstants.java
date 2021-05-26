package com.lwkandroid.lib.core.net.constants;

/**
 * 常量
 *
 * @author LWK
 */
public class ApiConstants
{
    /**
     * 请求读取默认超时时间
     */
    public static final long READ_TIMEOUT = 30000;
    /**
     * 请求写入默认超时时间
     */
    public static final long WRITE_TIMEOUT = 30000;
    /**
     * 请求连接默认超时时间
     */
    public static final long CONNECT_TIMEOUT = 30000;
    /**
     * 请求成功的默认状态码
     */
    public static final int RESULT_OK_CODE = 200;

    /**
     * 日志拦截器的tag
     */
    public static final String TAG_LOG_INTERCEPTOR = "tag_log";
    /**
     * 加载过程拦截器的tag
     */
    public static final String TAG_PROGRESS_INTERCEPTOR = "tag_progress";

    /**
     * 请求头Content-Type
     */
    public static final String HEADER_KEY_CONTENT_TYPE = "Content-Type";
    /**
     * 请求头Accept
     */
    public static final String HEADER_KEY_ACCEPT = "Accept";
    /**
     * 请求头Json
     */
    public static final String HEADER_VALUE_JSON = "application/json";
    /**
     * 默认磁盘缓存最小容量，10Mb
     */
    public static final long DISK_CACHE_MIN_SIZE = 10 * 1024 * 1024;
}
