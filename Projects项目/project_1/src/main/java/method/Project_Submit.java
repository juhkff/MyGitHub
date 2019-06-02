package com.method;

import connection.MyConnection;
import module.Project;
import module.Project_In_Submit;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*客户-提交列表界面*/
public class Project_Submit {
    @Test
    public void test1(){
        ArrayList<Project_In_Submit> getSubmitProjectList=getSubmitProjectList("busNameTest");
        for(Project_In_Submit project_in_submit:getSubmitProjectList){
            System.out.println(project_in_submit.getProName()+"\t"+project_in_submit.getProType()+"\t"+project_in_submit.getReceiverName());
        }
    }
    //获得提交列表
    public ArrayList<Project_In_Submit> getSubmitProjectList(String userName){
        ArrayList<Project_In_Submit> project_in_submitArrayList=new ArrayList<Project_In_Submit>();
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT ProName,RequiredName,ProType,SettedTime,SendTime FROM projects WHERE ProFounder=? AND IsSended=? AND IsPassed=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,"1");
            preparedStatement.setString(3,"0");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                project_in_submitArrayList.add(new Project_In_Submit(resultSet.getString(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4),resultSet.getString(5)));
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

        return project_in_submitArrayList;
    }

    @Test
    public void test2(){
        System.out.println(passProject("testName"));
    }
    //(审核)通过该项目
    public String passProject(String proName){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="UPDATE projects SET IsPassed=? WHERE ProName=?";
        int result=-1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"1");
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

    public Project getProjectDetail(String proName){
        return new Project_Details().getOneProject(proName);
    }
}
