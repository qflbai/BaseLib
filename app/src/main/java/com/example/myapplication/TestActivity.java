package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.test.TestViewModle;
import com.qflbai.lib.base.activity.AbsLifecycleActivity;
import com.qflbai.lib.base.activity.BaseActivity;

public class TestActivity extends AbsLifecycleActivity<TestViewModle> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initBackToolbar("test");

        mViewModel.getNet();
    }
}
