package servlets.login;

import com.google.gson.Gson;
import model.message.ContactMessage;
import tools.Login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "GetContactMessageListServlet",urlPatterns = "/GetContactMessageList")
public class GetContactMessageListServlet extends HttpServlet {
    public static void main(String[] args){
        String userID="6416977175";
        String exitTime="2018-05-16 10:42:15";
        ArrayList<ContactMessage> contactMessages=new ArrayList<ContactMessage>();
        contactMessages=Login.getContactMessageList(userID,exitTime);
//        Gson gson=new Gson();
//        System.out.println(gson.toJson(contactMessages));
        for (ContactMessage contactMessage:contactMessages){
            System.out.println(contactMessage.getUserID()+"\t"+contactMessage.getNickName()+"\t"+contactMessage.getTheLattestmessage()+"\t"+contactMessage.getMessageNum());
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userID=request.getParameter("userID");
        String exitTime=request.getParameter("exitTime");

        ArrayList<ContactMessage> contactMessages=new ArrayList<ContactMessage>();
        contactMessages=Login.getContactMessageList(userID,exitTime);
        int i=0;
        for(ContactMessage contactMessage:contactMessages){
            i++;
            System.out.println("第"+i+"个!");
        }
        PrintWriter printWriter=response.getWriter();
        Gson gson=new Gson();
        printWriter.print(gson.toJson(contactMessages));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
    }
}
