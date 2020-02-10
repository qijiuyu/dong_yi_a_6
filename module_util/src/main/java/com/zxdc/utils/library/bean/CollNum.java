package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/12/8.
 */

public class CollNum extends BaseBean {

    private CollNumBean data;

    public CollNumBean getData() {
        return data;
    }

    public void setData(CollNumBean data) {
        this.data = data;
    }

    public static class CollNumBean implements Serializable{
        private int construccount;
        private int designercount;
        private int imgcasecount;
        private int loupancount;
        private int newscount;
        private int shopcount;
        private int softcasecount;
        private int stylecasecount;
        private int vrcasecount;

        public int getConstruccount() {
            return construccount;
        }

        public void setConstruccount(int construccount) {
            this.construccount = construccount;
        }

        public int getDesignercount() {
            return designercount;
        }

        public void setDesignercount(int designercount) {
            this.designercount = designercount;
        }

        public int getImgcasecount() {
            return imgcasecount;
        }

        public void setImgcasecount(int imgcasecount) {
            this.imgcasecount = imgcasecount;
        }

        public int getLoupancount() {
            return loupancount;
        }

        public void setLoupancount(int loupancount) {
            this.loupancount = loupancount;
        }

        public int getNewscount() {
            return newscount;
        }

        public void setNewscount(int newscount) {
            this.newscount = newscount;
        }

        public int getShopcount() {
            return shopcount;
        }

        public void setShopcount(int shopcount) {
            this.shopcount = shopcount;
        }

        public int getSoftcasecount() {
            return softcasecount;
        }

        public void setSoftcasecount(int softcasecount) {
            this.softcasecount = softcasecount;
        }

        public int getStylecasecount() {
            return stylecasecount;
        }

        public void setStylecasecount(int stylecasecount) {
            this.stylecasecount = stylecasecount;
        }

        public int getVrcasecount() {
            return vrcasecount;
        }

        public void setVrcasecount(int vrcasecount) {
            this.vrcasecount = vrcasecount;
        }
    }
}
