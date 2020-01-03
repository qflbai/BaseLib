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
import com.qflbai.lib.net.RetrofitManage;
import com.qflbai.lib.net.callback.NetCallback;
import com.qflbai.lib.net.retrofit.RetrofitService;
import com.qflbai.lib.net.rxjava.NetObserver;
import com.qflbai.lib.net.url.NetBaseUrl;
import com.qflbai.lib.utils.log.LogUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

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
        showDialogLoading();
        DaggerDraggerComponent.builder().application((Application) App.getAPPContext()).build().inject(this);
        String name = student.getName();
        LogUtil.i(tag,name+"  "+student.getmApplication());

        NetBaseUrl.setBaseURL("http://192.168.11.77:8611");
        getDepartmentList();
    }

    private void getDepartmentList() {
        LogUtil.e(tag,"adsffafdasfsadfafdfadsfasdf");
        showError();
        Map<String, Object> map = new HashMap<>();
        map.put("orgid", "8d93421eb2ac11e9bc3d68f728197b1e");
        map.put("orgname", "昆明供电局");
        map.put("orgcode", "050100");
        String path = "appkmSafety/phone/login/loadUnitTree";
        RetrofitService service = RetrofitManage.newInstance().createService(RetrofitService.class);
        service.getNet(path, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver(new NetCallback() {
                    @Override
                    public void onResponse(String dataJson) {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));


    }

}
