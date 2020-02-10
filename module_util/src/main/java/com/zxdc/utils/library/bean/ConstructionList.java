package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/24.
 */

public class ConstructionList extends BaseBean {

    private List<ConstructionBean> data=new ArrayList<>();

    public List<ConstructionBean> getData() {
        return data;
    }

    public void setData(List<ConstructionBean> data) {
        this.data = data;
    }

    public static class ConstructionBean implements Serializable{
        private int id;
        private int construcid;
        //所属地区
        private String district;
        //图片
        private String img;
        //楼盘名称
        private String loupanname;
        //在施工地名称
        private String name;
        //施工阶段
        private String stage;
        //面积
        private String square;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getConstrucid() {
            return construcid;
        }

        public void setConstrucid(int construcid) {
            this.construcid = construcid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLoupanname() {
            return loupanname;
        }

        public void setLoupanname(String loupanname) {
            this.loupanname = loupanname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getSquare() {
            return square;
        }

        public void setSquare(String square) {
            this.square = square;
        }
    }
}
