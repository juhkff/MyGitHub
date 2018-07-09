package wrapper;

import com.google.gson.Gson;
import javafx.scene.paint.Color;
import model.paint.Pixel;
import tools.Online;

import java.io.IOException;
import java.net.DatagramPacket;
import java.sql.SQLException;

import static wrapper.StaticVariable.messageSocketAddress;
import static wrapper.StaticVariable.messageds;
import static wrapper.StaticVariable.paintDataOutputStream;

public class PaintWrapper {
    private static Gson gson=new Gson();

    public static void sendPaintRequest(String userID,String userName,String anotherID,String anotherName){
        if (anotherName.equals("")){
            try {
                anotherName=Online.findUserByUserID(anotherID).getNickName();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] bytes=("PaintRequest/"+userID+"/"+userName+"/"+anotherID+"/"+anotherName).getBytes();
        DatagramPacket datagramPacket=new DatagramPacket(bytes,0,bytes.length,messageSocketAddress);
        try {
            messageds.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(String userID,String nickName,String anotherID,String anotherName,boolean ifAgree){
        if(anotherName.equals("")){
            try {
                anotherName=Online.findUserByUserID(anotherID).getNickName();
                System.out.println(anotherName);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String content="PaintAgree/"+userID+"/"+nickName+"/"+anotherID+"/"+anotherName+"/"+ifAgree;
        System.out.println(content);
        byte[] bytes=content.getBytes();
        DatagramPacket datagramPacket=new DatagramPacket(bytes,0,bytes.length,messageSocketAddress);
        try {
            System.out.println("准备发送 !");
            messageds.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendAgreeResponse(String userID,String nickName,String anotherID,String anotherName){
        sendResponse(userID,nickName,anotherID,anotherName,true);
    }

    public static void sendRefuseResponse(String userID,String nickName,String anotherID,String anotherName){
        sendResponse(userID,nickName,anotherID,anotherName,false);
    }

    public static void sendPixel(Pixel pixel){
//        String pixel_trans=gson.toJson(pixel);
        try {
            paintDataOutputStream.writeUTF(gson.toJson(pixel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendStopMark(){
        Pixel pixel=new Pixel("over","over","over","over");
        try {
            paintDataOutputStream.writeUTF(gson.toJson(pixel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
