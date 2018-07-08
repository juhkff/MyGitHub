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

@WebServlet(name = "UpdateContactNameServlet",urlPatterns = "/UpdateContactName")
public class UpdateContactNameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userID=request.getParameter("userID");
        String ID=request.getParameter("ID");
        String newName=request.getParameter("newName");
        PrintWriter printWriter=response.getWriter();
        try {
            Online.updateContactBZ(userID,ID,newName);
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
