package module;

/*用于客户-在发布的外包界面*/
public class Project_In_Published {
    private String proName;
    private String publishTime;
    private String deadLine;

    public Project_In_Published(String proName, String publishTime, String deadLine) {
        this.proName = proName;
        this.publishTime = publishTime;
        this.deadLine = deadLine;
    }

    public Project_In_Published() {

    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }
}
