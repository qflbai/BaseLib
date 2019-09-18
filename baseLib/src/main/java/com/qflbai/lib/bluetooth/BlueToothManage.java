package com.qflbai.lib.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

import com.qflbai.lib.bluetooth.callback.BLDeviceSearchCallback;
import com.qflbai.lib.bluetooth.callback.BLPasswordCallback;
import com.qflbai.lib.bluetooth.callback.BLStateCallback;
import com.qflbai.lib.bluetooth.service.BluetoothLeService;
import com.qflbai.lib.utils.bluetooth.BluetoothUtil;
import com.qflbai.lib.utils.log.LogUtil;
import com.qflbai.lib.utils.sharedpreferences.SpUtil;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * ProjectName: SunTechProjects
 * ClassDesc: ******
 * CreateUser: 201704012
 * CreateDate: 2018/3/2 14:26
 * UpdateUser: 201704012
 * UpdateDate:  2018/3/2 14:26
 * UpdateDesc: ****** 蓝牙管理类
 */
public class BlueToothManage {
    private final String TAG = BlueToothManage.class.getSimpleName();

    /**
     * 上下文对象
     */
    private Context mContext;
    private Activity mActivity;
    /**
     * 蓝牙后台服务器
     */
    private BluetoothLeService mBLService;
    /**
     * 蓝牙请求状态值
     */
    public static final int REQUEST_ENABLE = 1000;

    public BlueToothManage(Context context, Activity activity) {
        mContext = context;
        mActivity = activity;
    }

    /**
     * 初始化蓝牙
     */
    public void initBlueTooth() {
        startBLService();
    }

    /**
     * 是否有蓝牙设备连接
     *
     * @return
     */
    public boolean isHavaBlDeviceConnectioned() {
        return mBLService.isHavaBlDeviceConnectioned();
    }

    /**
     * 获取已连接设备
     *
     * @return
     */
    public BluetoothDevice getConnectionedDeviceed() {
        return mBLService.getConnectionedDeviceed();
    }

