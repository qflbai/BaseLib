/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qflbai.lib.bluetooth.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.qflbai.lib.utils.log.LogUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
public class BluetoothLeService extends Service {
    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;

    public static final String BLURTOOTH_PWD_REQUEST = "passwordrequest";  //蓝牙请求发送密码
    public static final String BLURTOOTH_RESULT = "result";  //蓝牙命令返回的结果标识符
    public static final String BLURTOOTH_DECODE_RESULT = "decoderesult";  //蓝牙解码结果
    public static final String BLURTOOTH_VOLTAGE = "voltage";  //蓝牙命令返回电量标识符
    public static final String BLURTOOTH_PASSWORD = "password";  //蓝牙密码

    /**
     * 连接失败原因:密码错误
     */
    public final static String PWD_ERROR = "com.suntech.bluetooth.pwd_error";
    /**
     * 连接失败原因：未知
     */
    public final static String UNKNOWN_ERROR = "com.suntech.bluetooth.unknown_error";

    /**/
    public final static String DEVICE_ADDRESS = "com.suntech.bluetooth.le.DEVICE_ADDRESS";

    /*连接返回status = 133*/
    public final static String GATT_STATUS_133 = "com.suntech.bluetooth.le.GATT_STATUS_133";

    /*连接成功*/
    public final static String ACTION_GATT_CONNECTED = "com.suntech.bluetooth.le.ACTION_GATT_CONNECTED";
    /*断开连接*/
    public final static String ACTION_GATT_DISCONNECTED = "com.suntech.bluetooth.le.ACTION_GATT_DISCONNECTED";
    /**
     * 掉线(服务器端蓝牙被断开)
     */
    public final static String ACTION_GATT_DROPPED = "com.suntech.bluetooth.le.ACTION_GATT_DROPPED";
    /**
     * 不是公司蓝牙设备
     */
    public final static String ACTION_GATT_DEVICE_ERROR = "com.suntech.bluetooth.le.action_gatt_device_error";
    /*可以通讯*/
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.suntech.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    /*接收到数据*/
    public final static String ACTION_DATA_AVAILABLE = "com.suntech.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA = "com.suntech.bluetooth.le.EXTRA_DATA";

