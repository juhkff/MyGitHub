package servlets.json;

import com.google.gson.Gson;
import model.contact.FullContact;
import model.contact.Sort;
import tools.Online;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetGroupListJsonServlet",urlPatterns = "/GetGroupListJson")
public class GetGroupListJsonServlet extends HttpServlet {
    public static void main(String[] args){
        String userID="6416977175";
//        ArrayList<FullContact> fullContacts=new ArrayList<FullContact>();
        ArrayList<FullContact> fullContacts=Online.getGroupList(userID);
        if(fullContacts==null){
            fullContacts=new ArrayList<FullContact>();
        }
        System.out.println(new Gson().toJson(fullContacts));
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String userID=request.getParameter("userID");
//        ArrayList<FullContact> fullContacts=new ArrayList<FullContact>();
        ArrayList<FullContact> fullContacts=Online.getGroupList(userID);
        if(fullContacts==null){
            fullContacts=new ArrayList<FullContact>();
        }
        PrintWriter printWriter=response.getWriter();
        printWriter.print(new Gson().toJson(fullContacts));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
    }
}
