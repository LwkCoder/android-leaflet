package com.lwkandroid.lib.core.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

/**
 * @description: Room Dao层基础接口
 * @author: LWK
 */
@Dao
public interface IRoomBaseDao<T>
{
    /**
     * 插入单条数据
     *
     * @param item 数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertItem(T item);

    /**
     * 插入list数据
     *
     * @param items 数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertItems(List<T> items);

    /**
     * 删除item
     *
     * @param item 数据
     */
    @Delete
    Maybe<Integer> deleteItem(T item);

    /**
     * 更新item
     *
     * @param item 数据
     */
    @Update
    Single<Integer> updateItem(T item);
}
