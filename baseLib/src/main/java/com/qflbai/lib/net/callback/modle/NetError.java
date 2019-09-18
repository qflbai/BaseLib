package com.qflbai.lib.net.callback.modle;

/**
 * @author WenXian Bai
 * @Date: 2018/9/11.
 * @Description:
 */
public class NetError {
    /**
     * 异常
     */
    private Throwable e;
    /**
     * 状态码
     */
    private  int httpCode;
    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 服务业务错误信息
     * @return
     */
    private String serverMeassea;

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getServerMeassea() {
        return serverMeassea;
    }

    public void setServerMeassea(String serverMeassea) {
        this.serverMeassea = serverMeassea;
    }
}
