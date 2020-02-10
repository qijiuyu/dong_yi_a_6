package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/21.
 */

public class MainDecorate extends BaseBean {

    private List<DecorateBean> data=new ArrayList<>();

    public List<DecorateBean> getData() {
        return data;
    }

    public void setData(List<DecorateBean> data) {
        this.data = data;
    }

    public static class DecorateBean implements Serializable{
        private int  id;
        //栏目ID
        private int cid;
        //栏目名称
        private String cname;
        //时间
        private String datetime;
        private String  img;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
