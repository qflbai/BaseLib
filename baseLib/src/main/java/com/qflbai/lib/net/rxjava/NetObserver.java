package com.qflbai.lib.net.rxjava;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.qflbai.lib.base.BaseApplication;
import com.qflbai.lib.base.data.ViewState;
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
    private  MutableLiveData<ViewState> mLiveData;
    private  ViewState mViewState;

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
            if(mLiveData!=null){
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
                    if(mLiveData!=null){
                        mViewState.setState(ViewState.State.loadingOk);
                        mLiveData.postValue(mViewState);
                    }

                    String jsonString = response.body().string();
                    mNetCallback.onResponse(jsonString);
                } catch (IOException e) {
                    ToastUtil.show(mContext, "IO异常");
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
        ToastUtil.show(mContext, code + "");
        if(mLiveData!=null){
            mViewState.setState(ViewState.State.netError);
            mViewState.setMessage("加载失败");
            mLiveData.postValue(mViewState);
        }
        mNetCallback.onError(httpException);

    }

    @Override
    public void onError(Throwable e) {
        mIsNetRequesting = false;
        if (e instanceof SocketTimeoutException) {
            ToastUtil.show(mContext, "网络连接超时");
        }
        if(mLiveData!=null){
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
