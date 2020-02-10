package com.zxdc.utils.library.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/12/3.
 */

public class MainCaseImg extends BaseBean {

    private List<CaseImg> data=new ArrayList<>();

    public List<CaseImg> getData() {
        return data;
    }

    public void setData(List<CaseImg> data) {
        this.data = data;
    }
}
