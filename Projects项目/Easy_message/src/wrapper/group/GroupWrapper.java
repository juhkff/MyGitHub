package wrapper.group;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.group.Group;
import model.group.GroupMember;
import model.group.GroupMessage;
import model.group.SimpleGroup;
import model.property.User;
import test.Client.LoginClient;
import test.Client.Request;
import test.Client.RequestProperty;
import tools.Chat;
import tools.DateTime;
import wrapper.ThreadWrapper;
import wrapper.thread.ListenerAllMembersThread;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import static wrapper.StaticVariable.URL_ADDRESS;
import static wrapper.StaticVariable.groups;
import static wrapper.StaticVariable.ifbreak;

public class GroupWrapper {
    public static SimpleGroup joinGroup(String userID, String groupID, String nickName, byte[] headIcon, String exitTime, BlockingQueue<GroupMessage> groupMessages) {
        Gson gson30 = new Gson();
        String headIconTrans = gson30.toJson(headIcon);
        Map<String, String> parameters30 = new HashMap<String, String>();
//        parameters30.put("URL_ADDRESS", URL_ADDRESS);
        parameters30.put("userID", userID);
        parameters30.put("groupID", groupID);
        parameters30.put("userName", nickName);
        parameters30.put("userHeadIconTrans", headIconTrans);
        Request request30 = new Request(URL_ADDRESS + "/JoinGroup", parameters30, RequestProperty.APPLICATION);
        /**客户端获得的返回值是SimpleGroup对象**/
        String result30 = request30.doPost();
//                    System.out.println(result30);
        Gson gson31 = new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type31 = new TypeToken<SimpleGroup>() {
        }.getType();
        SimpleGroup simpleGroup = gson31.fromJson(result30, type31);

        /**将新获得的群的SimpleGroup对象添加到groups全局变量中**/
        groups.put(simpleGroup.getGroupID(), simpleGroup);

        /**添加监听此群的线程**/
        ThreadWrapper.GroupListenerThread groupListenerThread = new ThreadWrapper.GroupListenerThread(userID, groupID, exitTime, groupMessages);
        Thread thread30 = new Thread(groupListenerThread);
        thread30.start();

        return simpleGroup;
    }

    public static Group getFullGroup(String groupID) {
        SimpleGroup simpleGroup = groups.get(groupID);
        String groupName = simpleGroup.getGroupName();
        byte[] groupIcon = simpleGroup.getGroupIcon();                          /**null值**/
        String groupIcon_Trans = new Gson().toJson(groupIcon);                  /**字符串的"null"**/
        String theLatestMessage = simpleGroup.getTheLatestMessage();
        String theLatestSendTime = simpleGroup.getTheLatestSendTime();
        Map<String, String> parameters31 = new HashMap<String, String>();
        parameters31.put("URL_ADDRESS", URL_ADDRESS);
        parameters31.put("groupID", groupID);
        parameters31.put("groupName", groupName);
        parameters31.put("groupIcon", groupIcon_Trans);
        parameters31.put("theLatestMessage", theLatestMessage);
        parameters31.put("theLatestSendTime", theLatestSendTime);
        Request request31 = new Request(URL_ADDRESS + "/GetFullGroup", parameters31, RequestProperty.APPLICATION);
        String group_Trans = request31.doPost();

        Gson gson31 = new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type31 = new TypeToken<Group>() {
        }.getType();
        /**获得具有完整信息的群对象(但不包括群聊天记录)**/
        Group group = gson31.fromJson(group_Trans, type31);
        System.out.println("您进入到了该群中.");
        return group;
    }

    public static void startGroupListener(Group group) {
        /**开启监听本群成员信息变化的线程，并在此线程中处理信息的变化**/
        ListenerAllMembersThread listenerAllMembersThread = new ListenerAllMembersThread(group);
        Thread thread71 = new Thread(listenerAllMembersThread);
        thread71.start();
    }

