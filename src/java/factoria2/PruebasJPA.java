/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria2;

import controladores.ClienteJpaController;
import entidades.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author kk
 */
public class PruebasJPA {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAJSPSERVLETWEBPU");
    static EntityManager em = emf.createEntityManager();
    static ClienteJpaController cjc = new ClienteJpaController(emf);
    public static void main(String[] args) {
        
        Cliente cliente = new Cliente();
        Vendedor vendedor = new Vendedor();
        vendedor.setIdVendedor(101);
        cliente.setIdCliente(2101);
        cliente.setLimiteCredito(55000);
        actualizar();
    }
    
     public static void actualizar(){
         Cliente cliente = em.find(Cliente.class, 2101);
        em.getTransaction().begin();
        cliente.setEmpresa("Jones Actualizado");
        em.getTransaction().commit();
        System.out.println("ACT CORRECTA");
    }
    
}
