package servlets.json;

import com.google.gson.Gson;
import model.contact.FullContact;
import tools.Online;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.util.ArrayList;

@WebServlet(name = "GetFriendListJsonServlet",urlPatterns = "/GetFriendListJson")
public class GetFriendListJsonServlet extends HttpServlet {
    public static void main(String[] arags){
        String userID="7999375901";
        ArrayList<FullContact> fullContacts=new ArrayList<FullContact>();
        fullContacts=Online.getFriendList(userID);
        //已经得到完全信息的好友列表，然后覆盖原文件
        Gson gson=new Gson();
        String fileContent=gson.toJson(fullContacts);
        System.out.println(fileContent);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String userID=request.getParameter("userID");
        ArrayList<FullContact> fullContacts=new ArrayList<FullContact>();
        fullContacts=Online.getFriendList(userID);
        if(fullContacts==null){
            fullContacts=new ArrayList<FullContact>();
        }
        //已经得到完全信息的好友列表，然后覆盖原文件
        Gson gson=new Gson();
        String fileContent=gson.toJson(fullContacts);
        PrintWriter printWriter=response.getWriter();
        printWriter.print(fileContent);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
    }
}
