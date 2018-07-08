package servlets.json;

import com.google.gson.Gson;
import model.contact.Sort;
import tools.Online;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

@WebServlet(name = "GetGroupSortJsonServlet",urlPatterns = "/GetGroupSortJson")
public class GetGroupSortJsonServlet extends HttpServlet {
    public static void main(String[] args){
        String userID="7999375901";
        ArrayList<Sort> sorts=new ArrayList<Sort>();
        sorts=Online.getGroupSortJson(userID);
        if(sorts==null){
            sorts=new ArrayList<Sort>();
        }
        //得到分组列表后
        Gson gson = new Gson();
        String groups = gson.toJson(sorts);
        System.out.println(groups);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String userID=request.getParameter("userID");
        ArrayList<Sort> sorts=new ArrayList<Sort>();
        sorts=Online.getGroupSortJson(userID);
        if(sorts==null){
            sorts=new ArrayList<Sort>();
        }
        //得到分组列表后
        Gson gson = new Gson();
        String groups = gson.toJson(sorts);

        PrintWriter printWriter=response.getWriter();
        printWriter.print(groups);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
    }
}
