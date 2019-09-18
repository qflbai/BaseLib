package com.qflbai.lib;


/**
 * @author WenXian Bai
 * @Date: 2018/3/15.
 * @Description: 系统环境配置
 */

public final class LibBuildConfig {

    /**
     * 获取是否是debug模式
     *
     * @return
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
        //  return false;
    }
}
