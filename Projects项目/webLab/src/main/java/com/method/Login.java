package com.method;

import com.connection.MyConnection;
import com.model.EasyUser;
import com.model.User;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Login {
    public String processLogin(String userId, String passWord) {
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT PassWord FROM users WHERE UserId=?";
        String result = "";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                result = "1";    //没有这个用户名.
            } else {
                resultSet.previous();   //向前滚动一条数据
                while (resultSet.next()) {
                    if (resultSet.getString(1).equals(passWord)) {
                        result = "0";    //成功
                    } else {
                        result = "2";    //密码不正确
                    }
                }
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

        return result;
    }

    public User getUserInfo(String userId) {
        User user = new User();
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM users WHERE UserId=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
                user.setTeamName(resultSet.getString("TeamName"));

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

        return user;
    }

    public User getUserInfo(String shouldBeNull,String userName) {
        if (shouldBeNull!=null)
            return null;
        User user = new User();
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM users WHERE UserName=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setUserId(resultSet.getString("userId"));
                user.setUserName(userName);
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
            if (preparedStatement != null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        return user;
    }

    //获得头像
    public InputStream getHeadIcon(String userId) {
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT HeadIcon FROM users WHERE UserId=?";
        InputStream inputStream = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                inputStream = resultSet.getBinaryStream("HeadIcon");
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

        return inputStream;
    }

    //获得头像
    public InputStream getHeadIcon(String shouldBeNull,String userName) {
        if (shouldBeNull!=null)
            return null;
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT HeadIcon FROM users WHERE UserName=?";
        InputStream inputStream = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                inputStream = resultSet.getBinaryStream("HeadIcon");
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

        return inputStream;
    }

    //更改头像
    public InputStream updateHeadIcon(String userId, InputStream inputStream) throws Exception {
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE users SET HeadIcon=? WHERE UserId=?";
        int result = -1;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBinaryStream(1, inputStream);
            preparedStatement.setString(2, userId);
            result = preparedStatement.executeUpdate();
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

        if (result > 0)
            return inputStream;
        else
            throw new Exception("Error!");
    }

    //修改用户信息
    public String updateUserInfo(String userName, String phoneNum, String emailAddress, String userId) throws Exception {
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE users SET UserName=?,PhoneNum=?,EmailAddress=? WHERE UserId=?";
        int result = -1;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, phoneNum);
            preparedStatement.setString(3, emailAddress);
            preparedStatement.setString(4, userId);
            result = preparedStatement.executeUpdate();
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

        if (result > 0)
            return "0";
        else
            throw new Exception("Error!");
    }

    public ArrayList<EasyUser> searchUsers(String[] userInfos) {
        ArrayList<EasyUser> easyUsers = new ArrayList<EasyUser>();
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = null;
        ResultSet resultSet = null;
        try {
            for (String each : userInfos) {
                sql = "SELECT UserId,UserName,PhoneNum,EmailAddress,Status1,Status2,RealName FROM users WHERE UserName=? OR PhoneNum=? OR EmailAddress=? OR Status1=? OR Status2=? OR RealName=?";
                preparedStatement = connection.prepareStatement(sql);
                for (int i = 1; i < 7; i++) {
                    preparedStatement.setString(i, each);
                }
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next())
                    continue;
                else {
                    resultSet.previous();
                    break;
                }
            }
            while (resultSet.next()) {
                boolean isthis=true;
                for (String each : userInfos) {
                    if (!each.equals(resultSet.getString(2)) && !each.equals(resultSet.getString(3))
                            && !each.equals(resultSet.getString(4)) && !each.equals(resultSet.getString(5))
                            && !each.equals(resultSet.getString(6)) && !each.equals(resultSet.getString(7))) {
                        isthis=false;
                        break;
                    }
                }
                if (isthis) {
                    easyUsers.add(new EasyUser(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getString(6), resultSet.getString(7)));
                }
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

        return easyUsers;
    }
}
