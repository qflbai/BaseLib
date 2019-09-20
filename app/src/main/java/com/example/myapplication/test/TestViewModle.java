package com.example.myapplication.test;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.qflbai.lib.base.viewmodel.BaseViewModel;

import javax.inject.Inject;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/18 0018 15:40
 * @Version: 1.0
 * @description:
 */
public class TestViewModle extends BaseViewModel<TestModle>{
    @Inject
    public TestViewModle(@NonNull Application application, TestModle model) {
        super(application, model);
    }

    public MutableLiveData<String> getNet(){
         mModel.getNet();
         return null;
    }
}
