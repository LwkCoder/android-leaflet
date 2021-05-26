package com.lwkandroid.lib.core.net.listener;


import com.lwkandroid.lib.core.net.bean.ProgressInfo;

/**
 * Created by LWK
 *  通用加载过程监听
 */

public interface OnProgressListener
{
    /**
     * 加载过程的回调
     *
     * @param info 过程数据
     */
    void onProgress(ProgressInfo info);

    /**
     * 错误回调
     *
     * @param id 进度信息的 id
     * @param e  错误
     */
    void onError(long id, Exception e);
}
