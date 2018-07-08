package wrapper.thread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.group.Group;
import model.group.GroupMember;
import test.Client.Request;
import test.Client.RequestProperty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static wrapper.StaticVariable.URL_ADDRESS;
import static wrapper.StaticVariable.ifout;

//监听群成员头像等信息的变化
public class ListenerAllMembersThread implements Runnable{
    private Group group;
    private ArrayList<GroupMember> groupMembers;
    private Request request;
    private Map<String, String> parameters = new HashMap<String, String>();
    private String result;

    public ListenerAllMembersThread(Group group) {
        this.group = group;
        this.parameters.put("groupID", this.group.getGroupID());
        this.request = new Request(URL_ADDRESS + "/ListenAllMembers", this.parameters, RequestProperty.APPLICATION);
        ifout.put(group.getGroupID(), true);
    }

    @Override
    public void run() {
        while (true) {
            if (!ifout.get(group.getGroupID())) {
                //若退出了此界面，则停止监听
                break;
            } else {
                /**在此界面时，持续间断监听**/
                result = null;
                result = this.request.doPost();
                if (result.equals("none")) {

                } else {
                    groupMembers = null;
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    Type type = new TypeToken<ArrayList<GroupMember>>() {
                    }.getType();

                    /**获得信息发生改变的GroupMember对象列表**/
                    groupMembers = gson.fromJson(result, type);

                    for (GroupMember groupMember : groupMembers) {
                        /**拿更改后的对象去更新列表中原有的**/
//                            group.getGroupMember(groupMember)
                        group.replaceGroupMemver(groupMember);
                    }

                    /**重画界面?**/
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
