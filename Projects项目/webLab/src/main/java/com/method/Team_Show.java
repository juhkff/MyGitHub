package com.method;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.connection.MyConnection;
import com.model.Student_In_Team;
import com.model.Team;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*团队总览界面(用户没有团队时的界面)*/
    /*@Test
    public void test(){
        ArrayList<Team> teamArrayList=getTeamList();
        for(Team team:teamArrayList){
            System.out.println(team.getTeamName()+"\t"+team.getTeamNum()+"\t"+team.getTeamIntro()+"\t"+team.getTeamLeader()+"\t"+team.getTeamMember());
        }
    }*/
public class Team_Show {
    //测试方法


    //用户未加入团队，加载团队列表
    public ArrayList<Team> getTeamList(){
        ArrayList<Team> teamArrayList=new ArrayList<Team>();
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        String sql="SELECT * FROM teams";
        try {
            preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Team team=new Team();
                team.setTeamName(resultSet.getString("TeamName"));
                team.setTeamNum(resultSet.getInt("TeamNum"));
                team.setTeamIntro(resultSet.getString("TeamIntro"));
                team.setTeamMember(resultSet.getString("TeamMember"));
                team.setTeamLeader(resultSet.getString("TeamLeader"));
                teamArrayList.add(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        return teamArrayList;
    }

   /* @Test
    public void test2(){
        String result2=addMember("stuIdTest2","realNameTest2","teamNameTest");
        System.out.println(result2);
    }*/
    //用户申请加入团队
    public String addMember(String stuId,String realName,String teamName){//[{"realName":"realNameTest","stuId":"stuIdTest"},{"realName":"realNameTest2","stuId":"stuIdTest2"}]
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        //修改团队表中的成员栏
        String sql="SELECT TeamMember,TeamNum FROM teams WHERE TeamName=?";
        String teamMember=null;
        int teamNum=0;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,teamName);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                teamMember=resultSet.getString(1);
                teamNum=resultSet.getInt(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //加入新成员
        Type type=new TypeToken<ArrayList<Student_In_Team>>(){}.getType();
        Gson gson=new GsonBuilder().enableComplexMapKeySerialization().create();
        ArrayList<Student_In_Team> student_in_teamArrayList=gson.fromJson(teamMember,type);
        student_in_teamArrayList.add(new Student_In_Team(realName,stuId));

        gson=new Gson();
        teamMember=gson.toJson(student_in_teamArrayList);
        teamNum++;
        int result2=-1;
        try {
            String sql2="UPDATE teams SET TeamMember=?,TeamNum=? WHERE TeamName=?";
            preparedStatement=connection.prepareStatement(sql2);
            preparedStatement.setString(1,teamMember);
            preparedStatement.setInt(2,teamNum);
            preparedStatement.setString(3,teamName);
            result2=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //更改用户表中该用户的数据
        int result3=-1;
        try {
            String sql3="UPDATE users SET TeamName=? WHERE RealName=? AND Status2=?";
            preparedStatement=connection.prepareStatement(sql3);
            preparedStatement.setString(1,teamName);
            preparedStatement.setString(2,realName);
            preparedStatement.setString(3,stuId);
            result3=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        if(result2+result3>=2)
            return "0";         //成功
        else
            return "1";         //失败(error)
    }


    /*@Test
    public void test3(){
        String result=createTeam("userNameTest","realNameTest","stuIdTest","teamNameTest","teamIntroTest");
        System.out.println(result);
    }*/

    //用户创建新团队
    public String createTeam(String userName,String realName,String stuId,String teamName,String teamIntro){
        ArrayList<Student_In_Team> student_in_teamArrayList=new ArrayList<Student_In_Team>();
        Student_In_Team student_in_team=new Student_In_Team(realName,stuId);
        student_in_teamArrayList.add(student_in_team);

        String teamMember=new Gson().toJson(student_in_teamArrayList);

        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=null;
        int result1=-1;
        String sql="INSERT INTO teams(TeamName,TeamNum,TeamIntro,TeamMember,TeamLeader) VALUES (?,?,?,?,?)";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,teamName);
            preparedStatement.setInt(2,1);
            preparedStatement.setString(3,teamIntro);
            preparedStatement.setString(4,teamMember);
            preparedStatement.setString(5,userName);
            result1=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int result2=-1;
        String sql2="UPDATE users SET IsLeader=?,TeamName=? WHERE UserName=? AND RealName=?";
        try {
            preparedStatement=connection.prepareStatement(sql2);
            preparedStatement.setString(1,"1");
            preparedStatement.setString(2,teamName);
            preparedStatement.setString(3,userName);
            preparedStatement.setString(4,realName);
            result2=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement!=null)
                preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConnection.Close();

        if (result1+result2>=2)
            return "0";             //成功
        else
            return "1";             //失败(error)
    }

}
