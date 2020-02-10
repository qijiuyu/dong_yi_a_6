package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DesignerDetails extends BaseBean {

    private DesignerBean data;

    public DesignerBean getData() {
        return data;
    }

    public void setData(DesignerBean data) {
        this.data = data;
    }

    public static class DesignerBean implements Serializable{
        //案例数
        private int casecount;
        //设计师级别
        private String dlevel;
        //擅长风格
        private String dstyle;
        //设计师类型
        private String dtype;
        //标识
        private int id;
        //设计师头像
        private String img;
        //设计师名称
        private String name;
        //设计师角标
        private String tagimg;
        //从业年限
        private int workingyear;

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

        public String getDstyle() {
            return dstyle;
        }

        public void setDstyle(String dstyle) {
            this.dstyle = dstyle;
        }

        public String getDtype() {
            return dtype;
        }

        public void setDtype(String dtype) {
            this.dtype = dtype;
        }

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

        public String getTagimg() {
            return tagimg;
        }

        public void setTagimg(String tagimg) {
            this.tagimg = tagimg;
        }

        public int getWorkingyear() {
            return workingyear;
        }

        public void setWorkingyear(int workingyear) {
            this.workingyear = workingyear;
        }
    }


}
