package com.qflbai.lib.base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.qflbai.lib.base.model.BaseModel;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/19 0019 11:50
 * @Version: 1.0
 * @description:
 */
public class DataViewModel extends BaseViewModel<BaseModel> {
    public DataViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

    /**
     * 传入Class 获得{@link retrofit2.Retrofit#create(Class)} 对应的Class
     * @param service
     * @param <T>
     * @return {@link retrofit2.Retrofit#create(Class)}
     */
    public <T> T getRetrofitService(Class<T> service){
        return mModel.getRetrofitService(service);
    }

    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param database
     * @param dbName
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database, @Nullable String dbName){
        return mModel.getRoomDatabase(database,dbName);
    }
}
