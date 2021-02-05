package factoria2;

import controladores.VendedorJpaController;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class OperacionCrud {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAJSPSERVLETWEBPU");
    EntityManager em = emf.createEntityManager();
    VendedorJpaController vjc = new VendedorJpaController(emf);

    public List<Vendedor> obtenerListaVendedores() {
        List<Vendedor> vendedor_al = new ArrayList<Vendedor>();
        String jpql = "SELECT v.nombre, v.idVendedor FROM vendedor v";
        try {
            Query query = em.createQuery(jpql);
            vendedor_al = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error");
            vendedor_al = null;
        }
        return vendedor_al;
    }

    public boolean Validar(String user, int idV) {
        Boolean bandera = false;
        String jpql = "SELECT v FROM Vendedor v WHERE v.nombre='" + user + "' AND v.idVendedor=" + idV;
        Query query = em.createQuery(jpql);
        List<Vendedor> articulos_al = query.getResultList();
        System.out.println(articulos_al.get(0));
        if (articulos_al.size() >= 1) {
            bandera = true;
        }
        System.out.println(bandera);
        return bandera;
    }
}