    // ffe2
    public final static UUID UUID_WRITE = UUID
            .fromString("0000ffe2-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_NOTIFY = UUID
            .fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_SERVICE = UUID
            .fromString("0000ffe0-0000-1000-8000-00805f9b34fb");

    public static String ADDRESS = null;
    public final static String MODEL = "Bluetooth";
    public BluetoothGattCharacteristic mNotifyCharacteristic = null;
    public BluetoothGattCharacteristic mWriteCharacteristic = null;


    public boolean WriteValue(String strValue) {
        if (null == mWriteCharacteristic) {

            return WriteGattValue(strValue);
        } else {
            return ffe2WriteValue(strValue);
        }
    }

    public boolean ffe2WriteValue(String strValue) {
        boolean bool = false;
        int sendContentCount = strValue.length();
        final int itemCount = 20;

        LinkedList<String> mlist = new LinkedList<String>();
        if (sendContentCount > itemCount) {
            int itemNum = sendContentCount / itemCount;
            int residue = sendContentCount % itemCount;
            int startIndex = 0;
            int endIndex = 0;
            for (int i = 0; i <= itemNum; i++) {
                endIndex = itemCount * i;
                if (endIndex > 0) {
                    startIndex = endIndex - itemCount;
                    mlist.add(strValue.substring(startIndex, endIndex));
                }
            }
            if (residue > 0) {
                mlist.add(strValue.substring(endIndex, endIndex + residue));
            }
            for (String str : mlist) {
                bool = WriteGattValue(str);
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } else {
            bool = WriteGattValue(strValue);
        }

        return bool;

    }

    public boolean WriteGattValue(String strValue) {

        BluetoothGattCharacteristic characteristic = null;
        if (null != mWriteCharacteristic) {

            characteristic = mWriteCharacteristic;
        } else {
            characteristic = mNotifyCharacteristic;
        }
        characteristic.setWriteType(2);  //ada by corey20170606 解决了蓝牙接收不到消息的问题
        characteristic.setValue(strValue.getBytes());
        // Mylog.e(TAG, "WriteGattValue:"+strValue);
        if (null != mBluetoothGatt) {
            boolean result = mBluetoothGatt.writeCharacteristic(characteristic);
            return result;
        } else {
            return false;
        }
    }

    public static byte[] HexStringToBytes(String paramString) {
        int i = paramString.length() / 2;
        if (paramString.length() % 2 == 1)
            ++i;
        byte[] arrayOfByte = new byte[i];
        int j = 0;
        for (int k = 0; ; k += 2) {
            if (k > -1 + paramString.length())
                return arrayOfByte;
            int l = k + 2;
            if (l > paramString.length())
                l = k + 1;
            arrayOfByte[j] = (byte) (0xFF & Integer.parseInt(paramString.substring(k, l), 16));
            ++j;
        }
    }

    /**
     * 寻找服务
     *
     * @param gatt
     */
    public void findService(BluetoothGatt gatt) {
        BluetoothGattService service = gatt.getService(UUID_SERVICE);
        if (service != null) {
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID_NOTIFY);
            if (characteristic != null) {
                mNotifyCharacteristic = characteristic;
                setCharacteristicNotification(characteristic, true);
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            }

            BluetoothGattCharacteristic writeCharacteristic = service.getCharacteristic(UUID_WRITE);
            if (writeCharacteristic != null) {
                mWriteCharacteristic = writeCharacteristic;
                setCharacteristicNotification(writeCharacteristic, true);
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            }

            if (null == characteristic && null == writeCharacteristic) {
                broadcastUpdate(ACTION_GATT_DEVICE_ERROR, gatt.getDevice().getAddress());
            }
        } else {
            broadcastUpdate(ACTION_GATT_DEVICE_ERROR, gatt.getDevice().getAddress());
        }
    }

    public void findService(List<BluetoothGattService> gattServices) {

        for (BluetoothGattService gattService : gattServices) {

            Log.i(TAG, gattService.getUuid().toString());
            Log.i(TAG, UUID_SERVICE.toString());
            if (gattService.getUuid().toString().equalsIgnoreCase(UUID_SERVICE.toString())) {
                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(UUID_NOTIFY.toString())) {
                        Log.i(TAG, gattCharacteristic.getUuid().toString());
                        Log.i(TAG, UUID_NOTIFY.toString());
                        mNotifyCharacteristic = gattCharacteristic;
                        setCharacteristicNotification(gattCharacteristic, true);
                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                        return;
                    }
                }
            }
        }
    }


    /**
     * 发现连接更改和服务
     */

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (status == BluetoothGatt.GATT_SUCCESS) {

                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    intentAction = ACTION_GATT_CONNECTED;
                    broadcastUpdate(intentAction, gatt.getDevice().getAddress());
                    mIsHavaBlDeviceConnectioned = true;
                    if (null != mBluetoothGatt) {
                        LogUtil.i(TAG, "Attempting to start service discovery:" + mBluetoothGatt.discoverServices());
                    }
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    mIsHavaBlDeviceConnectioned = false;
                    intentAction = ACTION_GATT_DISCONNECTED;
                    closeBluetoothGattFor133Status();
                    broadcastUpdate(intentAction, gatt.getDevice().getAddress());
                }
            } else if (status == 8 || status == 19) {
                if (newState == 0) {
                    intentAction = ACTION_GATT_DROPPED;
                    broadcastUpdate(intentAction, gatt.getDevice().getAddress());
                    mIsHavaBlDeviceConnectioned = false;
                }
            } else {
                //add by corey20170418 连接异常 status = 133
                intentAction = GATT_STATUS_133;
                closeBluetoothGattFor133Status();

                broadcastUpdate(intentAction, gatt.getDevice().getAddress());
                mIsHavaBlDeviceConnectioned = false;
            }

        }

        /**
         * 发现服务，在蓝牙连接的时候会调用
         * @param gatt
         * @param status
         */
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                findService(gatt);
            } else {
                if (mBluetoothGatt.getDevice().getUuids() == null)
                    Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor bd, int status) {
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor bd, int status) {
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int a, int b) {
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int a) {
        }

    };

    /**
     * add by corey20170609 蓝牙连接失败的133情况 关闭BluetoothGatt 释放连接
     */
    private void closeBluetoothGattFor133Status() {

        if (mBluetoothGatt == null) {
            return;
        }

        disconnect();
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    /*add by corey20170417  多传了当前状态的设备的地址  */
    private void broadcastUpdate(final String action, final String address) {

        ADDRESS = address;
        final Intent intent = new Intent(action);
        intent.putExtra("DEVICE_ADDRESS", address);
        sendOrderedBroadcast(intent, null);
    }

    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);

        final byte[] data = characteristic.getValue();
        if (data != null && data.length > 0) {

            intent.putExtra(EXTRA_DATA, data);
        }
        sendOrderedBroadcast(intent, null);
    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        LogUtil.w(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.w(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtil.w(TAG,"onDestroy");
        super.onDestroy();
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     * @return Return true if the connection is initiated successfully. The connection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }


        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
         mBluetoothDevice = device;
        return true;
    }


    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }

        mBluetoothGatt.disconnect();
    }

    /**
     * 在使用一个给定的BLE设备后，应用程序必须调用这个方法来确保资源被正确释放。
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled        If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }

    private boolean mIsHavaBlDeviceConnectioned = false;

    /**
     * 是否有设备连接
     * @return
     */
    public boolean isHavaBlDeviceConnectioned(){
        LogUtil.w(TAG,"isHavaBlDeviceConnectioned"+this.toString());
        return mIsHavaBlDeviceConnectioned;
    }


    private BluetoothDevice mBluetoothDevice;

    /**
     * 获取蓝牙设备
     * @return
     */
    public BluetoothDevice getConnectionedDeviceed(){
        return mBluetoothDevice;
    }
}
