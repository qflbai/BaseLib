package com.qflbai.lib.net.body;

import java.util.List;

/**
 * @author: qflbai
 * @CreateDate: 2019/12/21 12:01
 * @Version: 1.0
 * @description:
 */
public class PageServerResponseResult {

    /**
     * succeed : true
     * code : -1
     * data :
     */

    private boolean succeed;
    private int code;
    private String msg;
    private DataBeanX data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * pageNo : 1
         * pageSize : 10
         * pageCount : 7
         * totalRecords : 61
         */

        private int pageNo;
        private int pageSize;
        private int pageCount;
        private int totalRecords;
        private Object data;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
