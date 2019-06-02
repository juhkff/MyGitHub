package com.method;

import com.connection.MyConnection;
import com.model.Project_In_Show;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*外包总览界面*/
public class Project_Show {
    //获得未接外包列表
    public ArrayList<Project_In_Show> getProjectList() {
        ArrayList<Project_In_Show> project_in_showArrayList = new ArrayList<Project_In_Show>();
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT ProName,ProTags,ProFounder,ProContent,PublicTime FROM projects WHERE IsRequired=? ORDER BY PublicTime DESC";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project_In_Show project_in_show = new Project_In_Show();
                project_in_show.setProName(resultSet.getString(1));
                project_in_show.setProTags(resultSet.getString(2));
                project_in_show.setUserName(resultSet.getString(3));
                project_in_show.setProContent(resultSet.getString(4));

                project_in_showArrayList.add(project_in_show);
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

        return project_in_showArrayList;
    }

    public ArrayList<Project_In_Show> searchProjects(String proTags) {
        if (proTags.equals("")) {
            return getProjectList();
        } else {
            ArrayList<Project_In_Show> project_in_showArrayList = new ArrayList<Project_In_Show>();
            MyConnection myConnection = new MyConnection();
            Connection connection = myConnection.getConnection();
            PreparedStatement preparedStatement = null;
            String sql = "SELECT ProName,ProTags,ProFounder,ProContent,PublicTime FROM projects WHERE IsRequired=0 ORDER BY PublicTime DESC";
            proTags = proTags.replaceAll(" +", " ");  //把多个空格替换为单个空格，" +"表示连续的多个空格
            if (proTags.startsWith(" "))
                proTags=proTags.substring(1);
            String[] proTagList = proTags.split(" ");
            for (String eachTag : proTagList) {
                sql += "AND ProTags LIKE '%" + eachTag + "%' ";
            }
            try {
                preparedStatement = connection.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Project_In_Show project_in_show = new Project_In_Show();
                    project_in_show.setProName(resultSet.getString(1));
                    project_in_show.setProTags(resultSet.getString(2));
                    project_in_show.setUserName(resultSet.getString(3));
                    project_in_show.setProContent(resultSet.getString(4));

                    project_in_showArrayList.add(project_in_show);
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

            return project_in_showArrayList;
        }
    }
}
