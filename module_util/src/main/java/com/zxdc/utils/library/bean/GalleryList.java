package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/24.
 */

public class GalleryList extends BaseBean {

    private List<GalleryBean> data=new ArrayList<>();

    public List<GalleryBean> getData() {
        return data;
    }

    public void setData(List<GalleryBean> data) {
        this.data = data;
    }

    public static class GalleryBean implements Serializable{
        private int id;
        //所属案例ID
        private int caseid;
        private int imgid;
        //所属设计师ID
        private int designerid;
        //空间
        private String housespace;
        private String img;
        private String name;

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

        public int getDesignerid() {
            return designerid;
        }

        public void setDesignerid(int designerid) {
            this.designerid = designerid;
        }

        public String getHousespace() {
            return housespace;
        }

        public void setHousespace(String housespace) {
            this.housespace = housespace;
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

        public int getImgid() {
            return imgid;
        }

        public void setImgid(int imgid) {
            this.imgid = imgid;
        }
    }
}
