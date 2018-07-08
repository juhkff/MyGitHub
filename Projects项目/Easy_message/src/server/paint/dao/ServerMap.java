package server.paint.dao;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerMap {
    //存放连接方ID及其Socket的全局Map变量
    public static Map<String,Socket> socketMap=new HashMap<String, Socket>();
}
