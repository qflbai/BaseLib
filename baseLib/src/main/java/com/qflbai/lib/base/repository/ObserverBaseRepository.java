package com.qflbai.lib.base.repository;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/11/30.
 * @Description:
 */
public class ObserverBaseRepository extends BaseRepository implements Observer<Response<ResponseBody>> {
    @Override
    public void onSubscribe(Disposable d) {
        addDisposable(d);
    }

    @Override
    public void onNext(Response<ResponseBody> responseBodyResponse) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
