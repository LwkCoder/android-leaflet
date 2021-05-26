package com.lwkandroid.lib.core.utils.json;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Json解析工具类入口
 *
 * @author LWK
 */

public class JsonUtils
{
    private JsonUtils()
    {
    }

    static
    {
        IMPL = new GsonStrategyImpl();
    }

    private static final IJsonStrategy IMPL;

    public static boolean isJsonData(String data)
    {
        return IMPL.isJsonData(data);
    }

    public static boolean isJsonObject(String data)
    {
        return IMPL.isJsonObject(data);
    }

    public static boolean isJsonArray(String data)
    {
        return IMPL.isJsonArray(data);
    }

    public static boolean isJsonNull(String data)
    {
        return IMPL.isJsonNull(data);
    }

    public static boolean isJsonPrimitive(String data)
    {
        return IMPL.isJsonPrimitive(data);
    }

    public static String toJson(Object object)
    {
        return IMPL.toJson(object);
    }

    public static String toJson(Object object, boolean includeNulls)
    {
        return IMPL.toJson(object, includeNulls);
    }

    public static String toJson(Object src, Type typeOfSrc)
    {
        return IMPL.toJson(src, typeOfSrc);
    }

    public static String toJson(Object src, Type typeOfSrc, boolean includeNulls)
    {
        return IMPL.toJson(src, typeOfSrc, includeNulls);
    }

    public static <T> T fromJson(String json, Class<T> type)
    {
        return IMPL.fromJson(json, type);
    }

    public static <T> T fromJson(String json, Type type)
    {
        return IMPL.fromJson(json, type);
    }

    public static <T> T fromJson(Reader reader, Class<T> type)
    {
        return IMPL.fromJson(reader, type);
    }

    public static <T> T fromJson(Reader reader, Type type)
    {
        return IMPL.fromJson(reader, type);
    }

    public static Type getListType(Type type)
    {
        return IMPL.getListType(type);
    }

    public static Type getSetType(Type type)
    {
        return IMPL.getSetType(type);
    }

    public static Type getMapType(Type keyType, Type valueType)
    {
        return IMPL.getMapType(keyType, valueType);
    }

    public static Type getArrayType(Type type)
    {
        return IMPL.getArrayType(type);
    }

    public static Type getType(Type rawType, Type... typeArguments)
    {
        return IMPL.getType(rawType, typeArguments);
    }
}
