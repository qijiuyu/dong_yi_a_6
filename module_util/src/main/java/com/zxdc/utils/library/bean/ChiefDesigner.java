package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/12/5.
 */

public class ChiefDesigner extends BaseBean {

    private DesignerBean data;

    public DesignerBean getData() {
        return data;
    }

    public void setData(DesignerBean data) {
        this.data = data;
    }

    public static class DesignerBean implements Serializable{
        private int commonid;
        private String commontype;
        private String commonvalue;

        public int getCommonid() {
            return commonid;
        }

        public void setCommonid(int commonid) {
            this.commonid = commonid;
        }

        public String getCommontype() {
            return commontype;
        }

        public void setCommontype(String commontype) {
            this.commontype = commontype;
        }

        public String getCommonvalue() {
            return commonvalue;
        }

        public void setCommonvalue(String commonvalue) {
            this.commonvalue = commonvalue;
        }
    }
}
