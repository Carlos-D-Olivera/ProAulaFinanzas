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
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cliente;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cuenta;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.CuentaAhorro;
import java.util.ArrayList;
import java.util.List;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.CuentaCorriente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jimmy
 */
public class CuentaJpaController implements Serializable {

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuenta cuenta) throws PreexistingEntityException, Exception {
        if (cuenta.getCuentaAhorroList() == null) {
            cuenta.setCuentaAhorroList(new ArrayList<CuentaAhorro>());
        }
        if (cuenta.getCuentaCorrienteList() == null) {
            cuenta.setCuentaCorrienteList(new ArrayList<CuentaCorriente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco bancoId = cuenta.getBancoId();
            if (bancoId != null) {
                bancoId = em.getReference(bancoId.getClass(), bancoId.getNit());
                cuenta.setBancoId(bancoId);
            }
            Cliente clienteId = cuenta.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getId());
                cuenta.setClienteId(clienteId);
            }
            List<CuentaAhorro> attachedCuentaAhorroList = new ArrayList<CuentaAhorro>();
            for (CuentaAhorro cuentaAhorroListCuentaAhorroToAttach : cuenta.getCuentaAhorroList()) {
                cuentaAhorroListCuentaAhorroToAttach = em.getReference(cuentaAhorroListCuentaAhorroToAttach.getClass(), cuentaAhorroListCuentaAhorroToAttach.getId());
                attachedCuentaAhorroList.add(cuentaAhorroListCuentaAhorroToAttach);
            }
            cuenta.setCuentaAhorroList(attachedCuentaAhorroList);
            List<CuentaCorriente> attachedCuentaCorrienteList = new ArrayList<CuentaCorriente>();
            for (CuentaCorriente cuentaCorrienteListCuentaCorrienteToAttach : cuenta.getCuentaCorrienteList()) {
                cuentaCorrienteListCuentaCorrienteToAttach = em.getReference(cuentaCorrienteListCuentaCorrienteToAttach.getClass(), cuentaCorrienteListCuentaCorrienteToAttach.getId());
                attachedCuentaCorrienteList.add(cuentaCorrienteListCuentaCorrienteToAttach);
            }
            cuenta.setCuentaCorrienteList(attachedCuentaCorrienteList);
            em.persist(cuenta);
            if (bancoId != null) {
                bancoId.getCuentaList().add(cuenta);
                bancoId = em.merge(bancoId);
            }
            if (clienteId != null) {
                clienteId.getCuentaList().add(cuenta);
                clienteId = em.merge(clienteId);
            }
            for (CuentaAhorro cuentaAhorroListCuentaAhorro : cuenta.getCuentaAhorroList()) {
                Cuenta oldCuentasIdOfCuentaAhorroListCuentaAhorro = cuentaAhorroListCuentaAhorro.getCuentasId();
                cuentaAhorroListCuentaAhorro.setCuentasId(cuenta);
                cuentaAhorroListCuentaAhorro = em.merge(cuentaAhorroListCuentaAhorro);
                if (oldCuentasIdOfCuentaAhorroListCuentaAhorro != null) {
                    oldCuentasIdOfCuentaAhorroListCuentaAhorro.getCuentaAhorroList().remove(cuentaAhorroListCuentaAhorro);
                    oldCuentasIdOfCuentaAhorroListCuentaAhorro = em.merge(oldCuentasIdOfCuentaAhorroListCuentaAhorro);
                }
            }
            for (CuentaCorriente cuentaCorrienteListCuentaCorriente : cuenta.getCuentaCorrienteList()) {
                Cuenta oldCuentaIdOfCuentaCorrienteListCuentaCorriente = cuentaCorrienteListCuentaCorriente.getCuentaId();
                cuentaCorrienteListCuentaCorriente.setCuentaId(cuenta);
                cuentaCorrienteListCuentaCorriente = em.merge(cuentaCorrienteListCuentaCorriente);
                if (oldCuentaIdOfCuentaCorrienteListCuentaCorriente != null) {
                    oldCuentaIdOfCuentaCorrienteListCuentaCorriente.getCuentaCorrienteList().remove(cuentaCorrienteListCuentaCorriente);
                    oldCuentaIdOfCuentaCorrienteListCuentaCorriente = em.merge(oldCuentaIdOfCuentaCorrienteListCuentaCorriente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuenta(cuenta.getCcc()) != null) {
                throw new PreexistingEntityException("Cuenta " + cuenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getCcc());
            Banco bancoIdOld = persistentCuenta.getBancoId();
            Banco bancoIdNew = cuenta.getBancoId();
            Cliente clienteIdOld = persistentCuenta.getClienteId();
            Cliente clienteIdNew = cuenta.getClienteId();
            List<CuentaAhorro> cuentaAhorroListOld = persistentCuenta.getCuentaAhorroList();
            List<CuentaAhorro> cuentaAhorroListNew = cuenta.getCuentaAhorroList();
            List<CuentaCorriente> cuentaCorrienteListOld = persistentCuenta.getCuentaCorrienteList();
            List<CuentaCorriente> cuentaCorrienteListNew = cuenta.getCuentaCorrienteList();
            List<String> illegalOrphanMessages = null;
            for (CuentaAhorro cuentaAhorroListOldCuentaAhorro : cuentaAhorroListOld) {
                if (!cuentaAhorroListNew.contains(cuentaAhorroListOldCuentaAhorro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CuentaAhorro " + cuentaAhorroListOldCuentaAhorro + " since its cuentasId field is not nullable.");
                }
            }
            for (CuentaCorriente cuentaCorrienteListOldCuentaCorriente : cuentaCorrienteListOld) {
                if (!cuentaCorrienteListNew.contains(cuentaCorrienteListOldCuentaCorriente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CuentaCorriente " + cuentaCorrienteListOldCuentaCorriente + " since its cuentaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (bancoIdNew != null) {
                bancoIdNew = em.getReference(bancoIdNew.getClass(), bancoIdNew.getNit());
                cuenta.setBancoId(bancoIdNew);
            }
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getId());
                cuenta.setClienteId(clienteIdNew);
            }
            List<CuentaAhorro> attachedCuentaAhorroListNew = new ArrayList<CuentaAhorro>();
            for (CuentaAhorro cuentaAhorroListNewCuentaAhorroToAttach : cuentaAhorroListNew) {
                cuentaAhorroListNewCuentaAhorroToAttach = em.getReference(cuentaAhorroListNewCuentaAhorroToAttach.getClass(), cuentaAhorroListNewCuentaAhorroToAttach.getId());
                attachedCuentaAhorroListNew.add(cuentaAhorroListNewCuentaAhorroToAttach);
            }
            cuentaAhorroListNew = attachedCuentaAhorroListNew;
            cuenta.setCuentaAhorroList(cuentaAhorroListNew);
            List<CuentaCorriente> attachedCuentaCorrienteListNew = new ArrayList<CuentaCorriente>();
            for (CuentaCorriente cuentaCorrienteListNewCuentaCorrienteToAttach : cuentaCorrienteListNew) {
                cuentaCorrienteListNewCuentaCorrienteToAttach = em.getReference(cuentaCorrienteListNewCuentaCorrienteToAttach.getClass(), cuentaCorrienteListNewCuentaCorrienteToAttach.getId());
                attachedCuentaCorrienteListNew.add(cuentaCorrienteListNewCuentaCorrienteToAttach);
            }
            cuentaCorrienteListNew = attachedCuentaCorrienteListNew;
            cuenta.setCuentaCorrienteList(cuentaCorrienteListNew);
            cuenta = em.merge(cuenta);
            if (bancoIdOld != null && !bancoIdOld.equals(bancoIdNew)) {
                bancoIdOld.getCuentaList().remove(cuenta);
                bancoIdOld = em.merge(bancoIdOld);
            }
            if (bancoIdNew != null && !bancoIdNew.equals(bancoIdOld)) {
                bancoIdNew.getCuentaList().add(cuenta);
                bancoIdNew = em.merge(bancoIdNew);
            }
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getCuentaList().remove(cuenta);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getCuentaList().add(cuenta);
                clienteIdNew = em.merge(clienteIdNew);
            }
            for (CuentaAhorro cuentaAhorroListNewCuentaAhorro : cuentaAhorroListNew) {
                if (!cuentaAhorroListOld.contains(cuentaAhorroListNewCuentaAhorro)) {
                    Cuenta oldCuentasIdOfCuentaAhorroListNewCuentaAhorro = cuentaAhorroListNewCuentaAhorro.getCuentasId();
                    cuentaAhorroListNewCuentaAhorro.setCuentasId(cuenta);
                    cuentaAhorroListNewCuentaAhorro = em.merge(cuentaAhorroListNewCuentaAhorro);
                    if (oldCuentasIdOfCuentaAhorroListNewCuentaAhorro != null && !oldCuentasIdOfCuentaAhorroListNewCuentaAhorro.equals(cuenta)) {
                        oldCuentasIdOfCuentaAhorroListNewCuentaAhorro.getCuentaAhorroList().remove(cuentaAhorroListNewCuentaAhorro);
                        oldCuentasIdOfCuentaAhorroListNewCuentaAhorro = em.merge(oldCuentasIdOfCuentaAhorroListNewCuentaAhorro);
                    }
                }
            }
            for (CuentaCorriente cuentaCorrienteListNewCuentaCorriente : cuentaCorrienteListNew) {
                if (!cuentaCorrienteListOld.contains(cuentaCorrienteListNewCuentaCorriente)) {
                    Cuenta oldCuentaIdOfCuentaCorrienteListNewCuentaCorriente = cuentaCorrienteListNewCuentaCorriente.getCuentaId();
                    cuentaCorrienteListNewCuentaCorriente.setCuentaId(cuenta);
                    cuentaCorrienteListNewCuentaCorriente = em.merge(cuentaCorrienteListNewCuentaCorriente);
                    if (oldCuentaIdOfCuentaCorrienteListNewCuentaCorriente != null && !oldCuentaIdOfCuentaCorrienteListNewCuentaCorriente.equals(cuenta)) {
                        oldCuentaIdOfCuentaCorrienteListNewCuentaCorriente.getCuentaCorrienteList().remove(cuentaCorrienteListNewCuentaCorriente);
                        oldCuentaIdOfCuentaCorrienteListNewCuentaCorriente = em.merge(oldCuentaIdOfCuentaCorrienteListNewCuentaCorriente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cuenta.getCcc();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
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
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getCcc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CuentaAhorro> cuentaAhorroListOrphanCheck = cuenta.getCuentaAhorroList();
            for (CuentaAhorro cuentaAhorroListOrphanCheckCuentaAhorro : cuentaAhorroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the CuentaAhorro " + cuentaAhorroListOrphanCheckCuentaAhorro + " in its cuentaAhorroList field has a non-nullable cuentasId field.");
            }
            List<CuentaCorriente> cuentaCorrienteListOrphanCheck = cuenta.getCuentaCorrienteList();
            for (CuentaCorriente cuentaCorrienteListOrphanCheckCuentaCorriente : cuentaCorrienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the CuentaCorriente " + cuentaCorrienteListOrphanCheckCuentaCorriente + " in its cuentaCorrienteList field has a non-nullable cuentaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Banco bancoId = cuenta.getBancoId();
            if (bancoId != null) {
                bancoId.getCuentaList().remove(cuenta);
                bancoId = em.merge(bancoId);
            }
            Cliente clienteId = cuenta.getClienteId();
            if (clienteId != null) {
                clienteId.getCuentaList().remove(cuenta);
                clienteId = em.merge(clienteId);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
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

    public Cuenta findCuenta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
