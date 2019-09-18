package com.qflbai.lib.net.callback;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description: 网络请求回调
 */

public interface NetCallback extends BaseNetCallback{

    /**
     * 网路请求成功
     *
     * @param dataJson 请求成功json数据
     */
    void onResponse(String dataJson);

}
