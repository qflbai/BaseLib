package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initBackToolbar("");
    }
    protected void initBackToolbar(CharSequence title) {
        Toolbar toolbar = getToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //setToolbarTitle(title);
        getTitleTextView().setText(title);
    }

    protected TextView getTitleTextView() {
        return findViewById(R.id.tv_title);
    }

    protected Toolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }

}
