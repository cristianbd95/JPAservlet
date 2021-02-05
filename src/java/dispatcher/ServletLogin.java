package dispatcher;

import factoria2.Conexion;
import factoria2.OperacionCrud;
import factoria2.Vendedor;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletLogin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        OperacionCrud oc = new OperacionCrud();
        String accion = request.getParameter("accion");
        if (accion.equalsIgnoreCase("Enviar")) {
            String login = request.getParameter("txtLogin");
            int password = Integer.parseInt(request.getParameter("txtPassword"));
            
            
            if (oc.Validar(login, password) == true) {
                HttpSession misession = request.getSession(true);
                Vendedor vendedor = new Vendedor();
                vendedor.setIdVendedor(password);
                vendedor.setNombre(login);
                String nombre = vendedor.getNombre();
                misession.setAttribute("logeado", nombre);
                
                request.setAttribute("usuario", vendedor); //Enviar este objeto a principal.jsp
                request.getRequestDispatcher("Controlador?direccion=principal").forward(request, response);
            } else {
                //request.getRequestDispatcher("/dispatcher/formulario.jsp").forward(request, response);
                out.println("VENDEDOR NULL");
                out.println(oc.Validar(login,password));
            }
        } else {
            request.getRequestDispatcher("/dispatcher/formulario.jsp").forward(request, response);
        }
    }

}
