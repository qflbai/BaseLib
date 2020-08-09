package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.qflbai.lib.utils.toast.ToastUtil;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        LinearLayout viewById = findViewById(R.id.ll);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(MainActivity2.this,"ddd");
            }
        });

        EditText viewById1 = findViewById(R.id.et);
        viewById1.setOnKeyListener(null);
        viewById1.setMovementMethod(null);
    }
}