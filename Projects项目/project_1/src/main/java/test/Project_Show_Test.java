package test;

import method.Project_Show;
import module.Project;
import module.Project_In_Show;

import java.util.ArrayList;

public class Project_Show_Test {
    public static void main(String[] args){
        Project_Show project_show=new Project_Show();
        ArrayList<Project_In_Show> project_in_showArrayList=project_show.getProjectList();
        for(Project_In_Show project_in_show: project_in_showArrayList){
            System.out.println(project_in_show);
        }

        ArrayList<Project_In_Show> project_in_showArrayList1=project_show.searchProjects("");
        for(Project_In_Show project_in_show: project_in_showArrayList1){
            System.out.println(project_in_show.getProName());
        }
    }
}
