package misrc;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Suma extends HttpServlet {

    public static int contador = 0;

    public double d1 = 0.0;
    public double d2 = 0.0;
    public double dS = 0.0;

    public void formulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<HTML><HEAD> <TITLE>SUMA DE 2 NUMEROS</TITLE></HEAD><BODY><CENTER><TABLE ALIGN=center BORDER=0 WIDTH='100%' HEIGHT='100%'><TR ALIGN=center><TD ALIGN=center>");
        out.println("<FORM ACTION=\"http://localhost:8090/SERVLET/Suma\" METHOD=post><TABLE BORDER=0 ALIGN=center BGCOLOR=#bbbbbb CELLPADDING=5 CELLSPACING=0>");
        out.println("<TR><TD COLSPAN=3><CENTER><H2>SUMA DE 2 NUMEROS</H2></CENTER></TD></TR>");
        out.println("<TR><TD COLSPAN=3 ALIGN=center><PRE>Numero 1  ?  <INPUT STYLE=text-align:center TYPE=text SIZE=13 NAME=txt_dNumero1 VALUE=" + d1 + "></PRE></TD></TR>");
        out.println("<TR><TD COLSPAN=3 ALIGN=center><PRE>Numero 2  ?  <INPUT STYLE=text-align:center TYPE=text SIZE=13 NAME=txt_dNumero2 VALUE=" + d2 + "></PRE></TD></TR>");
        out.println("<TR><TD COLSPAN=3 ALIGN=center><PRE>Resultado :  <INPUT STYLE=text-align:center TYPE=text SIZE=13 NAME=txt_dResultado VALUE=" + dS + " onFocus=blur();></PRE></TD></TR>");
        out.println("<TR><TD ALIGN=center><INPUT TYPE=submit NAME=bto_aceptar VALUE='    Aceptar    '></TD><TD ALIGN=center><INPUT TYPE=submit NAME=bto_limpiar VALUE='    Limpiar    '></TD><TD ALIGN=center><INPUT TYPE=button NAME=bto_cerrar VALUE='     Cerrar     ' onClick=self.close();></TD></TR>");
        out.println("</TABLE></FORM></TD></TR></TABLE></CENTER></BODY></HTML>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        if (request.getParameter("bto_limpiar") != null) {
            contador = 0;
        };

        if (contador == 0) {
            contador++;
            this.d1 = 0.0;
            this.d2 = 0.0;
            this.dS = 0.0;
            formulario(request, response);
        }

        if (request.getParameter("bto_aceptar") != null) {
            try {
                this.d1 = Double.parseDouble(request.getParameter("txt_dNumero1"));
                this.d2 = Double.parseDouble(request.getParameter("txt_dNumero2"));
                this.dS = d1 + d2;
                formulario(request, response);
            } catch (Exception e) {
                this.d1 = 0.0;
                this.d2 = 0.0;
                this.dS = 0.0;
                formulario(request, response);
                out.println("<script>alert('DATOS DE ENTRADA INCORRECTOS')</script>");
            }
        };

    }

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
        doPost(peticion, respuesta);
    }

    public void destroy() {
        super.destroy();
    }

}
