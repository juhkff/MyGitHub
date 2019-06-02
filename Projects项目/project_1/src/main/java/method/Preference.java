package com.method;

import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*三、偏好设置(仅学生)*/
public class Preference {
    //1：上传偏好
    public String uploadPrefer(String Preferences,String UserNumber){
        String result="1";

        //合并多个空格(如："aa bb    cc  dd  ee   cs "变成"aa bb cc dd ee cs")
        Preferences=Preferences.replaceAll(" +", " ");  //把多个空格替换为单个空格，" +"表示连续的多个空格

        //更新数据库表
        String sql="UPDATE users SET Prefers=? WHERE UserId=?";
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        int i=-1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,Preferences);
            preparedStatement.setString(2,UserNumber);
            i=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (i>0)
            result="0";     //result为"0"-上传成功
        else if (i==0)
            result="1";     //result为"1"-上传失败

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        return result;
        //存到表中的是完整的用单个空格相隔的所有偏好的一个字符串
    }

    //2、获得用户（学生）偏好
    public String[] showPrefers(String UserNumber) {
        String[] preferList;
        String preferences = null;
        String sql = "SELECT Prefers FROM users WHERE UserId=?";
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UserNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                preferences = resultSet.getString("Prefers");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preferences == null) {
            preferences = "error";
        }
        preferList=preferences.split(" ");

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        return preferList;
    }
}
