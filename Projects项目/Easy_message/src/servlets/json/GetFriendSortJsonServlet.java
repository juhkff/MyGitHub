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
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetFriendSortJsonServlet", urlPatterns = "/GetFriendSortJson")
public class GetFriendSortJsonServlet extends HttpServlet {
    public static void main(String[] args) {
        String userID = "7999375901";
        ArrayList<Sort> sorts = new ArrayList<Sort>();
        try {
            sorts = Online.getFriendSort(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sorts == null) {
            sorts = new ArrayList<Sort>();
        }
        System.out.println(new Gson().toJson(sorts));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String userID = request.getParameter("userID");

        ArrayList<Sort> sorts = new ArrayList<Sort>();
        try {
            sorts = Online.getFriendSort(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sorts == null) {
            sorts = new ArrayList<Sort>();
        }

        Gson gson = new Gson();
        String result = gson.toJson(sorts);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
    }
}
