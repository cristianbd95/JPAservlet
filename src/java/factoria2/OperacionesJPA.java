package factoria2;

import controladores.ClienteJpaController;
import controladores.exceptions.NonexistentEntityException;
import entidades.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class OperacionesJPA {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAJSPSERVLETWEBPU");
    EntityManager em = emf.createEntityManager();
    ClienteJpaController cjc = new ClienteJpaController(emf);

    public Cliente buscar(int idCliente) {
        Cliente cliente = cjc.findCliente(idCliente);

        return cliente;
    }

    public List<Cliente> BuscarTodosClientes() {
        List<Cliente> clientes_al = cjc.findClienteEntities();
        return clientes_al;
    }

    public List<String> BuscarTodosClientes2() {
        String jpql = "SELECT c.empresa FROM Cliente c";
        Query query = em.createQuery(jpql);
        List<String> clientes_al = query.getResultList();
        return clientes_al;
    }

    public void actualizar(Cliente cliente) {
        em.getTransaction().begin();
        em.merge(cliente);
        em.getTransaction().commit();
        System.out.println("ACT CORRECTA");
    }

    public  void actualizarBueno(int idCliente, String empresa, int credito) {
        Cliente cliente = em.find(Cliente.class, idCliente);
        em.getTransaction().begin();
        cliente.setEmpresa(empresa);
        cliente.setLimiteCredito(credito);
        em.getTransaction().commit();
        System.out.println("ACT CORRECTA");
    }

}
