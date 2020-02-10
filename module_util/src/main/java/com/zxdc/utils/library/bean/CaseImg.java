package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/12/3.
 */

public class CaseImg implements Serializable {

    private int id;
    //案例类型 （硬装）风格案例，（软装）软装范本，（VR）VR样板间
    private String decoratetype;
    //案例图片
    private String img;
    //标题
    private String title;
    //详情url
    private String detailurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDecoratetype() {
        return decoratetype;
    }

    public void setDecoratetype(String decoratetype) {
        this.decoratetype = decoratetype;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }
}
