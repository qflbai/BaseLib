package com.example.myapplication.b.view.activity;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.b.viewmodel.uViewModel;
import com.qflbai.lib.base.activity.AbsLifecycleActivity;

/**
 * @author: qflbai
 * @CreateDate:
 * @Version: 1.0
 * @description:
 */
public class uActivity extends AbsLifecycleActivity<uViewModel> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutu);
    }
}
