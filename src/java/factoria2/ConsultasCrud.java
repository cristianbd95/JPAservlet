package factoria2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConsultasCrud {

    public List<Consulta1> obtenerConsulta1(Connection conexion) {
        List<Consulta1> consulta_al = new ArrayList<Consulta1>();
        String query = "SELECT  IdVendedor, nombre, ciudad, region FROM oficina o, vendedor v WHERE o.IdOficina=v.IdOficina";
        try {
            Statement sql = conexion.createStatement();
            ResultSet rs = sql.executeQuery(query);
            while (rs.next()) {
                Consulta1 consulta = new Consulta1();
                consulta.setIdVendedor(rs.getInt(1));
                consulta.setCiudad(rs.getString(2));
                consulta.setVendedor(rs.getString(3));
                consulta.setRegion(rs.getString(4));
                consulta_al.add(consulta);
            }
        } catch (SQLException ex) {
            System.out.println("Error");
            consulta_al = null;
        }
        return consulta_al;
    }

    public List<Consulta2> obtenerConsulta2(Connection conexion) {
        List<Consulta2> consulta_al = new ArrayList<Consulta2>();
        String query = "SELECT IdPedido, importe,nombre,empresa FROM pedido p,vendedor v,cliente c WHERE v.IdVendedor=p.IdVendedor AND p.IdCliente=c.IdCliente AND importe>25000";
        try {
            Statement sql = conexion.createStatement();
            ResultSet rs = sql.executeQuery(query);
            while (rs.next()) {
                Consulta2 consulta = new Consulta2();
                consulta.setIdPedido(rs.getInt(1));
                consulta.setImporte(rs.getDouble(2));
                consulta.setNombre(rs.getString(3));
                consulta.setEmpresa(rs.getString(4));
                consulta_al.add(consulta);
            }
        } catch (SQLException ex) {
            System.out.println("Error");
            consulta_al = null;
        }
        return consulta_al;
    }

    public List<Object[]> obtenerConsultasSinClase(Connection conexion) {
        List<Object[]> consulta_al = new ArrayList<Object[]>();
        String query = "SELECT  IdVendedor, nombre, ciudad, region FROM oficina o, vendedor v WHERE o.IdOficina=v.IdOficina";
        try {
            Statement sql = conexion.createStatement();
            ResultSet rs = sql.executeQuery(query);
            while (rs.next()) {
                Object[] objeto = new Object[4];
                objeto[0] = rs.getInt(1);
                objeto[1] = rs.getString(2);
                objeto[2] = rs.getString(3);
                objeto[3] = rs.getString(4);
                consulta_al.add(objeto);
            }
        } catch (SQLException ex) {
            System.out.println("Error");
            consulta_al = null;
        }
        return consulta_al;
    }

}
