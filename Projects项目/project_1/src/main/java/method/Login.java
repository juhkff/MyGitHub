package com.method;

import connection.MyConnection;
import module.User;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public String processLogin(String userId,String passWord){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT PassWord FROM users WHERE UserId=?";
        String result="";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet==null){
                result= "1";
            }else {
                while (resultSet.next()) {
                    if (resultSet.getString(1).equals(passWord)) {
                        result= "0";
                    } else {
                        result= "2";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        return result;
    }

    public User getUserInfo(String userId){
        User user=new User();
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT * FROM users WHERE UserId=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                user.setUserId(userId);
                user.setUserName(resultSet.getString("userName"));
                user.setUserType(resultSet.getString("UserType"));
                user.setSex(resultSet.getString("Sex"));
                user.setPassWord(resultSet.getString("PassWord"));
                user.setPhoneNum(resultSet.getString("PhoneNum"));
                user.setEmailAddress(resultSet.getString("EmailAddress"));
                user.setIdCard(resultSet.getString("IdCard"));
                user.setStatus1(resultSet.getString("Status1"));
                user.setStatus2(resultSet.getString("Status2"));
                user.setPrefers(resultSet.getString("Prefers"));
                user.setIsLeader(resultSet.getString("IsLeader"));
                user.setRealName(resultSet.getString("RealName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        return user;
    }

    //获得头像
    public InputStream getHeadIcon(String userId){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT HeadIcon FROM users WHERE UserId=?";
        InputStream inputStream=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                inputStream=resultSet.getBinaryStream("HeadIcon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        return inputStream;
    }

    //更改头像
    public InputStream updateHeadIcon(String userId,InputStream inputStream) throws Exception {
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="UPDATE users SET HeadIcon=? WHERE UserId=?";
        int result=-1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setBinaryStream(1,inputStream);
            preparedStatement.setString(2,userId);
            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        if (result>0)
            return  inputStream;
        else
            throw new Exception("Error!");
    }

    //修改用户信息
    public String updateUserInfo(String userName,String phoneNum,String emailAddress,String userId) throws Exception {
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="UPDATE users SET UserName=?,PhoneNum=?,EmailAddress=? WHERE UserId=?";
        int result=-1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,phoneNum);
            preparedStatement.setString(3,emailAddress);
            preparedStatement.setString(4,userId);
            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        if (result>0)
            return "0";
        else
            throw new Exception("Error!");
    }
}
