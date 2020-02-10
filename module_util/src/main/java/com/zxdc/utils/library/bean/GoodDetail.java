package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/12/10.
 */

public class GoodDetail extends BaseBean {

    private GoodBean data;

    public GoodBean getData() {
        return data;
    }

    public void setData(GoodBean data) {
        this.data = data;
    }

    public static class GoodBean implements Serializable{
        private int id;
        private String imgurls;
        private String name;
        private double price;
        private String ruleinfo;
        private String spuinfo;
        private String subtitle;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgurls() {
            return imgurls;
        }

        public void setImgurls(String imgurls) {
            this.imgurls = imgurls;
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

        public String getRuleinfo() {
            return ruleinfo;
        }

        public void setRuleinfo(String ruleinfo) {
            this.ruleinfo = ruleinfo;
        }

        public String getSpuinfo() {
            return spuinfo;
        }

        public void setSpuinfo(String spuinfo) {
            this.spuinfo = spuinfo;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }
    }
}
