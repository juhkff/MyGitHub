package server.paint.listener;

import server.paint.transmit.PaintTransThread;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static server.paint.dao.ServerMap.socketMap;

public class PaintListener implements Runnable{
    private Socket socket;
    private InputStream inputStream;

    public PaintListener(Socket socket, InputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        DataInputStream dataInputStream=new DataInputStream(inputStream);
        while (true){
            try {
                String anotherID=dataInputStream.readUTF();
                System.out.println("另一个人ID:"+anotherID);
                OutputStream outputStream =socketMap.get(anotherID).getOutputStream();
                System.out.println("引用成功 !");
                PaintTransThread paintTransThread=new PaintTransThread(inputStream,outputStream);
                Thread thread=new Thread(paintTransThread);
                thread.start();
                //此线程必须阻塞住这个父线程
                thread.join();
            } catch (IOException e) {
//                e.printStackTrace();
                System.out.println("用户下线?");
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
