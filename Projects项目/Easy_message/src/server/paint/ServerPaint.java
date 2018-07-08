package server.paint;

import server.paint.response.PaintResponseThread;
import wrapper.StaticVariable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPaint /*implements Runnable*/{
    /*@Override
    public void run() {*/
    public static void main(String[] args){
        ServerSocket serverSocket=null;
        Socket socket=null;

        try {
            serverSocket=new ServerSocket(StaticVariable.PAINT_SERVER_SOCKET_PORT);
            while (true){
                socket=serverSocket.accept();
                //当有新的连接时，开启一个应答线程
                PaintResponseThread paintResponseThread=new PaintResponseThread(socket);
                Thread thread=new Thread(paintResponseThread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
