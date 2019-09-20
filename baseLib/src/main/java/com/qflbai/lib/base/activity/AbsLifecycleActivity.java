package com.qflbai.lib.base.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.qflbai.lib.base.data.ViewState;
import com.qflbai.lib.base.viewmodel.BaseViewModel;
import com.qflbai.lib.event.TUtil;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;


/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public class AbsLifecycleActivity<T extends BaseViewModel> extends BaseActivity implements   HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> mAndroidInjector;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    protected T mViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewModel = VMProviders(this,mViewModelFactory,  TUtil.getInstance(this, 0));
        dataObserver();
    }


    protected <T extends BaseViewModel> T VMProviders(AppCompatActivity appCompatActivity, ViewModelProvider.Factory factory,@NonNull Class modelClass) {
        return (T) ViewModelProviders.of(appCompatActivity,factory).get(modelClass);
    }

    protected void dataObserver() {
        if (mViewModel != null) {
            mViewModel.mModel.mLoadState.observe(this, viewObserver);
        }
    }


    /**
     * 布局变化
     */
    protected Observer<ViewState> viewObserver = new Observer<ViewState>() {
        @Override
        public void onChanged(@Nullable ViewState viewState) {
            if(viewState==null){
                return;
            }
            ViewState.State state = viewState.getState();
            String message = viewState.getMessage();
            switch (state) {
                case loading:
                    if (message == null) {
                        showLoading();
                    } else {
                        showLoading(message);
                    }
                    break;
                case loadingOk:
                    showContentView();
                    break;
                case noData:
                    if (message == null) {
                        showError(true, "数据为空");
                    } else {
                        showError(viewState.isClickable(), message);
                    }
                    break;
                case netError:
                    if (message == null) {
                        showError(true, "网络错误");
                    } else {
                        showError(viewState.isClickable(), message);
                    }
                    break;
                default:
                    showContentView();
                    break;
            }
        }
    };

    @Override
    public AndroidInjector<Object> androidInjector() {
        return mAndroidInjector;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.mModel.unDisposable();
        }
    }
}
