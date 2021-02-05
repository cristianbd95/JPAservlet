package misrc;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Procesar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
          
            String login = request.getParameter("txtLogin");
            String password = request.getParameter("txtPassword");
            /*
            X =
            D = B2 -4AC = 0 RAIZ UNICA   SOLUCION REAL    X
                        > 0 DOS RAICES   SOLUCION REAL     X1,X2
                                
                        < 0 SOLUCION COMPLEJA
            
            */
            double x1 = 1.3;
            double x2 = 2.4;
            out.println("X1: <input type=text name=x1 value="+x1+">");
            out.println("X2: <input type=text name=x2 value="+x2+">");
            
                    
                    
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Procesar</title>");            
            out.println("</head>");
            out.println("<body>");
            //out.println("<h1>" + "Login: " + login + "</h1></br>");
            //out.println("<h1>" + "Password: " + password + "</h1></br>");
            out.println("</body>");
            out.println("</html>");
        }
    }



}
