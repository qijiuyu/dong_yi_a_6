package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/25.
 */

public class DesignerList extends BaseBean {

    private List<DesignerBean> data=new ArrayList<>();

    public List<DesignerBean> getData() {
        return data;
    }

    public void setData(List<DesignerBean> data) {
        this.data = data;
    }

    public static class DesignerBean implements Serializable{
        private int designerid;
        //案例数
        private int casecount;
        //最新案例 3个
        private List<CaseImg> caseimgs=new ArrayList<>();
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

        public int getDesignerid() {
            return designerid;
        }

        public void setDesignerid(int designerid) {
            this.designerid = designerid;
        }

        public int getCasecount() {
            return casecount;
        }

        public void setCasecount(int casecount) {
            this.casecount = casecount;
        }

        public List<CaseImg> getCaseimgs() {
            return caseimgs;
        }

        public void setCaseimgs(List<CaseImg> caseimgs) {
            this.caseimgs = caseimgs;
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

    public static class CaseImg implements Serializable{
        private int commonid;
        private String commonvalue;

        public int getCommonid() {
            return commonid;
        }

        public void setCommonid(int commonid) {
            this.commonid = commonid;
        }

        public String getCommonvalue() {
            return commonvalue;
        }

        public void setCommonvalue(String commonvalue) {
            this.commonvalue = commonvalue;
        }
    }
}
