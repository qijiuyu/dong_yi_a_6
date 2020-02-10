package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/24.
 */

public class SoftLoadingList extends BaseBean {

    private List<SoftLoadingBean> data=new ArrayList<>();

    public List<SoftLoadingBean> getData() {
        return data;
    }

    public void setData(List<SoftLoadingBean> data) {
        this.data = data;
    }

    public static class SoftLoadingBean implements Serializable{
        private int id;
        private int caseid;
        //所属设计师图片
        private String designerimg;
        //所属设计师等级
        private String designerlevel;
        //所属设计师名称
        private String designername;
        //案例风格
        private String dstyle;
        private String img;
        private String name;
        //案例面积
        private String square;
        //案例城市
        private String cityname;
        //楼盘名称
        private String loupanname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCaseid() {
            return caseid;
        }

        public void setCaseid(int caseid) {
            this.caseid = caseid;
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

        public String getDesignerimg() {
            return designerimg;
        }

        public void setDesignerimg(String designerimg) {
            this.designerimg = designerimg;
        }

        public String getDesignerlevel() {
            return designerlevel;
        }

        public void setDesignerlevel(String designerlevel) {
            this.designerlevel = designerlevel;
        }

        public String getDesignername() {
            return designername;
        }

        public void setDesignername(String designername) {
            this.designername = designername;
        }

        public String getDstyle() {
            return dstyle;
        }

        public void setDstyle(String dstyle) {
            this.dstyle = dstyle;
        }

        public String getSquare() {
            return square;
        }

        public void setSquare(String square) {
            this.square = square;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getLoupanname() {
            return loupanname;
        }

        public void setLoupanname(String loupanname) {
            this.loupanname = loupanname;
        }
    }
}
