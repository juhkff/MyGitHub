package server.bridgeThread.download;

import java.io.*;
import java.net.Socket;

public class DownloadFileThread implements Runnable{
    private Socket socket;
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private FileInputStream fileInputStream;
    private byte[] bytes=new byte[1];
    public DownloadFileThread(Socket socket, OutputStream outputStream, File file) {
        this.socket=socket;
        this.outputStream=outputStream;
        this.dataOutputStream=new DataOutputStream(outputStream);
        try {
            this.fileInputStream=new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        int len=0;
        try {
            while ((len=fileInputStream.read(bytes))>0){
                dataOutputStream.write(bytes,0,len);
                System.out.println("好友分组文件读取到: "+new String(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                dataOutputStream.flush();
                outputStream.flush();
//                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
