package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/12/13.
 */

public class Help extends BaseBean {

    private List<HelpBean> data;

    public List<HelpBean> getData() {
        return data;
    }

    public void setData(List<HelpBean> data) {
        this.data = data;
    }

    public static class HelpBean implements Serializable{
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
