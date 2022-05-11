/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicolombo.ingsistemas.poo.finanzas.crudl;

import co.edu.unicolombo.ingsistemas.poo.finanzas.crudl.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cuenta;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.CuentaAhorro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jimmy
 */
public class CuentaAhorroJpaController implements Serializable {

    public CuentaAhorroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentaAhorro cuentaAhorro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuentasId = cuentaAhorro.getCuentasId();
            if (cuentasId != null) {
                cuentasId = em.getReference(cuentasId.getClass(), cuentasId.getCcc());
                cuentaAhorro.setCuentasId(cuentasId);
            }
            em.persist(cuentaAhorro);
            if (cuentasId != null) {
                cuentasId.getCuentaAhorroList().add(cuentaAhorro);
                cuentasId = em.merge(cuentasId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentaAhorro cuentaAhorro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaAhorro persistentCuentaAhorro = em.find(CuentaAhorro.class, cuentaAhorro.getId());
            Cuenta cuentasIdOld = persistentCuentaAhorro.getCuentasId();
            Cuenta cuentasIdNew = cuentaAhorro.getCuentasId();
            if (cuentasIdNew != null) {
                cuentasIdNew = em.getReference(cuentasIdNew.getClass(), cuentasIdNew.getCcc());
                cuentaAhorro.setCuentasId(cuentasIdNew);
            }
            cuentaAhorro = em.merge(cuentaAhorro);
            if (cuentasIdOld != null && !cuentasIdOld.equals(cuentasIdNew)) {
                cuentasIdOld.getCuentaAhorroList().remove(cuentaAhorro);
                cuentasIdOld = em.merge(cuentasIdOld);
            }
            if (cuentasIdNew != null && !cuentasIdNew.equals(cuentasIdOld)) {
                cuentasIdNew.getCuentaAhorroList().add(cuentaAhorro);
                cuentasIdNew = em.merge(cuentasIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentaAhorro.getId();
                if (findCuentaAhorro(id) == null) {
                    throw new NonexistentEntityException("The cuentaAhorro with id " + id + " no longer exists.");
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
            CuentaAhorro cuentaAhorro;
            try {
                cuentaAhorro = em.getReference(CuentaAhorro.class, id);
                cuentaAhorro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentaAhorro with id " + id + " no longer exists.", enfe);
            }
            Cuenta cuentasId = cuentaAhorro.getCuentasId();
            if (cuentasId != null) {
                cuentasId.getCuentaAhorroList().remove(cuentaAhorro);
                cuentasId = em.merge(cuentasId);
            }
            em.remove(cuentaAhorro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentaAhorro> findCuentaAhorroEntities() {
        return findCuentaAhorroEntities(true, -1, -1);
    }

    public List<CuentaAhorro> findCuentaAhorroEntities(int maxResults, int firstResult) {
        return findCuentaAhorroEntities(false, maxResults, firstResult);
    }

    private List<CuentaAhorro> findCuentaAhorroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuentaAhorro.class));
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

    public CuentaAhorro findCuentaAhorro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuentaAhorro.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaAhorroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuentaAhorro> rt = cq.from(CuentaAhorro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
