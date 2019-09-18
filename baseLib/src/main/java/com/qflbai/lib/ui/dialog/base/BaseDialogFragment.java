package com.qflbai.lib.ui.dialog.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @author WenXian Bai
 * @Date: 2018/3/20.
 * @Description: dialog基类
 */

public class BaseDialogFragment extends DialogFragment {

  /*  @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            //设置窗体背景色透明
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置宽高
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            if (getDialogWidth() > 0) {
                layoutParams.width = getDialogWidth();
            } else {
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            if (getDialogHeight() > 0) {
                layoutParams.height = getDialogHeight();
            } else {
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            //透明度
            layoutParams.dimAmount = getDimAmount();
            //位置
            layoutParams.gravity = getGravity();
            window.setAttributes(layoutParams);
        }
    }*/

    /**
     * dialog对象
     */
    private Dialog mDialog;
    /**
     * 对话框点击空白区域能取消
     */
    protected boolean mCanceledOnTouchOutside = false;
    /**
     * 返回键能取消
     */
    protected boolean mCancelable = true;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDialog = getDialog();
        mDialog.setCancelable(mCancelable);
        mDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        dialog.setCancelable(mCancelable);
        dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 默认弹窗位置为中心
     *
     * @return
     */
    private int getGravity() {
        return Gravity.CENTER;
    }

    /**
     * 默认宽高为包裹内容
     */

    private int getDialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private int getDialogWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private static final float DEFAULT_DIMAMOUNT = 0.2F;

    /**
     * 默认透明度为0.2
     */
    private float getDimAmount() {
        return DEFAULT_DIMAMOUNT;
    }

    /**
     * 窗口特性
     *
     * @param featureId
     */
    public void requestWindowFeature(int featureId) {
        if (mDialog != null) {
            mDialog.requestWindowFeature(featureId);
        }
    }

    /**
     * 移除dialog默认标题
     *
     * @param remove
     */
    public void removeWindowTitle(boolean remove) {
        if (mDialog != null) {
            if (remove) {
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
        }
    }

    /**
     * 设置此对话框是否在触摸窗口时被隐藏
     *
     * @param cancel
     */
    public void setmCanceledOnTouchOutside(boolean cancel) {
            mCanceledOnTouchOutside = cancel;
    }

    /**
     * 当点击返回键是否消失
     *
     * @param flag
     */
    public void setBackKeyCancelable(boolean flag) {
            mCancelable =flag;
    }

}
