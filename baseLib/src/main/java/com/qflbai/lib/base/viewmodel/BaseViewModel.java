package com.qflbai.lib.base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.qflbai.lib.base.model.BaseModel;
import com.qflbai.lib.event.TUtil;

import javax.inject.Inject;

/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public class BaseViewModel<T extends BaseModel> extends AndroidViewModel implements IViewModel {
    public String tag = getClaseName();

    public T mModel;
    @Inject
    public BaseViewModel(@NonNull Application application, T model) {
        super(application);
        mModel = model;
    }

    private String getClaseName() {
        return getClass().getSimpleName();
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if(mModel != null){
            mModel.onDestroy();
            mModel = null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

}
