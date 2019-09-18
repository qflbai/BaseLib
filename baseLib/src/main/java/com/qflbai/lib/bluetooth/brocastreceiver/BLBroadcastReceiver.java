package com.qflbai.lib.bluetooth.brocastreceiver;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qflbai.lib.bluetooth.callback.BLReceiveCallback;
import com.qflbai.lib.bluetooth.service.BluetoothLeService;
import com.qflbai.lib.utils.bluetooth.BluetoothUtil;
import com.qflbai.lib.utils.log.LogUtil;

/**
 * ProjectName: SunTechProjects
 * ClassDesc: ******
 * CreateUser: 201704012
 * CreateDate: 2018/2/28 10:35
 * UpdateUser: 201704012
 * UpdateDate:  2018/2/28 10:35
 * UpdateDesc: ******蓝牙广播接收
 */
public class BLBroadcastReceiver extends BroadcastReceiver {
    private String TAG = BLBroadcastReceiver.class.getSimpleName();
    Activity mActivity;
    BLReceiveCallback mBlStateCallback;
    public BLBroadcastReceiver(Activity activity, BLReceiveCallback blStateCallback) {
        mActivity = activity;
        mBlStateCallback = blStateCallback;
    }

    /**
     * 是否通信成功
     */
    private boolean mIsCommunicationSucceed = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        abortBroadcast();
        switch (action) {
            // 连接上（但还没发送密码）
            case BluetoothLeService.ACTION_GATT_CONNECTED:
                mIsCommunicationSucceed = true;

                break;
            //断开连接
            case BluetoothLeService.ACTION_GATT_DISCONNECTED:

                mActivity.invalidateOptionsMenu();
                // 断开回调
                mBlStateCallback.onBLDropped(BluetoothLeService.ACTION_GATT_DISCONNECTED);
                break;
            // 掉线
            case BluetoothLeService.ACTION_GATT_DROPPED:
                mIsCommunicationSucceed = false;
                // 掉线回调
                mBlStateCallback.onBLDropped(BluetoothLeService.ACTION_GATT_DROPPED);
                break;
            //发现服务
            case BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED:
                mActivity.invalidateOptionsMenu();
                break;

            // 接收数据(包括蓝牙配对数据)
            case BluetoothLeService.ACTION_DATA_AVAILABLE:
                dealDataAvailable(intent);
                break;
            case BluetoothLeService.GATT_STATUS_133:
                if (mIsCommunicationSucceed) {
                    mBlStateCallback.onBLDropped(BluetoothLeService.GATT_STATUS_133);
                }
                break;
            // 蓝牙状态改变
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (blueState) {
                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;
                    case BluetoothAdapter.STATE_ON:
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:

                        break;
                    case BluetoothAdapter.STATE_OFF:
                        mIsCommunicationSucceed = false;
                        mBlStateCallback.onLocalBLStateChange(BluetoothAdapter.STATE_OFF);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

    }

    /**
     * 是否是量子云码的码值
     */
    private boolean mIsCodeValue = false;
    /**
     * 处理蓝牙接收到的数据
     *
     * @param intent
     */
    private void dealDataAvailable(Intent intent) {
        // 蓝牙返回数据
        byte[] beginArrays = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
        LogUtil.i(TAG,"数据size:"+beginArrays.length);
        if (beginArrays == null || beginArrays.length <= 0) {
            return;
        }

        // 字节转为字符
        String bLData = new String(beginArrays);
        LogUtil.i(TAG, "收到的数据:"+bLData);
        if(null == bLData || bLData.length() == 0) return;

        // 去掉bLData中的回车换行符（/r/n）
        bLData = BluetoothUtil.removeEnterChar(bLData);
        //去掉bLData的第一个和最后一个字符串（即去掉“{” “}”）
        if(null == bLData || bLData.length() == 0) return;
        bLData = BluetoothUtil.dealBluetoothString(bLData);
        if(null == bLData || bLData.length() == 0) return;
        // 分割数据
        String[] splitBLData = bLData.split(":");

        // 与服务器端蓝牙交互响应结果处理（如密码是否正确）
        if (splitBLData.length >= 2) {
            if (splitBLData[1].contains("}")) {
                String middle = splitBLData[1];
                String[] temp = middle.split("\\" + "}");
                splitBLData[1] = temp[0];
            } else if (splitBLData[1].contains("{")) {
                String[] temp = splitBLData[1].split("\\" + "{");
                splitBLData[1] = temp[0];
            }
            // 返回结果处理
            dealBLResponse(splitBLData, intent);
        }  else {
            // 判断是不是蓝牙扫码的数据
            if (mIsCodeValue) {
                //扫码结果数据
                scanCodeStringBuffer.append(bLData);
                if (bLData.length() < 20) {
                    String codeValue = scanCodeStringBuffer.toString();

                    if (codeValue.length() >= 163) {
                        mIsCodeValue = false;

                        // 扫码结果回调
                        mBlStateCallback.onBLScanCodeResult(codeValue);
                    }
                }
            }

        }
    }

    /**
     * 量子云码容器（蓝牙接收量子云码字符是分批次的）
     */
    StringBuffer scanCodeStringBuffer = new StringBuffer();
    /**
     * 蓝牙设备电量
     */
    private String mDeviceVoltage = "";
    /**
     * 蓝牙响应结果数据处理
     *
     * @param responseData
     * @param intent
     */
    private void dealBLResponse(String[] responseData, Intent intent) {
        String key = responseData[0];//{result:0}
        switch (key) {
            // 扫量子云码结果
            case BluetoothLeService.BLURTOOTH_DECODE_RESULT:
                mIsCodeValue = true;
                // 清空数据
                scanCodeStringBuffer.setLength(0);
                scanCodeStringBuffer.append(responseData[1]);
                break;

            // 验证密码结果
            case BluetoothLeService.BLURTOOTH_RESULT:

                 /* 返回电量 这里应该不需要 */
            case BluetoothLeService.BLURTOOTH_VOLTAGE:

                mDeviceVoltage = BluetoothUtil.getDeviceVoltage(responseData[1]);
                break;
            default:
                break;
        }
    }
}
