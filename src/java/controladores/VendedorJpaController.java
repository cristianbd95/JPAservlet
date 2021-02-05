/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Oficina;
import entidades.Cliente;
import java.util.ArrayList;
import java.util.List;
import entidades.Pedido;
import entidades.Vendedor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Campus FP
 */
public class VendedorJpaController implements Serializable {

    public VendedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vendedor vendedor) throws PreexistingEntityException, Exception {
        if (vendedor.getClienteList() == null) {
            vendedor.setClienteList(new ArrayList<Cliente>());
        }
        if (vendedor.getPedidoList() == null) {
            vendedor.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oficina idOficina = vendedor.getIdOficina();
            if (idOficina != null) {
                idOficina = em.getReference(idOficina.getClass(), idOficina.getIdOficina());
                vendedor.setIdOficina(idOficina);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : vendedor.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            vendedor.setClienteList(attachedClienteList);
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : vendedor.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getIdPedido());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            vendedor.setPedidoList(attachedPedidoList);
            em.persist(vendedor);
            if (idOficina != null) {
                idOficina.getVendedorList().add(vendedor);
                idOficina = em.merge(idOficina);
            }
            for (Cliente clienteListCliente : vendedor.getClienteList()) {
                Vendedor oldIdVendedorOfClienteListCliente = clienteListCliente.getIdVendedor();
                clienteListCliente.setIdVendedor(vendedor);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdVendedorOfClienteListCliente != null) {
                    oldIdVendedorOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdVendedorOfClienteListCliente = em.merge(oldIdVendedorOfClienteListCliente);
                }
            }
            for (Pedido pedidoListPedido : vendedor.getPedidoList()) {
                Vendedor oldIdVendedorOfPedidoListPedido = pedidoListPedido.getIdVendedor();
                pedidoListPedido.setIdVendedor(vendedor);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldIdVendedorOfPedidoListPedido != null) {
                    oldIdVendedorOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldIdVendedorOfPedidoListPedido = em.merge(oldIdVendedorOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVendedor(vendedor.getIdVendedor()) != null) {
                throw new PreexistingEntityException("Vendedor " + vendedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vendedor vendedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor persistentVendedor = em.find(Vendedor.class, vendedor.getIdVendedor());
            Oficina idOficinaOld = persistentVendedor.getIdOficina();
            Oficina idOficinaNew = vendedor.getIdOficina();
            List<Cliente> clienteListOld = persistentVendedor.getClienteList();
            List<Cliente> clienteListNew = vendedor.getClienteList();
            List<Pedido> pedidoListOld = persistentVendedor.getPedidoList();
            List<Pedido> pedidoListNew = vendedor.getPedidoList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its idVendedor field is not nullable.");
                }
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its idVendedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idOficinaNew != null) {
                idOficinaNew = em.getReference(idOficinaNew.getClass(), idOficinaNew.getIdOficina());
                vendedor.setIdOficina(idOficinaNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            vendedor.setClienteList(clienteListNew);
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getIdPedido());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            vendedor.setPedidoList(pedidoListNew);
            vendedor = em.merge(vendedor);
            if (idOficinaOld != null && !idOficinaOld.equals(idOficinaNew)) {
                idOficinaOld.getVendedorList().remove(vendedor);
                idOficinaOld = em.merge(idOficinaOld);
            }
            if (idOficinaNew != null && !idOficinaNew.equals(idOficinaOld)) {
                idOficinaNew.getVendedorList().add(vendedor);
                idOficinaNew = em.merge(idOficinaNew);
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Vendedor oldIdVendedorOfClienteListNewCliente = clienteListNewCliente.getIdVendedor();
                    clienteListNewCliente.setIdVendedor(vendedor);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdVendedorOfClienteListNewCliente != null && !oldIdVendedorOfClienteListNewCliente.equals(vendedor)) {
                        oldIdVendedorOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdVendedorOfClienteListNewCliente = em.merge(oldIdVendedorOfClienteListNewCliente);
                    }
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Vendedor oldIdVendedorOfPedidoListNewPedido = pedidoListNewPedido.getIdVendedor();
                    pedidoListNewPedido.setIdVendedor(vendedor);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldIdVendedorOfPedidoListNewPedido != null && !oldIdVendedorOfPedidoListNewPedido.equals(vendedor)) {
                        oldIdVendedorOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldIdVendedorOfPedidoListNewPedido = em.merge(oldIdVendedorOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vendedor.getIdVendedor();
                if (findVendedor(id) == null) {
                    throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor vendedor;
            try {
                vendedor = em.getReference(Vendedor.class, id);
                vendedor.getIdVendedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = vendedor.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vendedor (" + vendedor + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable idVendedor field.");
            }
            List<Pedido> pedidoListOrphanCheck = vendedor.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vendedor (" + vendedor + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable idVendedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Oficina idOficina = vendedor.getIdOficina();
            if (idOficina != null) {
                idOficina.getVendedorList().remove(vendedor);
                idOficina = em.merge(idOficina);
            }
            em.remove(vendedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vendedor> findVendedorEntities() {
        return findVendedorEntities(true, -1, -1);
    }

    public List<Vendedor> findVendedorEntities(int maxResults, int firstResult) {
        return findVendedorEntities(false, maxResults, firstResult);
    }

    private List<Vendedor> findVendedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vendedor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vendedor findVendedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vendedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vendedor> rt = cq.from(Vendedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
