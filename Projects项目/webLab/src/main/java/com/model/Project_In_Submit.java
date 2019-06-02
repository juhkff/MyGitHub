package com.model;

/*用于提交列表的显示*/
public class Project_In_Submit {
    private String proName;
    private String receiverName;
    private String proType;
    private String deadLine;
    private String submitTime;

    public Project_In_Submit(String proName, String receiverName, String proType, String deadLine, String submitTime) {
        this.proName = proName;
        this.receiverName = receiverName;
        this.proType = proType;
        this.deadLine = deadLine;
        this.submitTime = submitTime;
    }

    public Project_In_Submit() {

    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }
}
