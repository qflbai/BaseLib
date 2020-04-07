package com.qflbai.lib.net.rxjava;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.qflbai.lib.base.BaseApplication;
import com.qflbai.lib.base.data.ViewState;
import com.qflbai.lib.di.component.AppComponent;
import com.qflbai.lib.net.callback.NetCallback;
import com.qflbai.lib.utils.toast.ToastUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description:
 */

public class NetObserver extends BaseObserver {
    /**
     * 回调接口
     */
    private NetCallback mNetCallback;
    /**
     * 上下文
     */
    private Context mContext;
    private MutableLiveData<ViewState> mLiveData;
    private ViewState mViewState;

    public NetObserver(NetCallback netCallback) {
        mNetCallback = netCallback;
        mContext = BaseApplication.getAPPContext();

    }

    public NetObserver(NetCallback netCallback, MutableLiveData<ViewState> liveData) {
        mNetCallback = netCallback;
        mContext = BaseApplication.getAPPContext();
        mLiveData = liveData;
        mViewState = new ViewState();
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mIsNetRequesting) {
            d.dispose();
        } else {
            mNetCallback.onSubscribe(d);
            //addNetManage(d);
            if (mLiveData != null) {
                mViewState.setState(ViewState.State.loading);
                mLiveData.postValue(mViewState);
            }
        }
        mIsNetRequesting = true;
    }

    @Override
    public void onNext(Response<ResponseBody> response) {
        mIsNetRequesting = false;
        boolean successful = response.isSuccessful();
        if (successful) {
            int code = response.code();
            if (code == 200) {
                try {
                    if (mLiveData != null) {
                        mViewState.setState(ViewState.State.loadingOk);
                        mLiveData.postValue(mViewState);
                    }

                    String jsonString = response.body().string();
                    mNetCallback.onResponse(jsonString);
                } catch (Exception e) {
                    mNetCallback.onError(e);
                    e.printStackTrace();
                }
            } else {
                netError(response);
            }
        } else {
            netError(response);
        }
    }

    /**
     * 网络异常
     *
     * @param response
     */
    private void netError(Response<ResponseBody> response) {
        HttpException httpException = new HttpException(response);
        int code = httpException.code();
        showError(code);
        if (mLiveData != null) {
            mViewState.setState(ViewState.State.netError);
            mViewState.setMessage("加载失败");
            mLiveData.postValue(mViewState);
        }
        mNetCallback.onError(httpException);

    }

    private void showError(int code) {
        switch (code) {
            case 201:
                ToastUtil.show(mContext, "查询失败");
                break;
            case 301:
                ToastUtil.show(mContext, "操作失败");
                break;
            case 441:
                ToastUtil.show(mContext, "登录失败");
                break;
            case 444:

                ComponentName componentName = new ComponentName(mContext,"com.yuanxin.hczzpt.home.LoginActivity");
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(componentName);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);

                ToastUtil.show(mContext, "登录失效");
                break;
            case 449:
                ToastUtil.show(mContext, "禁止登陆");
                break;
            case 701:
            case 700:
                ToastUtil.show(mContext, "禁止操作");
                break;
            default:
                ToastUtil.show(mContext, code + "");
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
        mIsNetRequesting = false;
        if (e instanceof SocketTimeoutException) {
            ToastUtil.show(mContext, "网络连接超时");
        }
        if (mLiveData != null) {
            mViewState.setState(ViewState.State.netError);
            mViewState.setMessage("加载失败");
            mLiveData.postValue(mViewState);
        }
        mNetCallback.onError(e);
    }

    @Override
    public void onComplete() {
        mIsNetRequesting = false;
    }
}
