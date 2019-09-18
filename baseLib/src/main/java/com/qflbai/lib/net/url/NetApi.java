package com.qflbai.lib.net.url;

/**
 * @author WenXian Bai
 * @Date: 2018/3/14.
 * @Description: 网络请求接口 （建议所有网络请求接口地址统一写在这里，便于管理）
 */

public final class NetApi {
    private NetApi() {
    }

    /**
     * 上下文
     */
    private static String mToken;

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

    /**
     * 登陆
     */
    public static final String LOGIN = "a/login";

    /**
     * 退出登录
     */
    public static final String QUIT = "a/logout";

    /**
     * 总部接口地址
     */
    public static class Headquarters {
        /**
         * 获取权限接口
         */
        public static final String APP_USER_LIMIT = "a/sys/user/appUser";
        /**
         * 替换关联
         */
        public static final String RELEVANCE_REPLACE = "a/erp/related/manual/replace";
        /**
         * 剔除关联
         */
        public static final String RELEVANCE_REJECT = "a/erp/related/manual/eliminate";
        /**
         * 解散关联
         */
        public static final String RELEVANCE_DISSOLVE = "a/erp/related/manual/disband";
        /**
         * 获取当前及下级所有经销商
         */
        public static final String ACENCY_ALL_LIST = "a/organization/manager/organizationTree";
        /**
         * 商品登记
         */
        public static final String GOODS_ENTERING = "a/erp/order/stockout";
        /**
         * 获取模板
         */
        public static final String GET_RELEVANCE_TEMPLATE = "a/unit/template/unitTemp/vctp";
        /**
         * 获取模板详情
         */
        public static final String GET_RELEVANCE_TEMPLATE_DETAILS = "a/unit/template/unitTempDel/detail";
        /**
         * 商品关联
         */
        public static final String GOODS_RELEVANCE = "a/erp/related/manual";
        /**
         * 关联入库
         */
        public static final String GOODS_RELEVANCE_ENTER = "a/erp/order/stockin";
        /**
         * 溯源查询
         */
        public static final String TRACEABILITY_QUERY = "a/erp/goodsLog/traceList";

        /**
         * 关联校验子码
         */
        public static final String LOWER_UINT_VERIFY = "a/erp/related/manual/validate/child";
        /**
         * 关联校验父码
         */
        public static final String HEIGHT_UINT_VERIFY = "a/erp/related/manual/validate/parent";

        /**
         * 空包校验
         */
        public static final String EMPTY_PACK_VERIFY = "a/erp/related/manual/validate/empty";

        /**
         * 获取系统参数
         */
        public static final String SYSTEM_PARAM = "a/erp/param/syn";
    }

    /**
     * 总部接口地址
     */
    public static class XiuZhengApp {
        /**
         * 获取权限接口
         */
        public static final String APP_USER_LIMIT = "a/sys/user/appUser";
        /**
         * 工厂出库
         */
        public static final String FACTORY_OUT_WAREHOUSE = "a/erp/order/stockout/factory";
        /**
         * 取消出库
         */
        public static final String CANCEL_OUT_WAREHOUSE = "a/erp/order/stockout/cancel";
        /**
         * 工厂关联
         */
        public static final String FACTORY_RELEVANCE = "a/erp/order/stockin";
        /**
         * 获取经销商
         */
        public static final String AGENCY_LIST = "a/organization/manager/getSelect";
        /**
         * 获取总部组织
         */
        public static final String HQ_ORG = "organization/manager/hq";
        /**
         * 获取当前及下级所有经销商
         */
        public static final String ACENCY_ALL_LIST = "a/organization/manager/organizationTree";

