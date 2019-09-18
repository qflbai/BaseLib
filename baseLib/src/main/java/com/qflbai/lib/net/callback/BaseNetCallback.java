package com.qflbai.lib.net.callback;

import io.reactivex.disposables.Disposable;

/**
 * @author WenXian Bai
 * @Date: 2018/5/27.
 * @Description: 网络回调
 */
public interface BaseNetCallback {
    /**
     * 开始网络请求（网络请求没开始）
     *
     * @param disposable
     */
    void onSubscribe(Disposable disposable);

    /**
     * 网络请求失败
     *
     * @param e
     */
    void onError(Throwable e);
}
