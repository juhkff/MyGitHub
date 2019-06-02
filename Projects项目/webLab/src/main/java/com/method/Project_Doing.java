package com.method;

import com.connection.MyConnection;
import com.model.Project;
import com.model.Project_In_Request;
import com.model.Team;
import com.model.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*客户-正在进行的外包界面*/
public class Project_Doing {

    @Test
    public void test1(){
        ArrayList<Project_In_Request> project_in_requestArrayList=getDoingProjectList("busNameTest");
        for(Project_In_Request project_in_request:project_in_requestArrayList){
            System.out.println(project_in_request.getProName()+"\t"+project_in_request.getProType()+"\t"+project_in_request.getReceiverName());
        }
    }
    //获得正在进行的外包的列表
    public ArrayList<Project_In_Request> getDoingProjectList(String userName){
        ArrayList<Project_In_Request> project_in_requestArrayList=new ArrayList<Project_In_Request>();
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT ProName,RequiredName,ProType FROM projects WHERE ProFounder=? AND IsAgreed=? AND IsSended=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,"1");
            preparedStatement.setString(3,"0");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                project_in_requestArrayList.add(new Project_In_Request(resultSet.getString(1),
                        resultSet.getString(2), resultSet.getString(3)));
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

        return project_in_requestArrayList;
    }

    //获得外包详情
    public Project getProjectDetails(String proName){
        return new Project_Details().getOneProject(proName);
    }

    //获得用户详情
    public User getUserDetails(String userName){
        return new Project_Request().getUserDetails(userName);
    }

    //获得团队详情
    public Team getTeamDetails(String teamName){
        return new Team_Detail().getTeamDetails(teamName);
    }

}
