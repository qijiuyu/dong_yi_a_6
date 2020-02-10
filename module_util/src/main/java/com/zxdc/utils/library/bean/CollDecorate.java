package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/12/7.
 */

public class CollDecorate extends BaseBean {

    private List<DecorateBean> data;

    public List<DecorateBean> getData() {
        return data;
    }

    public void setData(List<DecorateBean> data) {
        this.data = data;
    }

    public static class DecorateBean implements Serializable{
        private int id;
        private String cname;
        private String datetime;
        private String img;
        private int newsid;
        private String title;
        private int viewcount;
        private int sharecount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getNewsid() {
            return newsid;
        }

        public void setNewsid(int newsid) {
            this.newsid = newsid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getViewcount() {
            return viewcount;
        }

        public void setViewcount(int viewcount) {
            this.viewcount = viewcount;
        }

        public int getSharecount() {
            return sharecount;
        }

        public void setSharecount(int sharecount) {
            this.sharecount = sharecount;
        }
    }
}
