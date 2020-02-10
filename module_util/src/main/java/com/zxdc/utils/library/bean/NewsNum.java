package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/12/3.
 */

public class NewsNum extends BaseBean{

    private NewsNumBean data;

    public NewsNumBean getData() {
        return data;
    }

    public void setData(NewsNumBean data) {
        this.data = data;
    }

    public static class NewsNumBean implements Serializable{
        private int  dtcount;
        private int ggcount;
        private int hdcount;

        public int getDtcount() {
            return dtcount;
        }

        public void setDtcount(int dtcount) {
            this.dtcount = dtcount;
        }

        public int getGgcount() {
            return ggcount;
        }

        public void setGgcount(int ggcount) {
            this.ggcount = ggcount;
        }

        public int getHdcount() {
            return hdcount;
        }

        public void setHdcount(int hdcount) {
            this.hdcount = hdcount;
        }
    }
}
