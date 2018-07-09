package server.paint.transmit;

import javafx.scene.paint.Color;
import model.paint.Pixel;

import java.io.*;

public class PaintTransThread implements Runnable{
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    public PaintTransThread(InputStream inputStream, OutputStream outputStream) {
        dataInputStream=new DataInputStream(inputStream);
        dataOutputStream=new DataOutputStream(outputStream);
    }

    @Override
    public void run() {
        String pixel_trans;
        while (true){
            try {
                System.out.println("等待接收 ...");
                pixel_trans=dataInputStream.readUTF();
                System.out.println(pixel_trans);
                if (pixel_trans.contains("over")){
                    System.out.println("over");
                    dataOutputStream.writeUTF(pixel_trans);
                    break;
                }else {
                    System.out.println("转发!");
                    System.out.println(pixel_trans);
                    dataOutputStream.writeUTF(pixel_trans);
//                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
