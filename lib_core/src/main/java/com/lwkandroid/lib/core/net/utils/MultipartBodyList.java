package com.lwkandroid.lib.core.net.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络请求MultipartBody集合参数辅助工具
 *
 * @author LWK
 */
public class MultipartBodyList extends ArrayList<okhttp3.MultipartBody.Part>
{
    private static final long serialVersionUID = -3039120772270628562L;

    /**
     * 添加表单参数
     */
    public MultipartBodyList addFormData(String key, Object value)
    {
        add(MultipartBodyUtils.createFormDataPart(key, value));
        return this;
    }

    /**
     * 添加File参数
     */
    public MultipartBodyList addFile(String key, File file)
    {
        add(MultipartBodyUtils.createFormDataPart(key, file.getName(), RequestBodyUtils.createFileBody(file)));
        return this;
    }

    /**
     * 添加File参数
     */
    public MultipartBodyList addFiles(String key, List<File> fileList)
    {
        for (File file : fileList)
        {
            add(MultipartBodyUtils.createFormDataPart(key, file.getName(), RequestBodyUtils.createFileBody(file)));
        }
        return this;
    }

    /**
     * 添加Bytes参数
     */
    public MultipartBodyList addBytes(String key, String fileName, byte[] bytes)
    {
        add(MultipartBodyUtils.createFormDataPart(key, fileName, RequestBodyUtils.createBytesBody(bytes)));
        return this;
    }

    /**
     * 添加InputStream参数
     */
    @Deprecated
    public MultipartBodyList addInputStream(String key, String fileName, InputStream stream)
    {
        add(MultipartBodyUtils.createFormDataPart(key, fileName,
                RequestBodyUtils.createInputStreamBody(RequestBodyUtils.guessMimeType(fileName), stream)));
        return this;
    }
}

