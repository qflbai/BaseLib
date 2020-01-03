package com.qflbai.lib.base.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.qflbai.lib.R;
import com.qflbai.lib.base.LibBaseActivity;
import com.qflbai.lib.ui.dialog.LoadingDialog;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;


/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */

public class BaseActivity extends LibBaseActivity {

    private Bundle mSavedInstanceState;
    private View mRootView;
    private View mLoadingView;
    private View mContentView;
    private RelativeLayout mContainer;
    private View mErrorView;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;

    }

    @Override
    public void setContentView(int layoutResID) {

        mRootView = LayoutInflater.from(mContext).inflate(R.layout.activity_base, null, false);

        mContentView = LayoutInflater.from(mContext).inflate(layoutResID, null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mContentView.setLayoutParams(params);
        mContainer = mRootView.findViewById(R.id.container);
        mContainer.addView(mContentView);

        setContentView(mRootView);
        ButterKnife.bind(this);
        initStatusBar();
        initViews(mSavedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 显示内容
     */
    protected void showContentView() {
        mContentView.setVisibility(View.VISIBLE);
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }

        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 显示加载
     */
    protected void showLoading() {
        mContentView.setVisibility(View.INVISIBLE);
        if (mLoadingView == null) {
            mLoadingView = ((ViewStub) findViewById(R.id.vs_loading)).inflate();
        } else {
            mLoadingView.setVisibility(View.VISIBLE);
        }

        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }

        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
        }
    }

    protected void showLoading(String message) {
        mContentView.setVisibility(View.INVISIBLE);
        if (mLoadingView == null) {
            mLoadingView = ((ViewStub) findViewById(R.id.vs_loading)).inflate();
        } else {
            mLoadingView.setVisibility(View.VISIBLE);
        }

        TextView tvTip = mLoadingView.findViewById(R.id.text_tip);
        tvTip.setText(message);

        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }

        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 显示错误
     */
    protected void showError() {
        if (mErrorView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.vs_error_refresh);
            mErrorView = viewStub.inflate();
            // 点击加载失败布局
            mErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefresh();
                }
            });
        } else {
            mErrorView.setVisibility(View.VISIBLE);
        }

        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }

        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 显示错误
     */
    protected void showError(boolean clickable, String message) {
        if (mErrorView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.vs_error_refresh);
            mErrorView = viewStub.inflate();
            if (clickable) {
                // 点击加载失败布局
                mErrorView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRefresh();
                    }
                });
            }
        } else {
            mErrorView.setVisibility(View.VISIBLE);
        }

        TextView tvTip = mErrorView.findViewById(R.id.text_tip);
        tvTip.setText(message);

        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }

        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 显示弹窗加载
     */
    public void showDialogLoading() {
        try {
            if (mLoadingDialog == null) {
                mLoadingDialog = LoadingDialog.newInstance();
            }

            mLoadingDialog.showLoad(getSupportFragmentManager());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showDialogLoading(String hint) {
        try {
            if (mLoadingDialog == null) {
                mLoadingDialog = LoadingDialog.newInstance();
            }
            mLoadingDialog.showLoad(getSupportFragmentManager(), hint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏弹窗加载
     */
    public void hideDialogLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 初始化views
     *
     * @param savedInstanceState
     */
    public void initViews(Bundle savedInstanceState) {

    }

    public void initData() {

    }

    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }

    /**
     * 状态栏初始化
     */
    protected void initStatusBar() {
        ImmersionBar.with(this)
                .titleBar(getToolbar())
                .statusBarDarkFont(true)
                .init();
    }


    /**
     * 初始化toolbar(在子类中调用)
     *
     * @param title 标题
     */
    protected void initBackToolbar(CharSequence title) {
        Toolbar toolbar = getToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setToolbarTitle(title);
        ImageView ivBack = getIvBack();
        ivBack.setImageResource(R.mipmap.ic_fan_hui);
        LinearLayout llBack = getLlBack();
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinish();
            }
        });
    }

    /**
     * 初始化无返回按钮toolbar
     *
     * @param title 标题
     */
    protected void initNoBackToolbar(CharSequence title) {
        Toolbar toolbar = getToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setToolbarTitle(title);
    }

    /**
     * 初始化带返回图标描述
     *
     * @param title
     */
    protected void initBackToTitleToolbar(CharSequence title) {
        Toolbar toolbar = getToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setToolbarTitle(title);
        setBackTitle("返回");
        ImageView ivBack = getIvBack();
        ivBack.setImageResource(R.mipmap.ic_fan_hui);
        LinearLayout llBack = getLlBack();
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinish();
            }
        });
    }


    protected void initBackToTitleToolbar(CharSequence title, CharSequence backTitle) {
        Toolbar toolbar = getToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setBackTitle(backTitle);
        setToolbarTitle(title);

        ImageView ivBack = getIvBack();
        ivBack.setImageResource(R.mipmap.ic_fan_hui);
        LinearLayout llBack = getLlBack();
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinish();
            }
        });
    }


    /**
     * 设置返回标题
     *
     * @param backTitle
     */
    protected void setBackTitle(CharSequence backTitle) {
        TextView tvBack = getTvBack();
        tvBack.setText(backTitle);
    }

    /**
     * 获取toolbar
     *
     * @return
     */
    protected Toolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }

    /**
     * 获取返回按钮
     *
     * @return
     */
    protected LinearLayout getLlBack() {
        return findViewById(R.id.ll_back);
    }

    protected ImageView getIvBack() {
        return findViewById(R.id.iv_back);
    }

    protected TextView getTvBack() {
        return findViewById(R.id.tv_back_title);
    }

    /**
     * 得到标题 textview
     *
     * @return
     */
    protected TextView getTitleTextView() {
        return findViewById(R.id.tv_title);
    }

    /**
     * 返回副标题
     *
     * @return
     */
    protected TextView getTvSubtitleTitle() {
        return findViewById(R.id.tv_subtitle_title);
    }

    /**
     * 获取副标题image
     *
     * @return
     */
    protected ImageView getIvSubtitle() {
        return findViewById(R.id.iv_subtitle);
    }

    /**
     * 获取副标题image
     *
     * @return
     */
    protected ImageView getIvSubtitle1() {
        return findViewById(R.id.iv_subtitle_1);
    }

    /**
     * 获取副标题image
     *
     * @return
     */
    protected ImageView getIvSubtitle2() {
        return findViewById(R.id.iv_subtitle_1);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setToolbarTitle(CharSequence title) {
        getTitleTextView().setText(title);
    }


    /**
     * 页面跳转
     */
    public void startActivity(Intent intent) {
        // overridePendingTransition(R.anim.zoom_in_avtivity_switchover, R.anim.zoom_out_avtivity_switchover);
        super.startActivity(intent);
    }

    /**
     * 退出登录
     */
    @Override
    protected void quitLogin() {

    }

    /**
     * 页面销毁
     */
    protected void onFinish() {
        finish();
    }

    protected void onExit() {
        onFinish();
        System.exit(0);
    }


}
