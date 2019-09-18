package com.qflbai.lib.net.body;

/**
 * @author WenXian Bai
 * @Date: 2018/5/22.
 * @Description:
 */
public class DownloadInfo {
    /**
     * 文件总大小
     */
    private long totalSize;
    /**
     * 当前文件大小
     */
    private long currentSize;
    /**
     * 当前进度
     */
    private long progress;


    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }
}
