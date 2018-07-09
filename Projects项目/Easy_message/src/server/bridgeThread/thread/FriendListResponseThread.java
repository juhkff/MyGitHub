package server.bridgeThread.thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static server.staticvariable.ServerStatic.socket_FOR_FriendListMap;

public class FriendListResponseThread implements Runnable {
    private Socket socket;

    public FriendListResponseThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String userID = dataInputStream.readUTF();
            System.out.println("收到登陆后用户的type 1 请求 !   " + userID);
            if (socket_FOR_FriendListMap.containsKey(userID)) {
                socket_FOR_FriendListMap.replace(userID, socket);
                System.out.println("更改type_1_socketMap !");
            } else {
                socket_FOR_FriendListMap.put(userID, socket);
                System.out.println("新加入type_1_socketMap !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
