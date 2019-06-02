package com.method;

import com.connection.MyConnection;
import com.model.Project_In_Completed;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*客户-已经结束的外包界面*/
public class Project_Completed {

    @Test
    public void test1(){
        ArrayList<Project_In_Completed> project_in_completedArrayList=getCompletedProjectList("busNameTest");
        for(Project_In_Completed project_in_completed:project_in_completedArrayList){
            System.out.println(project_in_completed.getProName()+"\t"+project_in_completed.getProType()+"\t"+project_in_completed.getPublishTime());
        }
    }
    //获得已完成外包列表
    public ArrayList<Project_In_Completed> getCompletedProjectList(String userName){
        ArrayList<Project_In_Completed> project_in_completedArrayList=new ArrayList<Project_In_Completed>();
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT ProName,PublicTime,SendTime,ProType,RequiredName FROM projects WHERE ProFounder=? AND IsPassed=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,"1");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                project_in_completedArrayList.add(new Project_In_Completed(resultSet.getString(1),
                        resultSet.getString(2),resultSet.getString(3),
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

        return project_in_completedArrayList;
    }
}
