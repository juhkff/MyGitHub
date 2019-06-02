package com.model;

/*外包模版类,用于Gson*/
public class Project {
    private String name;                //外包名(暂定为唯一标识)
    private String tags=null;           //外包标签
    private String content;             //外包内容
    private String reward;              //外包报酬
    private String founder;             //外包发布者(用户名)
    private String publicTime;          //外包发布时间
    private String settedTime=null;     //规定完成时间
    private String sendTime=null;       //外包提交时间
    private String proType=null;        //外包形式(“0”-个人,”1”-团队)
    private String phoneNum;            //发布者手机号
    private String isRequired;          //是否已被学生请求接受(“0”-未被接受,”1”-已被要求接受)
    private String requiredName=null;   //外包申请者用户名/团队名
    private String isAgreed=null;       //发布者是否同意该学生的接受请求(“0”-未应答,”1”-同意,”2”-拒绝)
    private String isSended=null;       //接受该外包的学生是否已经提交该外包(“0”-未提交,”1”-已提交)
    private String isPassed=null;       //客户是否通过该已提交的外包(“0”-未处理,”1”-通过,”2”-驳回)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    public String getSettedTime() {
        return settedTime;
    }

    public void setSettedTime(String settedTime) {
        this.settedTime = settedTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getRequiredName() {
        return requiredName;
    }

    public void setRequiredName(String requiredName) {
        this.requiredName = requiredName;
    }

    public String getIsAgreed() {
        return isAgreed;
    }

    public void setIsAgreed(String isAgreed) {
        this.isAgreed = isAgreed;
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
