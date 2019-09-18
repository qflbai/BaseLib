package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qflbai.lib.base.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBackToolbar("hahhah");
        ImageView ivSubtitle = getIvSubtitle();
        ivSubtitle.setVisibility(View.VISIBLE);
        ivSubtitle.setImageResource(R.mipmap.emo_im_kissing);
        TextView tvSubtitleTitle = getTvSubtitleTitle();
        tvSubtitleTitle.setVisibility(View.VISIBLE);
        tvSubtitleTitle.setText("wwwww");
    }
}
