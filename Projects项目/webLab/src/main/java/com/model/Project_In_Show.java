package com.model;

/*专门用于在外包总览界面显示用的类*/

public class Project_In_Show {
    private String ProName;
    private String ProTags;
    private String UserName;
    private String ProContent;

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public String getProTags() {
        return ProTags;
    }

    public void setProTags(String proTag) {
        ProTags = proTag;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getProContent() {
        return ProContent;
    }

    public void setProContent(String proContent) {
        ProContent = proContent;
    }
}
