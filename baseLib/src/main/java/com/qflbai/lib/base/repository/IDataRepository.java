package com.qflbai.lib.base.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/19 0019 10:27
 * @Version: 1.0
 * @description:
 */
public interface IDataRepository {
    /**
     * 提供上下文
     * @return {@lik Context}
     */
    Context getContext();
    /**
     * 传入Class 通过{@link retrofit2.Retrofit#create(Class)} 获得对应的Class
     * @param service
     * @param <T>
     * @return {@link retrofit2.Retrofit#create(Class)}
     */
    <T> T getRetrofitService(@NonNull Class<T> service);

    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param database
     * @param dbName
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    <T extends RoomDatabase> T getRoomDatabase(T roomDatabase);
}
