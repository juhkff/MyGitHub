package servlets.online;

import tools.Online;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "updateSortServlet",urlPatterns = "/updateSort")
public class updateSortServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userID=request.getParameter("userID");
        String oldName=request.getParameter("oldName");
        String newName=request.getParameter("newName");
        PrintWriter printWriter=response.getWriter();
        try {
            Online.updateSort(userID,oldName,newName);
            printWriter.print("success");
        } catch (SQLException e) {
            e.printStackTrace();
            printWriter.print("false");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
    }
}
