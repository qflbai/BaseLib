package com.qflbai.lib.entity;

import java.util.List;

/**
 * @author WenXian Bai
 * @Date: 2018/3/7.
 * @Description:
 */

public class VersionUpdataBean {

    /**
     * total : 12
     * root : [{"fid":6,"forced_update":1,"remark":"","fdate":1512107889000,"fdetail":"版本1.64","fsize":"8","frelease":"1.64","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/inv-hq.apk","fpackagename":"inv-hq.apk","fname":"S4-总部"},{"fid":7,"forced_update":1,"remark":"","fdate":1512131128000,"fdetail":"S8 1.2","fsize":"6","frelease":"1.2","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/QianHaiSell-release.apk","fpackagename":"com.youdao.outpdasell.activity","fname":"S8-经销商"},{"fid":8,"forced_update":1,"remark":"","fdate":1512107815000,"fdetail":"1.0.4","fsize":"4","frelease":"1.0.4","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/sun-fleeing.apk","fpackagename":"com.suntech","fname":"S4-防窜查询"},{"fid":9,"forced_update":1,"remark":"","fdate":1512107820000,"fdetail":"1.0.6","fsize":"6","frelease":"1.0.6","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/sun-pda-hq.apk","fpackagename":"com.suntech","fname":"S4-扫码出库"},{"fid":10,"forced_update":1,"remark":"","fdate":1512131103000,"fdetail":"S8 1.2","fsize":"8","frelease":"1.2","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/QianHaiTotal-release.apk","fpackagename":"com.youdao.outpdatotal.activity","fname":"S8-总部"},{"fid":11,"forced_update":1,"remark":"","fdate":1512131181000,"fdetail":"S8 1.2","fsize":"5","frelease":"1.2","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/QianHaiAnticreep-release.apk","fpackagename":"com.suntech.qianHaiPdaAnt.activity","fname":"S8-扫码出库"},{"fid":12,"forced_update":1,"remark":"","fdate":1512131210000,"fdetail":"S8 1.2","fsize":"4","frelease":"1.2","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/QianHaiPreFleeing-release.apk","fpackagename":"com.suntech.pdaPreFleeing.activity","fname":"S8-防窜查询"},{"fid":13,"forced_update":1,"remark":"","fdate":1512107807000,"fdetail":"1.6.3","fsize":"6","frelease":"1.6.3","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/inv-agency.apk","fpackagename":"com.suntech","fname":"S4-经销商"},{"fid":14,"forced_update":1,"remark":"","fdate":1520303602000,"fdetail":"S8-快捷物理按钮-总部-1.2.4","fsize":"9","frelease":"1.2.4","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/QianHaiTotal-release.apk","fpackagename":"com.youdao.outpdatotal.activity","fname":"S8-快捷物理按钮-总部"},{"fid":15,"forced_update":1,"remark":"","fdate":1520303643000,"fdetail":"S8-快捷物理按钮-经销商-1.2.4","fsize":"7","frelease":"1.2.4","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/QianHaiSell-release.apk","fpackagename":"com.youdao.outpdasell","fname":"S8-快捷物理按钮-经销商"},{"fid":16,"forced_update":1,"remark":"","fdate":1520303684000,"fdetail":"S8-快捷物理按钮-防窜查询-1.2.4","fsize":"5","frelease":"1.2.4","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/QianHaiPreFleeing-release.apk","fpackagename":"com.suntech.pdaPreFleeing.activity","fname":"S8-快捷物理按钮-防窜查询"},{"fid":17,"forced_update":1,"remark":"","fdate":1520303727000,"fdetail":"S8-快捷物理按钮-扫码出库-1.2.4\r\n","fsize":"7","frelease":"1.2.4","ftype":"android","furl":"http://e.sun-tech.cn:80/liangziyunma/QianHaiAnticreep-release.apk","fpackagename":"com.suntech.qianHaiPdaAnt.activity","fname":"S8-快捷物理按钮-扫码出库"}]
     */

    private String total;
    private List<RootBean> root;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RootBean> getRoot() {
        return root;
    }

    public void setRoot(List<RootBean> root) {
        this.root = root;
    }

    public static class RootBean {
        /**
         * fid : 6
         * forced_update : 1
         * remark :
         * fdate : 1512107889000
         * fdetail : 版本1.64
         * fsize : 8
         * frelease : 1.64
         * ftype : android
         * furl : http://e.sun-tech.cn:80/liangziyunma/inv-hq.apk
         * fpackagename : inv-hq.apk
         * fname : S4-总部
         */

        private int fid;
        private int forced_update;
        private String remark;
        private long fdate;
        private String fdetail;
        private String fsize;
        private String frelease;
        private String ftype;
        private String furl;
        private String fpackagename;
        private String fname;

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getForced_update() {
            return forced_update;
        }

        public void setForced_update(int forced_update) {
            this.forced_update = forced_update;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getFdate() {
            return fdate;
        }

        public void setFdate(long fdate) {
            this.fdate = fdate;
        }

        public String getFdetail() {
            return fdetail;
        }

        public void setFdetail(String fdetail) {
            this.fdetail = fdetail;
        }

        public String getFsize() {
            return fsize;
        }

        public void setFsize(String fsize) {
            this.fsize = fsize;
        }

        public String getFrelease() {
            return frelease;
        }

        public void setFrelease(String frelease) {
            this.frelease = frelease;
        }

        public String getFtype() {
            return ftype;
        }

        public void setFtype(String ftype) {
            this.ftype = ftype;
        }

        public String getFurl() {
            return furl;
        }

        public void setFurl(String furl) {
            this.furl = furl;
        }

        public String getFpackagename() {
            return fpackagename;
        }

        public void setFpackagename(String fpackagename) {
            this.fpackagename = fpackagename;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }
    }
}
