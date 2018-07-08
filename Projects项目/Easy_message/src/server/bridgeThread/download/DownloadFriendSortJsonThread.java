package server.bridgeThread.download;

import com.google.gson.Gson;
import model.file.FileProgress;
import model.group.GroupMessage;
import model.message.ChatMessage;
import model.message.ContactMessage;
import model.message.FileMessage;
import model.message.NoticeMessage;
import model.paint.Pixel;
import model.property.User;
import server.bridgeThread.download.DownloadFileThread;
import server.model.ServerCommand;
import wrapper.LoginWrapper;
import wrapper.StaticVariable;
import wrapper.ThreadWrapper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static server.staticvariable.ServerStatic.socket_FOR_FriendSortMap;

public class DownloadFriendSortJsonThread implements Runnable{
    public static void main(String[] args) throws InterruptedException, SQLException {
        String userID="3223424432";			//juhkff
//		String userID="2313811116";			//juhkgf
        String passWord="aqko251068";
        String result=LoginWrapper.login(userID, passWord);
        if(!result.equals("true")) {
            System.out.println("登录失败!");
            System.exit(1);
        }else {
            User user=LoginWrapper.getUser(userID, passWord);
            System.out.println("昵称:"+user.getNickName()+"\t上次下线时间:"+user.getExitTime());
            String exitTime=user.getExitTime();


            BlockingQueue<ChatMessage> chatMessages=new ArrayBlockingQueue<ChatMessage>(1);                     //接收消息的线程，客户端写take方法块
            BlockingQueue<FileProgress> fileProgresses=new ArrayBlockingQueue<FileProgress>(1);                  //接收到文件接收时的进度条，理论上只能同时接收和发送一个文件
            BlockingQueue<FileMessage> fileMessages=new ArrayBlockingQueue<FileMessage>(1);                     //用于接收直传文件的请求，客户端写take方法块
            BlockingQueue<NoticeMessage> noticeMessages=new ArrayBlockingQueue<NoticeMessage>(1);     //用于接收好友请求等消息，客户端写take方法块
            BlockingQueue<ChatMessage> files=new ArrayBlockingQueue<ChatMessage>(1);                  //单独将聊天消息中的离线文件消息拿出来(聊天消息中仍包括离线文件消息)，客户端写take方法块
            BlockingQueue<GroupMessage> groupMessages=new ArrayBlockingQueue<GroupMessage>(1);        //用于接收群聊消息，客户端写take方法块
            BlockingQueue<Pixel> pixels=new ArrayBlockingQueue<Pixel>(10000);

            //创建所有线程
            ThreadWrapper.startAllThread(userID,exitTime,chatMessages,fileProgresses,fileMessages,noticeMessages,files,groupMessages,pixels);

            ArrayList<ContactMessage> contactMessages=LoginWrapper.getContactMessageList(userID,exitTime);
            for(ContactMessage contactMessage:contactMessages) {
                System.out.println(contactMessage.getUserID()+"\t"+contactMessage.getNickName()+"\t"+contactMessage.getTheLattestmessage()+"\t"+contactMessage.getMessageNum());
            }
        }
    }
    private BlockingQueue<ServerCommand> friendSortJsonCommand;

    public DownloadFriendSortJsonThread(BlockingQueue<ServerCommand> friendSortJsonCommand) {
        this.friendSortJsonCommand = friendSortJsonCommand;
    }

    @Override
    public void run() {
        while (true){
            String userID = null;
            try {
                ServerCommand  serverCommand = friendSortJsonCommand.take();
                System.out.println("收到一个命令命令某用户重新下载本地好友分组文件!   "+new Gson().toJson(serverCommand));
                userID=serverCommand.getUserID();
                Socket socket=socket_FOR_FriendSortMap.get(userID);
                System.out.println("到底有没有这个变量？"+socket);
                OutputStream outputStream=socket.getOutputStream();
                System.out.println("成功与用户"+userID+"建立了下载好友分组的通信!");
                DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
                dataOutputStream.writeUTF("SortFriends");
                File file=new File(StaticVariable.getUserinfoSortFriends(userID));

                //向客户端发送文件的过程
                DownloadFileThread downloadFileThread=new DownloadFileThread(socket,outputStream,file);
                Thread thread=new Thread(downloadFileThread);
                thread.start();

            } catch (InterruptedException e) {
//                e.printStackTrace();
                System.out.println("确认客户端是否已断开连接!客户端:"+userID);
                continue;
            } catch (IOException e) {
//                e.printStackTrace();
                System.out.println("确认客户端是否已断开连接!客户端:"+userID);
                continue;
            }
        }
    }
}
