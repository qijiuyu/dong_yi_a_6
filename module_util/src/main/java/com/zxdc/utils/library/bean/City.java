package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2020/1/7.
 */

public class City extends BaseBean {

    private List<CityBean> data;

    public List<CityBean> getData() {
        return data;
    }

    public void setData(List<CityBean> data) {
        this.data = data;
    }

    public static class CityBean implements Serializable{
        private String code;
        private List<CityList> citylist;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<CityList> getCitylist() {
            return citylist;
        }

        public void setCitylist(List<CityList> citylist) {
            this.citylist = citylist;
        }
    }

    public static class CityList implements Serializable{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
