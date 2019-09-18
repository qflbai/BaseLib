package com.qflbai.lib.constant;

/**
 * @author WenXian Bai
 * @Date: 2018/3/26.
 * @Description: 常量值(用于sp key 和 value)
 */

public class ConstantValues {
    /**
     * 引导页是否第一次打开key
     */
    public static final String KEY_GUIDE_ACTIVITY_IS_FIRST_OPEN = "key_guide_activity_is_first_open";

    /**
     * 用户信息
     */
    public static class UserInfo {
        /**
         * 用户id key
         */
        public static final String KEY_USER_ID = "key_user_id";
        /**
         * 登录名 key
         */
        public static final String KEY_USER_LOGIN_NAME = "key_user_login_name";
        /**
         * 用户名称 key
         */
        public static final String KEY_USER_NAME = "key_user_name";
        /**
         * 密码
         */
        public static final String KEY_USER_PWD = "key_user_pwd";
        /**
         * 身份验证，后续请求需要在Cookie中携带 key
         */
        public static final String KEY_USER_SESSIONID = "key_user_sessionid";

        /**
         * 权限类型key
         */
        public static final String KEY_LIMIT_TYPE = "key_limit_type";
        /**
         * 用户信息key
         */
        public static final String KEY_USER_INFO = "key_user_info";
    }

    public static class RelevanInfo {
        public static final String KEY_UNIT_NUM = "key_unit_num";
        public static final String DEFAULT_VALUE_UNIT_NUM = "50";
        /**
         * 关联模板key
         */
        public static final String KEY_TEMPLATE_INFOS = "key_template_infos";
        /**
         * 关联模板信息列表key
         */
        public static final String KEY_TEMPLATE_INFO = "key_template_info";

        public static final String KEY_BATCH = "key_batch";
    }

    /**
     * 收货
     */
    public static class Receiving{
        public static final String KEY_STATISTICS_NUM="key_statistics_num";
        public static final String KEY_STATISTICS_NUM_OFFLINE="key_statistics_num_offline";
    }


    /**
     * 作废激活
     */
    public static class InvalidActivation{
        public static final String KEY_ACTIVITY_OPERATION_TYPE="key_activity_operation_type";
    }

    public static class Traceability {
        /**
         * 溯源信息key
         */
        public static final String KEY_TRACEABILITY_INFO = "key_traceability_info";
        /**
         * 码值
         */
        public static final String KEY_CODE = "key_code";
    }

    /**
     * 意图action
     */
    public static class Action {
        /**
         * 主页 //todo :清单文件必须设，否则报错
         */
        public static final String MAIN_ACTIVITY = "com.suntech.app.action.main.activity";
        /**
         * 登录 //todo 清单文件
         */
        public static final String LOGIN_ACTIVITY = "com.suntech.app.acton.login_activity";
    }

    public static class Activity {
        /**
         * activity
         */
        public static final String KEY_ACTIVITY = "key_activity";
    }

    public static class ARouter{
        /**
         * head 主页
         */
        public static final String head_main_activity = "/head/main";
        /**
         * 扫码溯源
         */
        public static final String head_traceability_activity="/head/traceability";
        /**
         * 登记出入库
         */
        public static final String head_entering_activity="/head/entering";
        /**
         * 关联模本查询
         */
        public static final String head_query_template_activity="/head/query/template";
        /**
         * 关联模本查询
         */
        public static final String head_query_template_activity_1="/head/query/template1";
        /**
         * 异常处理
         */
        public static final String head_relevance_abnormal_activity="/head/relevance/abnormal";

        /**
         * 登录页面
         */
        public static final String kangyi_loging_activity = "/kangyi/loging/activity";
    }
}
