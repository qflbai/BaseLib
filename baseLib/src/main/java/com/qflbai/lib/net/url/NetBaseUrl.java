package com.qflbai.lib.net.url;


import com.qflbai.lib.LibBuildConfig;

/**
 * @author WenXian Bai
 * @Date: 2018/3/14.
 * @Description: 网络请求服务器地址
 * url格式：protocol :// hostname[:port] / path /
 */

public class NetBaseUrl {
    /**
     * http协议
     */
    private static final String URL_HTTP = "http://";
    /**
     * https协议
     */
    private static final String URL_HTTPS = "https://";
    /**
     * 协议
     */
    public static final String URL_PROTOCOL = URL_HTTP;

    /**
     * 端口号
     */
    private static final String URL_PORT = ":80";

    /**
     * 根路径
     */
    private static final String URL_BASE_PATH = "";

    /**
     * 路径
     */
    private static final String URL_PATH = URL_BASE_PATH;


    /**
     * debug模式主机地址
     */
    private static final String DEBUG_URL_IP = URL_PATH;

    /**
     * 主机名
     */
    private static final String URL_HOSTNAME = URL_PATH;
    /**
     * debug模式下主机名
     */
    private static final String DEBUG_URL_HOSTAME = DEBUG_URL_IP;


    /**
     * 请求基础路径
     */
    private static String BASE_URL = URL_HOSTNAME;

    /**
     * debug模式路径
     */
    private static String DEBUG_BASE_URL = DEBUG_URL_HOSTAME;

    /**
     * 获取网络请求地址
     *
     * @return
     */
    public static String getBaseUrl() {
        return LibBuildConfig.isDebug() ? DEBUG_BASE_URL : BASE_URL;
        // return BASE_URL;
    }

    /**
     * 设置网络请求地址
     */
    public static void setBaseURL(String baseUrl) {
        String tag = "/";
        String substring = baseUrl.substring(baseUrl.length() - 1, baseUrl.length());
        if (!substring.equals(tag)) {
            baseUrl = baseUrl + tag;
        }
        BASE_URL = baseUrl;
        DEBUG_BASE_URL = baseUrl;
    }
}
