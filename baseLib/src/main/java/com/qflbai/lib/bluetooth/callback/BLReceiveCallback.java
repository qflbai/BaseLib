package com.qflbai.lib.bluetooth.callback;

/**
 * ProjectName: SunTechProjects
 * ClassDesc: ******
 * CreateUser: 201704012
 * CreateDate: 2018/3/7 17:57
 * UpdateUser: 201704012
 * UpdateDate:  2018/3/7 17:57
 * UpdateDesc: ******
 * 蓝牙连接成功后的会滴
 */
public interface BLReceiveCallback {
    /**
     * 服务器蓝牙掉线
     * @param offCause 断开原因
     */
    void onBLDropped(String offCause);

    /**
     * 本地蓝牙状态改变(如关闭蓝牙)
     * @param state
     */
    void onLocalBLStateChange(int state);

    /**
     * 扫码结果
     * @param codeValue 码值
     */
    void onBLScanCodeResult(String codeValue);
}
