package com.lwkandroid.lib.core.context;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.lwkandroid.lib.common.WingsTools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description:全局Context提供者
 *
 * @author LWK
 * @date 2020/1/6
 */
public class ContextProvider extends ContentProvider
{
    public static Context mContext;

    @Override
    public boolean onCreate()
    {
        mContext = getContext().getApplicationContext();
        //初始化一些工具
        WingsTools.initTools(mContext);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        throw new IllegalAccessError("Not Implemented.This provider only provides the global variable of Application context");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        throw new IllegalAccessError("Not Implemented.This provider only provides the global variable of Application context");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
    {
        throw new IllegalAccessError("Not Implemented.This provider only provides the global variable of Application context");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        throw new IllegalAccessError("Not Implemented.This provider only provides the global variable of Application context");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        throw new IllegalAccessError("Not Implemented.This provider only provides the global variable of Application context");
    }

}
