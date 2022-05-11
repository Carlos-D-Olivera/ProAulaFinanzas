/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicolombo.ingsistemas.poo.finanzas.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author jimmy
 */
@Entity
@Table(name = "clientes", catalog = "bd_finanzas_proaula", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"}),
    @UniqueConstraint(columnNames = {"id"})})
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findById", query = "SELECT c FROM Cliente c WHERE c.id = :id"),
    @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email"),
    @NamedQuery(name = "Cliente.findByClave", query = "SELECT c FROM Cliente c WHERE c.clave = :clave")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    protected Integer id;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 100)
    protected String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "direccion", nullable = false, length = 65535)
    protected String direccion;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 75)
    protected String email;
    @Basic(optional = false)
    @Column(name = "clave", nullable = false, length = 45)
    protected String clave;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteId")
    protected List<ClienteOrganizacion> clienteOrganizacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteId")
    protected List<ClientePersona> clientePersonaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteId")
    protected List<Cuenta> cuentaList;
    @JoinColumn(name = "bancos_id", referencedColumnName = "nit", nullable = false)
    @ManyToOne(optional = false)
    protected Banco bancosId;

    public Cliente() {
    }

    public Cliente(Integer id) {
        this.id = id;
    }

    public Cliente(Integer id, String nombre, String direccion, String email, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.clave = clave;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public List<ClienteOrganizacion> getClienteOrganizacionList() {
        return clienteOrganizacionList;
    }

    public void setClienteOrganizacionList(List<ClienteOrganizacion> clienteOrganizacionList) {
        this.clienteOrganizacionList = clienteOrganizacionList;
    }

    public List<ClientePersona> getClientePersonaList() {
        return clientePersonaList;
    }

    public void setClientePersonaList(List<ClientePersona> clientePersonaList) {
        this.clientePersonaList = clientePersonaList;
    }

    public List<Cuenta> getCuentaList() {
        return cuentaList;
    }

    public void setCuentaList(List<Cuenta> cuentaList) {
        this.cuentaList = cuentaList;
    }

    public Banco getBancosId() {
        return bancosId;
    }

    public void setBancosId(Banco bancosId) {
        this.bancosId = bancosId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cliente[ id=" + id + " ]";
    }
    
}
