/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicolombo.ingsistemas.poo.finanzas.crudl;

import co.edu.unicolombo.ingsistemas.poo.finanzas.crudl.exceptions.IllegalOrphanException;
import co.edu.unicolombo.ingsistemas.poo.finanzas.crudl.exceptions.NonexistentEntityException;
import co.edu.unicolombo.ingsistemas.poo.finanzas.crudl.exceptions.PreexistingEntityException;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Banco;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Sucursal;
import java.util.ArrayList;
import java.util.List;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cuenta;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Empleado;
import co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jimmy
 */
public class BancoJpaController implements Serializable {

    public BancoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Banco banco) throws PreexistingEntityException, Exception {
        if (banco.getSucursalList() == null) {
            banco.setSucursalList(new ArrayList<Sucursal>());
        }
        if (banco.getCuentaList() == null) {
            banco.setCuentaList(new ArrayList<Cuenta>());
        }
        if (banco.getEmpleadoList() == null) {
            banco.setEmpleadoList(new ArrayList<Empleado>());
        }
        if (banco.getClienteList() == null) {
            banco.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sucursal> attachedSucursalList = new ArrayList<Sucursal>();
            for (Sucursal sucursalListSucursalToAttach : banco.getSucursalList()) {
                sucursalListSucursalToAttach = em.getReference(sucursalListSucursalToAttach.getClass(), sucursalListSucursalToAttach.getId());
                attachedSucursalList.add(sucursalListSucursalToAttach);
            }
            banco.setSucursalList(attachedSucursalList);
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : banco.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getCcc());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            banco.setCuentaList(attachedCuentaList);
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : banco.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getDni());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            banco.setEmpleadoList(attachedEmpleadoList);
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : banco.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getId());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            banco.setClienteList(attachedClienteList);
            em.persist(banco);
            for (Sucursal sucursalListSucursal : banco.getSucursalList()) {
                Banco oldBancoIdOfSucursalListSucursal = sucursalListSucursal.getBancoId();
                sucursalListSucursal.setBancoId(banco);
                sucursalListSucursal = em.merge(sucursalListSucursal);
                if (oldBancoIdOfSucursalListSucursal != null) {
                    oldBancoIdOfSucursalListSucursal.getSucursalList().remove(sucursalListSucursal);
                    oldBancoIdOfSucursalListSucursal = em.merge(oldBancoIdOfSucursalListSucursal);
                }
            }
            for (Cuenta cuentaListCuenta : banco.getCuentaList()) {
                Banco oldBancoIdOfCuentaListCuenta = cuentaListCuenta.getBancoId();
                cuentaListCuenta.setBancoId(banco);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldBancoIdOfCuentaListCuenta != null) {
                    oldBancoIdOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldBancoIdOfCuentaListCuenta = em.merge(oldBancoIdOfCuentaListCuenta);
                }
            }
            for (Empleado empleadoListEmpleado : banco.getEmpleadoList()) {
                Banco oldBancoIdOfEmpleadoListEmpleado = empleadoListEmpleado.getBancoId();
                empleadoListEmpleado.setBancoId(banco);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldBancoIdOfEmpleadoListEmpleado != null) {
                    oldBancoIdOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldBancoIdOfEmpleadoListEmpleado = em.merge(oldBancoIdOfEmpleadoListEmpleado);
                }
            }
            for (Cliente clienteListCliente : banco.getClienteList()) {
                Banco oldBancosIdOfClienteListCliente = clienteListCliente.getBancosId();
                clienteListCliente.setBancosId(banco);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldBancosIdOfClienteListCliente != null) {
                    oldBancosIdOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldBancosIdOfClienteListCliente = em.merge(oldBancosIdOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBanco(banco.getNit()) != null) {
                throw new PreexistingEntityException("Banco " + banco + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Banco banco) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco persistentBanco = em.find(Banco.class, banco.getNit());
            List<Sucursal> sucursalListOld = persistentBanco.getSucursalList();
            List<Sucursal> sucursalListNew = banco.getSucursalList();
            List<Cuenta> cuentaListOld = persistentBanco.getCuentaList();
            List<Cuenta> cuentaListNew = banco.getCuentaList();
            List<Empleado> empleadoListOld = persistentBanco.getEmpleadoList();
            List<Empleado> empleadoListNew = banco.getEmpleadoList();
            List<Cliente> clienteListOld = persistentBanco.getClienteList();
            List<Cliente> clienteListNew = banco.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Sucursal sucursalListOldSucursal : sucursalListOld) {
                if (!sucursalListNew.contains(sucursalListOldSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sucursal " + sucursalListOldSucursal + " since its bancoId field is not nullable.");
                }
            }
            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaListOldCuenta + " since its bancoId field is not nullable.");
                }
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its bancoId field is not nullable.");
                }
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its bancosId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Sucursal> attachedSucursalListNew = new ArrayList<Sucursal>();
            for (Sucursal sucursalListNewSucursalToAttach : sucursalListNew) {
                sucursalListNewSucursalToAttach = em.getReference(sucursalListNewSucursalToAttach.getClass(), sucursalListNewSucursalToAttach.getId());
                attachedSucursalListNew.add(sucursalListNewSucursalToAttach);
            }
            sucursalListNew = attachedSucursalListNew;
            banco.setSucursalList(sucursalListNew);
            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getCcc());
                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
            }
            cuentaListNew = attachedCuentaListNew;
            banco.setCuentaList(cuentaListNew);
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getDni());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            banco.setEmpleadoList(empleadoListNew);
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getId());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            banco.setClienteList(clienteListNew);
            banco = em.merge(banco);
            for (Sucursal sucursalListNewSucursal : sucursalListNew) {
                if (!sucursalListOld.contains(sucursalListNewSucursal)) {
                    Banco oldBancoIdOfSucursalListNewSucursal = sucursalListNewSucursal.getBancoId();
                    sucursalListNewSucursal.setBancoId(banco);
                    sucursalListNewSucursal = em.merge(sucursalListNewSucursal);
                    if (oldBancoIdOfSucursalListNewSucursal != null && !oldBancoIdOfSucursalListNewSucursal.equals(banco)) {
                        oldBancoIdOfSucursalListNewSucursal.getSucursalList().remove(sucursalListNewSucursal);
                        oldBancoIdOfSucursalListNewSucursal = em.merge(oldBancoIdOfSucursalListNewSucursal);
                    }
                }
            }
            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
                    Banco oldBancoIdOfCuentaListNewCuenta = cuentaListNewCuenta.getBancoId();
                    cuentaListNewCuenta.setBancoId(banco);
                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
                    if (oldBancoIdOfCuentaListNewCuenta != null && !oldBancoIdOfCuentaListNewCuenta.equals(banco)) {
                        oldBancoIdOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
                        oldBancoIdOfCuentaListNewCuenta = em.merge(oldBancoIdOfCuentaListNewCuenta);
                    }
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Banco oldBancoIdOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getBancoId();
                    empleadoListNewEmpleado.setBancoId(banco);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldBancoIdOfEmpleadoListNewEmpleado != null && !oldBancoIdOfEmpleadoListNewEmpleado.equals(banco)) {
                        oldBancoIdOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldBancoIdOfEmpleadoListNewEmpleado = em.merge(oldBancoIdOfEmpleadoListNewEmpleado);
                    }
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Banco oldBancosIdOfClienteListNewCliente = clienteListNewCliente.getBancosId();
                    clienteListNewCliente.setBancosId(banco);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldBancosIdOfClienteListNewCliente != null && !oldBancosIdOfClienteListNewCliente.equals(banco)) {
                        oldBancosIdOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldBancosIdOfClienteListNewCliente = em.merge(oldBancosIdOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = banco.getNit();
                if (findBanco(id) == null) {
                    throw new NonexistentEntityException("The banco with id " + id + " no longer exists.");
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
            Banco banco;
            try {
                banco = em.getReference(Banco.class, id);
                banco.getNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The banco with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sucursal> sucursalListOrphanCheck = banco.getSucursalList();
            for (Sucursal sucursalListOrphanCheckSucursal : sucursalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Banco (" + banco + ") cannot be destroyed since the Sucursal " + sucursalListOrphanCheckSucursal + " in its sucursalList field has a non-nullable bancoId field.");
            }
            List<Cuenta> cuentaListOrphanCheck = banco.getCuentaList();
            for (Cuenta cuentaListOrphanCheckCuenta : cuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Banco (" + banco + ") cannot be destroyed since the Cuenta " + cuentaListOrphanCheckCuenta + " in its cuentaList field has a non-nullable bancoId field.");
            }
            List<Empleado> empleadoListOrphanCheck = banco.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Banco (" + banco + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable bancoId field.");
            }
            List<Cliente> clienteListOrphanCheck = banco.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Banco (" + banco + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable bancosId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(banco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Banco> findBancoEntities() {
        return findBancoEntities(true, -1, -1);
    }

    public List<Banco> findBancoEntities(int maxResults, int firstResult) {
        return findBancoEntities(false, maxResults, firstResult);
    }

    private List<Banco> findBancoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Banco.class));
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

    public Banco findBanco(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Banco.class, id);
        } finally {
            em.close();
        }
    }

    public int getBancoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Banco> rt = cq.from(Banco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
