package server.bridgeThread.server;

import server.bridgeThread.thread.GroupSortResponseThread;
import wrapper.StaticVariable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GroupSortServer implements Runnable{
    @Override
    public void run() {
        ServerSocket serverSocket=null;
        Socket socket=null;
        try {
            serverSocket=new ServerSocket(StaticVariable.FILE_SERCER_SOCKET_JSON_GROUPSORT_PORT);
            while (true){
                socket=serverSocket.accept();
                System.out.println("收到新客户端的for群分组连接 !");
                GroupSortResponseThread groupSortResponseThread=new GroupSortResponseThread(socket/*,friendJsonCommand,friendSortJsonCommand,groupJsonCommand,groupSortJsonCommand*/);
                Thread thread=new Thread(groupSortResponseThread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
