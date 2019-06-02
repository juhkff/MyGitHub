package com.method;

import connection.MyConnection;
import module.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*外包详情页面*/
public class Project_Details {

    //获得具体某个外包的详细内容
    public Project getOneProject(String proName) {
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM projects WHERE ProName=?";
        Project project = new Project();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, proName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                project.setName(resultSet.getString("ProName"));
                project.setTags(resultSet.getString("ProTags"));
                project.setContent(resultSet.getString("ProContent"));
                project.setReward(resultSet.getString("ProReward"));
                project.setFounder(resultSet.getString("ProFounder"));
                //没有发布时间可以么?——可以，自动赋为null
                project.setPhoneNum(resultSet.getString("PhoneNum"));
                project.setSettedTime(resultSet.getString("SettedTime"));
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

        return project;
    }

    //点击发送请求时调用的方法
    public String sendRequest(String proName,String proType,String proReceiverName){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="UPDATE projects SET ProType=?,RequiredName=?,IsRequired=?,IsAgreed=? WHERE ProName=?";
        int i=-1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,proType);
            preparedStatement.setString(2,proReceiverName);
            preparedStatement.setString(3,"1");
            preparedStatement.setString(4,"0");                                                     //change-1
            preparedStatement.setString(5,proName);
            i=preparedStatement.executeUpdate();
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

        if (i>0)
            return "0";     //成功
        else
            return "1";     //失败(error)
    }

    //获得用户所在的团队名(用户必须是队长才行)
    public String getTeamName(String userName){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT TeamName FROM teams WHERE TeamLeader=?";
        String teamName=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                teamName=resultSet.getString(1);
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

        return teamName;
    }
}