        /**
         * 代理商入库
         */
        public static final String AGENCY_ENTER_WAREHOUSE = "a/erp/order/stockin/dealer";
        /**
         * 代理商出库
         */
        public static final String AGENCY_OUT_WAREHOUSE = "a/erp/order/stockout/dealer";
        /**
         * 总代调拨入库
         */
        public static final String CANNIBALIZE_ENTER_WAREHOUSE = "a/erp/order/transfer/in";
        /**
         * 工厂调拨出库
         */
        public static final String CANNIBALIZE_OUT_WAREHOUSE = "a/erp/order/transfer/out";
        /**
         * 历史查询
         */
        public static final String HISTORY_QUERY = "a/erp/goodsLog";
        /**
         * 获取调货入库订单
         */
        public static final String ALLOCATING_ENTER_ORDER = "a/erp/order/transfer/in";
        /**
         * 获取调货出库订单
         */
        public static final String ALLOCATING_OUT_ORDER = "a/erp/order/transfer/out";
        /**
         * 获取调货出库订单详情
         */
        public static final String ALLOCATING_OUT_ORDER_DETAILS = "a/erp/order/bill";
        /**
         * 调货出库
         */
        public static final String ALLOCATING_OUT_WAREHOUSE = "a/erp/order/transfer/dealer/out";
        /**
         * 调货入库库
         */
        public static final String ALLOCATING_ENTER_WAREHOUSE = "a/erp/order/transfer/dealer/in";

        /**
         * 获取下级数量
         */
        public static final String GET_CHILD_QUANTITY = "a/erp/stock/childQuantity";
        /**
         * 过期货入库
         */
        public static final String OVERDUE_ENTER = "a/erp/order/stockreturn/expire/in";
        /**
         * 过期货出库库
         */
        public static final String OVERDUE_OUT = "a/erp/order/stockreturn/expire/out";
        /**
         * 次品退货
         */
        public static final String DEFECTIVE_RETURNS = "a/erp/order/stockreturn/inferior/out";
        /**
         * 次品入库
         */
        public static final String DEFECTICE_ENTER = "a/erp/order/stockreturn/inferior/in";
        /**
         * 正品转仓
         */
        public static final String GOLDENEYE_SWITCH = "a/erp/stock/expire";
        /**
         * 正品转仓取消
         */
        public static final String goldeneye_switch = "a/erp/stock/expire/cancel";
        /**
         * 取消退货
         */
        public static final String CANCEL_RETURNS = "a/erp/order/stockreturn/out/cancel";
        /**
         * 调货出库取消
         */
        public static final String CANCEL_ALLOCATING_OUT = "a/erp/order/transfer/out/cancel";
        /**
         * 扫码查询
         */
        public static final String SCAN_CODE_QUERY = "a/erp/stock/scan";
        /**
         * 获取上级商品
         */
        public static final String GET_GOODS_INFO = "erp/goods/super";
        /**
         * 获取模板
         */
        public static final String GET_TEMPLATE_UNITTEMPDEL = "a/unit/template/unitTempDel/goodsId";

        /**
         * 次品转仓
         */
        public static final String DEFECTIVE_SWITCH = "a/erp/stock/inferior";

        /**
         * 取消次品转仓
         */
        public static final String CANCEL_DEFECTIVE_SWITCH = "a/erp/stock/inferior/cancel";

        /**
         * 获取仓库
         */
        public static final String GET_WAREHOUSE = "a/erp/warehouse";
        /**
         * 替换关联
         */
        public static final String RELEVANCE_REPLACE = "a/erp/related/manual/replace";
        /**
         * 关联入库替换
         */
        public static final String RELEVANCE_REPLACE_ENTER = "a/erp/stock/replace";

        /**
         * 取消关联
         */
        public static final String RELEVANCE_CANCEL = "a/erp/order/stockin/factory/cancel";

        /**
         * 匹配清单
         */
        public static final String PI_PEI_ORDER = "a/erp/order/transfer/getBill";

        /**
         * 作废激活
         */
        public static final String INVALID_ACTIVATION = "a/erp/record/codesInvalidRecord/upload";

        /**
         * 收货登记
         */
        public static final String RECEIVING_GOODS = "a/erp/order/stockin/register";

        /**
         * 统计
         */
        public static final String TONG_JI = "a/erp/goodsLog/statistics";

        /**
         * 统计详情
         */
        public static final String TONG_JI_XIANG_QING = "a/erp/goodsLog";
    }


    public static class Inspect {
        /**
         * 图片上传
         */
        public static final String IMAGE_UPLOAD = "a/erp/image/";
        /**
         * 稽查上报
         */
        public static final String INSPECT_SUBMIT = "a/erp/inspector/upload";
        /**
         * 物流查询
         */
        public static final String LOGISTICS_QUERY = "a/erp/goodsLog/list";
    }

    /**
     * 总部接口地址
     */
    public static class SDK {
        /**
         * 授权校验
         */
        public static final String AUTHORIZATION_VERIFY = "plugins/checksdk";
    }
}
