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

public class UpdateGroupJsonThread implements Runnable{
    private String userID;
    private ArrayList<FullContact> fullContacts=new ArrayList<FullContact>();
    private BlockingQueue<ServerCommand> groupJsonCommand;

    public UpdateGroupJsonThread(String userID, BlockingQueue<ServerCommand> groupJsonCommand) {
        this.userID = userID;
        this.groupJsonCommand = groupJsonCommand;
    }

    @Override
    public void run() {
        MyConnection myConnection=new MyConnection();
        Connection connection=myConnection.getConnection();
//        Connection connection=Conn.getConnection();
        String sql="SELECT ID,nickName,headIcon,status,sort,username FROM user_"+userID+"_contactlist WHERE isupdate=1 AND types=1";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            int i=0;
            while (resultSet.next()){
                i++;
                String ID=resultSet.getString("ID");
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
                FullContact fullContact =new FullContact(nickName,headIcon,status,sort,userID,username);
                fullContacts.add(fullContact);
            }

            sql="UPDATE user_"+userID+"_contactlist SET isupdate=0 WHERE isupdate=1 AND types=1";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.execute();

            sql="SELECT groupID,groupIntro FROM groups WHERE groupID IN (SELECT ID FROM user_"+userID+"_contactlist WHERE types=1)";
            preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet1=preparedStatement.executeQuery();
            while (resultSet.next()){
                String groupID=resultSet.getString("groupID");
                String groupIntro=resultSet.getString("groupIntro");
                for(FullContact fullContact:fullContacts){
                    if(fullContact.getId().equals(groupID)){
                        fullContact.setMotto(groupIntro);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            myConnection.Close();
        }

        //已得到完全信息的群列表，然后覆盖原文件
        Gson gson=new Gson();
        String fileContent=gson.toJson(fullContacts);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream=new FileOutputStream(StaticVariable.getUserinfoGroupsList(userID));
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
            ServerCommand serverCommand=new ServerCommand(userID,"Groups");
            try {
                System.out.println("put进了一个命令对象命令某用户重新下载群列表文件！   "+gson.toJson(serverCommand));
                groupJsonCommand.put(serverCommand);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
