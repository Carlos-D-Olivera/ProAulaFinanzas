/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicolombo.ingsistemas.poo.finanzas.crudl;

import co.edu.unicolombo.ingsistemas.poo.finanzas.crudl.exceptions.IllegalOrphanException;
import co.edu.unicolombo.ingsistemas.poo.finanzas.crudl.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Banco;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cliente;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.ClienteOrganizacion;
import java.util.ArrayList;
import java.util.List;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.ClientePersona;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cuenta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jimmy
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getClienteOrganizacionList() == null) {
            cliente.setClienteOrganizacionList(new ArrayList<ClienteOrganizacion>());
        }
        if (cliente.getClientePersonaList() == null) {
            cliente.setClientePersonaList(new ArrayList<ClientePersona>());
        }
        if (cliente.getCuentaList() == null) {
            cliente.setCuentaList(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco bancosId = cliente.getBancosId();
            if (bancosId != null) {
                bancosId = em.getReference(bancosId.getClass(), bancosId.getNit());
                cliente.setBancosId(bancosId);
            }
            List<ClienteOrganizacion> attachedClienteOrganizacionList = new ArrayList<ClienteOrganizacion>();
            for (ClienteOrganizacion clienteOrganizacionListClienteOrganizacionToAttach : cliente.getClienteOrganizacionList()) {
                clienteOrganizacionListClienteOrganizacionToAttach = em.getReference(clienteOrganizacionListClienteOrganizacionToAttach.getClass(), clienteOrganizacionListClienteOrganizacionToAttach.getNit());
                attachedClienteOrganizacionList.add(clienteOrganizacionListClienteOrganizacionToAttach);
            }
            cliente.setClienteOrganizacionList(attachedClienteOrganizacionList);
            List<ClientePersona> attachedClientePersonaList = new ArrayList<ClientePersona>();
            for (ClientePersona clientePersonaListClientePersonaToAttach : cliente.getClientePersonaList()) {
                clientePersonaListClientePersonaToAttach = em.getReference(clientePersonaListClientePersonaToAttach.getClass(), clientePersonaListClientePersonaToAttach.getDni());
                attachedClientePersonaList.add(clientePersonaListClientePersonaToAttach);
            }
            cliente.setClientePersonaList(attachedClientePersonaList);
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : cliente.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getCcc());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            cliente.setCuentaList(attachedCuentaList);
            em.persist(cliente);
            if (bancosId != null) {
                bancosId.getClienteList().add(cliente);
                bancosId = em.merge(bancosId);
            }
            for (ClienteOrganizacion clienteOrganizacionListClienteOrganizacion : cliente.getClienteOrganizacionList()) {
                Cliente oldClienteIdOfClienteOrganizacionListClienteOrganizacion = clienteOrganizacionListClienteOrganizacion.getClienteId();
                clienteOrganizacionListClienteOrganizacion.setClienteId(cliente);
                clienteOrganizacionListClienteOrganizacion = em.merge(clienteOrganizacionListClienteOrganizacion);
                if (oldClienteIdOfClienteOrganizacionListClienteOrganizacion != null) {
                    oldClienteIdOfClienteOrganizacionListClienteOrganizacion.getClienteOrganizacionList().remove(clienteOrganizacionListClienteOrganizacion);
                    oldClienteIdOfClienteOrganizacionListClienteOrganizacion = em.merge(oldClienteIdOfClienteOrganizacionListClienteOrganizacion);
                }
            }
            for (ClientePersona clientePersonaListClientePersona : cliente.getClientePersonaList()) {
                Cliente oldClienteIdOfClientePersonaListClientePersona = clientePersonaListClientePersona.getClienteId();
                clientePersonaListClientePersona.setClienteId(cliente);
                clientePersonaListClientePersona = em.merge(clientePersonaListClientePersona);
                if (oldClienteIdOfClientePersonaListClientePersona != null) {
                    oldClienteIdOfClientePersonaListClientePersona.getClientePersonaList().remove(clientePersonaListClientePersona);
                    oldClienteIdOfClientePersonaListClientePersona = em.merge(oldClienteIdOfClientePersonaListClientePersona);
                }
            }
            for (Cuenta cuentaListCuenta : cliente.getCuentaList()) {
                Cliente oldClienteIdOfCuentaListCuenta = cuentaListCuenta.getClienteId();
                cuentaListCuenta.setClienteId(cliente);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldClienteIdOfCuentaListCuenta != null) {
                    oldClienteIdOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldClienteIdOfCuentaListCuenta = em.merge(oldClienteIdOfCuentaListCuenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Banco bancosIdOld = persistentCliente.getBancosId();
            Banco bancosIdNew = cliente.getBancosId();
            List<ClienteOrganizacion> clienteOrganizacionListOld = persistentCliente.getClienteOrganizacionList();
            List<ClienteOrganizacion> clienteOrganizacionListNew = cliente.getClienteOrganizacionList();
            List<ClientePersona> clientePersonaListOld = persistentCliente.getClientePersonaList();
            List<ClientePersona> clientePersonaListNew = cliente.getClientePersonaList();
            List<Cuenta> cuentaListOld = persistentCliente.getCuentaList();
            List<Cuenta> cuentaListNew = cliente.getCuentaList();
            List<String> illegalOrphanMessages = null;
            for (ClienteOrganizacion clienteOrganizacionListOldClienteOrganizacion : clienteOrganizacionListOld) {
                if (!clienteOrganizacionListNew.contains(clienteOrganizacionListOldClienteOrganizacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClienteOrganizacion " + clienteOrganizacionListOldClienteOrganizacion + " since its clienteId field is not nullable.");
                }
            }
            for (ClientePersona clientePersonaListOldClientePersona : clientePersonaListOld) {
                if (!clientePersonaListNew.contains(clientePersonaListOldClientePersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientePersona " + clientePersonaListOldClientePersona + " since its clienteId field is not nullable.");
                }
            }
            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaListOldCuenta + " since its clienteId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (bancosIdNew != null) {
                bancosIdNew = em.getReference(bancosIdNew.getClass(), bancosIdNew.getNit());
                cliente.setBancosId(bancosIdNew);
            }
            List<ClienteOrganizacion> attachedClienteOrganizacionListNew = new ArrayList<ClienteOrganizacion>();
            for (ClienteOrganizacion clienteOrganizacionListNewClienteOrganizacionToAttach : clienteOrganizacionListNew) {
                clienteOrganizacionListNewClienteOrganizacionToAttach = em.getReference(clienteOrganizacionListNewClienteOrganizacionToAttach.getClass(), clienteOrganizacionListNewClienteOrganizacionToAttach.getNit());
                attachedClienteOrganizacionListNew.add(clienteOrganizacionListNewClienteOrganizacionToAttach);
            }
            clienteOrganizacionListNew = attachedClienteOrganizacionListNew;
            cliente.setClienteOrganizacionList(clienteOrganizacionListNew);
            List<ClientePersona> attachedClientePersonaListNew = new ArrayList<ClientePersona>();
            for (ClientePersona clientePersonaListNewClientePersonaToAttach : clientePersonaListNew) {
                clientePersonaListNewClientePersonaToAttach = em.getReference(clientePersonaListNewClientePersonaToAttach.getClass(), clientePersonaListNewClientePersonaToAttach.getDni());
                attachedClientePersonaListNew.add(clientePersonaListNewClientePersonaToAttach);
            }
            clientePersonaListNew = attachedClientePersonaListNew;
            cliente.setClientePersonaList(clientePersonaListNew);
            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getCcc());
                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
            }
            cuentaListNew = attachedCuentaListNew;
            cliente.setCuentaList(cuentaListNew);
            cliente = em.merge(cliente);
            if (bancosIdOld != null && !bancosIdOld.equals(bancosIdNew)) {
                bancosIdOld.getClienteList().remove(cliente);
                bancosIdOld = em.merge(bancosIdOld);
            }
            if (bancosIdNew != null && !bancosIdNew.equals(bancosIdOld)) {
                bancosIdNew.getClienteList().add(cliente);
                bancosIdNew = em.merge(bancosIdNew);
            }
            for (ClienteOrganizacion clienteOrganizacionListNewClienteOrganizacion : clienteOrganizacionListNew) {
                if (!clienteOrganizacionListOld.contains(clienteOrganizacionListNewClienteOrganizacion)) {
                    Cliente oldClienteIdOfClienteOrganizacionListNewClienteOrganizacion = clienteOrganizacionListNewClienteOrganizacion.getClienteId();
                    clienteOrganizacionListNewClienteOrganizacion.setClienteId(cliente);
                    clienteOrganizacionListNewClienteOrganizacion = em.merge(clienteOrganizacionListNewClienteOrganizacion);
                    if (oldClienteIdOfClienteOrganizacionListNewClienteOrganizacion != null && !oldClienteIdOfClienteOrganizacionListNewClienteOrganizacion.equals(cliente)) {
                        oldClienteIdOfClienteOrganizacionListNewClienteOrganizacion.getClienteOrganizacionList().remove(clienteOrganizacionListNewClienteOrganizacion);
                        oldClienteIdOfClienteOrganizacionListNewClienteOrganizacion = em.merge(oldClienteIdOfClienteOrganizacionListNewClienteOrganizacion);
                    }
                }
            }
            for (ClientePersona clientePersonaListNewClientePersona : clientePersonaListNew) {
                if (!clientePersonaListOld.contains(clientePersonaListNewClientePersona)) {
                    Cliente oldClienteIdOfClientePersonaListNewClientePersona = clientePersonaListNewClientePersona.getClienteId();
                    clientePersonaListNewClientePersona.setClienteId(cliente);
                    clientePersonaListNewClientePersona = em.merge(clientePersonaListNewClientePersona);
                    if (oldClienteIdOfClientePersonaListNewClientePersona != null && !oldClienteIdOfClientePersonaListNewClientePersona.equals(cliente)) {
                        oldClienteIdOfClientePersonaListNewClientePersona.getClientePersonaList().remove(clientePersonaListNewClientePersona);
                        oldClienteIdOfClientePersonaListNewClientePersona = em.merge(oldClienteIdOfClientePersonaListNewClientePersona);
                    }
                }
            }
            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
                    Cliente oldClienteIdOfCuentaListNewCuenta = cuentaListNewCuenta.getClienteId();
                    cuentaListNewCuenta.setClienteId(cliente);
                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
                    if (oldClienteIdOfCuentaListNewCuenta != null && !oldClienteIdOfCuentaListNewCuenta.equals(cliente)) {
                        oldClienteIdOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
                        oldClienteIdOfCuentaListNewCuenta = em.merge(oldClienteIdOfCuentaListNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ClienteOrganizacion> clienteOrganizacionListOrphanCheck = cliente.getClienteOrganizacionList();
            for (ClienteOrganizacion clienteOrganizacionListOrphanCheckClienteOrganizacion : clienteOrganizacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the ClienteOrganizacion " + clienteOrganizacionListOrphanCheckClienteOrganizacion + " in its clienteOrganizacionList field has a non-nullable clienteId field.");
            }
            List<ClientePersona> clientePersonaListOrphanCheck = cliente.getClientePersonaList();
            for (ClientePersona clientePersonaListOrphanCheckClientePersona : clientePersonaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the ClientePersona " + clientePersonaListOrphanCheckClientePersona + " in its clientePersonaList field has a non-nullable clienteId field.");
            }
            List<Cuenta> cuentaListOrphanCheck = cliente.getCuentaList();
            for (Cuenta cuentaListOrphanCheckCuenta : cuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Cuenta " + cuentaListOrphanCheckCuenta + " in its cuentaList field has a non-nullable clienteId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Banco bancosId = cliente.getBancosId();
            if (bancosId != null) {
                bancosId.getClienteList().remove(cliente);
                bancosId = em.merge(bancosId);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
