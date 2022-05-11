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
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cliente;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.ClienteOrganizacion;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Representante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jimmy
 */
public class ClienteOrganizacionJpaController implements Serializable {

    public ClienteOrganizacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteOrganizacion clienteOrganizacion) throws PreexistingEntityException, Exception {
        if (clienteOrganizacion.getRepresentanteList() == null) {
            clienteOrganizacion.setRepresentanteList(new ArrayList<Representante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteId = clienteOrganizacion.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getId());
                clienteOrganizacion.setClienteId(clienteId);
            }
            List<Representante> attachedRepresentanteList = new ArrayList<Representante>();
            for (Representante representanteListRepresentanteToAttach : clienteOrganizacion.getRepresentanteList()) {
                representanteListRepresentanteToAttach = em.getReference(representanteListRepresentanteToAttach.getClass(), representanteListRepresentanteToAttach.getNit());
                attachedRepresentanteList.add(representanteListRepresentanteToAttach);
            }
            clienteOrganizacion.setRepresentanteList(attachedRepresentanteList);
            em.persist(clienteOrganizacion);
            if (clienteId != null) {
                clienteId.getClienteOrganizacionList().add(clienteOrganizacion);
                clienteId = em.merge(clienteId);
            }
            for (Representante representanteListRepresentante : clienteOrganizacion.getRepresentanteList()) {
                ClienteOrganizacion oldClientesOganizacionIdOfRepresentanteListRepresentante = representanteListRepresentante.getClientesOganizacionId();
                representanteListRepresentante.setClientesOganizacionId(clienteOrganizacion);
                representanteListRepresentante = em.merge(representanteListRepresentante);
                if (oldClientesOganizacionIdOfRepresentanteListRepresentante != null) {
                    oldClientesOganizacionIdOfRepresentanteListRepresentante.getRepresentanteList().remove(representanteListRepresentante);
                    oldClientesOganizacionIdOfRepresentanteListRepresentante = em.merge(oldClientesOganizacionIdOfRepresentanteListRepresentante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClienteOrganizacion(clienteOrganizacion.getNit()) != null) {
                throw new PreexistingEntityException("ClienteOrganizacion " + clienteOrganizacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteOrganizacion clienteOrganizacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteOrganizacion persistentClienteOrganizacion = em.find(ClienteOrganizacion.class, clienteOrganizacion.getNit());
            Cliente clienteIdOld = persistentClienteOrganizacion.getClienteId();
            Cliente clienteIdNew = clienteOrganizacion.getClienteId();
            List<Representante> representanteListOld = persistentClienteOrganizacion.getRepresentanteList();
            List<Representante> representanteListNew = clienteOrganizacion.getRepresentanteList();
            List<String> illegalOrphanMessages = null;
            for (Representante representanteListOldRepresentante : representanteListOld) {
                if (!representanteListNew.contains(representanteListOldRepresentante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Representante " + representanteListOldRepresentante + " since its clientesOganizacionId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getId());
                clienteOrganizacion.setClienteId(clienteIdNew);
            }
            List<Representante> attachedRepresentanteListNew = new ArrayList<Representante>();
            for (Representante representanteListNewRepresentanteToAttach : representanteListNew) {
                representanteListNewRepresentanteToAttach = em.getReference(representanteListNewRepresentanteToAttach.getClass(), representanteListNewRepresentanteToAttach.getNit());
                attachedRepresentanteListNew.add(representanteListNewRepresentanteToAttach);
            }
            representanteListNew = attachedRepresentanteListNew;
            clienteOrganizacion.setRepresentanteList(representanteListNew);
            clienteOrganizacion = em.merge(clienteOrganizacion);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getClienteOrganizacionList().remove(clienteOrganizacion);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getClienteOrganizacionList().add(clienteOrganizacion);
                clienteIdNew = em.merge(clienteIdNew);
            }
            for (Representante representanteListNewRepresentante : representanteListNew) {
                if (!representanteListOld.contains(representanteListNewRepresentante)) {
                    ClienteOrganizacion oldClientesOganizacionIdOfRepresentanteListNewRepresentante = representanteListNewRepresentante.getClientesOganizacionId();
                    representanteListNewRepresentante.setClientesOganizacionId(clienteOrganizacion);
                    representanteListNewRepresentante = em.merge(representanteListNewRepresentante);
                    if (oldClientesOganizacionIdOfRepresentanteListNewRepresentante != null && !oldClientesOganizacionIdOfRepresentanteListNewRepresentante.equals(clienteOrganizacion)) {
                        oldClientesOganizacionIdOfRepresentanteListNewRepresentante.getRepresentanteList().remove(representanteListNewRepresentante);
                        oldClientesOganizacionIdOfRepresentanteListNewRepresentante = em.merge(oldClientesOganizacionIdOfRepresentanteListNewRepresentante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = clienteOrganizacion.getNit();
                if (findClienteOrganizacion(id) == null) {
                    throw new NonexistentEntityException("The clienteOrganizacion with id " + id + " no longer exists.");
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
            ClienteOrganizacion clienteOrganizacion;
            try {
                clienteOrganizacion = em.getReference(ClienteOrganizacion.class, id);
                clienteOrganizacion.getNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteOrganizacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Representante> representanteListOrphanCheck = clienteOrganizacion.getRepresentanteList();
            for (Representante representanteListOrphanCheckRepresentante : representanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClienteOrganizacion (" + clienteOrganizacion + ") cannot be destroyed since the Representante " + representanteListOrphanCheckRepresentante + " in its representanteList field has a non-nullable clientesOganizacionId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente clienteId = clienteOrganizacion.getClienteId();
            if (clienteId != null) {
                clienteId.getClienteOrganizacionList().remove(clienteOrganizacion);
                clienteId = em.merge(clienteId);
            }
            em.remove(clienteOrganizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClienteOrganizacion> findClienteOrganizacionEntities() {
        return findClienteOrganizacionEntities(true, -1, -1);
    }

    public List<ClienteOrganizacion> findClienteOrganizacionEntities(int maxResults, int firstResult) {
        return findClienteOrganizacionEntities(false, maxResults, firstResult);
    }

    private List<ClienteOrganizacion> findClienteOrganizacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteOrganizacion.class));
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

    public ClienteOrganizacion findClienteOrganizacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteOrganizacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteOrganizacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteOrganizacion> rt = cq.from(ClienteOrganizacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
