package com.qflbai.lib.net.rxjava;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.qflbai.lib.base.BaseApplication;
import com.qflbai.lib.base.data.ViewState;
import com.qflbai.lib.net.body.ServerResponseResult;
import com.qflbai.lib.net.callback.DataNetCallback;
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

public class DataNetObserver<T> extends BaseObserver {
    /**
     * 回调接口
     */
    private DataNetCallback<T> mNetCallback;
    /**
     * 上下文
     */
    private Context mContext;
    private MutableLiveData<ViewState> mLiveData;
    private ViewState mViewState;

    public DataNetObserver(DataNetCallback<T> netCallback) {
        mNetCallback = netCallback;
        mContext = BaseApplication.getAPPContext();
    }

    public DataNetObserver(DataNetCallback<T> netCallback, MutableLiveData<ViewState> liveData) {
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
                    String jsonString = response.body().string();
                    try {
                        ServerResponseResult serverResponseResult = JSONObject.parseObject(jsonString, ServerResponseResult.class);
                        T data = (T) serverResponseResult.getData();
                        if (mLiveData != null) {
                            mViewState.setState(ViewState.State.loadingOk);
                            mLiveData.postValue(mViewState);
                        }
                        mNetCallback.onOkResponse(serverResponseResult, data);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    ToastUtil.show(mContext, "IO异常");
                    mNetCallback.onError(e);
                    e.printStackTrace();
                } catch (Exception e) {
                    ToastUtil.show(mContext, "请求异常");
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
        ToastUtil.showCenter(mContext, code + "");
        if (mLiveData != null) {
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
            ToastUtil.showCenter(mContext, "网络连接超时");
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

    }
}
