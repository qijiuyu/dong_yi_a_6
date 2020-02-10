package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/11/29.
 */

public class CouponsNum extends BaseBean {

    private CouponsNumBean data;

    public CouponsNumBean getData() {
        return data;
    }

    public void setData(CouponsNumBean data) {
        this.data = data;
    }

    public static class CouponsNumBean implements Serializable{
        private int dsycount;
        private int ysxcount;
        private int ysycount;

        public int getDsycount() {
            return dsycount;
        }

        public void setDsycount(int dsycount) {
            this.dsycount = dsycount;
        }

        public int getYsxcount() {
            return ysxcount;
        }

        public void setYsxcount(int ysxcount) {
            this.ysxcount = ysxcount;
        }

        public int getYsycount() {
            return ysycount;
        }

        public void setYsycount(int ysycount) {
            this.ysycount = ysycount;
        }
    }
}
