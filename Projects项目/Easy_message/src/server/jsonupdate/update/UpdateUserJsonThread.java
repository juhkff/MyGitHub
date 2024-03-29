package server.jsonupdate.update;

import connection.Conn;
import connection.MyConnection;
import server.jsonupdate.update.UpdateFriendJsonThread;
import server.jsonupdate.update.UpdateFriendSortJsonThread;
import server.jsonupdate.update.UpdateGroupJsonThread;
import server.jsonupdate.update.UpdateGroupSortJsonThread;
import server.model.ServerCommand;
import wrapper.StaticVariable;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

public class UpdateUserJsonThread implements Runnable{
    private String userID;
    private BlockingQueue<ServerCommand> friendJsonCommand;
    private BlockingQueue<ServerCommand> friendSortJsonCommand;
    private BlockingQueue<ServerCommand> groupJsonCommand;
    private BlockingQueue<ServerCommand> groupSortJsonCommand;
    private PrintWriter printWriter;


    public UpdateUserJsonThread(String userID, BlockingQueue<ServerCommand> friendJsonCommand, BlockingQueue<ServerCommand> friendSortJsonCommand, BlockingQueue<ServerCommand> groupJsonCommand, BlockingQueue<ServerCommand> groupSortJsonCommand) {
        this.userID = userID;
        this.friendJsonCommand = friendJsonCommand;
        this.friendSortJsonCommand = friendSortJsonCommand;
        this.groupJsonCommand = groupJsonCommand;
        this.groupSortJsonCommand = groupSortJsonCommand;
    }

    @Override
    public void run() {
        int friendsSortNum=0;
        int groupSortNum=0;
        //先创建一次然后监听
        java.io.File file=new java.io.File(StaticVariable.getUserInfoSavePath(userID));
        if(!file.exists()) {
            file.mkdirs();
        }
        file=new java.io.File(StaticVariable.getUserinfoFriendsList(userID));
        try {
            if(!file.exists()) {
                file.createNewFile();
                printWriter=new PrintWriter(file);
                printWriter.write("[]");
                printWriter.flush();
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        file=new java.io.File(StaticVariable.getUserinfoGroupsList(userID));
        try {
            if (!file.exists()) {
                file.createNewFile();
                printWriter=new PrintWriter(file);
                printWriter.write("[]");
                printWriter.flush();
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        file=new java.io.File(StaticVariable.getUserinfoSortFriends(userID));
        try {
            if (!file.exists()) {
                file.createNewFile();
                printWriter=new PrintWriter(file);
                printWriter.write("[]");
                printWriter.flush();
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        file=new java.io.File(StaticVariable.getUserinfoSortGroups(userID));
        try {
            if(!file.exists()) {
                file.createNewFile();
                printWriter=new PrintWriter(file);
                printWriter.write("[]");
                printWriter.flush();
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i=0;
//            System.out.println("\n\n"+userID+"开始查询状态发生变化的好友 !\n\n");
            MyConnection myConnection=new MyConnection();
            Connection connection=myConnection.getConnection();
            String sql="SELECT ID FROM user_"+userID+"_contactlist WHERE isupdate=1 AND types=0";
            PreparedStatement preparedStatement;
            try {
                preparedStatement=connection.prepareStatement(sql);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    System.out.println(userID+"\n找到一个 !");
                    i++;
                }
                if(i>0){
                    System.out.println(userID+"\n总贡找到"+i+"个");
                    //当有好友信息/状态发生变化时执行覆盖文件的操作
                    UpdateFriendJsonThread updateFriendJsonThread=new UpdateFriendJsonThread(userID,friendJsonCommand);
                    Thread thread=new Thread(updateFriendJsonThread);
                    thread.start();

                    System.out.println(userID+"好友信息发生变动，重新生成表 !");
                }

                i=0;
                sql="SELECT ID FROM user_"+userID+"_contactlist WHERE isupdate=1 AND types=1";
                preparedStatement=connection.prepareStatement(sql);
                resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    i++;
                }
                if (i>0){
                    //当有群信息发生变化时执行覆盖文件的操作
                    UpdateGroupJsonThread updateGroupJsonThread=new UpdateGroupJsonThread(userID,groupJsonCommand);
                    Thread thread=new Thread(updateGroupJsonThread);
                    thread.start();
                    System.out.println(userID+"群信息发生变动，重新生成表 !");
                }

                i=0;
                sql="SELECT sort FROM user_"+userID+"_contactlist WHERE types=0 GROUP BY sort";
                preparedStatement=connection.prepareStatement(sql);
                resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    i++;
                }
                if (i!=friendsSortNum){
                    //当分组数量发生变化时执行覆盖文件的操作
                    friendsSortNum=i;

                    UpdateFriendSortJsonThread updateFriendSortJsonThread=new UpdateFriendSortJsonThread(userID,friendSortJsonCommand);
                    Thread thread=new Thread(updateFriendSortJsonThread);
                    thread.start();
                    System.out.println(userID+"好友分组发生变动，重新生成表 !");
                }

                i=0;
                sql="SELECT sort FROM user_"+userID+"_contactlist WHERE types=1 GROUP BY sort";
                preparedStatement=connection.prepareStatement(sql);
                ResultSet resultSet1=preparedStatement.executeQuery();
                while (resultSet.next()){
                    i++;
                }
                if (i!=groupSortNum){
                    //当分组数量发生变化时执行覆盖文件的操作
                    groupSortNum=i;

                    UpdateGroupSortJsonThread updateGroupSortJsonThread=new UpdateGroupSortJsonThread(userID,groupSortJsonCommand);
                    Thread thread=new Thread(updateGroupSortJsonThread);
                    thread.start();
                    System.out.println(userID+"群分组发生变动，重新生成表 !");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                myConnection.Close();
            }
        }
    }
}
