package com.example.myapplication.a.view.view.activity;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.a.view.viewmodel.JViewModel;
import com.qflbai.lib.base.activity.AbsLifecycleActivity;

/**
 * @author: qflbai
 * @CreateDate:
 * @Version: 1.0
 * @description:
 */
public class JActivity extends AbsLifecycleActivity<JViewModel> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
    }
}
