package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/30.
 */

public class TotalSearch extends BaseBean {

    private List<TotalSearchBean> data=new ArrayList<>();

    public List<TotalSearchBean> getData() {
        return data;
    }

    public void setData(List<TotalSearchBean> data) {
        this.data = data;
    }

    public static class TotalSearchBean implements Serializable{
        //分类名称
        private String name;

        private int index;

        private List<SearchList> list=new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<SearchList> getList() {
            return list;
        }

        public void setList(List<SearchList> list) {
            this.list = list;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }


    public static class SearchList implements Serializable{
        private int id;
        private int analysiscount;
        private int casecount;
        private String cityname;
        private String cname;
        private int constructioncount;
        private String detailurl;
        private String district;
        private String dlevel;
        private String dstyle;
        private String dtype;
        private String housespace;
        private String img;
        private String loupanname;
        private String name;
        private String square;
        private String stage;
        private String tagimg;
        private String workingyear;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAnalysiscount() {
            return analysiscount;
        }

        public void setAnalysiscount(int analysiscount) {
            this.analysiscount = analysiscount;
        }

        public int getCasecount() {
            return casecount;
        }

        public void setCasecount(int casecount) {
            this.casecount = casecount;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public int getConstructioncount() {
            return constructioncount;
        }

        public void setConstructioncount(int constructioncount) {
            this.constructioncount = constructioncount;
        }

        public String getDetailurl() {
            return detailurl;
        }

        public void setDetailurl(String detailurl) {
            this.detailurl = detailurl;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
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

        public String getHousespace() {
            return housespace;
        }

        public void setHousespace(String housespace) {
            this.housespace = housespace;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLoupanname() {
            return loupanname;
        }

        public void setLoupanname(String loupanname) {
            this.loupanname = loupanname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSquare() {
            return square;
        }

        public void setSquare(String square) {
            this.square = square;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getTagimg() {
            return tagimg;
        }

        public void setTagimg(String tagimg) {
            this.tagimg = tagimg;
        }

        public String getWorkingyear() {
            return workingyear;
        }

        public void setWorkingyear(String workingyear) {
            this.workingyear = workingyear;
        }
    }
}
