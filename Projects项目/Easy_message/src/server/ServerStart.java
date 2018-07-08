package server;

import server.bridgeThread.*;
import server.bridgeThread.server.FriendListServer;
import server.bridgeThread.server.FriendSortServer;
import server.bridgeThread.server.GroupListServer;
import server.bridgeThread.server.GroupSortServer;
import server.model.ServerCommand;
import server.paint.ServerPaint;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerStart {
    public static void main(String[] args){
        BlockingQueue<ServerCommand> friendJsonCommand=new ArrayBlockingQueue<ServerCommand>(1);
        BlockingQueue<ServerCommand> friendSortJsonCommand=new ArrayBlockingQueue<ServerCommand>(1);
        BlockingQueue<ServerCommand> groupJsonCommand=new ArrayBlockingQueue<ServerCommand>(1);
        BlockingQueue<ServerCommand> groupSortJsonCommand=new ArrayBlockingQueue<ServerCommand>(1);

        CommandThread commandThread=new CommandThread(friendJsonCommand,friendSortJsonCommand,groupJsonCommand,groupSortJsonCommand);
        Thread thread=new Thread(commandThread);
        thread.start();

        JsonServer jsonServer=new JsonServer(/*friendJsonCommand,friendSortJsonCommand,groupJsonCommand,groupSortJsonCommand*/);
        Thread thread1=new Thread(jsonServer);
        thread1.start();




        FriendListServer friendListServer=new FriendListServer();
        Thread thread2=new Thread(friendListServer);
        thread2.start();

        FriendSortServer friendSortServer=new FriendSortServer();
        Thread thread3=new Thread(friendSortServer);
        thread3.start();

        GroupListServer groupListServer=new GroupListServer();
        Thread thread4=new Thread(groupListServer);
        thread4.start();

        GroupSortServer groupSortServer=new GroupSortServer();
        Thread thread5=new Thread(groupSortServer);
        thread5.start();





        JsonListenerMain jsonListenerMain=new JsonListenerMain(friendJsonCommand,friendSortJsonCommand,groupJsonCommand,groupSortJsonCommand);
        Thread thread6=new Thread(jsonListenerMain);
        thread6.start();

//        ServerPaint serverPaint=new ServerPaint();
//        Thread thread7=new Thread(serverPaint);
//        thread7.start();
    }
}