    /**
     * 蓝牙是否已打开
     *
     * @return false:没打开
     */
    public boolean isBLOpen() {
        // 获取蓝牙适配器
        BluetoothAdapter bLAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bLAdapter == null || !bLAdapter.isEnabled()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 打开蓝牙设备
     */
    public void openBLDevice() {
        //弹出对话框提示用户是后打开
        Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        mActivity.startActivityForResult(enabler, REQUEST_ENABLE);
    }

    /**
     * 关闭蓝牙设备
     */
    public void closeBLDevice() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null && defaultAdapter.isEnabled()) {
            defaultAdapter.disable();
        }

    }

    /**
     * 搜索蓝牙设备接口
     */
    private BLDeviceSearchCallback mBLDeviceSearchCallback;

    /**
     * 设置搜索回调监听
     * @param blDeviceSearchCallback
     */
    public void setSearchDeviceCallBack(BLDeviceSearchCallback blDeviceSearchCallback){
        mBLDeviceSearchCallback = blDeviceSearchCallback;
    }
    /**
     * 开始搜索蓝牙列表
     */
    public void startSearchDevice() {
        // 获取蓝牙适配器
        BluetoothAdapter bLAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bLAdapter != null) {
            bLAdapter.startLeScan(mLeScanCallback);
        }
    }


    /**
     * 设置密码监听
     *
     * @param passwordCallback
     */
    public void setOnPwdListener(BLPasswordCallback passwordCallback) {
        mBLPasswordCallBack = passwordCallback;
    }

    /**
     * 停止获取蓝牙列表
     */
    public void stopSearch() {
        // 获取蓝牙适配器
        BluetoothAdapter bLAdapter = BluetoothAdapter.getDefaultAdapter();
        bLAdapter.stopLeScan(mLeScanCallback);
    }

    /**
     * 查找蓝牙设备监听回调
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        /**
         * Callback reporting an LE device found during a device scan initiated
         * by the {@link BluetoothAdapter#startLeScan} function.
         *
         * @param device     Identifies the remote device
         * @param rssi       The RSSI value for the remote device as reported by the
         *                   Bluetooth hardware. 0 if no RSSI value is available.
         * @param scanRecord The content of the advertisement record offered by
         */
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (null == device || null == device.getName() || device.getName().equals("null") || device.getName().equals("")) {
                return;
            }

            // 获取蓝牙设备
            if (mBLDeviceSearchCallback != null) {

                mBLDeviceSearchCallback.bLDeviceSearch(device, rssi, scanRecord);
            }
        }
    };

    public interface ServiceConnectionCallBack {
        void onServiceConnectionCallBack(BluetoothLeService bluetoothLeService);
    }

    private ServiceConnectionCallBack mServiceConnectionCallBack;

    public void setServiceConnectionCallBack(ServiceConnectionCallBack serviceConnectionCallBack) {
        mServiceConnectionCallBack = serviceConnectionCallBack;
    }

    /**
     * 开启后台蓝牙进程(绑定服务)
     */
    public void startBLService() {
        try {
            Intent intent = new Intent(mContext, BluetoothLeService.class);
            // 绑定服务
            mContext.bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
            mContext.startService(intent);
            LogUtil.w(TAG, "bindService()");
        } catch (Exception e) {
            e.printStackTrace();
            mBLService = null;
        }
    }

    /**
     * 解绑服务
     */
    public void unBLBindService() {
        mContext.unbindService(mServiceConnection);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取BlueToothLeService
            mBLService = ((BluetoothLeService.LocalBinder) service).getService();
            LogUtil.w(TAG, "onServiceConnected()");
            // 初始化BluetoothAdapter
            mBLService.initialize();
            if (mServiceConnectionCallBack != null) {
                mServiceConnectionCallBack.onServiceConnectionCallBack(mBLService);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBLService = null;
        }
    };

    /**
     * 蓝牙状态回调
     */
    private BLStateCallback mBlStateCallback;

    /**
     * 作为客户端连接其他蓝牙设备
     *
     * @param bluetoothDevice
     * @return 是否连接成功
     */
    public boolean connectBLDevice(BluetoothDevice bluetoothDevice, BLStateCallback blStateCallback) {
        mBlStateCallback = blStateCallback;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter.isEnabled() && mBLService != null && bluetoothDevice != null) {
            // 蓝牙mac地址
            String address = bluetoothDevice.getAddress();
            mBLService.connect(address);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 密码
     */
    private String mPwd = "";

    /**
     * 写入密码
     *
     * @param pwd
     */
    public boolean sendPwdToBLDevice(String pwd) {
        mPwd = pwd;
        String pwdCommand = "{password:" + pwd + "}";
        // 密码命令写入
        boolean isSucceed = mBLService.WriteValue(pwdCommand);
        return isSucceed;
    }

    /**
     * 选中设备
     */
    private BluetoothDevice mSelectedBluetoothDevice = null;

    /**
     * 设置当前已连接设备的信息
     *
     * @param bluetoothDevice : 若为null， 表示没有连接设备 若不为null，则表示有已连接的设备
     */
    public void setSelectedDeviceInfo(BluetoothDevice bluetoothDevice) {

        mSelectedBluetoothDevice = bluetoothDevice;
    }

    /**
     * 获取选中蓝牙设备
     *
     * @return
     */
    public BluetoothDevice getSelectedBlueToothDevice() {
        return mSelectedBluetoothDevice;
    }

    /**************************************************************接收蓝牙广播部分*****************************************************************/

    /**
     * 注册广播接收
     */
    public void registerBLReceiver() {
        // 注册
        mContext.registerReceiver(mBLBroadcastReceiver, bLIntentFilter());
    }

    /**
     * 注销广播接收
     */
    public void unRegisterBLReceiver() {
        // 取消注册
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
        intentFilter.setPriority(999);
        return intentFilter;
    }

    /**
     * 是否通信成功
     */
    private boolean mIsCommunicationSucceed = false;
    /**
     * 蓝牙广播接收
     */
    private BroadcastReceiver mBLBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                // 连接上（但还没发送密码）
                case BluetoothLeService.ACTION_GATT_CONNECTED:
                    mIsCommunicationSucceed = true;
                    // 回调
                    mBlStateCallback.onBLCommunicationSucceed();
                    break;
                //断开连接
                case BluetoothLeService.ACTION_GATT_DISCONNECTED:
                    if (isBLOpen()) {
                        return;
                    }
                    mIsCommunicationSucceed = false;
                    mActivity.invalidateOptionsMenu();
                    // 断开回调
                    if (mBlStateCallback != null) {
                        mBlStateCallback.onBLDropped(BluetoothLeService.ACTION_GATT_DISCONNECTED);
                    }
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
                // 不是公司设备
                case BluetoothLeService.ACTION_GATT_DEVICE_ERROR:
                    if (mIsCommunicationSucceed) {
                        mBLService.disconnect();
                        mBlStateCallback.onBLConnectFailure(BluetoothLeService.ACTION_GATT_DEVICE_ERROR);
                    }

                    break;
                // 接收数据(包括蓝牙配对数据)
                case BluetoothLeService.ACTION_DATA_AVAILABLE:
                    try {
                        dealDataAvailable(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case BluetoothLeService.GATT_STATUS_133:
                    if (mIsCommunicationSucceed) {
                        mBlStateCallback.onBLDropped(BluetoothLeService.GATT_STATUS_133);
                    } else {
                        mBlStateCallback.onBLConnectFailure(BluetoothLeService.GATT_STATUS_133);
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
                            if (mBLDeviceSearchCallback != null) {
                                mBLDeviceSearchCallback.bLStateChanged(BluetoothAdapter.STATE_OFF);
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 是否是量子云码的码值
     */
    private boolean mIsCodeValue = false;
    /**
     * 密码回调
     */
    private BLPasswordCallback mBLPasswordCallBack;

    /**
     * 处理蓝牙接收到的数据
     *
     * @param intent
     */
    private void dealDataAvailable(Intent intent) throws Exception {
        if (null == intent) return;
        // 蓝牙返回数据
        byte[] beginArrays = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
        // Mylog.e(TAG,"数据size:"+beginArrays.length);
        if (beginArrays == null || beginArrays.length == 0) {
            return;
        }

        // 字节转为字符
        String bLData = new String(beginArrays);
        if (null == bLData || bLData.length() == 0) {
            return;
        }

        // 去掉bLData中的回车换行符（/r/n）
        bLData = BluetoothUtil.removeEnterChar(bLData);
        if (null == bLData || bLData.length() == 0) {
            return;
        }
        //去掉bLData的第一个和最后一个字符串（即去掉“{” “}”）
        bLData = BluetoothUtil.dealBluetoothString(bLData);
        if (null == bLData || bLData.length() == 0) {
            return;
        }
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
            // 设置密码
        } else if (bLData.contains(BluetoothLeService.BLURTOOTH_PWD_REQUEST)) {
            if (mSelectedBluetoothDevice != null) {
                String address = mSelectedBluetoothDevice.getAddress();
                String pwd = SpUtil.getString(mContext, address, "");
                if (TextUtils.isEmpty(pwd) || pwd == null) {
                    //设置密码
                    if (mBLPasswordCallBack != null) {
                        mBLPasswordCallBack.setPwd(BlueToothManage.this);
                    }
                } else {
                    // 发送密码
                    sendPwdToBLDevice(pwd);
                }
            }

        } else {
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
    private String mDeviceVoltage = null;

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
                String pdwState = responseData[1];
                // 连接成功
                if ("1".equals(pdwState)) {// 密码正确
                    // 保存蓝牙密码
                    if (mBLPasswordCallBack != null) {
                        mBLPasswordCallBack.pwdOk(BlueToothManage.this, mPwd);
                    }

                    mBlStateCallback.onBLPairSucceed();
                    Toast.makeText(mContext, "蓝牙连接成功", Toast.LENGTH_SHORT).show();
                } else if ("0".equals(pdwState)) {// 密码错误
                    mBlStateCallback.onBLConnectFailure(BluetoothLeService.PWD_ERROR);
                    if (mBLPasswordCallBack != null) {
                        mBLPasswordCallBack.pwdError(BlueToothManage.this);
                    }
                    //密码清空
                    saveBluetoothPassword("");
                }
                /* 返回电量 这里应该不需要 */
            case BluetoothLeService.BLURTOOTH_VOLTAGE:

                mDeviceVoltage = BluetoothUtil.getDeviceVoltage(responseData[1]);
                break;
            default:
                break;
        }
    }

    /**
     * 获取蓝牙设备电量
     *
     * @return
     */
    public String getBLVoltage() {
        return mDeviceVoltage;
    }

    /**
     * 获取服务对象
     */
    public BluetoothLeService getBLService() {
        return mBLService;
    }

/************************************************************************** ui 蓝牙密码输入框 **************************************************************************/


    /**
     * 保存蓝牙设备密码
     */
    public void saveBluetoothPassword(String password) {
        String address = mSelectedBluetoothDevice.getAddress();
        SpUtil.putString(mContext, address, password);
    }

}
