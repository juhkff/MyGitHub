package server.bridgeThread.download;

import com.google.gson.Gson;
import server.bridgeThread.download.DownloadFileThread;
import server.model.ServerCommand;
import wrapper.StaticVariable;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import static server.staticvariable.ServerStatic.socket_FOR_FriendListMap;

public class DownLoadFriendJsonThread implements Runnable{
    private BlockingQueue<ServerCommand> friendJsonCommand;

    public DownLoadFriendJsonThread(BlockingQueue<ServerCommand> friendJsonCommand) {
        this.friendJsonCommand = friendJsonCommand;
    }

    @Override
    public void run() {
        while (true){
            String userID = null;
            try {
                ServerCommand serverCommand=friendJsonCommand.take();
                System.out.println("收到一个命令命令某用户重新下载本地好友列表文件!   "+new Gson().toJson(serverCommand));
                userID=serverCommand.getUserID();
                Socket socket=socket_FOR_FriendListMap.get(userID);
                System.out.println("到底有没有这个变量？"+socket);
                OutputStream outputStream=socket.getOutputStream();
                System.out.println("成功与用户"+userID+"建立了下载好友列表的通信!");
                DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
                dataOutputStream.writeUTF("Friends");
                File file=new File(StaticVariable.getUserinfoFriendsList(userID));

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
