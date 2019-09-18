package com.qflbai.lib.bluetooth.callback;

import android.bluetooth.BluetoothDevice;

/**
 * ProjectName: SunTechProjects
 * ClassDesc: ******
 * CreateUser: 201704012
 * CreateDate: 2018/3/2 14:55
 * UpdateUser: 201704012
 * UpdateDate:  2018/3/2 14:55
 * UpdateDesc: ******
 */
public interface BLDeviceSearchCallback {
    /**
     * 搜索蓝牙设备
     * @param device
     * @param rssi
     * @param scanRecord
     */
    void bLDeviceSearch(BluetoothDevice device, int rssi, byte[] scanRecord);

    /**
     * 自身蓝牙蓝牙状态
     * @param state
     */
    void bLStateChanged(int state);
}
