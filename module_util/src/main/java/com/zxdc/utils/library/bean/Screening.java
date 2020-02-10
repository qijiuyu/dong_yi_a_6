package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/23.
 */

public class Screening extends BaseBean {

    private List<ScreeningBean> data=new ArrayList<>();

    public List<ScreeningBean> getData() {
        return data;
    }

    public void setData(List<ScreeningBean> data) {
        this.data = data;
    }

    public static class ScreeningBean implements Serializable{
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
