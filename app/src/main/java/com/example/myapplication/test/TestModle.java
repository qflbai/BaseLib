package com.example.myapplication.test;

import com.qflbai.lib.base.model.BaseModel;
import com.qflbai.lib.base.repository.IDataRepository;
import com.qflbai.lib.net.callback.NetCallback;
import com.qflbai.lib.net.retrofit.RetrofitService;
import com.qflbai.lib.net.rxjava.NetObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/18 0018 15:41
 * @Version: 1.0
 * @description:
 */
public class TestModle extends BaseModel {

    private RetrofitService retrofitService;
    @Inject
    public TestModle(IDataRepository dataRepository) {
        super(dataRepository);
        retrofitService = getRetrofitService(RetrofitService.class);
    }
    public void getNet(){
        retrofitService.getNet("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver(new NetCallback() {
                    @Override
                    public void onResponse(String dataJson) {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                },mLoadState));
    }
}
