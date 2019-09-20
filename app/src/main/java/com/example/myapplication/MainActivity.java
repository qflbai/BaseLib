package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.dagger.Student;
import com.qflbai.lib.base.activity.BaseActivity;

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


    }
}
