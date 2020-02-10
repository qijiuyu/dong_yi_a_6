package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/24.
 */

public class GiftData extends BaseBean {

    private List<GiftDataBean> data=new ArrayList<>();

    public List<GiftDataBean> getData() {
        return data;
    }

    public void setData(List<GiftDataBean> data) {
        this.data = data;
    }

    public static class GiftDataBean implements Serializable{
        private int id;
        private String img;
        private String name;
        private double price;

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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
