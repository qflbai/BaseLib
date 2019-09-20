package com.qflbai.lib.base.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.qflbai.lib.base.data.ViewState;
import com.qflbai.lib.base.repository.IDataRepository;
import com.qflbai.lib.constant.ConstantValues;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/19 0019 10:25
 * @Version: 1.0
 * @description:
 */
public class BaseModel implements IModel {
    private IDataRepository mDataRepository;

    @Inject
    public BaseModel(IDataRepository dataRepository) {
        mDataRepository = dataRepository;
        mLoadState = new MutableLiveData<>();
    }

    @Override
    public void onDestroy() {
        mDataRepository = null;
    }

    /**
     * 传入Class 获得{@link retrofit2.Retrofit#create(Class)} 对应的Class
     *
     * @param service
     * @param <T>
     * @return {@link retrofit2.Retrofit#create(Class)}
     */
    public <T> T getRetrofitService(Class<T> service) {
        return mDataRepository.getRetrofitService(service);
    }


    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     *
     * @param database
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database) {
        return getRoomDatabase(database, ConstantValues.DEFAULT_DATABASE_NAME);
    }

    /**
     * 传入Class 通过{@link Room#databaseBuilder},{@link RoomDatabase.Builder<T>#build()}获得对应的Class
     *
     * @param database
     * @param dbName
     * @param <T>
     * @return {@link RoomDatabase.Builder<T>#build()}
     */
    public <T extends RoomDatabase> T getRoomDatabase(@NonNull Class<T> database, @Nullable String dbName) {
        return mDataRepository.getRoomDatabase(database, dbName);
    }

    private CompositeDisposable mCompositeDisposable;
    public MutableLiveData<ViewState> mLoadState;

    protected void postState(ViewState state) {
        if (mLoadState != null) {
            mLoadState.postValue(state);
        }

    }

    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

}
