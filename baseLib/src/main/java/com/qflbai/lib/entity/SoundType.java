package com.qflbai.lib.entity;

/**
 * @author WenXian Bai
 * @Date: 2018/9/27.
 * @Description:
 */
public enum SoundType {
    /**
     * 取消失败
     */
    cancel_no,
    /**
     * 取消成功
     */
    cancel_ok,
    /**
     * 入库失败
     */
    enter_no,
    /**
     * 入库成功
     */
    enter_ok,
    /**
     * 出库成功
     */
    out_ok,
    /**
     * 出库失败
     */
    out_no,
    /**
     * 关联成功
     */
    relevance_ok,
    /**
     * 关联失败
     */
    relevance_no,
    /**
     * 转仓成功
     */
    switch_house_ok,
    /**
     * 转仓失败
     */
    switch_house_no,
    /**
     * 收货登记成
     */
    receiving_goods_ok,
    /**
     * 离线收货登记成功
     */
    receiving_goods_offline_ok,
    /**
     * 收货失败
     */
    receiving_no,
}
