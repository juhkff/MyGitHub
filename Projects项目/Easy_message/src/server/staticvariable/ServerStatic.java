package server.staticvariable;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerStatic {
    public static Map<String,Socket> socket_FOR_FriendListMap=new HashMap<String, Socket>();
    public static Map<String,Socket> socket_FOR_FriendSortMap=new HashMap<String, Socket>();
    public static Map<String,Socket> socket_FOR_GroupListMap=new HashMap<String, Socket>();
    public static Map<String,Socket> socket_FOR_GroupSortMap=new HashMap<String, Socket>();
}
