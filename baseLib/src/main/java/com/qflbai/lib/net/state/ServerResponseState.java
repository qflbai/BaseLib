package com.qflbai.lib.net.state;

/**
 * @author WenXian Bai
 * @Date: 2018/5/15.
 * @Description:服务器响应状态值
 */
public class ServerResponseState {
    /**
     * 成功
     */
    public static final String ST_0 = "0";
    public static final String ST_011001 = "ST011001";
    public static final String ST_011002 = "ST011002";
    public static final String ST_011003 = "ST011003";
    public static final String ST_011004 = "ST011004";
    public static final String ST_011005 = "ST011005";
    public static final String ST_011006 = "ST011006";
    public static final String ST_011007 = "ST011007";
    public static final String ST_011099 = "ST011099";

    public static String getStateMessage(String state) {
        String message = "";
        switch (state) {
            case ST_0:
                message = "登陆成功";
                break;
            case ST_011001:
                message = "账号不存在";
                break;
            case ST_011002:
                message = "密码错误";
                break;
            case ST_011003:
                message = "验证码不正确";
                break;
            case ST_011004:
                message = "账号被锁定";
                break;
            case ST_011005:
                message = "用户在另一地登陆";
                break;
            case ST_011006:
                message = "登陆超时";
                break;
            case ST_011007:
                message = "权限不足";
                break;
            default:
                break;
        }
        return message;
    }
}
