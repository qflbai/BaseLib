package com.qflbai.lib.bluetooth.callback;

import com.qflbai.lib.bluetooth.BlueToothManage;

/**
 * @author WenXian Bai
 * @Date: 2018/10/23.
 * @Description:
 */
public interface BLPasswordCallback {
    /**
     * 密码正确
     */
    void pwdOk(BlueToothManage blueToothManage, String pwd);

    /**
     * 密码错误
     */
    void pwdError(BlueToothManage blueToothManage);

    /**
     * 设置密码
     */
    void setPwd(BlueToothManage blueToothManage);
}
