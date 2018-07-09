package server.bridgeThread.server;

import server.bridgeThread.thread.FriendListResponseThread;
import wrapper.StaticVariable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FriendListServer implements Runnable{
    @Override
    public void run() {
        ServerSocket serverSocket=null;
        Socket socket=null;
        try {
            serverSocket=new ServerSocket(StaticVariable.FILE_SERCER_SOCKET_JSON_FRIENDLIST_PORT);
            while (true){
                socket=serverSocket.accept();
                System.out.println("收到新客户端的for好友列表连接 !");
                FriendListResponseThread friendListResponseThread=new FriendListResponseThread(socket/*,friendJsonCommand,friendSortJsonCommand,groupJsonCommand,groupSortJsonCommand*/);
                Thread thread=new Thread(friendListResponseThread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
