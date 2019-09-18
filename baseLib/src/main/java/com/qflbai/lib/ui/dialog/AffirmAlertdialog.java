package com.qflbai.lib.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.qflbai.lib.ui.dialog.base.BaseDialogFragment;
import com.qflbai.lib.ui.dialog.listener.OnClickListener;

/**
 * @author WenXian Bai
 * @Date: 2018/3/21.
 * @Description: 警告对话框
 */

public class AffirmAlertdialog extends BaseDialogFragment {
    protected String tag = "AffirmAlertdialog";
    protected final static String KEY_TITLE = "key_title";
    protected final static String KEY_MESSAGE = "key_message";
    protected final static String KEY_NEGATIVE_BUTTON_LISTENNER = "key_negative_button_listenner";
    protected final static String KEY_POSITIVE_BUTTON_LISTENNER = "key_Positive_Button_Listenner";
    protected final static String KEY_NEUTRAL_BUTTON_LISTENNER = "key_neutral_button_listenner";
    protected final static String KEY_NEGATIVE_BUTTON_TEXT = "key_negative_button_text";
    protected final static String KEY_POSITIVE_BUTTON_TEXT = "key_positive_button_text";
    protected final static String KEY_NEUTRAL_BUTTON_TEXT = "key_neutral_button_text";

    /**
     * dialog标题
     */
    private String mTitle;
    /**
     * dialog内容
     */
    private String mMessage;
    /**
     * 否定键名
     */
    private String mNegativeButtonText;
    /**
     * 确认键名
     */
    private String mPositiveButtonText;
    /**
     * 中间件名
     */
    private String mNeutralButtonText;
    /**
     * 否定回调
     */
    private OnClickListener mNegativeButtonListenner;
    /**
     * 确认回调
     */
    private OnClickListener mPositiveButtonListenner;
    /**
     * 中间回调
     */
    private OnClickListener mNeutralButtonListenner;
    /**
     * dialog样式
     */
    private int mDialogStyle = -1;
    /**
     * 自定义控件
     */
    private View mView;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 状态保存
        if (mTitle != null && !TextUtils.isEmpty(mTitle)) {
            outState.putString(KEY_TITLE, mTitle);
        }
        if (mMessage != null && !TextUtils.isEmpty(mMessage)) {
            outState.putString(KEY_MESSAGE, mMessage);
        }
        if (mNegativeButtonListenner != null) {
            outState.putString(KEY_NEGATIVE_BUTTON_TEXT, mNegativeButtonText);
            outState.putSerializable(KEY_NEGATIVE_BUTTON_LISTENNER, mNegativeButtonListenner);
            //builder.setNegativeButton(mNegativeButtonText, mNegativeButtonListenner);
        }

        if (mPositiveButtonListenner != null) {
            outState.putString(KEY_POSITIVE_BUTTON_TEXT, mPositiveButtonText);
            outState.putSerializable(KEY_POSITIVE_BUTTON_LISTENNER, mPositiveButtonListenner);
            //builder.setPositiveButton(mPositiveButtonText, mPositiveButtonListenner);
        }
        if (mNeutralButtonListenner != null) {
            outState.putString(KEY_NEUTRAL_BUTTON_TEXT, mNeutralButtonText);
            outState.putSerializable(KEY_NEUTRAL_BUTTON_LISTENNER, mNeutralButtonListenner);
            //builder.setNeutralButton(mNeutralButtonText, mNeutralButtonListenner);
        }

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mMessage = savedInstanceState.getString(KEY_MESSAGE);
            mTitle = savedInstanceState.getString(KEY_TITLE);
            mNeutralButtonText = savedInstanceState.getString(KEY_NEUTRAL_BUTTON_TEXT);
            mNegativeButtonText = savedInstanceState.getString(KEY_NEGATIVE_BUTTON_TEXT);
            mPositiveButtonText = savedInstanceState.getString(KEY_POSITIVE_BUTTON_TEXT);
            mNeutralButtonListenner = (OnClickListener) savedInstanceState.getSerializable(KEY_NEUTRAL_BUTTON_LISTENNER);
            mNegativeButtonListenner = (OnClickListener) savedInstanceState.getSerializable(KEY_NEGATIVE_BUTTON_LISTENNER);
            mPositiveButtonListenner = (OnClickListener) savedInstanceState.getSerializable(KEY_POSITIVE_BUTTON_LISTENNER);
        }
        AlertDialog.Builder builder;
        if (mDialogStyle > 0) {
            // 设置dialog样式
            builder = new AlertDialog.Builder(getActivity(), mDialogStyle);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }
        if (mTitle != null && !TextUtils.isEmpty(mTitle)) {
            builder.setTitle(mTitle);
        }
        if (mMessage != null && !TextUtils.isEmpty(mMessage)) {
            builder.setMessage(mMessage);
        }


        if (mNegativeButtonListenner != null) {
            builder.setNegativeButton(mNegativeButtonText, mNegativeButtonListenner);
        }

        if (mPositiveButtonListenner != null) {
            builder.setPositiveButton(mPositiveButtonText, mPositiveButtonListenner);
        }
        if (mNeutralButtonListenner != null) {
            builder.setNeutralButton(mNeutralButtonText, mNeutralButtonListenner);
        }
        if (mView != null) {
            builder.setView(mView);
        }
        return builder.create();
    }


    /**
     * 显示dialog
     *
     * @param fragmentManager
     * @param title           标题
     * @param message         内容
     */
    public void showDialog(FragmentManager fragmentManager, String title, String message) {
        mTitle = title;
        mMessage = message;
        this.show(fragmentManager, tag);
    }

    /**
     * 显示dialog
     *
     * @param fragmentManager
     * @param message         内容
     */
    public void showMessageDialog(FragmentManager fragmentManager, String message) {
        mTitle = "";
        mMessage = message;
        this.show(fragmentManager, tag);
    }


    /**
     * 显示dialog
     *
     * @param fragmentManager
     * @param title           内容
     */
    public void showTitleDialog(FragmentManager fragmentManager, String title) {
        mMessage = "";
        mTitle = title;
        this.show(fragmentManager, tag);
    }

    /**
     * 设置自定义控件
     *
     * @param view
     */
    public void setView(View view) {
        mView = view;
    }

    /**
     * 设置否定监听
     *
     * @param text
     * @param listener
     */
    public void setNegativeButton(String text, OnClickListener listener) {
        mNegativeButtonText = text;
        mNegativeButtonListenner = listener;
    }

    /**
     * 设置否定监听
     *
     * @param text
     * @param listener
     */
    public void setPositiveButton(String text, OnClickListener listener) {
        mPositiveButtonText = text;
        mPositiveButtonListenner = listener;
    }

    /**
     * 设置否定监听
     *
     * @param text
     * @param listener
     */
    public void setNeutralButton(String text, OnClickListener listener) {
        mNeutralButtonText = text;
        mNeutralButtonListenner = listener;
    }

    /**
     * 设置dialog样式
     *
     * @param style
     */
    public void setDialogStyle(int style) {
        mDialogStyle = style;
    }
}
