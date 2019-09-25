package com.example.myapplication.a.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myapplication.a.view.model.JModel;
import com.qflbai.lib.base.viewmodel.BaseViewModel;


/**
 * @author: qflbai
 * @CreateDate:
 * @Version: 1.0
 * @description:
 */
public class JViewModel extends BaseViewModel<JModel> {
    public JViewModel(@NonNull Application application, JModel model) {
        super(application, model);
    }
}
