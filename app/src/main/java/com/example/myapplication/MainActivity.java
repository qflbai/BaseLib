package com.example.myapplication;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    @Inject
    Student student;
    private boolean isHasScreenShotListener = false;
    @BindView(R.id.iv)
    ImageView iv;

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
                Intent intent = new Intent(mContext, TestActivity.class);
                startActivity(intent);
            }
        });

        TextView tvSubtitleTitle = getTvSubtitleTitle();
        tvSubtitleTitle.setVisibility(View.VISIBLE);
        tvSubtitleTitle.setText("wwwww");
        // showDialogLoading();
        // showDialogLoading();
        DaggerDraggerComponent.builder().application((Application) App.getAPPContext()).build().inject(this);
        // String name = student.getName();
        // LogUtil.i(tag,name+"  "+student.getmApplication());

        // NetBaseUrl.setBaseURL("http://192.168.11.77:8611");
        // getDepartmentList();

        org.angmarch.views.NiceSpinnerPro niceSpinnerPro = findViewById(R.id.sp);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinnerPro.attachDataSource(dataset);
        niceSpinnerPro.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {

            }

            @Override
            public void onItemSelected(View view, int i, long l) {

            }
        });

        applyForPermissions();
    }

    @SuppressLint("CheckResult")
    private void applyForPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //申请的权限全部允许
                            initData();
                        } else {


                        }
                    }
                });
    }

    @Override
    public void initData() {
        //startScreenShotListen();
    }

    private void getDepartmentList() {
        LogUtil.e(tag, "adsffafdasfsadfafdfadsfasdf");
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

    /**
     * 监听
     */
    /*private void startScreenShotListen() {
        if (!isHasScreenShotListener && screenShotListenManager != null) {
            screenShotListenManager.setListener(new ScreenShotListenManager.OnScreenShotListener() {
                @Override
                public void onShot(String imagePath) {

                    String path = imagePath;
                    Bitmap bitmap = BitmapFactory.decodeFile(path);

                    RubberStampConfig config = new RubberStampConfig.RubberStampConfigBuilder()
                            .base(bitmap)
                            .rubberStamp("Watermark")
                            .rubberStampPosition(RubberStampPosition.TILE)
                            .margin(90, 90)
                            .alpha(90)
                            .rotation(0)
                            .textColor(Color.RED)
                            .textSize(45)
                            .build();

                    RubberStamp rubberStamp = new RubberStamp(mContext);
                    Bitmap bitmap1 = rubberStamp.addStamp(config);
                    iv.setImageBitmap(bitmap1);
                    StringsaveImageToFile(bitmap1,path);
                }
            });
            screenShotListenManager.startListen();
            isHasScreenShotListener = true;
        }
    }*/

    public void StringsaveImageToFile(Bitmap bitmap, String filename) {
        File f = new File(filename);
        ByteArrayOutputStream bos=null;
        FileOutputStream fos=null;
        try {
            // 此处不要判断文件是否存在,就是要覆盖截屏的图片做法
            bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();
            fos= new FileOutputStream(f);
            fos.write(bitmapdata);

        } catch (IOException e) {
            e.printStackTrace();
        }    try {
            if(fos!=null) {
                fos.flush();
                fos.close();
                bos.close();
                bos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
