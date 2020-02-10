package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/24.
 */

public class Voucher extends BaseBean {


    private List<VoucherBean> data=new ArrayList<>();

    public List<VoucherBean> getData() {
        return data;
    }

    public void setData(List<VoucherBean> data) {
        this.data = data;
    }

    public static class VoucherBean implements Serializable{
        private int id;
        private String createdate;
        private String spuimg;
        private String spuname;
        private double spuprice;
        private int status;

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
    }
}
