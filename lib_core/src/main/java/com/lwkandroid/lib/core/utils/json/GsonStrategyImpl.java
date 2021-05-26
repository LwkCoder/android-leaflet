package com.lwkandroid.lib.core.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description:Gson实现的策略
 *
 * @author LWK
 * @date 2020/1/10
 */
final class GsonStrategyImpl implements IJsonStrategy
{
    private static final Gson GSON = createGson(true);

    private static final Gson GSON_NO_NULLS = createGson(false);

    private static Gson createGson(final boolean serializeNulls)
    {
        final GsonBuilder builder = new GsonBuilder();
        if (serializeNulls)
        {
            builder.serializeNulls();
        }
        return builder.create();
    }

    @Override
    public boolean isJsonData(String data)
    {
        return isJsonNull(data) || isJsonPrimitive(data) || isJsonObject(data) || isJsonArray(data);
    }

    @Override
    public boolean isJsonObject(String data)
    {
        try
        {
            JsonElement element = JsonParser.parseString(data);
            if (element == null)
            {
                return false;
            }
            return element.isJsonObject();
        } catch (JsonSyntaxException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isJsonArray(String data)
    {
        try
        {
            JsonElement element = JsonParser.parseString(data);
            if (element == null)
            {
                return false;
            }
            return element.isJsonArray();
        } catch (JsonSyntaxException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isJsonNull(String data)
    {
        try
        {
            JsonElement element = JsonParser.parseString(data);
            if (element == null)
            {
                return false;
            }
            return element.isJsonNull();
        } catch (JsonSyntaxException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isJsonPrimitive(String data)
    {
        try
        {
            JsonElement element = JsonParser.parseString(data);
            if (element == null)
            {
                return false;
            }
            return element.isJsonPrimitive();
        } catch (JsonSyntaxException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toJson(Object object)
    {
        return toJson(object, true);
    }

    @Override
    public String toJson(Object object, boolean includeNulls)
    {
        return includeNulls ? GSON.toJson(object) : GSON_NO_NULLS.toJson(object);
    }

    @Override
    public String toJson(Object src, Type typeOfSrc)
    {
        return toJson(src, true);
    }

    @Override
    public String toJson(Object src, Type typeOfSrc, boolean includeNulls)
    {
        return includeNulls ? GSON.toJson(src, typeOfSrc) : GSON_NO_NULLS.toJson(src, typeOfSrc);
    }

    @Override
    public <T> T fromJson(String json, Class<T> type)
    {
        return GSON.fromJson(json, type);
    }

    @Override
    public <T> T fromJson(String json, Type type)
    {
        return GSON.fromJson(json, type);
    }

    @Override
    public <T> T fromJson(Reader reader, Class<T> type)
    {
        return GSON.fromJson(reader, type);
    }

    @Override
    public <T> T fromJson(Reader reader, Type type)
    {
        return GSON.fromJson(reader, type);
    }

    @Override
    public Type getListType(Type type)
    {
        return TypeToken.getParameterized(List.class, type).getType();
    }

    @Override
    public Type getSetType(Type type)
    {
        return TypeToken.getParameterized(Set.class, type).getType();
    }

    @Override
    public Type getMapType(Type keyType, Type valueType)
    {
        return TypeToken.getParameterized(Map.class, keyType, valueType).getType();
    }

    @Override
    public Type getArrayType(Type type)
    {
        return TypeToken.getArray(type).getType();
    }

    @Override
    public Type getType(Type rawType, Type... typeArguments)
    {
        return TypeToken.getParameterized(rawType, typeArguments).getType();
    }
}