    public static ArrayList<GroupMessage> getGroupChatList(String groupID, String exitTime) {
        /**获得该群聊天记录**/
        ArrayList<GroupMessage> groupMessages = new ArrayList<GroupMessage>();
        Map<String, String> parameters32 = new HashMap<String, String>();
        parameters32.put("groupID", groupID);
        parameters32.put("exitTime", exitTime);
        Request request32 = new Request(URL_ADDRESS + "/GetGroupChat", parameters32, RequestProperty.APPLICATION);
        String result32 = request32.doPost();
        Gson gson32 = new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type32 = new TypeToken<ArrayList<GroupMessage>>() {
        }.getType();
        /**获得聊天信息列表并输出信息到控制台上(从上次退出时间开始的所有消息都在里面)**/
        groupMessages = gson32.fromJson(result32, type32);
        if (groupMessages == null) {
            groupMessages = new ArrayList<GroupMessage>();
        }
        return groupMessages;
    }

    public static void sendMessage(String groupID, String senderID, String senderName, String text) {
        String sendTime = String.valueOf(new DateTime().getCurrentDateTime());
        /**转换编码**/
        try {
            text = Chat.encodeChinese(text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> parameters33 = new HashMap<String, String>();
        parameters33.put("groupID", groupID);
        parameters33.put("senderID", senderID);
        parameters33.put("senderName", senderName);
        parameters33.put("sendTime", sendTime);
        parameters33.put("Status", "0");
        parameters33.put("Content", text);

        Request request33 = new Request(URL_ADDRESS + "/GroupSend", parameters33, RequestProperty.APPLICATION);
        String result33 = request33.doPost();
        System.out.println("发送成功!");
    }

    public static void sendImg(String groupID, String senderID, String senderName, String img_path) {
        java.io.File Img = new java.io.File(img_path);
        InputStream inputStream = null;
        byte[] ImgBytes = new byte[0];
        try {
            inputStream = new FileInputStream(Img);
            if (inputStream != null) {
                ImgBytes = new byte[inputStream.available()];
                inputStream.read(ImgBytes, 0, inputStream.available());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送图片
        Gson gson = new Gson();
        String ImgByteTrans = gson.toJson(ImgBytes);
        String sendTime = String.valueOf(new DateTime().getCurrentDateTime());
        Map<String, String> parameters15 = new HashMap<String, String>();
        parameters15.put("senderID", senderID);
        parameters15.put("senderName", senderName);
        parameters15.put("sendTime", sendTime);
        parameters15.put("groupID", groupID);
        parameters15.put("ImgBytes", ImgByteTrans);
        Request request15 = new Request(URL_ADDRESS + "/SendGroupImg", parameters15, RequestProperty.APPLICATION);
        String result15 = request15.doPost();
        if (result15.equals("success"))
            System.out.println("图片发送成功!");
        /*
        else if (result15.equals("error"))
             System.out.println("图片发送失败...");
        else
             System.out.println("出错?");
        */
    }

    public static void sendImg(String groupID, String senderID, String senderName, java.io.File Img) {
//        java.io.File Img = new java.io.File(img_path);
        InputStream inputStream = null;
        byte[] ImgBytes = new byte[0];
        try {
            inputStream = new FileInputStream(Img);
            if (inputStream != null) {
                ImgBytes = new byte[inputStream.available()];
                inputStream.read(ImgBytes, 0, inputStream.available());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送图片
        Gson gson = new Gson();
        String ImgByteTrans = gson.toJson(ImgBytes);
        String sendTime = String.valueOf(new DateTime().getCurrentDateTime());
        Map<String, String> parameters15 = new HashMap<String, String>();
        parameters15.put("senderID", senderID);
        parameters15.put("senderName", senderName);
        parameters15.put("sendTime", sendTime);
        parameters15.put("groupID", groupID);
        parameters15.put("ImgBytes", ImgByteTrans);
        Request request15 = new Request(URL_ADDRESS + "/SendGroupImg", parameters15, RequestProperty.APPLICATION);
        String result15 = request15.doPost();
        if (result15.equals("success"))
            System.out.println("图片发送成功!");
        /*
        else if (result15.equals("error"))
             System.out.println("图片发送失败...");
        else
             System.out.println("出错?");
        */
    }

    public static boolean isCreator(GroupMember groupMember) {
        if (groupMember.getUserStatus() == 2)
            return true;
        else
            return false;
    }

    public static boolean updateGroupInfo(String userID, Group group, String newGroupName, byte[] newGroupIcon, String newGroupIntro) {
        System.out.println("\n检测您的身份...");
//        boolean isCreator = tools.Group.isCreator(group.getGroupMember(userID), group.getGroupID());
        boolean isCreator = isCreator(group.getGroupMember(userID));
        if (!isCreator) {
            return false;
        } else {
            group.setGroupName(newGroupName);
            group.setGroupIcon(newGroupIcon);
            group.setGroupIntro(newGroupIntro);

            /**提交修改后的群信息资料**/
            Map<String, String> parameters40 = new HashMap<String, String>();
            Gson gson40 = new Gson();
            String groupTrans = gson40.toJson(group);
            parameters40.put("groupTrans", groupTrans);
            Request request40 = new Request(URL_ADDRESS + "/ChangeGroupInfo", parameters40, RequestProperty.APPLICATION);
            String result40 = request40.doPost();
            System.out.println(result40);
            return true;
        }
    }

    public static void updateGroupInfo(Group group, String newGroupName, byte[] newGroupIcon, String newGroupIntro) {
        group.setGroupName(newGroupName);
        group.setGroupIcon(newGroupIcon);
        group.setGroupIntro(newGroupIntro);

        /**提交修改后的群信息资料**/
        Map<String, String> parameters40 = new HashMap<String, String>();
        Gson gson40 = new Gson();
        String groupTrans = gson40.toJson(group);
        parameters40.put("groupTrans", groupTrans);
        Request request40 = new Request(URL_ADDRESS + "/ChangeGroupInfo", parameters40, RequestProperty.APPLICATION);
        String result40 = request40.doPost();
        System.out.println(result40);
    }

    public static void quitGroup(String userID, String groupID) {
        Map<String, String> parameters35 = new HashMap<String, String>();
        parameters35.put("userID", userID);
        parameters35.put("groupID", groupID);
        Request request35 = new Request(URL_ADDRESS + "/QuitGroup", parameters35, RequestProperty.APPLICATION);
        String result35 = request35.doPost();

        /**将全局变量群列表中的相应群删除**/
        groups.remove(groupID);
        /**将判断键值对中该群ID对应的值改为false**/
        ifbreak.replace(groupID, false);

        /**再加载一次登录时读取群列表的操作*
        *//**实际程序应该用不到这步**//*
        ThreadWrapper.GroupListThread groupListThread = new ThreadWrapper.GroupListThread(userID);
        Thread thread7 = new Thread(groupListThread);
        thread7.start();*/
    }

    public static SimpleGroup joinGroup(User user, String groupID, BlockingQueue<GroupMessage> groupMessages) {
        return joinGroup(user.getUserID(),groupID,user.getNickName(),user.getHeadIcon(),user.getExitTime(),groupMessages);
    }

    public static void createNewGroup(String groupName, String userID, String headIcon_Trans, String nickName,String exitTime,BlockingQueue<GroupMessage> groupMessages) {
        String createTime = String.valueOf(new DateTime().getCurrentDateTime());
        Map<String, String> parameters20 = new HashMap<String, String>();
        parameters20.put("groupName", groupName);
        parameters20.put("creatorID", userID);
        parameters20.put("headIcon", headIcon_Trans);
        parameters20.put("createTime", createTime);
        parameters20.put("creatorName", nickName);
        Request request20 = new Request(URL_ADDRESS + "/CreateNewGroup", parameters20, RequestProperty.APPLICATION);
        /**servlet返回新创建的群的ID**/
        String result20 = request20.doPost();
        String groupID = result20;
        if (!request20.equals("error!CreateGroupServlet in Group.createNewGroup")) {
            System.out.println("创建群成功!群ID: " + groupID);
        }
        /**添加监听此群消息的线程**/
        ThreadWrapper.GroupListenerThread groupListenerThread = new ThreadWrapper.GroupListenerThread(userID, groupID, exitTime, groupMessages);
        Thread thread20 = new Thread(groupListenerThread);
        thread20.start();
    }
}
