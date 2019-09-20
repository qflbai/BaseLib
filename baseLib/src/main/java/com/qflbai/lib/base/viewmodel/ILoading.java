package com.qflbai.lib.base.viewmodel;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface ILoading {
    /**
     * 显示页面加载
     */
    void showLayoutLoading();

    /**
     * 隐藏页面加载
     */
    void hideLayoutLoading();
    /**
     * 显示弹窗加载
     */
    void showDialogLoading();

    /**
     * 隐藏弹窗加载
     */
    void hidDialogLoading();

}
