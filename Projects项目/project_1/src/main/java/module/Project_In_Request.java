package module;

/*用于外包申请界面*/
public class Project_In_Request {
    private String proName;
    private String receiverName;
    private String proType;

    public Project_In_Request(String proName, String receiverName, String proType) {
        this.proName = proName;
        this.receiverName = receiverName;
        this.proType = proType;
    }

    public Project_In_Request() {

    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }
}
