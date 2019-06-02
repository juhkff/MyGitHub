package test;

import method.Project_Release;

public class Project_Release_Test {
    public static void main(String[] args){
        Project_Release project_release=new Project_Release();
        String result=project_release.uploadProject("testName421","aad 啊 d sa  我去","testContentwqa","testsda","testRewasdard","tasdUserName","testPublicTime","testPhoneNum");
        System.out.println(result);
    }
}
