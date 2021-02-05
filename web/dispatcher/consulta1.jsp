
<%@page import="factoria2.OperacionesJPA"%>
<%@page import="entidades.Cliente"%>
<%@page import="java.util.ArrayList"%>
<%@page import="factoria2.Consulta1"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <br><center>
        <p>Estas logeado como <strong style="color:red">${logeado}</strong></p> <br><br>
        <p>Buscar Cliente Por Id.</p>
        <form action="http://localhost:8080/JPAJSPSERVLETWEB/Consultas" method="post"><br>
            Introduzca ID<input type="number" name="numId">
            <input type="submit" name="btoEnviar2" value="Buscar Cliente">
        </form>
        <form action="http://localhost:8080/JPAJSPSERVLETWEB/Consultas" method="post"><br>
            <input type="submit" name="btoEnviar" value="Enviar">
        </form>
    </center>
    <br><br><br>
    <%

        HttpSession misession = request.getSession(true);
        String logeado = (String) misession.getAttribute("logeado");
        if (request.getParameter("btoEnviar2") != null) {
            Cliente cliente = (Cliente) request.getAttribute("EnvioDatos");
            out.println("ID: " + cliente.getIdCliente()  + "<br>Empresa:" + cliente.getEmpresa()+ "<br>IdVendedor" + cliente.getIdVendedor().getIdVendedor());
        }
        OperacionesJPA oj = new OperacionesJPA();
        if (request.getParameter("btoEnviar") != null) {
            List<Cliente> consultas_al = oj.BuscarTodosClientes();
            if (consultas_al != null) {
                out.println("<center><table style='border: 2px solid black'>");
                out.println("<tr>");
                out.println("<th>ID </th>");
                out.println("<th>NOMBRE </th>");
                out.println("<th>ID VENDEDOR </th>");
                out.println("<th>LIMITE CREDITO </th>");
                out.println("</tr>");
                for (int i = 0; i < consultas_al.size(); i++) {
                    Cliente cliente = consultas_al.get(i);
                    out.println("<tr>");
                    out.println("<td>" + cliente.getIdCliente() + "</td>");
                    out.println("<td>" + cliente.getIdVendedor().getIdVendedor() + "</td>");
                    out.println("<td>" + cliente.getEmpresa() + "</td>");
                    out.println("<td>" + cliente.getLimiteCredito() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table></center>");

            } else {
                out.println("error");
            }
        }
    %>
</body>
</html>
