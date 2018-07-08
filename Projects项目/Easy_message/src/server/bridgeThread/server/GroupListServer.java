package server.bridgeThread.server;

import server.bridgeThread.thread.GroupListResponseThread;
import wrapper.StaticVariable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GroupListServer implements Runnable{
    @Override
    public void run() {
        ServerSocket serverSocket=null;
        Socket socket=null;
        try {
            serverSocket=new ServerSocket(StaticVariable.FILE_SERCER_SOCKET_JSON_GROUPLIST_PORT);
            while (true){
                socket=serverSocket.accept();
                System.out.println("收到新客户端的for群列表连接 !");
                GroupListResponseThread groupListResponseThread=new GroupListResponseThread(socket/*,friendJsonCommand,friendSortJsonCommand,groupJsonCommand,groupSortJsonCommand*/);
                Thread thread=new Thread(groupListResponseThread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
