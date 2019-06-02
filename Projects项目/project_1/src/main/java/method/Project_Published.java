package com.method;

import connection.Conn;
import connection.MyConnection;
import module.Project;
import module.Project_In_Published;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*客户-正在发布的外包页面*/
public class Project_Published {
    @Test
    public void test1(){
        ArrayList<Project_In_Published> project_in_publishedArrayList=getPublishProjectList("busNameTest");
        for(Project_In_Published project_in_published:project_in_publishedArrayList){
            System.out.println(project_in_published.getProName()+"\t"+project_in_published.getPublishTime()+"\t"+project_in_published.getDeadLine());
        }
    }
    //获得正在发布的外包列表
    public ArrayList<Project_In_Published> getPublishProjectList(String userName){
        ArrayList<Project_In_Published> project_in_publishedArrayList=new ArrayList<Project_In_Published>();
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT ProName,PublicTime,SettedTime FROM projects WHERE IsRequired=? AND ProFounder=?";        //当有人请求接受时从列表移除
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"0");
            preparedStatement.setString(2,userName);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                project_in_publishedArrayList.add(new Project_In_Published(resultSet.getString(1),
                        resultSet.getString(2),resultSet.getString(3)));
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

        return project_in_publishedArrayList;
    }

    //获得外包详细的信息
    public Project getProjectDetail(String proName){
        return new Project_Details().getOneProject(proName);
    }

    @Test
    public void test2(){
        System.out.println(deleteProject("testName421"));
    }
    //取消发布
    public String deleteProject(String proName){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="DELETE FROM projects WHERE ProName=?";
        int result=-1;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,proName);
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
