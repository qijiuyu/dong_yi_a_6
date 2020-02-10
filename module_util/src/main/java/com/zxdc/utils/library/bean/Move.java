package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/11/30.
 */

public class Move extends BaseBean {

    private MoveBean data;

    public MoveBean getData() {
        return data;
    }

    public void setData(MoveBean data) {
        this.data = data;
    }

    public static class MoveBean implements Serializable{
        private int id;
        private String img;
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
