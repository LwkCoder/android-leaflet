package com.lwkandroid.lib.core.net.cache.operator;


import com.lwkandroid.lib.core.net.bean.ApiDiskCacheBean;
import com.lwkandroid.lib.core.utils.common.CloseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 序列化对象的转换器，对象&对象中的其它所有对象都必须是要实现Serializable接口（序列化）
 *
 * @author LWK
 */

public class SerializableDiskOperator implements IDiskCacheOperator
{
    @Override
    public <T> ApiDiskCacheBean<T> load(InputStream source, Class<T> clazz)
    {
        //序列化的缓存不需要用到clazz
        ApiDiskCacheBean<T> value = null;
        ObjectInputStream oin = null;
        try
        {
            oin = new ObjectInputStream(source);
            value = (ApiDiskCacheBean<T>) oin.readObject();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            CloseUtils.closeIO(source);
        }
        return value;
    }

    @Override
    public boolean writer(OutputStream sink, Object data)
    {
        ObjectOutputStream oos = null;
        try
        {
            oos = new ObjectOutputStream(sink);
            oos.writeObject(data);
            oos.flush();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            CloseUtils.closeIO(sink);
        }
        return false;
    }
}
