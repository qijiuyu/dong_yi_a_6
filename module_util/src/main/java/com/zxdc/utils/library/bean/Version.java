package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class Version extends BaseBean{

    private VersionBean data;

    public VersionBean getData() {
        return data;
    }

    public void setData(VersionBean data) {
        this.data = data;
    }

    public static class VersionBean implements Serializable{
        private int commonid;
        //下载地址
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
