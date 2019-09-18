package com.qflbai.lib.utils.lacation;

import com.amap.api.location.AMapLocation;

/**
 * @author: qflbai
 * @CreateDate: 2019/8/14 0014 9:36
 * @Version: 1.0
 * @description:
 */
public interface LacationCallback {
    /**
     * 开始定位
     */
    void onStart();

    /**
     * 定位成功
     * @param aMapLocation
     */
    void onOk(AMapLocation aMapLocation);

    /**
     * 定位失败
     * @param aMapLocation
     */
    void onError(AMapLocation aMapLocation);
}
