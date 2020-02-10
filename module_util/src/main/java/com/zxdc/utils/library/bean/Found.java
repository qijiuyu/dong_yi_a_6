package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/12/5.
 */

public class Found extends BaseBean {

    private List<FoundBean> data;

    public List<FoundBean> getData() {
        return data;
    }

    public void setData(List<FoundBean> data) {
        this.data = data;
    }

    public static class FoundBean implements Serializable{
        private int id;
        private String img;
        private String subtitle;
        private String title;

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

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
