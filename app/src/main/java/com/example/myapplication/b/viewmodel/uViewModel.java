package com.example.myapplication.b.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myapplication.b.model.uModel;
import com.qflbai.lib.base.viewmodel.BaseViewModel;


/**
 * @author: qflbai
 * @CreateDate:
 * @Version: 1.0
 * @description:
 */
public class uViewModel extends BaseViewModel<uModel> {
    public uViewModel(@NonNull Application application, uModel model) {
        super(application, model);
    }
}
