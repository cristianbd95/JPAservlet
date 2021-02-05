/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidades.Director;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Oficina;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Campus FP
 */
public class DirectorJpaController implements Serializable {

    public DirectorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Director director) throws PreexistingEntityException, Exception {
        if (director.getOficinaList() == null) {
            director.setOficinaList(new ArrayList<Oficina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Oficina> attachedOficinaList = new ArrayList<Oficina>();
            for (Oficina oficinaListOficinaToAttach : director.getOficinaList()) {
                oficinaListOficinaToAttach = em.getReference(oficinaListOficinaToAttach.getClass(), oficinaListOficinaToAttach.getIdOficina());
                attachedOficinaList.add(oficinaListOficinaToAttach);
            }
            director.setOficinaList(attachedOficinaList);
            em.persist(director);
            for (Oficina oficinaListOficina : director.getOficinaList()) {
                Director oldIdDirectorOfOficinaListOficina = oficinaListOficina.getIdDirector();
                oficinaListOficina.setIdDirector(director);
                oficinaListOficina = em.merge(oficinaListOficina);
                if (oldIdDirectorOfOficinaListOficina != null) {
                    oldIdDirectorOfOficinaListOficina.getOficinaList().remove(oficinaListOficina);
                    oldIdDirectorOfOficinaListOficina = em.merge(oldIdDirectorOfOficinaListOficina);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDirector(director.getIdDirector()) != null) {
                throw new PreexistingEntityException("Director " + director + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Director director) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Director persistentDirector = em.find(Director.class, director.getIdDirector());
            List<Oficina> oficinaListOld = persistentDirector.getOficinaList();
            List<Oficina> oficinaListNew = director.getOficinaList();
            List<String> illegalOrphanMessages = null;
            for (Oficina oficinaListOldOficina : oficinaListOld) {
                if (!oficinaListNew.contains(oficinaListOldOficina)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Oficina " + oficinaListOldOficina + " since its idDirector field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Oficina> attachedOficinaListNew = new ArrayList<Oficina>();
            for (Oficina oficinaListNewOficinaToAttach : oficinaListNew) {
                oficinaListNewOficinaToAttach = em.getReference(oficinaListNewOficinaToAttach.getClass(), oficinaListNewOficinaToAttach.getIdOficina());
                attachedOficinaListNew.add(oficinaListNewOficinaToAttach);
            }
            oficinaListNew = attachedOficinaListNew;
            director.setOficinaList(oficinaListNew);
            director = em.merge(director);
            for (Oficina oficinaListNewOficina : oficinaListNew) {
                if (!oficinaListOld.contains(oficinaListNewOficina)) {
                    Director oldIdDirectorOfOficinaListNewOficina = oficinaListNewOficina.getIdDirector();
                    oficinaListNewOficina.setIdDirector(director);
                    oficinaListNewOficina = em.merge(oficinaListNewOficina);
                    if (oldIdDirectorOfOficinaListNewOficina != null && !oldIdDirectorOfOficinaListNewOficina.equals(director)) {
                        oldIdDirectorOfOficinaListNewOficina.getOficinaList().remove(oficinaListNewOficina);
                        oldIdDirectorOfOficinaListNewOficina = em.merge(oldIdDirectorOfOficinaListNewOficina);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = director.getIdDirector();
                if (findDirector(id) == null) {
                    throw new NonexistentEntityException("The director with id " + id + " no longer exists.");
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
            Director director;
            try {
                director = em.getReference(Director.class, id);
                director.getIdDirector();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The director with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Oficina> oficinaListOrphanCheck = director.getOficinaList();
            for (Oficina oficinaListOrphanCheckOficina : oficinaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Director (" + director + ") cannot be destroyed since the Oficina " + oficinaListOrphanCheckOficina + " in its oficinaList field has a non-nullable idDirector field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(director);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Director> findDirectorEntities() {
        return findDirectorEntities(true, -1, -1);
    }

    public List<Director> findDirectorEntities(int maxResults, int firstResult) {
        return findDirectorEntities(false, maxResults, firstResult);
    }

    private List<Director> findDirectorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Director.class));
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

    public Director findDirector(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Director.class, id);
        } finally {
            em.close();
        }
    }

    public int getDirectorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Director> rt = cq.from(Director.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
