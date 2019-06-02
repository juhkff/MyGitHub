package module;

/*用于已经完成的外包界面*/
public class Project_In_Completed {
    private String proName;
    private String publishTime;
    private String finishTime;
    private String proType;
    private String receiverName;

    public Project_In_Completed(String proName, String publishTime, String finishTime, String proType, String receiverName) {
        this.proName = proName;
        this.publishTime = publishTime;
        this.finishTime = finishTime;
        this.proType = proType;
        this.receiverName = receiverName;
    }

    public Project_In_Completed(String proName, String publishTime, String finishTime, String proType) {
        this.proName = proName;
        this.publishTime = publishTime;
        this.finishTime = finishTime;
        this.proType = proType;
    }

    public Project_In_Completed() {

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

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
