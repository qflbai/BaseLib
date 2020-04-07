package com.qflbai.lib.net.interceptor;

import com.qflbai.lib.net.url.BaseNetApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/3/14.
 * @Description: 添加token请求头
 */

public class TokenHeaderInterceptor implements Interceptor {
    public static final String COKIE_VALUE_PREFIX = "suntech.session.id=";

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = BaseNetApi.getToken();
        Request originalRequest = chain.request();
        // get new request, add request header
        Request updateRequest = originalRequest.newBuilder()
                .header("Jwt-Token", token)
                .addHeader("Jwt-Refresh", BaseNetApi.getJwtRefresh())
                .build();
        return chain.proceed(updateRequest);
    }
}
