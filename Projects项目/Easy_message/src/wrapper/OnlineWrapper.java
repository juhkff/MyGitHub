package wrapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import connection.Conn;
import model.contact.Contact;
import model.group.SimpleGroup;
import model.message.NoticeMessage;
import model.property.User;
import test.Client.Request;
import test.Client.RequestProperty;
import tools.Online;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static wrapper.StaticVariable.URL_ADDRESS;

public class OnlineWrapper {
    public static Map<String, Contact> showAllFriends(String userID) {
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("userID", userID);
        Request request1 = new Request(URL_ADDRESS + "/AddContact", parameter, RequestProperty.APPLICATION);
        String content = request1.doPost();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type = new TypeToken<Map<String, Contact>>() {
        }.getType();
        //Map<userID,nickName>
        Map<String, Contact> userList = gson.fromJson(content, type);
        return userList;
    }

    public static Map<String, Contact> searchFriends(String userID,String searchID) {
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("userID", userID);
        parameter.put("searchID",searchID);
        Request request1 = new Request(URL_ADDRESS + "/SearchFriend", parameter, RequestProperty.APPLICATION);
        String content = request1.doPost();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type = new TypeToken<Map<String, Contact>>() {
        }.getType();
        Map<String, Contact> userList = gson.fromJson(content, type);
        return userList;
    }

    public static ArrayList<SimpleGroup> showAllGroups(String userID){
        Map<String,String> parameters=new HashMap<>();
        parameters.put("userID",userID);
        Request request=new Request(URL_ADDRESS+"/AddGroup",parameters,RequestProperty.APPLICATION);
        String content=request.doPost();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type = new TypeToken<ArrayList<SimpleGroup>>() {
        }.getType();
        ArrayList<SimpleGroup> groupList= gson.fromJson(content, type);
        return groupList;
    }

    public static ArrayList<SimpleGroup> searchGroup(String userID,String searchID){
        Map<String,String> parameters=new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("searchID",searchID);
        Request request=new Request(URL_ADDRESS+"/SearchGroup",parameters,RequestProperty.APPLICATION);
        String content=request.doPost();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type = new TypeToken<ArrayList<SimpleGroup>>() {
        }.getType();
        ArrayList<SimpleGroup> groupList= gson.fromJson(content, type);
        return groupList;
    }

    //发送添加好友请求
    public static String sendAddRequest(String userID, String receiverID) {
        String nickName= null;
        try {
            nickName = Online.findUserByUserID(userID).getNickName();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> parameters2 = new HashMap<String, String>();
        parameters2.put("userID", userID);
        parameters2.put("nickName", nickName);
        parameters2.put("receiverID", receiverID);
        Request request2 = new Request(URL_ADDRESS + "/SendRequest", parameters2, RequestProperty.APPLICATION);
        String content1 = request2.doPost();                  //获得返回的提示信息
        return content1;
    }

    public static final User findUserByUserID(String userID) throws SQLException, IOException {
        Connection connection = Conn.getConnection();
        String sql = "SELECT * FROM userinfo WHERE userID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userID);

        ResultSet resultSet = preparedStatement.executeQuery();
        int i = 0;
        User user = null;
        while (resultSet.next()) {
            i++;
            //String userID=resultSet.getString("userID");
            String nickName = resultSet.getString("nickName");
            InputStream inputStream;
            inputStream=resultSet.getBinaryStream("headIcon");
            byte[] bytes=null;
            if(inputStream!=null){
                bytes=new byte[inputStream.available()];
                inputStream.read(bytes,0,inputStream.available());
            }
            boolean isMale = resultSet.getBoolean("isMale");
            String birthday = resultSet.getString("birthday");
            String email = resultSet.getString("email");
            String phoneNUm = resultSet.getString("phoneNum");
            String exitTime = resultSet.getString("exitTime");
            user = new User(userID, nickName, bytes,isMale, birthday, email, phoneNUm, exitTime);
        }
        Conn.Close();
        return user;
    }

    public static String agreeAdd(String userID, NoticeMessage noticeMessage){
        Map<String, String> parameters1 = new HashMap<String, String>();
        parameters1.put("userID", userID);
        parameters1.put("ID", noticeMessage.getAnotherID());
        parameters1.put("nickName", noticeMessage.getNickName());
        parameters1.put("property", String.valueOf(noticeMessage.getProperty()));
        Request request=new Request(URL_ADDRESS + "/AgreeFriend", parameters1, RequestProperty.APPLICATION);
        String result = request.doPost();                      //返回结果"success"
        return result;
    }


    //修改联系人备注、分组等信息
    public static void changeContact(String userID,String changedID,String sort,String username){
        Map<String,String> parameters=new HashMap<String, String>();
        parameters.put("userID",userID);
        parameters.put("changedID",changedID);
        parameters.put("user_name",username);
        parameters.put("sort",sort);
        Request request=new Request(URL_ADDRESS+"/ChangeContact",parameters,RequestProperty.APPLICATION);
        String result=request.doPost();
    }

    //修改联系人备注
    public static void changeContactUserName(String userID,String ID,String newName){
        Map<String,String> parameters=new HashMap<String, String>();
        parameters.put("userID",userID);
        parameters.put("ID",ID);
        parameters.put("newName",newName);
        Request request=new Request(URL_ADDRESS+"/UpdateContactName",parameters,RequestProperty.APPLICATION);
        String result=request.doPost();
    }

    //修改空分组名
    public static void changeSortName(String userID,String oldName,String newName){
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("userID", userID);
        parameter.put("oldName",oldName);
        parameter.put("newName",newName);
        Request request2 = new Request(URL_ADDRESS + "/updateSort", parameter, RequestProperty.APPLICATION);
        String result = request2.doPost();
    }

    public static void changeUserInfo(User user) {
        Map<String,String> parameters=new HashMap<String, String>();
        Gson gson=new Gson();
        String theuser=gson.toJson(user);
        parameters.put("user",theuser);
        Request request=new Request(URL_ADDRESS+"/CommitChange",parameters,RequestProperty.APPLICATION);
        String result=request.doPost();
    }

    public static void exitProgram(String userID) {
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("userID", userID);
        Request request2 = new Request(URL_ADDRESS + "/Exit", parameter, RequestProperty.APPLICATION);
        String content = request2.doPost();                                    //content:error/exit两种可能
        if (content.equals("exit")) {
            System.out.println("exit...");
            System.exit(1);
        } else if (content.equals("error"))
            System.out.println("退出异常!");
    }

}
