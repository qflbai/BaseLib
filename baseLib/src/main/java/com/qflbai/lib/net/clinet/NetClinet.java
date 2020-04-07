package com.qflbai.lib.net.clinet;

import com.qflbai.lib.net.interceptor.DownloadInterceptor;
import com.qflbai.lib.net.interceptor.LogInterceptor;
import com.qflbai.lib.net.interceptor.TokenHeaderInterceptor;
import com.qflbai.lib.net.listener.DownloadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
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
    private static final long READ_TIME_OUT = 2;
    /*
     * 网络连接超时时间
     */
    private static final long CONNECT_TIME_OUT = 2;
    /**
     * 网络写入时间
     */
    private static final long WRITE_TIME_OUT = 2;
    /**
     * 时间单位（分钟）
     */
    private static TimeUnit timeUnit = TimeUnit.MINUTES;

    private NetClinet() {
    }

    /**
     * Cookie
     */
    public static HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    public static CookieJar cookieJar = new CookieJar() {
        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            cookieStore.put(httpUrl.host(), list);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            List<Cookie> cookies = cookieStore.get(httpUrl.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };

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
                .cookieJar(cookieJar)
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
                .cookieJar(cookieJar)
                .addInterceptor(new DownloadInterceptor(downloadListener))
                // .addNetworkInterceptor(logInterceptor)
                .build();

    }

    public static synchronized OkHttpClient getDownloadInstance( ) {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new LogInterceptor());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, timeUnit)
                .readTimeout(READ_TIME_OUT, timeUnit)
                .writeTimeout(WRITE_TIME_OUT, timeUnit)
                .cookieJar(cookieJar)
                // .addNetworkInterceptor(logInterceptor)
                .build();

    }

}
