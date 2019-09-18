package com.qflbai.lib.utils.lacation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.qflbai.lib.base.BaseApplication;


/**
 * @author: qflbai
 * @CreateDate: 2019/7/29 19:02
 * @Version: 1.0
 * @description:
 */
public class LacationUtils {
    private AMapLocationListener mLocationListener;
    private AMapLocationClient mLocationClient;

    public void initLaction(final LacationCallback lacationCallback) {
        lacationCallback.onStart();
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        lacationCallback.onOk(aMapLocation);
                    } else {
                        lacationCallback.onError(aMapLocation);
                    }
                }

            }

        };

        //初始化定位
        mLocationClient = new AMapLocationClient(BaseApplication.getAPPContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = null;
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        mLocationClient.stopLocation();
        //启动定位
        mLocationClient.startLocation();

    }

    public void stop() {

    }

    public static final int OPEN_GPS_REQUSET_CODE = 887;

    public void openGPS(final Activity context) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("定位")
                .setMessage("没有开启定位")
                .setNegativeButton("取消", null)
                .setPositiveButton("打开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivityForResult(intent, OPEN_GPS_REQUSET_CODE);
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void openGPS(final Fragment fragment, Context context) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("定位")
                .setMessage("没有开启定位")
                .setNegativeButton("取消", null)
                .setPositiveButton("打开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        fragment.startActivityForResult(intent, OPEN_GPS_REQUSET_CODE);
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


}
