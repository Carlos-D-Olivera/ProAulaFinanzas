/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicolombo.ingsistemas.poo.finanzas.crudl;

import co.edu.unicolombo.ingsistemas.poo.finanzas.crudl.exceptions.NonexistentEntityException;
import co.edu.unicolombo.ingsistemas.poo.finanzas.crudl.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.ClienteOrganizacion;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Representante;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jimmy
 */
public class RepresentanteJpaController implements Serializable {

    public RepresentanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Representante representante) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteOrganizacion clientesOganizacionId = representante.getClientesOganizacionId();
            if (clientesOganizacionId != null) {
                clientesOganizacionId = em.getReference(clientesOganizacionId.getClass(), clientesOganizacionId.getNit());
                representante.setClientesOganizacionId(clientesOganizacionId);
            }
            em.persist(representante);
            if (clientesOganizacionId != null) {
                clientesOganizacionId.getRepresentanteList().add(representante);
                clientesOganizacionId = em.merge(clientesOganizacionId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRepresentante(representante.getNit()) != null) {
                throw new PreexistingEntityException("Representante " + representante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Representante representante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Representante persistentRepresentante = em.find(Representante.class, representante.getNit());
            ClienteOrganizacion clientesOganizacionIdOld = persistentRepresentante.getClientesOganizacionId();
            ClienteOrganizacion clientesOganizacionIdNew = representante.getClientesOganizacionId();
            if (clientesOganizacionIdNew != null) {
                clientesOganizacionIdNew = em.getReference(clientesOganizacionIdNew.getClass(), clientesOganizacionIdNew.getNit());
                representante.setClientesOganizacionId(clientesOganizacionIdNew);
            }
            representante = em.merge(representante);
            if (clientesOganizacionIdOld != null && !clientesOganizacionIdOld.equals(clientesOganizacionIdNew)) {
                clientesOganizacionIdOld.getRepresentanteList().remove(representante);
                clientesOganizacionIdOld = em.merge(clientesOganizacionIdOld);
            }
            if (clientesOganizacionIdNew != null && !clientesOganizacionIdNew.equals(clientesOganizacionIdOld)) {
                clientesOganizacionIdNew.getRepresentanteList().add(representante);
                clientesOganizacionIdNew = em.merge(clientesOganizacionIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = representante.getNit();
                if (findRepresentante(id) == null) {
                    throw new NonexistentEntityException("The representante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Representante representante;
            try {
                representante = em.getReference(Representante.class, id);
                representante.getNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The representante with id " + id + " no longer exists.", enfe);
            }
            ClienteOrganizacion clientesOganizacionId = representante.getClientesOganizacionId();
            if (clientesOganizacionId != null) {
                clientesOganizacionId.getRepresentanteList().remove(representante);
                clientesOganizacionId = em.merge(clientesOganizacionId);
            }
            em.remove(representante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Representante> findRepresentanteEntities() {
        return findRepresentanteEntities(true, -1, -1);
    }

    public List<Representante> findRepresentanteEntities(int maxResults, int firstResult) {
        return findRepresentanteEntities(false, maxResults, firstResult);
    }

    private List<Representante> findRepresentanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Representante.class));
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

    public Representante findRepresentante(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Representante.class, id);
        } finally {
            em.close();
        }
    }

    public int getRepresentanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Representante> rt = cq.from(Representante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
