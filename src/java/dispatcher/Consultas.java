/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispatcher;

import entidades.Cliente;
import factoria2.Conexion;
import factoria2.Consulta1;
import factoria2.Consulta2;
import factoria2.ConsultasCrud;
import factoria2.OperacionesJPA;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Consultas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<String> clientes_al;
        try (PrintWriter out = response.getWriter()) {

            OperacionesJPA oj = new OperacionesJPA();

            String boton = request.getParameter("btoEnviar");
            String boton2 = request.getParameter("btoEnviar2");
            String boton3 = request.getParameter("btoEnviar3");
            String enviaForm = request.getParameter("enviaForm");

            if (boton != null) {
                //List<Cliente> clientes_al = oj.BuscarTodosClientes2();
                clientes_al = oj.BuscarTodosClientes2();
                System.out.println("ENTRO");
                if (clientes_al != null) {
                    System.out.println("ENTRO CLIENTES LLENO");
                    request.setAttribute("EnvioDatos1", clientes_al);
                    System.out.println(clientes_al.get(0));
                    request.getRequestDispatcher("dispatcher/consulta1.jsp?control=llego").forward(request, response);
                } else {
                    out.println("ERROR CONSULTA");
                }
            } else {
                System.out.println("NO TRAE VALOR");
            }

            if (boton2 != null) {
                int id = Integer.parseInt(request.getParameter("numId"));
                Cliente cliente = oj.buscar(id);
                if (cliente != null) {
                    request.setAttribute("EnvioDatos", cliente);
                    request.getRequestDispatcher("/dispatcher/consulta1.jsp?control=llego").forward(request, response);
                } else {
                    out.println("ERROR CONSULTA");
                }
            } else {
                out.println("NO SE HA ENCONTRADO ESE CLIENTE");
            }

            if (boton3 != null) {
                int id = Integer.parseInt(request.getParameter("numId2"));
                Cliente cliente = oj.buscar(id);
                if (cliente != null) {
                    request.setAttribute("EnvioDatos2", cliente);
                    request.getRequestDispatcher("/dispatcher/consulta2.jsp?control=llego").forward(request, response);
                } else {
                    out.println("ERROR CONSULTA");
                }
            } else {
                out.println("NO SE HA ENCONTRADO ESE CLIENTE");
            }

            if (enviaForm != null) {
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                String empresa = request.getParameter("empresa");
                int idVendedor = Integer.parseInt(request.getParameter("idVendedor"));
                int credito = Integer.parseInt(request.getParameter("credito"));
                System.out.println(idCliente+empresa);
                Cliente cliente = new Cliente();
                cliente.setEmpresa(empresa);
                oj.actualizarBueno(idCliente, empresa, credito);
                    
                request.getRequestDispatcher("/dispatcher/consulta2.jsp?control=llego").forward(request, response);
                
            }else{
                System.out.println("BOTON FORM NULO");
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
