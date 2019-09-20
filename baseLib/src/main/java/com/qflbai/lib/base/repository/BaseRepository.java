package com.qflbai.lib.base.repository;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.qflbai.lib.base.data.ViewState;
import com.qflbai.lib.constant.ConstantValues;
import com.qflbai.lib.net.RetrofitManage;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public class BaseRepository implements IDataRepository {

    @Inject
    Application mApplication;
    private final RetrofitManage retrofitManage;

    @Inject
    public BaseRepository() {
        retrofitManage = RetrofitManage.newInstance();
    }

    /**
     * 提供上下文{@link Context}
     *
     * @return {@link #mApplication}
     */
    @Override
    public Context getContext() {
        return mApplication;
    }

    /**
     * 传入Class 通过{@link retrofit2.Retrofit#create(Class)} 获得对应的Class
     *
     * @param service
     * @param <T>
     * @return {@link retrofit2.Retrofit#create(Class)}
     */
    @Override
    public <T> T getRetrofitService(@NonNull Class<T> service) {
        return retrofitManage.createService(service);
    }

    public <T> T getRetrofitService(@NonNull Class<T> service, String baseUrl) {
        return retrofitManage.createService(service, baseUrl);
    }

    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    @Override
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull T roomDatabase) {
        return roomDatabase;
    }
}
