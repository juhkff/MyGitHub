package server.paint.response;

import com.sun.javafx.tk.TKScenePaintListener;
import server.paint.dao.ServerMap;
import server.paint.listener.PaintListener;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static server.paint.dao.ServerMap.socketMap;

public class PaintResponseThread implements Runnable{
    private Socket socket;

    public PaintResponseThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream=socket.getInputStream();
            DataInputStream dataInputStream=new DataInputStream(inputStream);
            //获得连接方的userID
            String userID=dataInputStream.readUTF();
            System.out.println("获得userID:"+userID);
            if (socketMap.containsKey(userID)){
                socketMap.replace(userID,socket);
            }else {
                socketMap.put(userID,socket);
            }
            //对每个连接服务器的用户开启监听线程
            PaintListener paintListener=new PaintListener(socket,inputStream);
            Thread thread=new Thread(paintListener);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
