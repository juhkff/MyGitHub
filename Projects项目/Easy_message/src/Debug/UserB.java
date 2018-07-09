package Debug;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.google.gson.Gson;

import model.contact.Contact;
import model.file.FileProgress;
import model.group.GroupMessage;
import model.group.SimpleGroup;
import model.message.ChatMessage;
import model.message.ContactMessage;
import model.message.FileMessage;
import model.message.NoticeMessage;
import model.paint.Pixel;
import model.property.User;
import wrapper.LoginWrapper;
import wrapper.OnlineWrapper;
import wrapper.PaintWrapper;
import wrapper.ThreadWrapper;
import wrapper.group.GroupWrapper;
public class UserB {
    public static void main(String[] args) throws SQLException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
//		System.out.print("输入账号:__________\b\b\b\b\b\b\b\b\b\b");				//6416977175
//		String userID = scanner.next();
        String userID="6233698193";
        System.out.println("请输入密码:");
//		String passWord = scanner.next();
        String passWord="aqko251068";

        String result = LoginWrapper.login(userID, passWord);
        if (!result.equals("true")) {
            System.out.println("账号或密码错误!");
            System.exit(1);
        }

        User user = LoginWrapper.getUser(userID, passWord);

        System.out.println("获得用户信息!");
        /** 获取用户的nickName属性 **/
        String nickName = user.getNickName();
        byte[] headIcon = user.getHeadIcon();
        String exitTime = user.getExitTime();

        System.out.println(nickName + "\t" + exitTime);

        BlockingQueue<ChatMessage> chatMessages = new ArrayBlockingQueue<ChatMessage>(1); // 接收消息的线程，客户端写take方法块
        BlockingQueue<FileProgress> fileProgresses = new ArrayBlockingQueue<FileProgress>(1); // 接收到文件接收时的进度条，理论上只能同时接收和发送一个文件
        BlockingQueue<FileMessage> fileMessages = new ArrayBlockingQueue<FileMessage>(1); // 用于接收直传文件的请求，客户端写take方法块
        BlockingQueue<NoticeMessage> noticeMessages = new ArrayBlockingQueue<NoticeMessage>(1); // 用于接收好友请求等消息，客户端写take方法块
        BlockingQueue<ChatMessage> files = new ArrayBlockingQueue<ChatMessage>(1); // 单独将聊天消息中的离线文件消息拿出来(聊天消息中仍包括离线文件消息)，客户端写take方法块
        BlockingQueue<GroupMessage> groupMessages = new ArrayBlockingQueue<GroupMessage>(1); // 用于接收群聊消息，客户端写take方法块

        BlockingQueue<Pixel> pixels = new ArrayBlockingQueue<Pixel>(10000); // 作死设10000，不知道会有什么反应

        // 创建所有线程
        ThreadWrapper.startAllThread(userID, exitTime, chatMessages, fileProgresses, fileMessages, noticeMessages,files, groupMessages, pixels);

        // 添加好友
        ArrayList<ContactMessage> contactMessages = LoginWrapper.getContactMessageList(userID, exitTime);
        for (ContactMessage contactMessage : contactMessages) {
            System.out.println(contactMessage.getUserID() + "\t" + contactMessage.getNickName() + "\t"
                    + contactMessage.getTheLattestmessage() + "\t" + contactMessage.getMessageNum());
        }
        ArrayList<SimpleGroup> groupList = OnlineWrapper.showAllGroups(userID);
        for(SimpleGroup simpleGroup:groupList) {
            SimpleGroup theSimpleGroup=simpleGroup;
            System.out.println("ID:"+simpleGroup.getGroupID()+"\tnickName:"+simpleGroup.getGroupName()+"\theadIcon:"+simpleGroup.getGroupIcon()+"\tIntro:"+simpleGroup.getTheLatestMessage()+"\t"+simpleGroup.getTheLatestSendTime());
        }


        PaintWrapper.sendPaintRequest(userID, "juhkrr", "7999375901", "juhksf");
        new Scanner(System.in).nextLine();
        int i=0;
        System.out.println("发送像素!");
//        Pixel pixel=new Pixel("1", "2", "3", "4","5");
        while (true) {
            i++;
//			Thread.sleep(1000);
//            PaintWrapper.sendPixel(pixel);
            System.out.println("发送像素"+i+" !");
        }
    }
}
