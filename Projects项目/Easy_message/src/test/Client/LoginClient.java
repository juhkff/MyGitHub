package test.Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.contact.Contact;
import model.file.FileProgress;
import model.file.MyFileInfo;
import model.group.Group;
import model.group.GroupMember;
import model.group.GroupMessage;
import model.group.SimpleGroup;
import model.message.ChatMessage;
import model.message.FileMessage;
import model.message.NoticeMessage;
import model.paint.Pixel;
import model.property.User;
import tools.Chat;
import tools.DateTime;
import tools.file.File;
import tools.file.model.FileSenderThread;
import tools.file.model.UDPUtils;
import wrapper.*;
import wrapper.group.GroupWrapper;
import wrapper.thread.OnlineTransThread;
import wrapper.thread.ReceiveFileThread;
import wrapper.thread.TransFileThread;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static wrapper.StaticVariable.*;

public class LoginClient {

    public static void main(String[] args) throws Exception {
        //String userID = "2461247724";               //juhkff
        String userID = "8133523681";               //juhkgf
        String passWord = "aqko251068";

        /**登录工作过程**/
        String nickName;
        byte[] headIcon;
        String exitTime;


        Scanner scanner = new Scanner(System.in);
        String result = LoginWrapper.login(userID, passWord);
        User user = null;
        if (result.equals("null"))
            System.out.println("帐号不存在，登录失败!");
        else if (result.equals("false"))
            System.out.println("密码错误，登录失败!");
        else if (result.equals("error"))
            System.out.println("发生了未知错误...");
        else if (result.equals("true")) {
            System.out.println("帐号密码验证成功!");
            user = LoginWrapper.getUser(userID, passWord);
            System.out.println("获得用户信息!");
            /**获取用户的nickName属性**/
            nickName = user.getNickName();
            headIcon = user.getHeadIcon();
            exitTime = user.getExitTime();


            BlockingQueue<ChatMessage> chatMessages = new ArrayBlockingQueue<ChatMessage>(1);                     //接收消息的线程，客户端写take方法块
            BlockingQueue<FileProgress> fileProgresses = new ArrayBlockingQueue<FileProgress>(1);                  //接收到文件接收时的进度条，理论上只能同时接收和发送一个文件
            BlockingQueue<FileMessage> fileMessages = new ArrayBlockingQueue<FileMessage>(1);                     //用于接收直传文件的请求，客户端写take方法块
            BlockingQueue<NoticeMessage> noticeMessages = new ArrayBlockingQueue<NoticeMessage>(1);     //用于接收好友请求等消息，客户端写take方法块
            BlockingQueue<ChatMessage> files = new ArrayBlockingQueue<ChatMessage>(1);                  //单独将聊天消息中的离线文件消息拿出来(聊天消息中仍包括离线文件消息)，客户端写take方法块
            BlockingQueue<GroupMessage> groupMessages = new ArrayBlockingQueue<GroupMessage>(1);        //用于接收群聊消息，客户端写take方法块

            BlockingQueue<Pixel> pixels=new ArrayBlockingQueue<Pixel>(10000);               //作死设10000，不知道会有什么反应

            //创建所有线程
            ThreadWrapper.startAllThread(userID, exitTime, chatMessages, fileProgresses, fileMessages, noticeMessages, files, groupMessages,pixels);

            /**---------------------------------以下情景为模拟用户使用程序，注意聊天应自动建立线程(不建立线程则无法实现多窗口聊天)--------------------------------**/

            String nextCommand;
            while (true) {
                /**交互指令**/
                System.out.println("\n聊天/Chat\t进入群操作界面/Groups\t添加好友/Add\t添加群/AddGroup退出程序/Exit\t处理请求/Deal\t上传离线文件/Submit\t创建群/CreateGroup\t更改个人信息/UpdateInfo");
                System.out.print("输入 您要进行的操作 :______\b\b\b\b\b\b");
                nextCommand = scanner.nextLine();

                if (nextCommand.equals("Chat")) {
                    /**与聊天有关的操作**/
                    System.out.println("--------------------------------------------------------------好友聊天界面--------------------------------------------------------------");

                    System.out.print("\n输入进行聊天的userID(0退出):______\b\b\b\b\b\b");
                    String anotherID;
                    int i = 0;
                    if (scanner.next().equals("0"))
                        continue;
                    do {
                        if (i > 0)
                            System.out.print("\nID不对，请重新输入!:______\b\b\b\b\b\b");
                        i++;
                        anotherID = scanner.next();
                    } while (anotherID.length() != 10 && anotherID.length() != 6);
                    //取得userID
                    /**
                     * 打开聊天界面
                     * **/

                    /**本模拟程序只能模拟与一个用户聊天的过程，实际应该可以与多个用户**/

                    ArrayList<ChatMessage> historyChatMessages = ChatWrapper.getSimpleChat(userID, anotherID);   //historyChatMessages存储聊天记录
                    int size = historyChatMessages.size();
                    if (size > 7)
                        size = 5;                                                                                        //历史记录过多（大于7）则只输出后5条
                    for (int index = historyChatMessages.size() - size; index < historyChatMessages.size(); index++) {
                        ChatMessage chatMessage = historyChatMessages.get(index);
                        if (chatMessage.getNature() == 1)                   //1:他向我说; 0:我向他说
                            System.out.println("" + chatMessage.getSendTime() + " : " + chatMessage.getMessage());
                        else if (chatMessage.getNature() == 0)
                            System.out.println("\t\t\t\t\t\t" + chatMessage.getSendTime() + " : " + chatMessage.getMessage());
                        else if (chatMessage.getNature() == 2)
                            System.out.println("\t\t\t\t\t\t文件:" + chatMessage.getMessage() + " sendTime : " + chatMessage.getSendTime());
                        else if (chatMessage.getNature() == 3)
                            System.out.println("文件:" + chatMessage.getMessage() + " sendTime : " + chatMessage.getSendTime());
                        else if (chatMessage.getNature() == 5)
                            System.out.println("\t\t\t\t\t\t图片:" + chatMessage.getImg() + " sendTime : " + chatMessage.getSendTime());
                        else if (chatMessage.getNature() == 6)
                            System.out.println("图片" + chatMessage.getImg() + " sendTime : " + chatMessage.getSendTime());
                    }
                    //DateTime dateTime = new DateTime();
                    byte nature = 0;
                    while (true) {
                        /**互动操作**/
                        System.out.println("\n发送消息(Exit退出):\tImg/发送图片\tSubmit/发送离线文件\tReceive/接收离线文件\tOnlineTransmit/发送在线文件\tHistory/聊天记录\t");
                        scanner.nextLine();
                        String message = scanner.nextLine();
                        if (message.equals("Exit"))
                            break;
                        else if (message.equals("History")) {
                            /**获取聊天记录**/
                            System.out.println("聊天记录");
                            int num = historyChatMessages.size();
                            if (num <= 10) {
                                for (ChatMessage chatMessage : historyChatMessages) {
                                    if (chatMessage.getNature() == 1)                   //1:他向我说; 0:我向他说
                                        System.out.println("" + chatMessage.getSendTime() + " : " + chatMessage.getMessage());
                                    else if (chatMessage.getNature() == 0)
                                        System.out.println("\t\t\t\t\t\t" + chatMessage.getSendTime() + " : " + chatMessage.getMessage());
                                    else if (chatMessage.getNature() == 2)
                                        System.out.println("\t\t\t\t\t\t文件:" + chatMessage.getMessage() + " sendTime : " + chatMessage.getSendTime());
                                    else if (chatMessage.getNature() == 3)
                                        System.out.println("文件:" + chatMessage.getMessage() + " sendTime : " + chatMessage.getSendTime());
                                    else if (chatMessage.getNature() == 5)
                                        System.out.println("\t\t\t\t\t\t图片:" + chatMessage.getImg() + " sendTime : " + chatMessage.getSendTime());
                                    else if (chatMessage.getNature() == 6)
                                        System.out.println("图片" + chatMessage.getImg() + " sendTime : " + chatMessage.getSendTime());
                                }
                            } else {
                                int j = 0;
                                String resp = "next";
                                do {
                                    if (!resp.equals("next")) {
                                        System.out.println("格式错误，请重新输入!");
                                        resp = scanner.next();
                                        continue;
                                    }
                                    j++;
                                    int k = 10 * (j - 1);
                                    for (; k < 10 * j && k < historyChatMessages.size(); k++) {
                                        ChatMessage chatMessage = historyChatMessages.get(k);
                                        if (chatMessage.getNature() == 1)                   //1:他向我说; 0:我向他说
                                            System.out.println("" + chatMessage.getSendTime() + " : " + chatMessage.getMessage());
                                        else if (chatMessage.getNature() == 0)
                                            System.out.println("\t\t\t\t\t\t" + chatMessage.getSendTime() + " : " + chatMessage.getMessage());
                                        else if (chatMessage.getNature() == 2)
                                            System.out.println("\t\t\t\t\t\t文件:" + chatMessage.getMessage() + " sendTime : " + chatMessage.getSendTime());
                                        else if (chatMessage.getNature() == 3)
                                            System.out.println("文件:" + chatMessage.getMessage() + " sendTime : " + chatMessage.getSendTime());
                                        else if (chatMessage.getNature() == 5)
                                            System.out.println("\t\t\t\t\t\t图片:" + chatMessage.getImg() + " sendTime : " + chatMessage.getSendTime());
                                        else if (chatMessage.getNature() == 6)
                                            System.out.println("图片" + chatMessage.getImg() + " sendTime : " + chatMessage.getSendTime());
                                    }
                                    if (k == historyChatMessages.size() - 1)
                                        break;
                                    System.out.println("下一页(next);退出(quit)");
                                    resp = scanner.next();
                                } while (!resp.equals("quit"));
                            }
                        } else if (message.equals("Img")) {
                            /**发送图片**/
                            System.out.println("输入您要发送的图片全路径(包括文件名及其后缀)");
                            String img_path = scanner.next();
                            String result15 = ChatWrapper.sendImg(userID, anotherID, img_path);
                            if (result15.equals("success"))
                                System.out.println("图片发送成功!");
                            else if (result15.equals("error"))
                                System.out.println("图片发送失败...");
                            else if (result15.equals("false"))
                                System.out.println("图片不存在!");
                            else
                                System.out.println("出错?");
                        } else if (!message.equals("Submit") && !message.equals("Receive") && !message.equals("OnlineTransmit")) {
                            /**发送消息**/
                            String sendResult = ChatWrapper.sendMessage(userID, anotherID, message);
                            if (sendResult.equals("try"))
                                System.out.println("外网发送中");
                            else if (sendResult.equals("success"))
                                System.out.println("离线式发送成功!");
                            else if (sendResult.equals("false"))
                                System.out.println("发送失败...");
                            else
                                System.out.println("未知错误...");
                        } else if (message.equals("Submit")) {
                            /**发送文件**/

                            /**
                             * 离线发送
                             * **/
                            try {
                                System.out.println("\n请输入文件在您PC上的全路径及文件名(包括后缀):");
                                String fileName = scanner.nextLine();

                                //发送文件的封装类方法
                                FileWrapper.sendFile(userID, anotherID, fileName, fileProgresses);

                            } catch (Exception e) {
                                System.out.println("离线文件传送失败...");
                                e.printStackTrace();
                            }
                        } else if (message.equals("Receive")) {
                            /**接收/下载 (对方发送的)离线文件**/
                            System.out.println("\n请输入您要接收的离线文件名(包括后缀):");
                            String fileName = scanner.nextLine();
                            //存放目录酌情考虑
                            System.out.println("\n请输入您要存到的地方(即本地目录):");
                            String localPath = scanner.nextLine();

                            //接收文件的封装类方法
                            FileWrapper.receiveFile(anotherID, userID, localPath, fileName, fileProgresses);

                        } else if (message.equals("OnlineTransmit")) {
                            /**
                             * Transmit online file
                             * **/
                            System.out.println("\n请输入文件在您PC上的全路径及文件名(包括后缀) (PS:路径可以有中文、但上传的文件本身不能含中文! 不能发送文件夹!):");
                            String fileName = scanner.nextLine();

                            //在线发送文件的封装类方法
                            FileWrapper.onlineTransFile(userID, anotherID, fileName/*,fileMessages*/, fileProgresses);

                        }
                    }
                } else if (nextCommand.equals("Groups")) {
                    System.out.println("请输入您想要进入的群ID");
                    String groupID = scanner.nextLine();

                    Group group = GroupWrapper.getFullGroup(groupID);
                    GroupWrapper.startGroupListener(group);
                    ArrayList<GroupMessage> groupMessageArrayList = GroupWrapper.getGroupChatList(groupID, exitTime);

                    for (GroupMessage groupMessage : groupMessageArrayList) {
                        /** 转换编码 **/
                        groupMessage.setContent(Chat.decodeChinese(groupMessage.getContent()));
                        /**PS:发送者头像是通过调用获取Group内部类指定ID的对象的方法，并调用此对象的getUserHeadIcon()方法获得的**/
                        if (groupMessage.getStatus() == 0) {
                            if (groupMessage.getContent() != null)
                                System.out.println("发送者头像:(模拟程序无法显示)" +/*(group.getGroupMember(groupMessage.getSenderID()).getUserHeadIcon()) + */groupMessage.getSenderName() + " : " + groupMessage.getContent() + "(" + groupMessage.getSendTime() + ")");
                            else if (groupMessage.getImg() != null)
                                System.out.println("发送者头像:(模拟程序无法显示)"/*+(group.getGroupMember(groupMessage.getSenderID()).getUserHeadIcon())*/ + groupMessage.getSenderName() + " : " + "图片: " +/*groupMessage.getContent()*/"(无法显示)" + "(" + groupMessage.getSendTime() + ")");
                        } else if (groupMessage.getStatus() == 1) {
                            System.out.println("文件 From: " + groupMessage.getSenderName() + "发送者头像: (无法显示)"/*+(group.getGroupMember(groupMessage.getSenderID()).getUserHeadIcon())*/ + "\tName: " + groupMessage.getContent());
                        }
                    }

                    System.out.println("\n聊天/Send\t发送图片/SendImg\t修改此群信息/Update\t退出该群/Quit\t退出群聊/ExitChat\tSendFile/上传文件");
                    String next;
                    while (true) {
                        next = null;
                        next = scanner.nextLine();
                        if (next.equals("Send")) {
                            /**聊天**/
                            System.out.println("输入您想说的:");
                            String content = scanner.nextLine();
                            GroupWrapper.sendMessage(groupID, userID, nickName, content);  //PS:可以在之前把User类对象user设为全局变量，在这里直接调用user.getUserName()方法即可得到发送者（即本机用户）昵称

                        } else if (next.equals("SendImg")) {
                            /**发送群聊图片**/
                            System.out.println("输入您要发送的图片全路径(包括文件名及其后缀)");
                            String img_path = scanner.next();

                            GroupWrapper.sendImg(groupID, userID, nickName, img_path);

                        } else if (next.equals("Update")) {
                            /**修改此群信息**/
                            System.out.println("新的群昵称: ");
                            String newGroupName = scanner.nextLine();

                            System.out.println("选择新头像路径：");
                            String path = scanner.nextLine();
                            java.io.File file = new java.io.File(path);
                            InputStream inputStream = null;
                            inputStream = new FileInputStream(file);
                            byte[] newGroupIcon = null;
                            if (inputStream != null) {
                                newGroupIcon = new byte[inputStream.available()];
                                inputStream.read(newGroupIcon, 0, inputStream.available());
                                inputStream.close();
                            }
                            System.out.println("新的群介绍: ");
                            String newGroupIntro = scanner.nextLine();

                            boolean isSuccess=GroupWrapper.updateGroupInfo(userID, group, newGroupName, newGroupIcon, newGroupIntro);
//                            GroupWrapper.updateGroupInfo(group,newGroupName,newGroupIcon,newGroupIntro);

                        } else if (next.equals("Quit")) {
                            /**退出该群**/
                            GroupWrapper.quitGroup(userID,groupID);
                        } else if (next.equals("ExitChat"))
                            break;
                        else if (next.equals("SendFile")) {
                            /**发送文件**/
                            try {
                                System.out.println("\n请输入文件在您PC上的全路径及文件名(包括后缀) (PS:路径可以有中文、但上传的文件本身不能含中文! 不能发送文件夹!):");
                                String fileName = scanner.nextLine();
                                if (File.isChinese(fileName)) {
                                    System.out.println("不能有中文!");
                                    continue;
                                }
                                System.out.println("文件上传中,请等待成功提示(您可以退出此窗口,但不要退出程序...)");
                                GroupUploadThread groupUploadThread = new GroupUploadThread(userID, user.getNickName(), fileName, groupID);
                                Thread thread1 = new Thread(groupUploadThread);
                                thread1.start();
                            } catch (Exception e) {
                                System.out.println("离线文件传送失败...");
                                e.printStackTrace();
                            }
                        }
                    }
                    /**获得群聊信息和群成员列表**/
                } else if (nextCommand.equals("Add")) {
                    /**添加好友**/
                    System.out.println("--------------------------------------------------------------添加好友界面--------------------------------------------------------------");
                    Map<String, Contact> userList = OnlineWrapper.showAllFriends(userID);
//                    Map<String, Contact> userList = OnlineWrapper.searchFriends(userID,"12311");
                    if (userList != null && userList.size() > 0) {
                        System.out.println();
                        System.out.println("可添加列表:");
                        for (String ID : userList.keySet()) {
                            Contact contact = userList.get(ID);
                            //String thenickName = contact.getNickName();
                            Boolean isMale = contact.isMale();
                            String male;
                            if (isMale == null)
                                male = "未设定";
                            else if (isMale)
                                male = "男";
                            else
                                male = "女";
                            System.out.println("帐号:" + ID +
                                    "\t昵称:" + contact.getNickName() +
                                    "\t性别:" + male +
                                    "\t头像:" + /*contact.getHeadIcon()*/"无法显示" +
                                    "\t个人介绍/个性签名:" + contact.getIntro() +
                                    "\t是否在线:" + contact.isStatus()
                            );
                        }
                        System.out.print("输入您要添加的userID(0退出):__________\b\b\b\b\b\b\b\b\b\b");
                        String receiverID;
                        int i = 0;
                        if (scanner.next().equals("0"))
                            continue;
                        do {
                            if (i > 0)
                                System.out.print("\n格式不对，请重新输入!:__________\b\b\b\b\b\b\b\b\b\b");
                            i++;
                            receiverID = scanner.next();
                        } while (receiverID.length() != 10);
                        //得到userID
                        String content1 = OnlineWrapper.sendAddRequest(userID, receiverID);

                        if (content1.equals("CG"))
                            System.out.println("请求已发出，请等待对方的回复!");
                        else if (content1.equals("CF"))
                            System.out.println("您已发送过该邀请，请不要重复发送!");
                        else
                            throw new Exception("发送邀请出错!LoginClient");
                    }
                } else if (nextCommand.equals("AddGroup")) {
                    /**添加群**/
                    ArrayList<SimpleGroup> groupList = OnlineWrapper.showAllGroups(userID);
//                    ArrayList<SimpleGroup> groupList=OnlineWrapper.searchGroup(userID,"1233");
                    /**获得Group列表**/
                    for (SimpleGroup group : groupList) {
                        System.out.println(
                                "群ID: " + group.getGroupID() +
                                        "\t群昵称: " + group.getGroupName() +
                                        "\t群头像: " +/*group.getGroupIcon()*/"无法显示"
                        );
                    }

                    System.out.println("\n输入您想要加入的群:");
                    /**获得想要加入的群ID**/
                    String groupID = scanner.nextLine();;

                    SimpleGroup simpleGroup=GroupWrapper.joinGroup(userID, groupID, nickName, headIcon, exitTime, groupMessages);
//                    SimpleGroup simpleGroup1=GroupWrapper.joinGroup(user, groupID ,groupMessages);
//                    /**将新获得的群的SimpleGroup对象添加到groups全局变量中**/
//                    groups.put(simpleGroup.getGroupID(), simpleGroup);


                    /**再加载一次登录时读取群列表的操作**//*
                     *//**实际程序应该用不到这步**//*
                    ThreadWrapper.GroupListThread groupListThread = new ThreadWrapper.GroupListThread(userID);
                    Thread thread8 = new Thread(groupListThread);
                    thread8.start();*/
                } else if (nextCommand.equals("Exit")) {
                    /**退出程序的过程**/
                    OnlineWrapper.exitProgram(userID);
                } else if (nextCommand.equals("Submit")) {
                    /**上传离线文件**/
                    try {
                        System.out.println("\n请输入文件在您PC上的全路径及文件名(包括后缀)");
                        String fileName = scanner.nextLine();

                        UploadSelfThread uploadSelfThread = new UploadSelfThread(userID, fileName, fileSocketAddress, fileds);
                        Thread thread52 = new Thread(uploadSelfThread);
                        thread52.start();
                        System.out.println("文件上传中,请等待成功提示(您可以退出此窗口,但不要退出程序...");


                        /*UploadFileRequest uploadFileRequest = new UploadFileRequest(fileName);                  //指定文件
                        String response = uploadFileRequest.upLoadFile(userID);                                 //指定用户userID
                        System.out.println(response);*/
                    } catch (Exception e) {
                        System.out.println("离线文件上传失败...");
                        e.printStackTrace();
                    }
                } else if (nextCommand.equals("CreateGroup")) {
                    /**创建群聊**/
                    Gson gson = new Gson();
                    String headIcon_Trans = gson.toJson(headIcon);                    //headIcon_Transy有可能等于null类型
                    System.out.println("输入群名称:");
                    String groupName = scanner.nextLine();
                    GroupWrapper.createNewGroup(groupName,userID,headIcon_Trans,nickName,exitTime,groupMessages);

                    /*
                    *//**再加载一次登录时读取群列表的操作**//*
                    *//**实际程序应该用不到这步**//*
                    ThreadWrapper.GroupListThread groupListThread = new ThreadWrapper.GroupListThread(userID);
                    Thread thread7 = new Thread(groupListThread);
                    thread7.start();*/
                } else if (nextCommand.equals("UpdateInfo")) {
                    /**更改个人信息**/

                    /**显示个人信息**/
                    System.out.println("\n您当前的个人信息:\n");
                    System.out.println("\n帐号:" + user.getUserID() +
                            "\n密码: " + user.getPassWord() +
                            "\n昵称:" + user.getNickName() +
                            "\n性别:" + (user.isMale() == true ? "男" : "女") +
                            "\n生日:" + user.getBirthday() +
                            "\n手机号:" + user.getPhoneNum() +
                            "\n电子邮箱:" + user.getEmail() +
                            "\n头像:" +/*user.getHeadIcon()*/"无法呈现" +
                            "\n个人介绍" + (user.getIntro() == null ? "暂无" : user.getIntro()) +
                            "\n最后登录时间" + user.getExitTime());

                    /** 更新用户信息 **/
                    for (int i = 1; i < user.getPropertyNum(); i++) {
                        switch (i) {
                            case 1: {
                                System.out.println("旧密码:" + user.getPassWord() + "\t输入新密码: ");
                                user.setPassWord(scanner.nextLine());
                                break;
                            }
                            case 2: {
                                System.out.println("旧昵称:" + user.getNickName() + "\t输入新昵称: ");
                                user.setNickName(scanner.nextLine());
                                break;
                            }
                            case 3: {
                                System.out.println("旧性别:" + (user.isMale() == true ? "男" : "女") + "\t输入新性别(男输入Y，女输入N):");
                                user.setMale(((scanner.next().equals("Y")) ? true : false));
                                break;
                            }
                            case 4: {
                                System.out.println("旧生日:" + user.getBirthday() + "\t新生日(必须按照格式填写!格式: 2017-12-06 23:41:55 ):");
                                String ifisbirthday = null;
                                do {
                                    if (ifisbirthday != null)
                                        System.out.println("格式输入错误!请重新输入!");
                                    ifisbirthday = scanner.nextLine();
                                } while (!ifisbirthday.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d"));
                                user.setBirthday(ifisbirthday);
                                System.out.println("成功更新用户生日! " + ifisbirthday);
                                break;
                            }
                            case 5: {
                                System.out.println("旧手机号: " + user.getPhoneNum() + "\t新手机号: ");
                                user.setPhoneNum(scanner.next());
                                break;
                            }
                            case 6: {
                                System.out.println("旧电子邮箱: " + user.getEmail() + "\t新电子邮箱: ");
                                String newEmail = scanner.nextLine();
                                if (newEmail == null || newEmail.equals("")) {
                                    newEmail = scanner.nextLine();
                                }
                                user.setEmail(/*scanner.nextLine()*/newEmail);
                                break;
                            }
                            case 7: {
                                System.out.print("当前头像: " + "暂不显示\t新头像: ");
                                System.out.println("更新头像？(Y/N)");
                                String ifupdate = scanner.next();
                                if (ifupdate.equals("Y")) {
                                    System.out.println("选择新头像路径：");
                                    String path = scanner.nextLine();
                                    while (path == null || path.equals("")) {
                                        System.out.println("选择新头像路径：");
                                        path = scanner.nextLine();
                                    }
                                    java.io.File file = new java.io.File(path);
                                    InputStream inputStream = new FileInputStream(file);
                                    byte[] bytes;
                                    if (inputStream != null) {
                                        bytes = new byte[inputStream.available()];
                                        inputStream.read(bytes, 0, inputStream.available());
                                        user.setHeadIcon(bytes);
                                    }
                                } else {
                                    break;
                                }
                                break;
                            }
                            case 8: {
                                System.out.println("旧个人介绍: " + user.getIntro() + "\t新个人介绍: ");
                                user.setIntro(scanner.nextLine());
                                break;
                            }
                            case 9: {
                                System.out.println("上次下线时间: " + user.getExitTime());
                                break;
                            }
                        }
                    }

                    String intro = null;
                    User user1 = new User(userID, nickName, headIcon, intro);
                    OnlineWrapper.changeUserInfo(user);
                    /**提交修改**/
                    Gson gson = new Gson();
                    String commitChange = gson.toJson(user);
                    Map<String, String> parameters18 = new HashMap<String, String>();
                    parameters18.put("user", commitChange);
                    Request request18 = new Request(URL_ADDRESS + "/CommitChange", parameters18, RequestProperty.APPLICATION);
                    String result18 = request18.doPost();
                    System.out.println(result18);
                    System.out.println("修改个人信息过程结束!");

                }


                scanner.nextLine();
            }
        }
    }


    /**
     * ------------------------------------------------------主方法分割线--------------------------------------------------------
     **/


    //发送文件的线程
    private static class UploadThread implements Runnable {
        private String fileName;
        private String userID;
        private String anotherID;

        public UploadThread(String fileName, String userID, String anotherID) {
            this.fileName = fileName;
            this.userID = userID;
            this.anotherID = anotherID;
        }

        @Override
        public void run() {
            UploadFileRequest uploadFileRequest = new UploadFileRequest(fileName);                  //指定文件
            String response = null;                                 //指定用户userID
            try {
                response = uploadFileRequest.upLoadFile(userID, anotherID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (response.equals("success"))
                System.out.println("离线文件发送成功!");
        }
    }


    //发送群文件的线程
    private static class GroupUploadThread implements Runnable {
        private String userID;
        private String userName;
        private String fileName;
        private String groupID;

        public GroupUploadThread(String userID, String userName, String fileName, String groupID) {
            this.userID = userID;
            this.userName = userName;
            this.fileName = fileName;
            this.groupID = groupID;
        }

        @Override
        public void run() {
            GroupUploadFileRequest groupUploadFileRequest = new GroupUploadFileRequest(fileName);                  //指定文件
            String response = null;                                 //指定用户userID
            try {
                response = groupUploadFileRequest.upLoadFile(groupID, userID, userName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (response.equals("success"))
                System.out.println("离线文件发送成功!");
        }
    }


    //上传文件到个人文件夹的线程
    private static class UploadSelfThread implements Runnable {
        private String userID;
        private String fileName;
        private SocketAddress fileSocketAddress;
        private DatagramSocket fileds;

        public UploadSelfThread(String userID, String fileName, SocketAddress fileSocketAddress, DatagramSocket fileds) {
            this.userID = userID;
            this.fileName = fileName;
            this.fileSocketAddress = fileSocketAddress;
            this.fileds = fileds;
        }

        @Override
        public void run() {
            java.io.File file = new java.io.File(fileName);
            //System.out.println("file:\t"+file.length());
            if (file.exists()) {
                //如果文件存在（即不报错的话）
                byte[] buf = new byte[20 * 1024];
                byte[] receiveBuf = new byte[1];
                int readSize = -1;
                RandomAccessFile randomAccessFile = null;
                DatagramPacket dpk = null;
                try {
                    randomAccessFile = new RandomAccessFile(file, "r");
                    System.out.println("ranfile:\t" + randomAccessFile.length());
                    long sendCount = 0;
                    MyFileInfo messages;
                    String thefileName = fileName.split("\\\\")[fileName.split("\\\\").length - 1];
                    String info;
                    Gson gson51 = new Gson();
                    byte[] sendBytes;
                    messages = new MyFileInfo(userID, thefileName, buf);
                    while ((readSize = randomAccessFile.read(buf, 0, buf.length)) != -1) {
                        info = null;
                        messages.setFilebytes(buf);
                        info = gson51.toJson(messages);
                        sendBytes = ("MyTransFile/" + info).getBytes();
                        dpk = new DatagramPacket(sendBytes, 0, sendBytes.length, fileSocketAddress);
                        fileds.send(dpk);
                        sendCount++;
                        if (sendCount % 100 == 0 || sendCount == 1) {
                            System.out.println("Current: " + (sendCount * readSize) + " /" + file.length() + " (" + ((sendCount * readSize * 100) / file.length()) + "%)");
                        }
                        {
                            while (true) {
                                dpk.setData(receiveBuf, 0, receiveBuf.length);
                                fileds.receive(dpk);

                                // confirm server get
                                if (!UDPUtils.isEqualsByteArray(UDPUtils.successData, receiveBuf, dpk.getLength())) {
                                    System.out.println("resend ...");
                                    dpk.setData(sendBytes, 0, sendBytes.length);
                                    fileds.send(dpk);
                                } else
                                    break;
                            }
                        }
                    }
                    Map<String, String> parameters51 = new HashMap<String, String>();
                    parameters51.put("userID", userID);
                    parameters51.put("fileName", fileName);
                    Request request51 = new Request(URL_ADDRESS + "/MyUpload", parameters51, RequestProperty.APPLICATION);
                    String result51 = request51.doPost();
                    System.out.println(result51);
                    System.out.println("上传完成!");
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }
}