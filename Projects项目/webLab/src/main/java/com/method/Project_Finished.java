package com.method;

import com.connection.MyConnection;
import com.model.Project_In_Finished;
import com.model.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*已完成外包界面*/
public class Project_Finished {
    @Test
    public void test1(){
        ArrayList<Project_In_Finished> project_in_finishedArrayList=getFinishedProjectList("222");
        for(Project_In_Finished project_in_finished:project_in_finishedArrayList){
            System.out.println(project_in_finished.getProName()+"\t"+project_in_finished.getProDeadLine());
        }
    }
    //显示已完成外包列表
    public ArrayList<Project_In_Finished> getFinishedProjectList(String userID){
        ArrayList<Project_In_Finished> project_in_finishedArrayList=new ArrayList<Project_In_Finished>();
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT UserName,TeamName FROM users WHERE UserId=?";
        String userName=null;
        String teamName=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userID);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                userName=resultSet.getString(1);
                teamName=resultSet.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql2=null;
        if (teamName!=null) {
            sql2 = "SELECT ProName,ProTags,ProFounder,SettedTime,SendTime,ProType,IsPassed FROM projects WHERE RequiredName=? OR RequiredName=?";
            try {
                preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, teamName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            sql2="SELECT ProName,ProTags,ProFounder,SettedTime,SendTime,ProType,IsPassed FROM projects WHERE RequiredName=?";
            try {
                preparedStatement=connection.prepareStatement(sql2);
                preparedStatement.setString(1,userName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                if (resultSet.getString(7)==null||!resultSet.getString(7).equals("1"))
                    continue;
                else {
                    project_in_finishedArrayList.add(new Project_In_Finished(resultSet.getString(1),
                            resultSet.getString(2),resultSet.getString(3),
                            resultSet.getString(4), resultSet.getString(5),
                            resultSet.getString(6)));
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

        return project_in_finishedArrayList;
    }

    //点击外包提供人查看详情时
    public User getUserInfo(String userName){
        return new Project_Accepted().getUserInfo(userName);
    }
}
