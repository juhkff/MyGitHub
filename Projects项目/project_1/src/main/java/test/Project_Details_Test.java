package test;

import method.Project_Details;
import module.Project;

public class Project_Details_Test {
    public static void main(String[] args) {
        Project_Details project_details = new Project_Details();
        Project project = project_details.getOneProject("testName");
        System.out.println(project.getContent());
        System.out.println(project.getPublicTime() + "\n");        //没有发布时间可以么?——可以，自动赋为null

        String teamName=project_details.getTeamName("222");
        System.out.println(teamName);

        String result=project_details.sendRequest("testName","2",teamName);
        System.out.println(result);
    }
}
