package test;

import connection.Conn;
import method.Preference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        /*Connection connection=Conn.getConnection();
        String sql="SELECT * FROM projects WHERE ProName=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,"test");
        ResultSet resultSet=preparedStatement.executeQuery();
        String proTags = null;
        while (resultSet.next()){
            proTags=resultSet.getString("ProTags");
        }
        System.out.println(proTags);*/

        Preference preference=new Preference();
        String result=preference.uploadPrefer("偏好一 偏好二 偏好三","111");
        System.out.println(result);

        Connection connection=Conn.getConnection();
        String sql="SELECT Prefers FROM users WHERE UserId=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,"111");
        ResultSet resultSet=preparedStatement.executeQuery();
        String prefers = null;
        while (resultSet.next()){
            prefers=resultSet.getString("Prefers");
        }
        System.out.println(prefers);

        String[] preferList=preference.showPrefers("111");
        for(String each:preferList){
            System.out.println(each+"\n");
        }
    }
}
