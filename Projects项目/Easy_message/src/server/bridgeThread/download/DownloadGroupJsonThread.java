package server.bridgeThread.download;

import com.google.gson.Gson;
import server.bridgeThread.download.DownloadFileThread;
import server.model.ServerCommand;
import wrapper.StaticVariable;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import static server.staticvariable.ServerStatic.socket_FOR_GroupListMap;


public class DownloadGroupJsonThread implements Runnable{
    private BlockingQueue<ServerCommand> groupJsonCommand;

    public DownloadGroupJsonThread(BlockingQueue<ServerCommand> groupJsonCommand) {
        this.groupJsonCommand = groupJsonCommand;
    }

    @Override
    public void run() {
        while (true){
            String userID=null;
            try {
                ServerCommand  serverCommand = groupJsonCommand.take();
                System.out.println("收到一个命令命令某用户重新下载本地群列表文件!   "+new Gson().toJson(serverCommand));
                userID=serverCommand.getUserID();
                Socket socket=socket_FOR_GroupListMap.get(userID);
                System.out.println("到底有没有这个变量？"+socket);
                OutputStream outputStream=socket.getOutputStream();
                System.out.println("成功与用户"+userID+"建立了下载群列表的通信!");
                DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
                dataOutputStream.writeUTF("Groups");
                File file=new File(StaticVariable.getUserinfoGroupsList(userID));
                //向客户端发送文件的过程
                DownloadFileThread downloadFileThread=new DownloadFileThread(socket,outputStream,file);
                Thread thread=new Thread(downloadFileThread);
                thread.start();
            } catch (InterruptedException e) {
//                e.printStackTrace();
                System.out.println("确认客户端是否已断开连接(???)!客户端:"+userID);
                continue;
            } catch (IOException e) {
//                e.printStackTrace();
                System.out.println("确认客户端是否已断开连接(??????)!客户端:"+userID);
                continue;
            }
        }
    }
}
