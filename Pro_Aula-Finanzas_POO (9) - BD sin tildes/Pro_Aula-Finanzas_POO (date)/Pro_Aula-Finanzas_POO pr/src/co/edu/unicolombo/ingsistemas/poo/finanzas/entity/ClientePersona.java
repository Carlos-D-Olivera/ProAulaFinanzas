/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicolombo.ingsistemas.poo.finanzas.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jimmy
 */
@Entity
@Table(name = "clientespersona", catalog = "bd_finanzas_proaula", schema = "")
@NamedQueries({
    @NamedQuery(name = "ClientePersona.findAll", query = "SELECT c FROM ClientePersona c"),
    @NamedQuery(name = "ClientePersona.findByDni", query = "SELECT c FROM ClientePersona c WHERE c.dni = :dni"),
    @NamedQuery(name = "ClientePersona.findByApellido", query = "SELECT c FROM ClientePersona c WHERE c.apellido = :apellido"),
    @NamedQuery(name = "ClientePersona.findByFNacimiento", query = "SELECT c FROM ClientePersona c WHERE c.fNacimiento = :fNacimiento"),
    @NamedQuery(name = "ClientePersona.findByGenero", query = "SELECT c FROM ClientePersona c WHERE c.genero = :genero")})
public class ClientePersona extends Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dni", nullable = false, length = 15)
    private String dni;
    @Basic(optional = false)
    @Column(name = "apellido", nullable = false, length = 45)
    private String apellido;
    @Basic(optional = false)
    @Column(name = "f_nacimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fNacimiento;
    @Basic(optional = false)
    @Column(name = "genero", nullable = false, length = 9)
    private String genero;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Cliente clienteId;

    public ClientePersona() {
    }

    public ClientePersona(String dni) {
        this.dni = dni;
    }

    public ClientePersona(String dni, String apellido, Date fNacimiento, String genero) {
        this.dni = dni;
        this.apellido = apellido;
        this.fNacimiento = fNacimiento;
        this.genero = genero;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFNacimiento() {
        return fNacimiento;
    }

    public void setFNacimiento(Date fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dni != null ? dni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientePersona)) {
            return false;
        }
        ClientePersona other = (ClientePersona) object;
        if ((this.dni == null && other.dni != null) || (this.dni != null && !this.dni.equals(other.dni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.unicolombo.ingsistemas.poo.finanzas.entity.ClientePersona[ dni=" + dni + " ]";
    }
    
}
