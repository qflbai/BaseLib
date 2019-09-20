package com.qflbai.lib.net.clinet;

import com.qflbai.lib.net.interceptor.DownloadInterceptor;
import com.qflbai.lib.net.interceptor.LogInterceptor;
import com.qflbai.lib.net.interceptor.TokenHeaderInterceptor;
import com.qflbai.lib.net.listener.DownloadListener;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description:
 */

public class NetClinet {

    /**
     * 网络读取超时时间
     */
    private static final long READ_TIME_OUT = 3000;
    /*
     * 网络连接超时时间
     */
    private static final long CONNECT_TIME_OUT = 3000;
    /**
     * 网络写入时间
     */
    private static final long WRITE_TIME_OUT = 3000;
    /**
     * 时间单位（分钟）
     */
    private static TimeUnit timeUnit = TimeUnit.SECONDS;

    private NetClinet() {
    }

    /**
     * 适用于所有带token请求
     *
     * @return
     */
    public static synchronized OkHttpClient newInstance() {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new LogInterceptor());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, timeUnit)
                .readTimeout(READ_TIME_OUT, timeUnit)
                .writeTimeout(WRITE_TIME_OUT, timeUnit)
                .addNetworkInterceptor(new TokenHeaderInterceptor())
                .addNetworkInterceptor(logInterceptor)
                .build();

    }


    /**
     * 下载
     *
     * @return
     */
    public static synchronized OkHttpClient getDownloadInstance(DownloadListener downloadListener) {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new LogInterceptor());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, timeUnit)
                .readTimeout(READ_TIME_OUT, timeUnit)
                .writeTimeout(WRITE_TIME_OUT, timeUnit)
                .addInterceptor(new DownloadInterceptor(downloadListener))
                .addNetworkInterceptor(logInterceptor)
                .build();

    }

}
