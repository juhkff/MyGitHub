package server.bridgeThread.thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static server.staticvariable.ServerStatic.socket_FOR_GroupListMap;

public class GroupListResponseThread implements Runnable{
    private Socket socket;

    public GroupListResponseThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String userID = dataInputStream.readUTF();
            System.out.println("收到登陆后用户的type 3 请求 !   " + userID);
            if (socket_FOR_GroupListMap.containsKey(userID)) {
                socket_FOR_GroupListMap.replace(userID, socket);
                System.out.println("更改type_3_socketMap !");
            } else {
                socket_FOR_GroupListMap.put(userID, socket);
                System.out.println("新加入type_3_socketMap !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
