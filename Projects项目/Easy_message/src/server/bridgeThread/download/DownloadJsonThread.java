package server.bridgeThread.download;

import wrapper.StaticVariable;

import java.io.*;
import java.net.Socket;

public class DownloadJsonThread implements Runnable{
    private Socket socket;
    private InputStream inputStream;
    private DataInputStream dataInputStream;
    private FileInputStream fileInputStream;
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private String filePath="C:\\Easy_message\\UserInfo";

    public DownloadJsonThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputStream=socket.getInputStream();
            dataInputStream=new DataInputStream(inputStream);

            outputStream=socket.getOutputStream();
            dataOutputStream=new DataOutputStream(outputStream);

            String userID=dataInputStream.readUTF();                                                //获得用户的ID

            System.out.println("DownloadJsonThread.java:31 收到userID !"+userID);

            filePath+="\\"+userID;
            File file=new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }

            for(int i=1;i<=4;i++){
                switch (i){
                    case 1:                                                                         //1:下载用户好友列表
                    {
                        fileInputStream=new FileInputStream(StaticVariable.getUserinfoFriendsList(userID));
                        byte[] bytes=new byte[1];
                        int len=0;
                        while ((len=fileInputStream.read(bytes))>0){
                            dataOutputStream.writeUTF(StaticVariable.continueMark);
                            System.out.println("发送第一个文件的continue !");
                            dataOutputStream.write(bytes,0,len);
                            System.out.println("发送第一个文件的内容 !"+new String(bytes));
//                            dataOutputStream.flush();
                        }
                        dataOutputStream.writeUTF(StaticVariable.overMark);
                        System.out.println("第一个文件发送完毕 !");
                        break;
                    }
                    case 2:
                    {                                                                               //2:下载用户群列表
                        fileInputStream=new FileInputStream(StaticVariable.getUserinfoGroupsList(userID));
                        byte[] bytes=new byte[1];
                        int len=0;
                        while ((len=fileInputStream.read(bytes))>0){
                            dataOutputStream.writeUTF(StaticVariable.continueMark);
                            System.out.println("发送第二个文件的continue !");
                            dataOutputStream.write(bytes,0,len);
                            System.out.println("发送第二个文件的内容 !"+new String(bytes));
//                            dataOutputStream.flush();
                        }
                        dataOutputStream.writeUTF(StaticVariable.overMark);
                        System.out.println("第二个文件发送完毕 !");
                        break;
                    }

                    case 3:
                    {                                                                               //3:下载用户好友分组
                        fileInputStream=new FileInputStream(StaticVariable.getUserinfoSortFriends(userID));
                        byte[] bytes=new byte[1];
                        int len=0;
                        while ((len=fileInputStream.read(bytes))>0){
                            dataOutputStream.writeUTF(StaticVariable.continueMark);
                            System.out.println("发送第三个文件的continue !");
                            dataOutputStream.write(bytes,0,len);
                            System.out.println("发送第三个文件的内容 !"+new String(bytes));
//                            dataOutputStream.flush();
                        }
                        dataOutputStream.writeUTF(StaticVariable.overMark);
                        System.out.println("第三个文件发送完毕 !");
                        break;
                    }

                    case 4:
                    {                                                                               //4:下载用户群分组
                        fileInputStream=new FileInputStream(StaticVariable.getUserinfoSortGroups(userID));
                        byte[] bytes=new byte[1];
                        int len=0;
                        while ((len=fileInputStream.read(bytes))>0){
                            dataOutputStream.writeUTF(StaticVariable.continueMark);
                            System.out.println("发送第四个文件的continue !");
                            dataOutputStream.write(bytes,0,len);
                            System.out.println("发送第四个文件的内容 !"+new String(bytes));
//                            dataOutputStream.flush();
                        }
                        dataOutputStream.writeUTF(StaticVariable.overMark);
                        System.out.println("Json数据发送完毕!");
                        break;
                    }
                }
            }
            outputStream.close();
            fileInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
