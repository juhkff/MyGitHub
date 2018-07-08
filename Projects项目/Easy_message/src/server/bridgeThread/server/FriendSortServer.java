package server.bridgeThread.server;

import server.bridgeThread.thread.FriendSortResponseThread;
import wrapper.StaticVariable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FriendSortServer implements Runnable{
    @Override
    public void run() {
        ServerSocket serverSocket=null;
        Socket socket=null;
        try {
            serverSocket=new ServerSocket(StaticVariable.FILE_SERCER_SOCKET_JSON_FRIENDSORT_PORT);
            while (true){
                socket=serverSocket.accept();
                System.out.println("收到新客户端的for好友分组连接 !");
                FriendSortResponseThread friendSortResponseThread=new FriendSortResponseThread(socket/*,friendJsonCommand,friendSortJsonCommand,groupJsonCommand,groupSortJsonCommand*/);
                Thread thread=new Thread(friendSortResponseThread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
