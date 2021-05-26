package com.lwkandroid.lib.core.net.cache.operator;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.lwkandroid.lib.core.net.bean.ApiDiskCacheBean;
import com.lwkandroid.lib.core.utils.common.CloseUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by LWK
 * GSON-数据转换器其实就是存储字符串的操作
 * 优点：
 * 相对于SerializableDiskConverter转换器，存储的对象不需要进行序列化（Serializable），
 * 特别是一个对象中又包含很多其它对象，每个对象都需要Serializable，比较麻烦
 * <p>
 * 缺点：
 * 就是存储和读取都要使用Gson进行转换，object->String->Object的给一个过程，相对来说
 * 每次都要转换性能略低，但是以现在的手机性能可以忽略不计了。
 *
 * @author LWK
 */

public class GsonDiskOperator implements IDiskCacheOperator
{
    private Gson mGson;

    public GsonDiskOperator()
    {
        this.mGson = new Gson();
    }

    public GsonDiskOperator(Gson gson)
    {
        this.mGson = mGson;
    }

    @Override
    public <T> ApiDiskCacheBean<T> load(InputStream source, Class<T> clazz)
    {
        ApiDiskCacheBean<T> entity = null;
        try
        {
            TypeAdapter<ApiDiskCacheBean<T>> adapter = (TypeAdapter<ApiDiskCacheBean<T>>) mGson.getAdapter(
                    TypeToken.getParameterized(ApiDiskCacheBean.class, clazz));
            JsonReader jsonReader = mGson.newJsonReader(new InputStreamReader(source));
            entity = adapter.read(jsonReader);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            CloseUtils.closeIO(source);
        }

        return entity;
    }

    @Override
    public boolean writer(OutputStream sink, Object data)
    {
        try
        {
            String json = mGson.toJson(data);
            byte[] bytes = json.getBytes();
            sink.write(bytes, 0, bytes.length);
            sink.flush();
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            CloseUtils.closeIO(sink);
        }
        return false;
    }
}
