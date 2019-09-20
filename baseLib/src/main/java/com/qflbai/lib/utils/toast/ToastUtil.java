package com.qflbai.lib.utils.toast;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.qflbai.lib.R;


/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description: Toast
 */

public class ToastUtil {

    /**
     * 正常显示
     *
     * @param context
     * @param text
     */
    public static void show(Context context, String text) {
        if (context == null || text == null) {
            return;
        }
        DToast.make(context)
                .setView(View.inflate(context, R.layout.layout_toast, null))
                .setText(R.id.toast_content, text)
                .setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100)
                .show();
    }


    /**
     * 中间显示 (显示2秒)
     */
    public static void showCenter(Context context, String text) {
        if (context == null || text == null) {
            return;
        }

        //通过setView()设置自定义的Toast布局
        DToast.make(context)
                .setView(View.inflate(context, R.layout.layout_toast, null))
                .setText(R.id.toast_content, text)
                .setGravity(Gravity.CENTER, 0, 0)
                .showLong();
    }



    /**
     * 取消Toast显示
     */
    public static void cancel() {
        try {
            DToast.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 取消activity toast
     *
     * @param activity
     */
    public static void cancelActivityToast(Activity activity) {
        try {
            DToast.cancelActivityToast(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消所有
     *
     * @param activity
     */
    public static void cancelALl(Activity activity) {
        cancelActivityToast(activity);
        cancel();
    }

}
