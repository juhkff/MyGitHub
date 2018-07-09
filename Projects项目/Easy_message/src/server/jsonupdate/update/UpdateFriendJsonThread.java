package server.jsonupdate.update;

import com.google.gson.Gson;
import connection.Conn;
import connection.MyConnection;
import model.contact.FullContact;
import server.model.ServerCommand;
import wrapper.StaticVariable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class UpdateFriendJsonThread implements Runnable{
    private String userID;
    private ArrayList<FullContact> friends=new ArrayList<FullContact>();
    private BlockingQueue<ServerCommand> friendJsonCommand;

    public UpdateFriendJsonThread(String userID, BlockingQueue<ServerCommand> friendJsonCommand) {
        this.userID = userID;
        this.friendJsonCommand = friendJsonCommand;
    }

    @Override
    public void run() {

        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
//        Connection connection=Conn.getConnection();
        String sql="SELECT ID,nickName,headIcon,status,sort,username FROM user_"+userID+"_contactlist WHERE isupdate=1 AND types=0";
        System.out.println(userID+"\n查找好友中isupdate值为1的信息!\n");
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            int i=0;
            while (resultSet.next()){
                i++;
                String ID=resultSet.getString("ID");
                System.out.println(ID+"发生变动 !");
                String nickName=resultSet.getString("nickName");
                InputStream inputStream=null;
                inputStream=resultSet.getBinaryStream("headIcon");
                byte[] headIcon=null;
                if (inputStream!=null){
                    headIcon=new byte[inputStream.available()];
                    inputStream.read(headIcon,0,inputStream.available());
                    inputStream.close();
                }
                int status=resultSet.getInt("status");
                String sort=resultSet.getString("sort");
                String username=resultSet.getString("username");
                FullContact fullContact =new FullContact(nickName,headIcon,status,sort,ID,username);
                friends.add(fullContact);
            }
            sql="UPDATE user_"+userID+"_contactlist SET isupdate=0 WHERE isupdate=1 AND types=0";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.execute();
            System.out.println("更新isupdate的值 !");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            myConnection.Close();
        }


        //所有好友的列表
        MyConnection myConnection1=new MyConnection();
        Connection connection1=myConnection1.getConnection();
//        Connection connection1=Conn.getConnection();
        String sql1="SELECT userID,intro FROM userinfo where userID IN (SELECT ID FROM user_"+userID+"_contactlist WHERE types=0)";
        try {
            PreparedStatement preparedStatement1=connection1.prepareStatement(sql1);
            ResultSet resultSet=preparedStatement1.executeQuery();
            while (resultSet.next()){
                String userID=resultSet.getString("userID");
                String intro=resultSet.getString("intro");
                for(FullContact fullContact :friends){
                    if (fullContact.getId().equals(userID)){
                        fullContact.setMotto(intro);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            myConnection1.Close();
        }

        System.out.println(userID+"获得修改后的好友的全部信息 !");
        //已经得到完全信息的好友列表，然后覆盖原文件
        Gson gson=new Gson();
        String fileContent=gson.toJson(friends);

        System.out.println("用户"+userID+"在表中的好友列表文件的新文件内容:"+fileContent);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream=new FileOutputStream(StaticVariable.getUserinfoFriendsList(userID));
            fileOutputStream.write(fileContent.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ServerCommand serverCommand=new ServerCommand(userID,"Friends");
            try {
                System.out.println("put进了一个命令对象命令某用户重新下载好友列表文件！   "+gson.toJson(serverCommand));
                friendJsonCommand.put(serverCommand);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
