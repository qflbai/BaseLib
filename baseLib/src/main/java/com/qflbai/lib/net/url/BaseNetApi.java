package com.qflbai.lib.net.url;

/**
 * @author WenXian Bai
 * @Date: 2018/3/14.
 * @Description: 网络请求接口 （建议所有网络请求接口地址统一写在这里，便于管理）
 */

public class BaseNetApi {
    public BaseNetApi() {
    }

    /**
     * 上下文
     */
    private static String mToken="";
    private static String mJwtRefresh="";

    /**
     * 设置token
     *
     * @param token
     */
    public static void setToken(String token) {
        mToken = token;
    }

    public static String getToken() {
        return mToken;
    }

    public static void setJwtRefresh(String JwtRefresh){
        mJwtRefresh=JwtRefresh;
    }

    public static String getJwtRefresh(){
        return mJwtRefresh;
    }
}
