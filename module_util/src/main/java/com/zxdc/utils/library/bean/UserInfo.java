package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/11/22.
 */

public class UserInfo extends BaseBean {

    private UserBean data;

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public static class UserBean implements Serializable{
        //标识
        private int id;
        //是否推送通知
        private int alerttag;
        //头像
        private String imgurl;
        //登录名
        private String loginname;
        //手机号
        private String mobile;
        //昵称
        private String nickname;
        //微信openid
        private String openid;
        //0-首次登陆  1-已登录过
        private int firstlogin;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAlerttag() {
            return alerttag;
        }

        public void setAlerttag(int alerttag) {
            this.alerttag = alerttag;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public int getFirstlogin() {
            return firstlogin;
        }

        public void setFirstlogin(int firstlogin) {
            this.firstlogin = firstlogin;
        }
    }
}
