package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/12/3.
 */

public class News extends BaseBean {

    private List<NewsBean> data=new ArrayList<>();

    public List<NewsBean> getData() {
        return data;
    }

    public void setData(List<NewsBean> data) {
        this.data = data;
    }

    public static class NewsBean implements Serializable{
        private int id;
        private String actend;
        private String actstart;
        private String content;
        private String createdate;
        private String img;
        private int status;
        private String title;
        private String actstatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getActend() {
            return actend;
        }

        public void setActend(String actend) {
            this.actend = actend;
        }

        public String getActstart() {
            return actstart;
        }

        public void setActstart(String actstart) {
            this.actstart = actstart;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getActstatus() {
            return actstatus;
        }

        public void setActstatus(String actstatus) {
            this.actstatus = actstatus;
        }
    }
}
