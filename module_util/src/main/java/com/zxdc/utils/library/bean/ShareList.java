package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/29.
 */

public class ShareList extends BaseBean {

    private List<ShareYear> data=new ArrayList<>();

    public List<ShareYear> getData() {
        return data;
    }

    public void setData(List<ShareYear> data) {
        this.data = data;
    }

    public static class ShareYear implements Serializable{
        private String dateyear;
        private List<ShareDay> list=new ArrayList<>();

        public String getDateyear() {
            return dateyear;
        }

        public void setDateyear(String dateyear) {
            this.dateyear = dateyear;
        }

        public List<ShareDay> getList() {
            return list;
        }

        public void setList(List<ShareDay> list) {
            this.list = list;
        }
    }


    public static class ShareDay implements Serializable{
        private String dateday;
        private List<ShareData> list;

        public String getDateday() {
            return dateday;
        }

        public void setDateday(String dateday) {
            this.dateday = dateday;
        }

        public List<ShareData> getList() {
            return list;
        }

        public void setList(List<ShareData> list) {
            this.list = list;
        }
    }



    public static class ShareData implements Serializable{
        //分享时间
        private String datehour;
        //标题
        private String sharename;
        //内容
        private String sharetitle;
        //类型
        private String sharetype;

        public String getDatehour() {
            return datehour;
        }

        public void setDatehour(String datehour) {
            this.datehour = datehour;
        }

        public String getSharename() {
            return sharename;
        }

        public void setSharename(String sharename) {
            this.sharename = sharename;
        }

        public String getSharetitle() {
            return sharetitle;
        }

        public void setSharetitle(String sharetitle) {
            this.sharetitle = sharetitle;
        }

        public String getSharetype() {
            return sharetype;
        }

        public void setSharetype(String sharetype) {
            this.sharetype = sharetype;
        }
    }
}