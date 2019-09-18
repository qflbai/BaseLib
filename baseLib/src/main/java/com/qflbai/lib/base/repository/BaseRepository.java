package com.qflbai.lib.base.repository;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public  class BaseRepository {

    private CompositeDisposable mCompositeDisposable;
    public MutableLiveData<String> loadState;

    public BaseRepository() {
        loadState = new MutableLiveData<>();
    }

    protected void postState(String state) {
        if (loadState != null) {
            loadState.postValue(state);
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
