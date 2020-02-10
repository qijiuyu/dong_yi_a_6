package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/12/6.
 */

public class IsColl extends BaseBean {

    private IsCollBean data;

    public IsCollBean getData() {
        return data;
    }

    public void setData(IsCollBean data) {
        this.data = data;
    }

    public static class IsCollBean implements Serializable{
        //是否收藏 0-否 1-是
        private int iscollect;
        //设计师 是否关注 0-否 1-是
        private int isfollow;

        public int getIscollect() {
            return iscollect;
        }

        public void setIscollect(int iscollect) {
            this.iscollect = iscollect;
        }

        public int getIsfollow() {
            return isfollow;
        }

        public void setIsfollow(int isfollow) {
            this.isfollow = isfollow;
        }
    }
}
