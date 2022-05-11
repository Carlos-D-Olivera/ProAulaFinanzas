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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jimmy
 */
@Entity
@Table(name = "clientesoganizacion", catalog = "bd_finanzas_proaula", schema = "")
@NamedQueries({
    @NamedQuery(name = "ClienteOrganizacion.findAll", query = "SELECT c FROM ClienteOrganizacion c"),
    @NamedQuery(name = "ClienteOrganizacion.findByNit", query = "SELECT c FROM ClienteOrganizacion c WHERE c.nit = :nit"),
    @NamedQuery(name = "ClienteOrganizacion.findByNoEmpleados", query = "SELECT c FROM ClienteOrganizacion c WHERE c.noEmpleados = :noEmpleados")})
public class ClienteOrganizacion extends Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nit", nullable = false, length = 20)
    private String nit;
    @Basic(optional = false)
    @Column(name = "no_empleados", nullable = false)
    private int noEmpleados;
    @Basic(optional = false)
    @Lob
    @Column(name = "tipo_organizacion", nullable = false, length = 65535)
    private String tipoOrganizacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientesOganizacionId")
    private List<Representante> representanteList;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Cliente clienteId;

    public ClienteOrganizacion() {
    }

    public ClienteOrganizacion(String nit) {
        this.nit = nit;
    }

    public ClienteOrganizacion(String nit, int noEmpleados, String tipoOrganizacion) {
        this.nit = nit;
        this.noEmpleados = noEmpleados;
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public int getNoEmpleados() {
        return noEmpleados;
    }

    public void setNoEmpleados(int noEmpleados) {
        this.noEmpleados = noEmpleados;
    }

    public String getTipoOrganizacion() {
        return tipoOrganizacion;
    }

    public void setTipoOrganizacion(String tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public List<Representante> getRepresentanteList() {
        return representanteList;
    }

    public void setRepresentanteList(List<Representante> representanteList) {
        this.representanteList = representanteList;
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
        hash += (nit != null ? nit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteOrganizacion)) {
            return false;
        }
        ClienteOrganizacion other = (ClienteOrganizacion) object;
        if ((this.nit == null && other.nit != null) || (this.nit != null && !this.nit.equals(other.nit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.unicolombo.ingsistemas.poo.finanzas.entity.ClienteOrganizacion[ nit=" + nit + " ]";
    }
    
}
