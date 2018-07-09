package server.bridgeThread.thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static server.staticvariable.ServerStatic.socket_FOR_FriendListMap;
import static server.staticvariable.ServerStatic.socket_FOR_GroupSortMap;

public class GroupSortResponseThread implements Runnable{
    private Socket socket;

    public GroupSortResponseThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String userID = dataInputStream.readUTF();
            System.out.println("收到登陆后用户的type 4 请求 !   " + userID);
            if (socket_FOR_GroupSortMap.containsKey(userID)) {
                socket_FOR_GroupSortMap.replace(userID, socket);
                System.out.println("更改type_4_socketMap !");
            } else {
                socket_FOR_GroupSortMap.put(userID, socket);
                System.out.println("新加入type_4_socketMap !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
