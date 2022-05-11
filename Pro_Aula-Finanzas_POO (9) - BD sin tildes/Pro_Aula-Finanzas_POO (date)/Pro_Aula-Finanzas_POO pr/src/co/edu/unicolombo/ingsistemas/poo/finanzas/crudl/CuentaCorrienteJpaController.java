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
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.CuentaCorriente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jimmy
 */
public class CuentaCorrienteJpaController implements Serializable {

    public CuentaCorrienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentaCorriente cuentaCorriente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuentaId = cuentaCorriente.getCuentaId();
            if (cuentaId != null) {
                cuentaId = em.getReference(cuentaId.getClass(), cuentaId.getCcc());
                cuentaCorriente.setCuentaId(cuentaId);
            }
            em.persist(cuentaCorriente);
            if (cuentaId != null) {
                cuentaId.getCuentaCorrienteList().add(cuentaCorriente);
                cuentaId = em.merge(cuentaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentaCorriente cuentaCorriente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaCorriente persistentCuentaCorriente = em.find(CuentaCorriente.class, cuentaCorriente.getId());
            Cuenta cuentaIdOld = persistentCuentaCorriente.getCuentaId();
            Cuenta cuentaIdNew = cuentaCorriente.getCuentaId();
            if (cuentaIdNew != null) {
                cuentaIdNew = em.getReference(cuentaIdNew.getClass(), cuentaIdNew.getCcc());
                cuentaCorriente.setCuentaId(cuentaIdNew);
            }
            cuentaCorriente = em.merge(cuentaCorriente);
            if (cuentaIdOld != null && !cuentaIdOld.equals(cuentaIdNew)) {
                cuentaIdOld.getCuentaCorrienteList().remove(cuentaCorriente);
                cuentaIdOld = em.merge(cuentaIdOld);
            }
            if (cuentaIdNew != null && !cuentaIdNew.equals(cuentaIdOld)) {
                cuentaIdNew.getCuentaCorrienteList().add(cuentaCorriente);
                cuentaIdNew = em.merge(cuentaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentaCorriente.getId();
                if (findCuentaCorriente(id) == null) {
                    throw new NonexistentEntityException("The cuentaCorriente with id " + id + " no longer exists.");
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
            CuentaCorriente cuentaCorriente;
            try {
                cuentaCorriente = em.getReference(CuentaCorriente.class, id);
                cuentaCorriente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentaCorriente with id " + id + " no longer exists.", enfe);
            }
            Cuenta cuentaId = cuentaCorriente.getCuentaId();
            if (cuentaId != null) {
                cuentaId.getCuentaCorrienteList().remove(cuentaCorriente);
                cuentaId = em.merge(cuentaId);
            }
            em.remove(cuentaCorriente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentaCorriente> findCuentaCorrienteEntities() {
        return findCuentaCorrienteEntities(true, -1, -1);
    }

    public List<CuentaCorriente> findCuentaCorrienteEntities(int maxResults, int firstResult) {
        return findCuentaCorrienteEntities(false, maxResults, firstResult);
    }

    private List<CuentaCorriente> findCuentaCorrienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuentaCorriente.class));
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

    public CuentaCorriente findCuentaCorriente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuentaCorriente.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCorrienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuentaCorriente> rt = cq.from(CuentaCorriente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
