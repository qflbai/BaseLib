package com.qflbai.lib.net.rxjava;

import android.content.Context;

import com.qflbai.lib.net.callback.NetCallback;

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

    public NetObserver(Context context, NetCallback netCallback) {
        mNetCallback = netCallback;
        mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mIsNetRequesting) {
            d.dispose();
        } else {
            mNetCallback.onSubscribe(d);
            addNetManage(d);
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
                    mNetCallback.onResponse(jsonString);
                } catch (IOException e) {
                    //ToastUtil.show(mContext, "IO异常");
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
        if (code == 401) {
            //ToastUtil.show(mContext, code + "登陆超时...");

        } else {
           // ToastUtil.show(mContext, code + "错误");
            mNetCallback.onError(httpException);
        }
    }

    @Override
    public void onError(Throwable e) {
        mIsNetRequesting = false;
        if (e instanceof SocketTimeoutException) {
            //ToastUtil.show(mContext, "网络连接超时");
        }
        mNetCallback.onError(e);
    }

    @Override
    public void onComplete() {
        mIsNetRequesting = false;
    }
}
