package com.qflbai.lib.utils.toast;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description: Toast
 */

public class ToastUtil {
 /*   private static Toast mToast;
    private static Toast mToastView;

    *//**
     * 正常显示
     *
     * @param context
     * @param text
     *//*
    public static void show(Context context, String text) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.show();
    }

    *//**
     * 长时间显示
     *//*
    public static void showLong(Context context, String text) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.show();
    }


    *//**
     * 中间显示
     *//*
    public static void showCenter(Context context, String text) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    *//**
     * text显示
     *//*
    public static void showCenterView(Context context, String text) {
        if (mToastView == null) {
            mToastView = new Toast(context);
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.toast_itme_text_view, null);
        TextView textView = inflate.findViewById(R.id.tv_toast_show);
        textView.setText(text);
        mToastView.setDuration(Toast.LENGTH_LONG);
        mToastView.setGravity(Gravity.CENTER, 0, 0);
        mToastView.setView(inflate);
        mToastView.show();
    }

    *//**
     * text显示
     *//*
    public static void showView(Context context, String text) {
        if (mToastView == null) {
            mToastView = new Toast(context);
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.toast_itme_text_view, null);
        TextView textView = inflate.findViewById(R.id.tv_toast_show);
        textView.setText(text);
        mToastView.setDuration(Toast.LENGTH_LONG);
        mToastView.setView(inflate);
        mToastView.show();
    }

    *//**
     * 取消Toast显示
     *//*
    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
        if (mToastView != null) {
            mToastView.cancel();
        }
    }*/
}
