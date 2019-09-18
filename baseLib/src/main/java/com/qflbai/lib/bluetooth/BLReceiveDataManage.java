package com.qflbai.lib.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;

import com.qflbai.lib.bluetooth.brocastreceiver.BLBroadcastReceiver;
import com.qflbai.lib.bluetooth.callback.BLReceiveCallback;
import com.qflbai.lib.bluetooth.service.BluetoothLeService;


/**
 * ProjectName: SunTechProjects
 * ClassDesc: ******
 * CreateUser: 201704012
 * CreateDate: 2018/3/8 11:59
 * UpdateUser: 201704012
 * UpdateDate:  2018/3/8 11:59
 * UpdateDesc: ******蓝牙连接成功后管理器
 */
public class BLReceiveDataManage  {
    private Context mContext;
    private Activity mActivity;
    private BLBroadcastReceiver mBLBroadcastReceiver;

    public BLReceiveDataManage(Context context, Activity activity){
        mContext = context;
        mActivity = activity;
    }

    public void registerReceive(BLReceiveCallback bLStateCallback){
        mBLBroadcastReceiver = new BLBroadcastReceiver(mActivity, bLStateCallback);
        mContext.registerReceiver(mBLBroadcastReceiver,bLIntentFilter());
    }

    public void unRegisterReceive(){
        mContext.unregisterReceiver(mBLBroadcastReceiver);
    }

    /**
     * 注册蓝牙接收的事件
     */
    private IntentFilter bLIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.GATT_STATUS_133);  //add by corey20170418  情况：status=133
        intentFilter.addAction(BluetoothDevice.ACTION_UUID);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DROPPED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DEVICE_ERROR);

        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.setPriority(1000);
        return intentFilter;
    }
}
