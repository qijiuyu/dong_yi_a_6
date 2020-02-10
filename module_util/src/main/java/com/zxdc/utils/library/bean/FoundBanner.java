package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/21.
 */

public class FoundBanner extends BaseBean {

    private BannerBean data;

    public BannerBean getData() {
        return data;
    }

    public void setData(BannerBean data) {
        this.data = data;
    }

    public static class BannerBean implements Serializable{
        private int id;
        //优惠券图片
        private String img;

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
    }
}
