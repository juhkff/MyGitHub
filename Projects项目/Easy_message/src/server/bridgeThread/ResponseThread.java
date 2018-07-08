package server.bridgeThread;

import server.bridgeThread.download.DownloadJsonThread;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class ResponseThread implements Runnable{
    private Socket socket;
    /*private BlockingQueue<ServerCommand> friendJsonCommand;
    private BlockingQueue<ServerCommand> friendSortJsonCommand;
    private BlockingQueue<ServerCommand> groupJsonCommand;
    private BlockingQueue<ServerCommand> groupSortJsonCommand;*/

    public ResponseThread(Socket socket/*, BlockingQueue<ServerCommand> friendJsonCommand, BlockingQueue<ServerCommand> friendSortJsonCommand, BlockingQueue<ServerCommand> groupJsonCommand, BlockingQueue<ServerCommand> groupSortJsonCommand*/) {
        this.socket = socket;
        /*this.friendJsonCommand = friendJsonCommand;
        this.friendSortJsonCommand = friendSortJsonCommand;
        this.groupJsonCommand = groupJsonCommand;
        this.groupSortJsonCommand = groupSortJsonCommand;*/
    }

    @Override
    public void run() {
        try {
            InputStream inputStream=socket.getInputStream();
            DataInputStream dataInputStream=new DataInputStream(inputStream);
            String request=dataInputStream.readUTF();
            if(request.equals("Download")){
                System.out.println("收到新上线用户的Download请求 !");
                //下载四个Json文件的请求
                DownloadJsonThread downloadJsonThread=new DownloadJsonThread(socket);
                Thread thread=new Thread(downloadJsonThread);
                thread.start();
            }
            /*if(request.equals("Listener")){
                String userID=dataInputStream.readUTF();
                System.out.println("收到登陆后用户的Listener请求 !   "+userID);
                if(socketMap.containsKey(userID)){
                    socketMap.replace(userID,socket);
                    System.out.println("更改socketMap !");
                }else {
                    socketMap.put(userID, socket);
                    System.out.println("新加入socketMap !");
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
