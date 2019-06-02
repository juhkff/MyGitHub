package com.method;

import connection.MyConnection;
import module.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*客户-外包申请界面*/
public class Project_Request {

    @Test
    public void test1(){
        ArrayList<Project_In_Request> project_in_requestArrayList=getProjectList("busNameTest");
        for(Project_In_Request project_in_request:project_in_requestArrayList){
            System.out.println(project_in_request.getProName()+"\t"+project_in_request.getProType()+"\t"+project_in_request.getReceiverName());
        }
    }
    //获得该页面的外包列表
    public ArrayList<Project_In_Request> getProjectList(String userName){
        ArrayList<Project_In_Request> project_in_requestArrayList=new ArrayList<Project_In_Request>();
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT ProName,RequiredName,ProType FROM projects WHERE ProFounder=? AND IsRequired=? AND IsAgreed=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,"1");
            preparedStatement.setString(3,"0");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                project_in_requestArrayList.add(new Project_In_Request(resultSet.getString(1),
                        resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement != null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        return project_in_requestArrayList;
    }

    //获得外包详情
    public Project getProjectDetails(String proName){
        return new Project_Details().getOneProject(proName);
    }

    //获得用户详情
    public User getUserDetails(String userName){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT * FROM users WHERE UserName=?";
        User user=new User();
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                user.setUserId(resultSet.getString("UserId"));
                user.setUserType(resultSet.getString("UserType"));
                user.setUserName(resultSet.getString("UserName"));
                user.setPhoneNum(resultSet.getString("PhoneNum"));
                user.setEmailAddress(resultSet.getString("emailAddress"));
                user.setStatus1(resultSet.getString("Status1"));
                user.setStatus2(resultSet.getString("Status2"));
                user.setRealName(resultSet.getString("RealName"));
                user.setSex(resultSet.getString("Sex"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //获得团队详情
    public Team getTeamDetails(String teamName){
        return new Team_Detail().getTeamDetails(teamName);
    }

    @Test
    public void test2(){
        System.out.println(agreeProjectRequest("test2"));
    }
    //同意该外包请求时调用
    public String agreeProjectRequest(String proName){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="UPDATE projects SET IsAgreed=?,IsSended=? WHERE ProName=?";        //同意的话不用改isRequired值
        int result=-1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"1");
            preparedStatement.setString(2,"0");
            preparedStatement.setString(3,proName);
            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement != null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        if (result>0)
            return "0";
        else
            return "1";
    }

    @Test
    public void test3(){
        System.out.println(rejectProjectRequest("test2"));
    }
    //拒绝该外包请求时调用
    public String rejectProjectRequest(String proName){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
//        String sql="UPDATE projects SET IsRequired=?,RequiredName=null,IsAgreed=?";
        String sql="UPDATE projects SET IsRequired=?,IsAgreed=? WHERE ProName=?";
        int result=-1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"0");
            preparedStatement.setString(2,"2");
            preparedStatement.setString(3,proName);
            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement != null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        if (result>0)
            return "0";
        else
            return "1";
    }
}
