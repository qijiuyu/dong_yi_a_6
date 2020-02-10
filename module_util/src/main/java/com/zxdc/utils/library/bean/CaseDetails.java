package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/12/9.
 */

public class CaseDetails extends BaseBean {

    private CaseBean data;

    public CaseBean getData() {
        return data;
    }

    public void setData(CaseBean data) {
        this.data = data;
    }

    public static class CaseBean implements Serializable{
        private String name;
        private String img;
        private String dstyle;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDstyle() {
            return dstyle;
        }

        public void setDstyle(String dstyle) {
            this.dstyle = dstyle;
        }
    }
}
