package com.model;

/*用于显示已完成外包界面*/
public class Project_In_Finished {
    private String proName;             //外包名
    private String proTags;             //外包标签
    private String proFounder;          //外包发布者
    private String proDeadLine;         //外包规定完成时间
    private String proFinishTime;       //外包完成时间
    private String proType;             //外包形式

    public Project_In_Finished(String proName, String proTags, String proFounder, String proDeadLine, String proFinishTime, String proType) {
        this.proName = proName;
        this.proTags = proTags;
        this.proFounder = proFounder;
        this.proDeadLine = proDeadLine;
        this.proFinishTime = proFinishTime;
        this.proType = proType;
    }

    public Project_In_Finished() {

    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProTags() {
        return proTags;
    }

    public void setProTags(String proTags) {
        this.proTags = proTags;
    }

    public String getProFounder() {
        return proFounder;
    }

    public void setProFounder(String proFounder) {
        this.proFounder = proFounder;
    }

    public String getProDeadLine() {
        return proDeadLine;
    }

    public void setProDeadLine(String proDeadLine) {
        this.proDeadLine = proDeadLine;
    }

    public String getProFinishTime() {
        return proFinishTime;
    }

    public void setProFinishTime(String proFinishTime) {
        this.proFinishTime = proFinishTime;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }
}
