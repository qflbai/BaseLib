package com.qflbai.lib.net.interceptor;

import com.qflbai.lib.net.body.DownloadResponseBody;
import com.qflbai.lib.net.listener.DownloadListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/3/29.
 * @Description: 下载拦截器
 */

public class DownloadInterceptor implements Interceptor {
    private DownloadListener downloadListener;

    public DownloadInterceptor(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response
                .newBuilder()
                .body(new DownloadResponseBody(response.body(), downloadListener))
                .build();
    }
}
