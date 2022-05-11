/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicolombo.ingsistemas.poo.finanzas.crudl;

import co.edu.unicolombo.ingsistemas.poo.finanzas.crudl.exceptions.IllegalOrphanException;
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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jimmy
 */
public class SucursalJpaController implements Serializable {

    public SucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sucursal sucursal) throws PreexistingEntityException, Exception {
        if (sucursal.getEmpleadoList() == null) {
            sucursal.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco bancoId = sucursal.getBancoId();
            if (bancoId != null) {
                bancoId = em.getReference(bancoId.getClass(), bancoId.getNit());
                sucursal.setBancoId(bancoId);
            }
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : sucursal.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getDni());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            sucursal.setEmpleadoList(attachedEmpleadoList);
            em.persist(sucursal);
            if (bancoId != null) {
                bancoId.getSucursalList().add(sucursal);
                bancoId = em.merge(bancoId);
            }
            for (Empleado empleadoListEmpleado : sucursal.getEmpleadoList()) {
                Sucursal oldSucursalIdOfEmpleadoListEmpleado = empleadoListEmpleado.getSucursalId();
                empleadoListEmpleado.setSucursalId(sucursal);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldSucursalIdOfEmpleadoListEmpleado != null) {
                    oldSucursalIdOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldSucursalIdOfEmpleadoListEmpleado = em.merge(oldSucursalIdOfEmpleadoListEmpleado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSucursal(sucursal.getId()) != null) {
                throw new PreexistingEntityException("Sucursal " + sucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sucursal sucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal persistentSucursal = em.find(Sucursal.class, sucursal.getId());
            Banco bancoIdOld = persistentSucursal.getBancoId();
            Banco bancoIdNew = sucursal.getBancoId();
            List<Empleado> empleadoListOld = persistentSucursal.getEmpleadoList();
            List<Empleado> empleadoListNew = sucursal.getEmpleadoList();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its sucursalId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (bancoIdNew != null) {
                bancoIdNew = em.getReference(bancoIdNew.getClass(), bancoIdNew.getNit());
                sucursal.setBancoId(bancoIdNew);
            }
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getDni());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            sucursal.setEmpleadoList(empleadoListNew);
            sucursal = em.merge(sucursal);
            if (bancoIdOld != null && !bancoIdOld.equals(bancoIdNew)) {
                bancoIdOld.getSucursalList().remove(sucursal);
                bancoIdOld = em.merge(bancoIdOld);
            }
            if (bancoIdNew != null && !bancoIdNew.equals(bancoIdOld)) {
                bancoIdNew.getSucursalList().add(sucursal);
                bancoIdNew = em.merge(bancoIdNew);
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Sucursal oldSucursalIdOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getSucursalId();
                    empleadoListNewEmpleado.setSucursalId(sucursal);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldSucursalIdOfEmpleadoListNewEmpleado != null && !oldSucursalIdOfEmpleadoListNewEmpleado.equals(sucursal)) {
                        oldSucursalIdOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldSucursalIdOfEmpleadoListNewEmpleado = em.merge(oldSucursalIdOfEmpleadoListNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sucursal.getId();
                if (findSucursal(id) == null) {
                    throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal sucursal;
            try {
                sucursal = em.getReference(Sucursal.class, id);
                sucursal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleado> empleadoListOrphanCheck = sucursal.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable sucursalId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Banco bancoId = sucursal.getBancoId();
            if (bancoId != null) {
                bancoId.getSucursalList().remove(sucursal);
                bancoId = em.merge(bancoId);
            }
            em.remove(sucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sucursal> findSucursalEntities() {
        return findSucursalEntities(true, -1, -1);
    }

    public List<Sucursal> findSucursalEntities(int maxResults, int firstResult) {
        return findSucursalEntities(false, maxResults, firstResult);
    }

    private List<Sucursal> findSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursal.class));
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

    public Sucursal findSucursal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursal> rt = cq.from(Sucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
