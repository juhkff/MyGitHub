package module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import connection.MyConnection;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*Gson使用示范(不知道会不会用到)*/
/*Project和User两个类主要用于用Gson的jar包传Json格式的数据，不知道在页面和后台的交互中会不会用到*/
public class Moudle_Example {
    public static void main(String[] args) throws SQLException {
        //比如我从数据库调用外包表中的所有记录的所有栏
        String sql="SELECT * FROM projects";
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        //preparedStatement.setString(1,"test");
        ResultSet resultSet=preparedStatement.executeQuery();
        ArrayList<Project> projectArrayList=new ArrayList<Project>();
        while (resultSet.next()){
            Project project=new Project();
            String proName=resultSet.getString("ProName");
            String proTags=resultSet.getString("ProTags");
            String proContent=resultSet.getString("ProContent");
            String proReward=resultSet.getString("ProReward");
            String proFounder=resultSet.getString("ProFounder");
            String publicTime=resultSet.getString("PublicTime");
            String settedTime=resultSet.getString("SettedTime");
            String sendTime=resultSet.getString("SendTime");
            String proType=resultSet.getString("ProType");
            String phoneNum=resultSet.getString("PhoneNum");
            String isRequired=resultSet.getString("IsRequired");
            String requiredName=resultSet.getString("RequiredName");
            String isAgreed=resultSet.getString("IsAgreed");
            String isSended=resultSet.getString("IsSended");
            String isPassed=resultSet.getString("IsPassed");

            project.setName(proName);
            project.setTags(proTags);
            project.setContent(proContent);
            project.setReward(proReward);
            project.setFounder(proFounder);
            project.setPublicTime(publicTime);
            project.setSettedTime(settedTime);
            project.setSendTime(sendTime);
            project.setProType(proType);
            project.setPhoneNum(phoneNum);
            project.setIsRequired(isRequired);
            project.setRequiredName(requiredName);
            project.setIsAgreed(isAgreed);
            project.setIsSended(isSended);
            project.setIsPassed(isPassed);

            //添加进列表中
            projectArrayList.add(project);
        }

        //最后用Gson的方法直接将ArrayList类转换为Json格式的字符串
        Gson gson=new Gson();
        String transferString=gson.toJson(projectArrayList);
        System.out.println(transferString);

        //字符串转换回类
        Gson gson1=new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type=new TypeToken<ArrayList<Project>>(){}.getType();
        ArrayList<Project> arrayList=gson1.fromJson(transferString,type);
        Project project=arrayList.get(0);
        //注意:Json字符串中没有的类属性，就是数据库中值为空的栏，它们在转成类时可以被自动赋为null.
        System.out.println(project.getContent());
        System.out.println(project.getIsPassed());
    }
}
