package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/24.
 */

public class CaseList extends BaseBean {

    private List<CaseListBean> data=new ArrayList<>();

    public List<CaseListBean> getData() {
        return data;
    }

    public void setData(List<CaseListBean> data) {
        this.data = data;
    }

    public static class CaseListBean implements Serializable{
        private int id;
        //案例城市
        private String cityname;
        //所属设计师ID
        private int designerid;
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
        private String loupanname;
        //案例面积
        private String square;
        private int caseid;

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

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public int getDesignerid() {
            return designerid;
        }

        public void setDesignerid(int designerid) {
            this.designerid = designerid;
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

        public String getLoupanname() {
            return loupanname;
        }

        public void setLoupanname(String loupanname) {
            this.loupanname = loupanname;
        }

        public String getSquare() {
            return square;
        }

        public void setSquare(String square) {
            this.square = square;
        }

        public int getCaseid() {
            return caseid;
        }

        public void setCaseid(int caseid) {
            this.caseid = caseid;
        }
    }
}
