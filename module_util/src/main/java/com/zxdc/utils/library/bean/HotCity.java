package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/23.
 */

public class HotCity extends BaseBean {

    private List<HotCityBean> data=new ArrayList<>();

    public List<HotCityBean> getData() {
        return data;
    }

    public void setData(List<HotCityBean> data) {
        this.data = data;
    }

    public static class HotCityBean implements Serializable{
        private int id;
        private String cityname;
        private String img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
