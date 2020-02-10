package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/21.
 */

public class MainCase extends BaseBean {

    private List<MainCaseBean> data=new ArrayList<>();

    public List<MainCaseBean> getData() {
        return data;
    }

    public void setData(List<MainCaseBean> data) {
        this.data = data;
    }

    public static class MainCaseBean implements Serializable{
        private String style;
        private List<CaseImg> list;

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public List<CaseImg> getList() {
            return list;
        }

        public void setList(List<CaseImg> list) {
            this.list = list;
        }
    }

}
