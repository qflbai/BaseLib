package com.qflbai.lib.base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.qflbai.lib.base.repository.BaseRepository;
import com.qflbai.lib.event.TUtil;

/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public class BaseViewModel<T extends BaseRepository> extends AndroidViewModel {
    public String tag = getClaseName();

    public T mRepository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = TUtil.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
        }
    }
    private String getClaseName(){
        return getClass().getSimpleName();
    }


}
