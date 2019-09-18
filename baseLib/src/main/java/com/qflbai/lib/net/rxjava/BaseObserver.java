package com.qflbai.lib.net.rxjava;


import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/5/27.
 * @Description:
 */
public abstract class BaseObserver implements Observer<Response<ResponseBody>> {
    /**
     * 是否正在网络请求()
     */
    protected boolean mIsNetRequesting = false;
    /**
     * 网络请求网阀
     */
    protected CompositeDisposable disposables = new CompositeDisposable();

    public void addNetManage(Disposable disposable) {
        disposables.add(disposable);
    }

    /**
     * 关闭所有网络请求
     */
    public void closeAllNet() {
        mIsNetRequesting = false;
        disposables.clear();
    }

    /**
     * 关闭当前
     */
    public void closeNet(Disposable disposable) {
        mIsNetRequesting = false;
        disposables.remove(disposable);
    }

}
