package wrapper.thread.info;

import test.Client.Request;
import test.Client.RequestProperty;
import tools.file.File;
import wrapper.StaticVariable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static wrapper.StaticVariable.URL_ADDRESS;

public class ReadJsonThread implements Runnable{
    private String userID;
    private Map<String,String> parameter=new HashMap<String, String>();
    private Request request1;
    private Request request2;
    private Request request3;
    private Request request4;

    public ReadJsonThread(String userID) {
        this.userID = userID;
        parameter.put("userID",userID);
        request1=new Request(URL_ADDRESS+"/GetFriendListJson",parameter,RequestProperty.APPLICATION);
        request2=new Request(URL_ADDRESS+"/GetFriendSortJson",parameter,RequestProperty.APPLICATION);
        request3=new Request(URL_ADDRESS+"/GetGroupListJson",parameter,RequestProperty.APPLICATION);
        request4=new Request(URL_ADDRESS+"/GetGroupSortJson",parameter,RequestProperty.APPLICATION);
    }

    @Override
    public void run() {
        while (true){
            String friendListJson=request1.doPost();
            String friendSortJson=request2.doPost();
            String groupListJson=request3.doPost();
            String groupSortJson=request4.doPost();

            String[] jsonList=new String[4];
            jsonList[0]=friendListJson;
            jsonList[1]=friendSortJson;
            jsonList[2]=groupListJson;
            jsonList[3]=groupSortJson;

            String result= String.valueOf(jsonList);

            try {

                FileOutputStream fileOutputStream=new FileOutputStream(StaticVariable.USERINFO_FRIENDS_LIST);
                FileOutputStream fileOutputStream1=new FileOutputStream(StaticVariable.USERINFO_GROUPS_LIST);
                FileOutputStream fileOutputStream2=new FileOutputStream(StaticVariable.USERINFO_SORT_FRIENDS);
                FileOutputStream fileOutputStream3=new FileOutputStream(StaticVariable.USERINFO_SORT_GROUPS);

                fileOutputStream.write(friendListJson.getBytes(),0,friendListJson.getBytes().length);
                fileOutputStream1.write(groupListJson.getBytes(),0,groupListJson.getBytes().length);
                fileOutputStream2.write(friendSortJson.getBytes(),0,friendSortJson.getBytes().length);
                fileOutputStream3.write(groupSortJson.getBytes(),0,groupSortJson.getBytes().length);

                fileOutputStream.flush();
                fileOutputStream1.flush();
                fileOutputStream2.flush();
                fileOutputStream3.flush();

                fileOutputStream.close();
                fileOutputStream1.close();
                fileOutputStream2.close();
                fileOutputStream3.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(10000);                                        //10s一请求
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
