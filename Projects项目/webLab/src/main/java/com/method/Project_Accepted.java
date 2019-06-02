package com.method;

import com.connection.MyConnection;
import com.model.Project;
import com.model.Project_In_Accepted;
import com.model.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*已接外包界面*/
public class Project_Accepted {
    @Test
    public void test1(){
        ArrayList<Project_In_Accepted> project_in_acceptedArrayList=getAcceptedProjectList("222");
        for(Project_In_Accepted project_in_accepted:project_in_acceptedArrayList){
            System.out.println(project_in_accepted.getProName()+"\t"+project_in_accepted.getProType()+"\t"+project_in_accepted.getDeadLine());
        }
    }
    //获得已接外包列表
    public ArrayList<Project_In_Accepted> getAcceptedProjectList(String userId){
        ArrayList<Project_In_Accepted> project_in_acceptedArrayList=new ArrayList<Project_In_Accepted>();
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        //根据用户ID找出用户名和团队名.
        String sql="SELECT UserName,TeamName FROM users WHERE UserId=?";
        String userName = null,teamName = null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                userName=resultSet.getString(1);
                teamName=resultSet.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //用户名一定有，团队名不一定有(可能为null)
        String sql2;
        if (teamName!=null){
            sql2="SELECT ProName,ProFounder,SettedTime,ProType,IsSended,IsPassed,IsAgreed FROM projects WHERE RequiredName=? OR RequiredName=?";
            try {
                preparedStatement=connection.prepareStatement(sql2);
                preparedStatement.setString(1,userName);
                preparedStatement.setString(2,teamName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            sql2="SELECT ProName,ProFounder,SettedTime,ProType,IsSended,IsPassed,IsAgreed FROM projects WHERE RequiredName=?";
            try {
                preparedStatement=connection.prepareStatement(sql2);
                preparedStatement.setString(1,userName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(7).equals("0")||resultSet.getString(7).equals("2")||(resultSet.getString(6)!=null&&resultSet.getString(6).equals("1"))) {     //error1:   !=null&&
                    continue;
                } else {
                    Project_In_Accepted project_in_accepted = new Project_In_Accepted();
                    project_in_accepted.setProName(resultSet.getString(1));
                    project_in_accepted.setPublicUser(resultSet.getString(2));
                    project_in_accepted.setDeadLine(resultSet.getString(3));
                    project_in_accepted.setProType(resultSet.getString(4));
                    project_in_accepted.setIsSended(resultSet.getString(5));
                    project_in_accepted.setIsPassed(resultSet.getString(6));
                    project_in_accepted.setCurrentType();
                    project_in_acceptedArrayList.add(project_in_accepted);
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

        return project_in_acceptedArrayList;
    }

    //点击外包名称即查看外包详情时
    public Project getDetails(String proName){
        //调用之前写的方法即可.
        return new Project_Details().getOneProject(proName);
    }

    //点击外包提供人即查看提供者详细信息时
    public User getUserInfo(String userName){
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

    @Test
    public void test2(){
        System.out.println(quitProject("testName"));
    }
    //放弃该外包
    public String quitProject(String proName){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="UPDATE projects SET ProType=null,IsRequired=?,RequiredName=null,IsAgreed=null,IsSended=null WHERE ProName=?";
        int result=-1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"0");
            preparedStatement.setString(2,proName);
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
        System.out.println(submitProject("testName"));
    }
    //提交该外包
    public String submitProject(String proName){
        //获得现在的时间
        String curTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //需要改：外包提交时间SendTime,IsSended="1",IsPassed="0"
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="UPDATE projects SET SendTime=?,IsSended=?,IsPassed=? WHERE ProName=?";
        int result=-1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,curTime);
            preparedStatement.setString(2,"1");
            preparedStatement.setString(3,"0");
            preparedStatement.setString(4,proName);
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
