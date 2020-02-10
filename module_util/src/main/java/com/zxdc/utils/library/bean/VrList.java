package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/24.
 */

public class VrList extends BaseBean {

    private List<VrBean> data=new ArrayList<>();

    public List<VrBean> getData() {
        return data;
    }

    public void setData(List<VrBean> data) {
        this.data = data;
    }

    public static class VrBean implements Serializable{
        private int id;
        private int caseid;
        private String img;
        private String name;
        private String detailurl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCaseid() {
            return caseid;
        }

        public void setCaseid(int caseid) {
            this.caseid = caseid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetailurl() {
            return detailurl;
        }

        public void setDetailurl(String detailurl) {
            this.detailurl = detailurl;
        }
    }
}
