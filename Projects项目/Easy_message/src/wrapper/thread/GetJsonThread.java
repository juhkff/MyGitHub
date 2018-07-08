package wrapper.thread;

import wrapper.StaticVariable;

import java.io.*;
import java.net.Socket;

public class GetJsonThread implements Runnable{
    private Socket socket;
    private String userID;
    private InputStream inputStream;
    private DataInputStream dataInputStream;

    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private FileOutputStream fileOutputStream;

    public GetJsonThread(String userID) {
        this.userID = userID;
    }

    @Override
    public void run() {
        try {
            socket=new Socket(StaticVariable.FILE_SERVER_IP,StaticVariable.FILE_SERVER_SOCKET_JSON_PORT);
//            socket=new Socket("localhost",StaticVariable.FILE_SERVER_SOCKET_JSON_PORT);
            outputStream=socket.getOutputStream();
            DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
            dataOutputStream.writeUTF("Download");
            inputStream=socket.getInputStream();
            dataInputStream=new DataInputStream(inputStream);
            outputStream=socket.getOutputStream();
            dataOutputStream=new DataOutputStream(outputStream);

            dataOutputStream.writeUTF(userID);

            for(int i=1;i<=4;i++){
                switch (i){
                    case 1:
                    {                                                                                                   //1:接收用户好友列表
                        fileOutputStream=new FileOutputStream(StaticVariable.USERINFO_FRIENDS_LIST);
                        byte[] bytes=new byte[1];
                        int len=0;
                        String mark=null;
                        System.out.println("Json开始下载!");
                        while (/*(len=dataInputStream.read(bytes))>0*/true){
                            mark="";
                            System.out.println("准备接受第一个文件的continue ...");
                            mark=dataInputStream.readUTF();
                            System.out.println("接收到第一个文件的continue !"+mark);
                            if (mark.equals(StaticVariable.continueMark)) {
                                len=dataInputStream.read(bytes);
                                System.out.println("接收到的长度: "+len);
                                System.out.println("接收到第一个文件的内容 ! "+new String(bytes));
                                fileOutputStream.write(bytes, 0, len);
                                System.out.println("将第一个文件的内容写入本地 !");
                            }else if ((mark.equals(StaticVariable.overMark))){
                                System.out.println("第一个文件接收完毕 !");
                                fileOutputStream.flush();
                                break;
                            }
                        }
                        break;
                    }

                    case 2:
                    {
                        fileOutputStream=new FileOutputStream(StaticVariable.USERINFO_GROUPS_LIST);
                        byte[] bytes=new byte[1];
                        int len=0;
                        String mark=null;
                        while (true){
                            mark="";
                            System.out.println("准备接受第二个文件的continue ...");
                            mark=dataInputStream.readUTF();
                            System.out.println("接收到第二个文件的continue !"+mark);
                            if (mark.equals(StaticVariable.continueMark)){
                                len=dataInputStream.read(bytes);
                                System.out.println("接收到第二个文件的内容 ! "+new String(bytes));
                                fileOutputStream.write(bytes, 0, len);
                                System.out.println("将第二个文件的内容写入本地 !");
                            }else if (mark.equals(StaticVariable.overMark)){
                                System.out.println("第二个文件接收完毕 !");
                                fileOutputStream.flush();
                                break;
                            }
                        }
                        break;
                    }

                    case 3:
                    {
                        fileOutputStream=new FileOutputStream(StaticVariable.USERINFO_SORT_FRIENDS);
                        byte[] bytes=new byte[1];
                        int len=0;
                        String mark=null;
                        while (true){
                            mark="";
                            if ((mark=dataInputStream.readUTF()).equals(StaticVariable.continueMark)){
                                len=dataInputStream.read(bytes);
                                fileOutputStream.write(bytes,0,len);
                            }else if (mark.equals(StaticVariable.overMark)){
                                fileOutputStream.flush();
                                break;
                            }
                        }
                        break;
                    }

                    case 4:
                    {
                        fileOutputStream=new FileOutputStream(StaticVariable.USERINFO_SORT_GROUPS);
                        byte[] bytes=new byte[1];
                        int len=0;
                        String mark=null;
                        while (true){
                            mark="";
                            if ((mark=dataInputStream.readUTF()).equals(StaticVariable.continueMark)){
                                len=dataInputStream.read(bytes);
                                fileOutputStream.write(bytes,0,len);
                            }else if (mark.equals(StaticVariable.overMark)){
                                fileOutputStream.flush();
                                break;
                            }

                        }
                        System.out.println("Json下载完毕!");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream!=null)
                    inputStream.close();
                if(outputStream!=null)
                    outputStream.close();
                if (fileOutputStream!=null){
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
