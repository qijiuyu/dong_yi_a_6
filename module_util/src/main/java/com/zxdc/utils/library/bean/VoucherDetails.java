package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/12/2.
 */

public class VoucherDetails extends BaseBean {

    private VoucherDetailsBean data;

    public VoucherDetailsBean getData() {
        return data;
    }

    public void setData(VoucherDetailsBean data) {
        this.data = data;
    }

    public static class VoucherDetailsBean implements Serializable{
        private int id;
        private String createdate;
        private String spuimg;
        private String spuname;
        private double spuprice;
        private int status;
        private int spuid;
        private String telname;
        private String telphone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getSpuimg() {
            return spuimg;
        }

        public void setSpuimg(String spuimg) {
            this.spuimg = spuimg;
        }

        public String getSpuname() {
            return spuname;
        }

        public void setSpuname(String spuname) {
            this.spuname = spuname;
        }

        public double getSpuprice() {
            return spuprice;
        }

        public void setSpuprice(double spuprice) {
            this.spuprice = spuprice;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSpuid() {
            return spuid;
        }

        public void setSpuid(int spuid) {
            this.spuid = spuid;
        }

        public String getTelname() {
            return telname;
        }

        public void setTelname(String telname) {
            this.telname = telname;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }
    }
}
