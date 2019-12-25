package com.qflbai.lib.net.body;

/**
 * @author: qflbai
 * @CreateDate: 2019/7/23 13:59
 * @Version: 1.0
 * @description:
 */
public class ServerResponseResult {

    /**
     * isSuccess : true
     * code : 1
     * data : {"succeed":true,"code":-1,"data":{"pageNo":1,"pageSize":20,"pageCount":1,"totalRecords":1,"data":[{"id":"73088ac8a47c11e9a630f0def196f706","name":"验电器","number":"ydq00002","size":"10kV","factory":"XX厂家","lasttrialdate":1568908800000,"trialperiod":"半年","nexttrialdate":1568908800000,"invaliddate":1569772800000,"keeper":"无","keepway":"集中","createtime":1563465600000,"operatorcode":"e8f36797d5bd446eb117809a7dd83daf","operatorname":"常智刚","deleteflag":0,"orgunitcode":"050100-3400-04","orgunitname":"赵跃宏/供电服务班/客户服务中心/昆明供电局","state":0,"monthchecktime":"18","lastTrialDateStr":"2019-09-20","nextTrialDateStr":"2019-09-20","invalidDateStr":"2019-09-30","pageNo":0,"pageSize":0}]}}
     * message : 数据查询成功
     */

    private boolean succeed;
    private int code;
    private Object data;
    private String msg;

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
