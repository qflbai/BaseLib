package com.qflbai.lib.net.listener;

import com.qflbai.lib.net.body.DownloadInfo;

import java.io.File;

/**
 * @author WenXian Bai
 * @Date: 2018/3/29.
 * @Description: 下载监听
 */

public interface DownloadListener {
    /**
     * 开始下载
     */
    void onStarted();

    /**
     * 下载进度
     *
     * @param downloadInfo 下载文件信息
     */
    void onLoading(DownloadInfo downloadInfo);

    /**
     * 下载成功
     *
     * @param result
     */
    void onSuccess(File result);

    /**
     * 下载失败
     *
     * @param ex
     */
    void onFail(Throwable ex);
}
