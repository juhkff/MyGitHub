package com.method;

import com.connection.MyConnection;
import com.model.Static_Variablility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class Register {
    public String createNewMember(String type,String userName,String passWord,String phoneNum,String emailAddress,String sex,
                               String realName,String idCard,String status1,String status2) throws Exception {
        String userId=createNewId();
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="INSERT INTO users(UserId,UserType,UserName,PassWord,PhoneNum,EmailAddress,IdCard,Status1,Status2,RealName,Sex,HeadIcon) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        InputStream inputStream=null;
        int result=-1;
        try {
            inputStream=new FileInputStream(Static_Variablility.head_Icon_Path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userId);
            preparedStatement.setString(2,type);
            preparedStatement.setString(3,userName);
            preparedStatement.setString(4,passWord);
            preparedStatement.setString(5,phoneNum);
            preparedStatement.setString(6,emailAddress);
            preparedStatement.setString(7,idCard);
            preparedStatement.setString(8,status1);
            preparedStatement.setString(9,status2);
            preparedStatement.setString(10,realName);
            preparedStatement.setString(11,sex);
            preparedStatement.setBinaryStream(12,inputStream);
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
            return userId;
        else
            throw new Exception("Error!");
    }

    private String createNewId(){
        String result="";
        Random random=new Random();
        for(int i=0;i<5;i++){
            result+=random.nextInt(10);
        }
        result=(random.nextInt(9)+1)+result;
        return result;
    }
}
