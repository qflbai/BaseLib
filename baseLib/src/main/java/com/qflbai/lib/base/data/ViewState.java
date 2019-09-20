package com.qflbai.lib.base.data;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/18 0018 14:36
 * @Version: 1.0
 * @description:
 */
public class ViewState {
    private String message;
    private State state;
    private boolean clickable;

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

   public enum State{
        /**
         * 网络错误
         */
        netError,
        /**
         * 没有数据
         */
        noData,
        /**
         * 加载成功
         */
        loadingOk,
        /**
         * 正在加载
         */
        loading,

    }
}
