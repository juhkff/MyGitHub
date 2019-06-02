package com.method;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.connection.MyConnection;
import com.model.Student_In_Team;
import com.model.Team;
import com.model.User;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*某个团队的团队界面(用户加入团队的界面)*/
public class Team_Detail {

    /*@Test
    public void test2(){
        Team team=getTeamDetails("teamNameTest");
        System.out.println(team.getTeamName()+"\t"+team.getTeamNum()+"\t"+team.getTeamLeader()+"\t"+team.getTeamIntro()+"\t"+team.getTeamMember());
    }*/
    //获得团队的详细信息(团队名，团队介绍，团队成员(Json)，团队成员数量)
    public Team getTeamDetails(String teamName){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT TeamName,TeamNum,TeamIntro,TeamMember,TeamLeader FROM teams WHERE TeamName=?";
        String teamMember=null;
        Team team=new Team();
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,teamName);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                team.setTeamName(resultSet.getString(1));
                team.setTeamNum(resultSet.getInt(2));
                team.setTeamIntro(resultSet.getString(3));
                team.setTeamMember(resultSet.getString(4));
                team.setTeamLeader(resultSet.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    /*@Test
    public void test3(){
        ArrayList<Student_In_Team> student_in_teamArrayList=getMemberList("teamNameTest");
        for(Student_In_Team student_in_team:student_in_teamArrayList){
            System.out.println(student_in_team.getRealName()+"\t"+student_in_team.getStuId());
        }
    }*/
    //获得某个团队的所有成员信息(仅有真实姓名和学号)
    public ArrayList<Student_In_Team> getMemberList(String teamName){
        ArrayList<Student_In_Team> student_in_teamArrayList=null;
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT TeamMember FROM teams WHERE TeamName=?";
        String teamMember=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,teamName);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                teamMember=resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Type type=new TypeToken<ArrayList<Student_In_Team>>(){}.getType();
        Gson gson=new GsonBuilder().enableComplexMapKeySerialization().create();
        student_in_teamArrayList=gson.fromJson(teamMember,type);

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        return student_in_teamArrayList;
    }

    /*@Test
    public void test4(){
        User user=getUserInfomation("realNameTest2");
        System.out.println(user.getUserId()+"\t"+user.getEmailAddress()+"\t"+
        user.getIdCard()+"\t"+user.getIsLeader()+"\t"+user.getPassWord()+"\t"+user.getPhoneNum()+
        "\t"+user.getPrefers()+"\t"+user.getSex()+"\t"+user.getStatus1()+"\t"+user.getStatus2()+
                "\t"+user.getUserType()+"\t"+user.getRealName()+"\t"+user.getTeamName()+"\t"+user.getUserName());
    }*/
    //获得某个团队成员的详细个人信息
    public User getUserInfomation(String realName){
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT * FROM users WHERE RealName=?";
        User user=new User();
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,realName);
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
        return user;
    }
}
