package com.model;

/*用户模版类，用于Gson*/

/*注意！该类没有头像属性！*/
public class User {

    /*初始值赋为null的是数据库中可为空的数据，赋为""的是数据库中默认值为空字符串（且不允许空值——会自动赋为空字符串）的数据*/
    private String userId=null;              //用户账号(主键、6位数字)
    private String userType;            //用户类型(学生/客户)
    private String userName;            //用户名
    private String passWord;            //密码
    private String phoneNum;            //手机号码
    private String emailAddress=null;   //电子邮箱
    private String idCard;              //身份证号
    private String status1;             //学号/企业
    private String status2;             //学号/企业地址
    private String realName;            //真实姓名
    private String sex;                 //性别("M"-男;"F"-女)
    private String prefers="";          //学生喜好(标据此筛选签).该值默认为空字符串

    private String isLeader="0";        //是否为队长.默认为"0"-否."1"-是.
    private String teamName=null;       //团队名，没有则为null

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPrefers() {
        return prefers;
    }

    public void setPrefers(String prefers) {
        this.prefers = prefers;
    }

    public String getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(String isLeader) {
        this.isLeader = isLeader;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
