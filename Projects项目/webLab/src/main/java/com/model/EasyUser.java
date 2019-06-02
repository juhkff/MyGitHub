package com.model;

public class EasyUser {
    private String userId=null;              //用户账号(主键、6位数字)
    private String userName;            //用户名
    private String phoneNum;            //手机号码
    private String emailAddress=null;   //电子邮箱
    private String status1;             //学校/企业
    private String status2;             //学号/企业地址
    private String realName;            //真实姓名

    public EasyUser(String userId, String userName, String phoneNum, String emailAddress, String status1, String status2, String realName) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.emailAddress = emailAddress;
        this.status1 = status1;
        this.status2 = status2;
        this.realName = realName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
