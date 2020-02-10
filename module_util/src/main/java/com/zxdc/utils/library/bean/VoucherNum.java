package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/11/23.
 */

public class VoucherNum extends BaseBean {

    private VoucherNumBean data;

    public VoucherNumBean getData() {
        return data;
    }

    public void setData(VoucherNumBean data) {
        this.data = data;
    }

    public static class VoucherNumBean implements Serializable{
        //待领取数量
        private int dlqcount;
        //待确认数量
        private int dqrcount;
        //已领取数量
        private int ylqcount;
        //已失效数量
        private int ysxcount;

        public int getDlqcount() {
            return dlqcount;
        }

        public void setDlqcount(int dlqcount) {
            this.dlqcount = dlqcount;
        }

        public int getDqrcount() {
            return dqrcount;
        }

        public void setDqrcount(int dqrcount) {
            this.dqrcount = dqrcount;
        }

        public int getYlqcount() {
            return ylqcount;
        }

        public void setYlqcount(int ylqcount) {
            this.ylqcount = ylqcount;
        }

        public int getYsxcount() {
            return ysxcount;
        }

        public void setYsxcount(int ysxcount) {
            this.ysxcount = ysxcount;
        }
    }
}
