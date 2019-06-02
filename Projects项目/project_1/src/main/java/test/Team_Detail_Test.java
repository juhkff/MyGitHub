package test;

import connection.MyConnection;
import module.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Team_Detail_Test {
    public static void main(String[] args){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT * FROM users WHERE RealName=?";
        User user=new User();
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"888");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                user.setEmailAddress(resultSet.getString("EmailAddress"));      //getString()方法返回null可以吗？——可以
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(user.getEmailAddress());
    }
}
