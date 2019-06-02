package com.model;

/*在团队列表显示的成员类*/
public class Student_In_Team {
    private String realName;        //真实姓名
    private String stuId;           //学号

    public Student_In_Team(String realName, String stuId){
        this.realName=realName;
        this.stuId=stuId;
    }

    public Student_In_Team() {

    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
}
