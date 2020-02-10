package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/12/6.
 */

public class Pinzhi extends BaseBean {

    private List<PinzhiBean> data;

    public List<PinzhiBean> getData() {
        return data;
    }

    public void setData(List<PinzhiBean> data) {
        this.data = data;
    }

    public static class PinzhiBean implements Serializable{
        private int id;
        private String img;
        private String title;
        private String subtitle;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }
    }
}
