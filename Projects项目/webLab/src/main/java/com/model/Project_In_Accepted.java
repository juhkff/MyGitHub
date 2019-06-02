package com.model;

/*用于已接外包列表的外包类*/
public class Project_In_Accepted {
    private String proName;
    private String publicUser;
    private String deadLine;
    private String proType;
    private String currentType;         //currentType的值根据isSended和isPassed的值决定
    private String isSended;
    private String isPassed;

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getPublicUser() {
        return publicUser;
    }

    public void setPublicUser(String publicUser) {
        this.publicUser = publicUser;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getCurrentType() {
        return currentType;
    }

    public void setCurrentType() {
        if(getIsSended().equals("0"))              //未提交
            this.currentType="1";           //正在进行
        else if (getIsPassed().equals("0"))        //已提交+未处理
            this.currentType="2";
        else if(getIsPassed().equals("2"))         //已提交+拒绝/驳回
            this.currentType="3";           //未通过审核
    }

    public String getIsSended() {
        return isSended;
    }

    public void setIsSended(String isSended) {
        this.isSended = isSended;
    }

    public String getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(String isPassed) {
        this.isPassed = isPassed;
    }


}
