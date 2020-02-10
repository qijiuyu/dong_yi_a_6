package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/29.
 */

public class Coupons extends BaseBean {

    private List<CouponsBean> data=new ArrayList<>();

    public List<CouponsBean> getData() {
        return data;
    }

    public void setData(List<CouponsBean> data) {
        this.data = data;
    }

    public static class CouponsBean implements Serializable{
        private int id;
        private String enddate;
        private String getdate;
        private String imgurl;
        private String num;
        private String outdate;
        private String remark;
        private String startdate;
        private int status;
        private String usedate;
        private String useremark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getGetdate() {
            return getdate;
        }

        public void setGetdate(String getdate) {
            this.getdate = getdate;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getOutdate() {
            return outdate;
        }

        public void setOutdate(String outdate) {
            this.outdate = outdate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUsedate() {
            return usedate;
        }

        public void setUsedate(String usedate) {
            this.usedate = usedate;
        }

        public String getUseremark() {
            return useremark;
        }

        public void setUseremark(String useremark) {
            this.useremark = useremark;
        }
    }
}
