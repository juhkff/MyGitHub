package server.bridgeThread.thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static server.staticvariable.ServerStatic.socket_FOR_FriendSortMap;

public class FriendSortResponseThread implements Runnable{
    private Socket socket;

    public FriendSortResponseThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String userID = dataInputStream.readUTF();
            System.out.println("收到登陆后用户的type 2 请求 !   " + userID);
            if (socket_FOR_FriendSortMap.containsKey(userID)) {
                socket_FOR_FriendSortMap.replace(userID, socket);
                System.out.println("更改type_2_socketMap !");
            } else {
                socket_FOR_FriendSortMap.put(userID, socket);
                System.out.println("新加入type_2_socketMap !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
