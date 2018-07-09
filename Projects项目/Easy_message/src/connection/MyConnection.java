package connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyConnection {
    private static final String URL = "jdbc:mysql://123.207.13.112:3306/easy_message?useSSL=false&autoReconnect=true&useUnicode=true&mysqlEncoding=utf8&characterEncoding=utf-8";
    private static final String USER = "root";
    private static final String PASSWORD = "7895123";
    private java.sql.Connection connection;

    public java.sql.Connection getConnection(){
        connection=null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void Close(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        MyConnection myConnection =new MyConnection();
        java.sql.Connection connection1= myConnection.getConnection();
        String sql="SELECT * FROM userinfo";
        PreparedStatement preparedStatement=connection1.prepareStatement(sql);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            String userID=resultSet.getString("userID");
            System.out.println(userID);
        }
        MyConnection myConnection2 =new MyConnection();
        java.sql.Connection connection3= myConnection2.getConnection();
        String sql2="SELECT * FROM userinfo";
        PreparedStatement preparedStatement1=connection3.prepareStatement(sql2);

        myConnection.Close();

        ResultSet resultSet1=preparedStatement1.executeQuery();
        while (resultSet1.next()){
            System.out.println(resultSet1.getString("nickName"));
        }

        /*java.sql.MyConnection myConnection=Conn.getConnection();
        String sql="SELECT * FROM userinfo";
        PreparedStatement preparedStatement=myConnection.prepareStatement(sql);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            String userID=resultSet.getString("userID");
            System.out.println(userID);
        }
        java.sql.MyConnection connection1=Conn.getConnection();
        String sql2="SELECT * FROM userinfo";
        PreparedStatement preparedStatement2=connection1.prepareStatement(sql2);
        Conn.Close();
        ResultSet resultSet1=preparedStatement2.executeQuery();
        while (resultSet1.next()){
            System.out.println(resultSet1.getString("nickName"));
        }*/
    }
}
