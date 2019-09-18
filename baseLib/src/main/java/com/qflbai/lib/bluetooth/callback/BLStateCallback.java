package com.qflbai.lib.bluetooth.callback;

/**
 * ProjectName: SunTechProjects
 * ClassDesc: ******
 * CreateUser: 201704012
 * CreateDate: 2018/3/2 16:49
 * UpdateUser: 201704012
 * UpdateDate:  2018/3/2 16:49
 * UpdateDesc: ******
 */
public interface BLStateCallback {
    /**
     * 服务器蓝牙掉线
     * @param offCause 断开原因
     */
    void onBLDropped(String offCause);

    /**
     * 配对成功
     */
    void onBLPairSucceed();

    /**
     * 通信成功（密码验证通过）
     */
    void onBLCommunicationSucceed();

    /**
     * 连接失败
     * @param errorCause 失败原因
     */
    void onBLConnectFailure(String errorCause);

    /**
     * 扫码结果
     * @param codeValue 码值
     */
    void onBLScanCodeResult(String codeValue);
}
