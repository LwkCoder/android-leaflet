/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lwkandroid.lib.core.net.cache.operator;


import com.lwkandroid.lib.core.net.bean.ApiDiskCacheBean;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 硬盘缓存接口
 *
 * @author LWK
 */
public interface IDiskCacheOperator
{
    /**
     * 读取
     *
     * @param source 输入流
     * @param clazz  读取数据后要转换的数据类型
     *               这里没有用泛型T或者Type来做，是因为本框架决定的一些问题，泛型会丢失
     * @return
     */
    <T> ApiDiskCacheBean<T> load(InputStream source, Class<T> clazz);

    /**
     * 写入
     *
     * @param sink
     * @param data 保存的数据
     * @return
     */
    boolean writer(OutputStream sink, Object data);

}
