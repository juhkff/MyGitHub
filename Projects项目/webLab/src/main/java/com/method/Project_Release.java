package com.method;

import com.connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*发布外包界面*/
public class Project_Release {
    public String uploadProject(String proName,String proTags,String proContent,String deadLine,String reward,String userName,String publicTime,String phoneNum){
        //合并多个空格(如："aa bb    cc  dd  ee   cs "变成"aa bb cc dd ee cs")
        proTags=proTags.replaceAll(" +", " ");  //把多个空格替换为单个空格，" +"表示连续的多个空格

        String DeadLine=null;
        if(deadLine!=null)
            DeadLine=deadLine;
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        String sql="INSERT INTO projects(ProName,ProTags,ProContent,ProReward,ProFounder,PublicTime,SettedTime,PhoneNum,IsRequired) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement=null;
        int i = -1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,proName);
            preparedStatement.setString(2,proTags);
            preparedStatement.setString(3,proContent);
            preparedStatement.setString(4,reward);
            preparedStatement.setString(5,userName);
            preparedStatement.setString(6,publicTime);
            preparedStatement.setString(7,DeadLine);
            preparedStatement.setString(8,phoneNum);
            preparedStatement.setString(9,"0");
            i=preparedStatement.executeUpdate();
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

        if (i>0)
            return "0";             //返回0:成功
        else
            return "1";             //返回1:操作失败(error)
    }
}
