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
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Banco;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Empleado;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Sucursal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jimmy
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco bancoId = empleado.getBancoId();
            if (bancoId != null) {
                bancoId = em.getReference(bancoId.getClass(), bancoId.getNit());
                empleado.setBancoId(bancoId);
            }
            Sucursal sucursalId = empleado.getSucursalId();
            if (sucursalId != null) {
                sucursalId = em.getReference(sucursalId.getClass(), sucursalId.getId());
                empleado.setSucursalId(sucursalId);
            }
            em.persist(empleado);
            if (bancoId != null) {
                bancoId.getEmpleadoList().add(empleado);
                bancoId = em.merge(bancoId);
            }
            if (sucursalId != null) {
                sucursalId.getEmpleadoList().add(empleado);
                sucursalId = em.merge(sucursalId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleado(empleado.getDni()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getDni());
            Banco bancoIdOld = persistentEmpleado.getBancoId();
            Banco bancoIdNew = empleado.getBancoId();
            Sucursal sucursalIdOld = persistentEmpleado.getSucursalId();
            Sucursal sucursalIdNew = empleado.getSucursalId();
            if (bancoIdNew != null) {
                bancoIdNew = em.getReference(bancoIdNew.getClass(), bancoIdNew.getNit());
                empleado.setBancoId(bancoIdNew);
            }
            if (sucursalIdNew != null) {
                sucursalIdNew = em.getReference(sucursalIdNew.getClass(), sucursalIdNew.getId());
                empleado.setSucursalId(sucursalIdNew);
            }
            empleado = em.merge(empleado);
            if (bancoIdOld != null && !bancoIdOld.equals(bancoIdNew)) {
                bancoIdOld.getEmpleadoList().remove(empleado);
                bancoIdOld = em.merge(bancoIdOld);
            }
            if (bancoIdNew != null && !bancoIdNew.equals(bancoIdOld)) {
                bancoIdNew.getEmpleadoList().add(empleado);
                bancoIdNew = em.merge(bancoIdNew);
            }
            if (sucursalIdOld != null && !sucursalIdOld.equals(sucursalIdNew)) {
                sucursalIdOld.getEmpleadoList().remove(empleado);
                sucursalIdOld = em.merge(sucursalIdOld);
            }
            if (sucursalIdNew != null && !sucursalIdNew.equals(sucursalIdOld)) {
                sucursalIdNew.getEmpleadoList().add(empleado);
                sucursalIdNew = em.merge(sucursalIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empleado.getDni();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getDni();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Banco bancoId = empleado.getBancoId();
            if (bancoId != null) {
                bancoId.getEmpleadoList().remove(empleado);
                bancoId = em.merge(bancoId);
            }
            Sucursal sucursalId = empleado.getSucursalId();
            if (sucursalId != null) {
                sucursalId.getEmpleadoList().remove(empleado);
                sucursalId = em.merge(sucursalId);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
