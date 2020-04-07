package com.qflbai.lib.net;

import com.qflbai.lib.net.clinet.NetClinet;
import com.qflbai.lib.net.listener.DownloadListener;
import com.qflbai.lib.net.retrofit.RetrofitService;
import com.qflbai.lib.net.retrofit.converter.FastJsonConverterFactory;
import com.qflbai.lib.net.url.NetBaseUrl;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description:
 */

public class RetrofitManage {
    /**
     * 一般retrofit
     */
    private Retrofit retrofit;


    /**
     * 下载retrorfit
     */
    private Retrofit downLoadRetrofit;


    private RetrofitManage() {
    }

    public static RetrofitManage newInstance() {
        return new RetrofitManage();
    }

    /**
     * 获取Retrofit
     *
     * @param baseUrl
     * @return
     */
    private Retrofit createRetrofit(String baseUrl) {
        if (retrofit == null) {
            synchronized (RetrofitManage.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(NetClinet.newInstance())
                            .addConverterFactory(FastJsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     * 重置baseUrl
     *
     * @param baseUrl
     * @return
     */
    private Retrofit resetBaseRetrofit(String baseUrl) {
        return retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(NetClinet.newInstance())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    /**
     * 默认使用RetrofitService
     */
    public RetrofitService createService() {
        return createService(RetrofitService.class);
    }

    /**
     * 获取RetrofitService
     *
     * @param baseUrl ip地址
     * @return
     */
    public RetrofitService createService(String baseUrl) {
        return createRetrofit(baseUrl).create(RetrofitService.class);
    }

    /**
     * 获取RetrofitService
     *
     * @return
     */
    public <T> T createService(Class<T> service) {
        // 获取服务器地址
        String baseUrl = NetBaseUrl.getBaseUrl();
        return (T) createRetrofit(baseUrl).create(service);
    }

    public <T> T createService(Class<T> service, String baseUrl) {
        // 获取服务器地址
        return (T) resetBaseRetrofit(baseUrl).create(service);
    }


    /**
     * 获取Retrofit(下载)
     *
     * @param baseUrl
     * @return
     */
    private Retrofit createDownloadRetrofit(String baseUrl, DownloadListener downloadListener) {
        return downLoadRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(NetClinet.getDownloadInstance(downloadListener))
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    private Retrofit createDownloadRetrofit(String baseUrl) {
        return downLoadRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(NetClinet.getDownloadInstance())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public RetrofitService createDownloadService(String baseUrl) {
        return createDownloadRetrofit(baseUrl).create(RetrofitService.class);
    }

    public RetrofitService createDownloadService() {
        // 获取服务器地址
        String baseUrl = NetBaseUrl.getBaseUrl();
        return createDownloadRetrofit(baseUrl).create(RetrofitService.class);
    }

    /**
     * 获取下载RetrofitService(用于下载)
     *
     * @return
     */
    public RetrofitService createDownloadService(String baseUrl, DownloadListener downloadListener) {
        return createDownloadRetrofit(baseUrl, downloadListener).create(RetrofitService.class);
    }

    /**
     * 获取下载RetrofitService(用于下载)
     *
     * @return
     */
    public RetrofitService createDownloadService(DownloadListener downloadListener) {
        // 获取服务器地址
        String baseUrl = NetBaseUrl.getBaseUrl();
        return createDownloadRetrofit(baseUrl, downloadListener).create(RetrofitService.class);
    }

}
