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
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cliente;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.ClientePersona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jimmy
 */
public class ClientePersonaJpaController implements Serializable {

    public ClientePersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientePersona clientePersona) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteId = clientePersona.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getId());
                clientePersona.setClienteId(clienteId);
            }
            em.persist(clientePersona);
            if (clienteId != null) {
                clienteId.getClientePersonaList().add(clientePersona);
                clienteId = em.merge(clienteId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientePersona(clientePersona.getDni()) != null) {
                throw new PreexistingEntityException("ClientePersona " + clientePersona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientePersona clientePersona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientePersona persistentClientePersona = em.find(ClientePersona.class, clientePersona.getDni());
            Cliente clienteIdOld = persistentClientePersona.getClienteId();
            Cliente clienteIdNew = clientePersona.getClienteId();
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getId());
                clientePersona.setClienteId(clienteIdNew);
            }
            clientePersona = em.merge(clientePersona);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getClientePersonaList().remove(clientePersona);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getClientePersonaList().add(clientePersona);
                clienteIdNew = em.merge(clienteIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = clientePersona.getDni();
                if (findClientePersona(id) == null) {
                    throw new NonexistentEntityException("The clientePersona with id " + id + " no longer exists.");
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
            ClientePersona clientePersona;
            try {
                clientePersona = em.getReference(ClientePersona.class, id);
                clientePersona.getDni();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientePersona with id " + id + " no longer exists.", enfe);
            }
            Cliente clienteId = clientePersona.getClienteId();
            if (clienteId != null) {
                clienteId.getClientePersonaList().remove(clientePersona);
                clienteId = em.merge(clienteId);
            }
            em.remove(clientePersona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClientePersona> findClientePersonaEntities() {
        return findClientePersonaEntities(true, -1, -1);
    }

    public List<ClientePersona> findClientePersonaEntities(int maxResults, int firstResult) {
        return findClientePersonaEntities(false, maxResults, firstResult);
    }

    private List<ClientePersona> findClientePersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientePersona.class));
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

    public ClientePersona findClientePersona(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientePersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientePersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientePersona> rt = cq.from(ClientePersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
