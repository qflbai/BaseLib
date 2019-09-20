package com.qflbai.lib.base.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.qflbai.lib.base.data.ViewState;
import com.qflbai.lib.base.viewmodel.BaseViewModel;
import com.qflbai.lib.event.TUtil;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;


/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public abstract class AbsLifecycleFragment<T extends BaseViewModel> extends BaseFragment {
    @Inject
    DispatchingAndroidInjector<Object> mAndroidInjector;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    protected T mViewModel;

    @Override
    public void onAttach(Context context) {
        //Dagger.Android Fragment 注入
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void initView(Bundle state) {
        mViewModel = VMProviders(this,  TUtil.getInstance(this, 0));
        if (null != mViewModel) {
            getLifecycle().addObserver(mViewModel);
            dataObserver();
        }
    }


    /**
     * create ViewModelProviders
     *
     * @return ViewModel
     */
    protected <T extends ViewModel> T VMProviders(BaseFragment fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment,mViewModelFactory).get(modelClass);

    }

    protected void dataObserver() {
        mViewModel.mModel.mLoadState.observe(this, viewObserver);
    }


    /**
     * 布局变化
     */
    protected Observer viewObserver = (Observer<ViewState>) viewState -> {
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
                    showEmptyView(true, "数据为空");
                } else {
                    showEmptyView(viewState.isClickable(), message);
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
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mViewModel != null) {
            getLifecycle().removeObserver(mViewModel);
            mViewModel = null;
        }
    }
}
