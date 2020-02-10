package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/30.
 */

public class MainDesigner extends BaseBean{

    private List<MainDesignerBean> data=new ArrayList<>();

    public List<MainDesignerBean> getData() {
        return data;
    }

    public void setData(List<MainDesignerBean> data) {
        this.data = data;
    }

    public static class MainDesignerBean implements Serializable{
        private int id;
        //案例数
        private int casecount;
        //设计师级别
        private String dlevel;
        //头像
        private String img;
        //名称
        private String name;
        //擅长
        private String style;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCasecount() {
            return casecount;
        }

        public void setCasecount(int casecount) {
            this.casecount = casecount;
        }

        public String getDlevel() {
            return dlevel;
        }

        public void setDlevel(String dlevel) {
            this.dlevel = dlevel;
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

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }
    }
}
