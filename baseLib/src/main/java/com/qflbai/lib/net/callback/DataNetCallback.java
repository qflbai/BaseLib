package com.qflbai.lib.net.callback;

import com.qflbai.lib.net.body.ServerResponseResult;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description: 网络请求回调
 */

public interface DataNetCallback<T> extends BaseNetCallback {


    /**
     * 网路请求成功
     *
     * @param dataJson 请求成功json数据
     */
    void onOkResponse(ServerResponseResult serverResponseResult,T dataJson);

    /**
     * 网路请求成功
     *
     * @param dataJson             请求json数据
     * @param serverResponseResult 服务器响应结果
     */
    void onFailResponse(String dataJson, ServerResponseResult serverResponseResult);

}
