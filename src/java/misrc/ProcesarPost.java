package misrc;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcesarPost extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String login = request.getParameter("txtLogin");
            String password = request.getParameter("txtPassword");
            out.println("<h1>Login ingresado es: " + login + "</h1></br>");
            out.println("<h1>Password ingresado es: " + password + "</h1></br>");
        }
    }
}
