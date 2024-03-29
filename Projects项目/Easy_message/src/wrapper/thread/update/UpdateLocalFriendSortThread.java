package wrapper.thread.update;

import wrapper.StaticVariable;

import java.io.*;
import java.net.Socket;

public class UpdateLocalFriendSortThread implements Runnable{
    private Socket socket;
    private InputStream inputStream;
    private DataInputStream dataInputStream;
    private FileOutputStream fileOutputStream;

    public UpdateLocalFriendSortThread(Socket socket, InputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            fileOutputStream=new FileOutputStream(StaticVariable.USERINFO_SORT_FRIENDS);
            dataInputStream=new DataInputStream(inputStream);
            byte[] bytes=new byte[1];
            int len=0;
            while ((len = dataInputStream.read(bytes)) > 0) {
                System.out.println("继续读取...");
                fileOutputStream.write(bytes,0,len);
                System.out.println("好友分组文件读取到: "+new String(bytes));
            }
            System.out.println("好友分组更新完毕 !");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
//                if (inputStream!=null){
//                    inputStream.close();
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
