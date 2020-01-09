package com.qflbai.lib.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.qflbai.lib.R;
import com.qflbai.lib.ui.dialog.base.BaseDialogFragment;

/**
 * @author WenXian Bai
 * @Date: 2018/3/21.
 * @Description: 加载
 */

public class LoadingDialog extends BaseDialogFragment {
    /**
     * Bundel key
     */
    private static final String TV_CONTENT = "tv_content";
    /**
     * 内容显示textview
     */
    //private @BindView(R.id.tv_message) TextView mTvMessage;
    private String mMessage;

    private static LoadingDialog mLoadingDialog;
    /**
     * 是否显示
     */
    private boolean mIsShow = false;

    public static LoadingDialog newInstance() {

        mLoadingDialog = new LoadingDialog();
        //Bundle args = new Bundle();

        return mLoadingDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View v = inflater.inflate(R.layout.lib_dialog_loading_full_screen, null);
        TextView tvMessage = v.findViewById(R.id.tv_message);
        if (mMessage != null) {
            tvMessage.setText("" + mMessage);
        }
        return v;
    }

    /**
     * 显示加载
     *
     * @param message
     */
    public void showLoad(FragmentManager fragmentManager, String message) {
        mIsShow = true;
        fragmentManager.executePendingTransactions();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (!this.isAdded() && fragmentManager.findFragmentByTag(this.toString()) == null) {

            fragmentTransaction.add(this, this.toString());
        } else {
            //在每个add事务前增加一个remove事务，防止连续的add
            /*fragmentManager.beginTransaction().remove(this).commit();
            fragmentTransaction.add(this, this.toString());*/
            fragmentTransaction.show(this);
        }
        fragmentTransaction.commitAllowingStateLoss();


        mMessage = message;
    }

    public void showLoad(FragmentManager fragmentManager) {
        mIsShow = true;
        mMessage = "";
        fragmentManager.executePendingTransactions();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!this.isAdded() && fragmentManager.findFragmentByTag(this.toString()) == null) {
            fragmentTransaction.add(this, this.toString());
        } else {
            //在每个add事务前增加一个remove事务，防止连续的add
            // fragmentManager.beginTransaction().remove(this).commit();
            //fragmentTransaction.add(mLoadingDialog, mLoadingDialog.toString());
            fragmentTransaction.show(this);
        }
        fragmentTransaction.commitAllowingStateLoss();

    }


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            manager.beginTransaction().remove(this).commitAllowingStateLoss();
            super.show(manager, tag);
        } catch (Exception e) {
            //同一实例使用不同的tag会异常,这里捕获一下
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        if (mIsShow) {
            super.dismiss();
            mIsShow = false;

        }
    }
}
