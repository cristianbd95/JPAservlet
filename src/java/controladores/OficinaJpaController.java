/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Director;
import entidades.Oficina;
import entidades.Vendedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Campus FP
 */
public class OficinaJpaController implements Serializable {

    public OficinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Oficina oficina) throws PreexistingEntityException, Exception {
        if (oficina.getVendedorList() == null) {
            oficina.setVendedorList(new ArrayList<Vendedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Director idDirector = oficina.getIdDirector();
            if (idDirector != null) {
                idDirector = em.getReference(idDirector.getClass(), idDirector.getIdDirector());
                oficina.setIdDirector(idDirector);
            }
            List<Vendedor> attachedVendedorList = new ArrayList<Vendedor>();
            for (Vendedor vendedorListVendedorToAttach : oficina.getVendedorList()) {
                vendedorListVendedorToAttach = em.getReference(vendedorListVendedorToAttach.getClass(), vendedorListVendedorToAttach.getIdVendedor());
                attachedVendedorList.add(vendedorListVendedorToAttach);
            }
            oficina.setVendedorList(attachedVendedorList);
            em.persist(oficina);
            if (idDirector != null) {
                idDirector.getOficinaList().add(oficina);
                idDirector = em.merge(idDirector);
            }
            for (Vendedor vendedorListVendedor : oficina.getVendedorList()) {
                Oficina oldIdOficinaOfVendedorListVendedor = vendedorListVendedor.getIdOficina();
                vendedorListVendedor.setIdOficina(oficina);
                vendedorListVendedor = em.merge(vendedorListVendedor);
                if (oldIdOficinaOfVendedorListVendedor != null) {
                    oldIdOficinaOfVendedorListVendedor.getVendedorList().remove(vendedorListVendedor);
                    oldIdOficinaOfVendedorListVendedor = em.merge(oldIdOficinaOfVendedorListVendedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOficina(oficina.getIdOficina()) != null) {
                throw new PreexistingEntityException("Oficina " + oficina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Oficina oficina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oficina persistentOficina = em.find(Oficina.class, oficina.getIdOficina());
            Director idDirectorOld = persistentOficina.getIdDirector();
            Director idDirectorNew = oficina.getIdDirector();
            List<Vendedor> vendedorListOld = persistentOficina.getVendedorList();
            List<Vendedor> vendedorListNew = oficina.getVendedorList();
            if (idDirectorNew != null) {
                idDirectorNew = em.getReference(idDirectorNew.getClass(), idDirectorNew.getIdDirector());
                oficina.setIdDirector(idDirectorNew);
            }
            List<Vendedor> attachedVendedorListNew = new ArrayList<Vendedor>();
            for (Vendedor vendedorListNewVendedorToAttach : vendedorListNew) {
                vendedorListNewVendedorToAttach = em.getReference(vendedorListNewVendedorToAttach.getClass(), vendedorListNewVendedorToAttach.getIdVendedor());
                attachedVendedorListNew.add(vendedorListNewVendedorToAttach);
            }
            vendedorListNew = attachedVendedorListNew;
            oficina.setVendedorList(vendedorListNew);
            oficina = em.merge(oficina);
            if (idDirectorOld != null && !idDirectorOld.equals(idDirectorNew)) {
                idDirectorOld.getOficinaList().remove(oficina);
                idDirectorOld = em.merge(idDirectorOld);
            }
            if (idDirectorNew != null && !idDirectorNew.equals(idDirectorOld)) {
                idDirectorNew.getOficinaList().add(oficina);
                idDirectorNew = em.merge(idDirectorNew);
            }
            for (Vendedor vendedorListOldVendedor : vendedorListOld) {
                if (!vendedorListNew.contains(vendedorListOldVendedor)) {
                    vendedorListOldVendedor.setIdOficina(null);
                    vendedorListOldVendedor = em.merge(vendedorListOldVendedor);
                }
            }
            for (Vendedor vendedorListNewVendedor : vendedorListNew) {
                if (!vendedorListOld.contains(vendedorListNewVendedor)) {
                    Oficina oldIdOficinaOfVendedorListNewVendedor = vendedorListNewVendedor.getIdOficina();
                    vendedorListNewVendedor.setIdOficina(oficina);
                    vendedorListNewVendedor = em.merge(vendedorListNewVendedor);
                    if (oldIdOficinaOfVendedorListNewVendedor != null && !oldIdOficinaOfVendedorListNewVendedor.equals(oficina)) {
                        oldIdOficinaOfVendedorListNewVendedor.getVendedorList().remove(vendedorListNewVendedor);
                        oldIdOficinaOfVendedorListNewVendedor = em.merge(oldIdOficinaOfVendedorListNewVendedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = oficina.getIdOficina();
                if (findOficina(id) == null) {
                    throw new NonexistentEntityException("The oficina with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oficina oficina;
            try {
                oficina = em.getReference(Oficina.class, id);
                oficina.getIdOficina();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The oficina with id " + id + " no longer exists.", enfe);
            }
            Director idDirector = oficina.getIdDirector();
            if (idDirector != null) {
                idDirector.getOficinaList().remove(oficina);
                idDirector = em.merge(idDirector);
            }
            List<Vendedor> vendedorList = oficina.getVendedorList();
            for (Vendedor vendedorListVendedor : vendedorList) {
                vendedorListVendedor.setIdOficina(null);
                vendedorListVendedor = em.merge(vendedorListVendedor);
            }
            em.remove(oficina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Oficina> findOficinaEntities() {
        return findOficinaEntities(true, -1, -1);
    }

    public List<Oficina> findOficinaEntities(int maxResults, int firstResult) {
        return findOficinaEntities(false, maxResults, firstResult);
    }

    private List<Oficina> findOficinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Oficina.class));
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

    public Oficina findOficina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Oficina.class, id);
        } finally {
            em.close();
        }
    }

    public int getOficinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Oficina> rt = cq.from(Oficina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
