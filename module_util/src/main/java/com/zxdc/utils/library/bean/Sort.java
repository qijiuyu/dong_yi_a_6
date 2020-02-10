package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/11/26.
 */

public class Sort implements Serializable {

    /**
     * 1：最新， 工作年限,距离
     * 2：人气,案例数
     * 3：设计师数
     * 4：户型解析数
     */
    private int filed;
    //1-升序 2-降序
    private int sort;

    public Sort(int filed,int sort){
        this.filed=filed;
        this.sort=sort;
    }

    public int getFiled() {
        return filed;
    }

    public void setFiled(int filed) {
        this.filed = filed;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
