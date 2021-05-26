package com.lwkandroid.lib.core.utils.json;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Description:定义Json相关方法
 *
 * @author LWK
 * @date 2020/1/10
 */
public interface IJsonStrategy
{
    boolean isJsonData(String data);

    boolean isJsonObject(String data);

    boolean isJsonArray(String data);

    boolean isJsonNull(String data);

    boolean isJsonPrimitive(String data);

    String toJson(Object object);

    String toJson(Object object, boolean includeNulls);

    String toJson(Object src, Type typeOfSrc);

    String toJson(Object src, Type typeOfSrc, boolean includeNulls);

    <T> T fromJson(String json, Class<T> type);

    <T> T fromJson(String json, Type type);

    <T> T fromJson(Reader reader, Class<T> type);

    <T> T fromJson(Reader reader, Type type);

    Type getListType(Type type);

    Type getSetType(Type type);

    Type getMapType(Type keyType, Type valueType);

    Type getArrayType(Type type);

    Type getType(Type rawType, Type... typeArguments);
}
