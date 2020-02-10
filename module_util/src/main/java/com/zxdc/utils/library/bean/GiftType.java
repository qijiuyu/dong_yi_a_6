package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/24.
 */

public class GiftType extends BaseBean {

    private List<GiftTypeBean> data=new ArrayList<>();

    public List<GiftTypeBean> getData() {
        return data;
    }

    public void setData(List<GiftTypeBean> data) {
        this.data = data;
    }

    public static class GiftTypeBean implements Serializable{
        private int id;
        private String name;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
