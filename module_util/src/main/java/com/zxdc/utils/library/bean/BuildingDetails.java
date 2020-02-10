package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuildingDetails extends BaseBean {

    private BuildingBean data;

    public BuildingBean getData() {
        return data;
    }

    public void setData(BuildingBean data) {
        this.data = data;
    }

    public static class BuildingBean implements Serializable{
        private int id;
        //户型解析数
        private int analysiscount;
        //案例数
        private int casecount;
        //在施工地数
        private int constructioncount;
        private String img;
        private String name;

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

        public int getConstructioncount() {
            return constructioncount;
        }

        public void setConstructioncount(int constructioncount) {
            this.constructioncount = constructioncount;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
