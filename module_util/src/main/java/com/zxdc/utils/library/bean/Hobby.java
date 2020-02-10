package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/12/17.
 */

public class Hobby extends BaseBean {

    private List<HobbyBean> data;

    public List<HobbyBean> getData() {
        return data;
    }

    public void setData(List<HobbyBean> data) {
        this.data = data;
    }

    public static class HobbyBean implements Serializable{
        private int id;
        private String img;
        private String name;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
