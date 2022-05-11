/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicolombo.ingsistemas.poo.finanzas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author jimmy
 */
@Entity
@Table(name = "representantes", catalog = "bd_finanzas_proaula", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})})
@NamedQueries({
    @NamedQuery(name = "Representante.findAll", query = "SELECT r FROM Representante r"),
    @NamedQuery(name = "Representante.findByNit", query = "SELECT r FROM Representante r WHERE r.nit = :nit"),
    @NamedQuery(name = "Representante.findByNombre", query = "SELECT r FROM Representante r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Representante.findByTelefono", query = "SELECT r FROM Representante r WHERE r.telefono = :telefono"),
    @NamedQuery(name = "Representante.findByGenero", query = "SELECT r FROM Representante r WHERE r.genero = :genero"),
    @NamedQuery(name = "Representante.findByEmail", query = "SELECT r FROM Representante r WHERE r.email = :email")})
public class Representante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nit", nullable = false, length = 15)
    private String nit;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "telefono", nullable = false, length = 10)
    private String telefono;
    @Basic(optional = false)
    @Column(name = "genero", nullable = false, length = 9)
    private String genero;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 70)
    private String email;
    @JoinColumn(name = "clientes_oganizacion_id", referencedColumnName = "nit", nullable = false)
    @ManyToOne(optional = false)
    private ClienteOrganizacion clientesOganizacionId;

    public Representante() {
    }

    public Representante(String nit) {
        this.nit = nit;
    }

    public Representante(String nit, String nombre, String telefono, String genero, String email) {
        this.nit = nit;
        this.nombre = nombre;
        this.telefono = telefono;
        this.genero = genero;
        this.email = email;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClienteOrganizacion getClientesOganizacionId() {
        return clientesOganizacionId;
    }

    public void setClientesOganizacionId(ClienteOrganizacion clientesOganizacionId) {
        this.clientesOganizacionId = clientesOganizacionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nit != null ? nit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Representante)) {
            return false;
        }
        Representante other = (Representante) object;
        if ((this.nit == null && other.nit != null) || (this.nit != null && !this.nit.equals(other.nit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Representante[ nit=" + nit + " ]";
    }
    
}
