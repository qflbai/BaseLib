package com.example.myapplication;


import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.dagger.Student;
import com.example.myapplication.dagger.id.DaggerDraggerComponent;
import com.qflbai.lib.base.activity.BaseActivity;
import com.qflbai.lib.utils.log.LogUtil;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {
    @Inject
    Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBackToolbar("hahhah");
        ImageView ivSubtitle = getIvSubtitle();
        ivSubtitle.setVisibility(View.VISIBLE);
        ivSubtitle.setImageResource(R.mipmap.emo_im_kissing);

        ivSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,TestActivity.class);
                startActivity(intent);
            }
        });

        TextView tvSubtitleTitle = getTvSubtitleTitle();
        tvSubtitleTitle.setVisibility(View.VISIBLE);
        tvSubtitleTitle.setText("wwwww");
        showDialogLoading();
        DaggerDraggerComponent.builder().application((Application) App.getAPPContext()).build().inject(this);
        String name = student.getName();
        LogUtil.i(tag,name+"  "+student.getmApplication());
    }
}
