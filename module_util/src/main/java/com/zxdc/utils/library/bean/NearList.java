package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/21.
 */

public class NearList extends BaseBean {

    private List<NearBean> data=new ArrayList<>();

    public List<NearBean> getData() {
        return data;
    }

    public void setData(List<NearBean> data) {
        this.data = data;
    }

    public static class NearBean implements Serializable{
        private int  id;
        private int shopid;
        //案例数
        private int casecount;
        //在施工地数
        private int constructcount;
        //门店图片
        private String img;
        //门店名称
        private String name;
        //联系方式
        private String telphone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShopid() {
            return shopid;
        }

        public void setShopid(int shopid) {
            this.shopid = shopid;
        }

        public int getCasecount() {
            return casecount;
        }

        public void setCasecount(int casecount) {
            this.casecount = casecount;
        }

        public int getConstructcount() {
            return constructcount;
        }

        public void setConstructcount(int constructcount) {
            this.constructcount = constructcount;
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

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }
    }
}
