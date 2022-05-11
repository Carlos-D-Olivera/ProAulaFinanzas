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
@Table(name = "cuentas", catalog = "bd_finanzas_proaula", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ccc"})})
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByCcc", query = "SELECT c FROM Cuenta c WHERE c.ccc = :ccc"),
    @NamedQuery(name = "Cuenta.findByNombre", query = "SELECT c FROM Cuenta c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cuenta.findBySaldoActual", query = "SELECT c FROM Cuenta c WHERE c.saldoActual = :saldoActual"),
    @NamedQuery(name = "Cuenta.findBySaldoMedio", query = "SELECT c FROM Cuenta c WHERE c.saldoMedio = :saldoMedio")})
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ccc", nullable = false, length = 20)
    protected String ccc;
    @Column(name = "nombre", length = 100)
    protected String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo_actual", precision = 12, scale = 0)
    protected Float saldoActual;
    @Column(name = "saldo_medio", precision = 12, scale = 0)
    protected Float saldoMedio;
    @JoinColumn(name = "banco_id", referencedColumnName = "nit", nullable = false)
    @ManyToOne(optional = false)
    protected Banco bancoId;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    protected Cliente clienteId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentasId")
    protected List<CuentaAhorro> cuentaAhorroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentaId")
    protected List<CuentaCorriente> cuentaCorrienteList;

    public Cuenta() {
    }

    public Cuenta(String ccc) {
        this.ccc = ccc;
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Float saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Float getSaldoMedio() {
        return saldoMedio;
    }

    public void setSaldoMedio(Float saldoMedio) {
        this.saldoMedio = saldoMedio;
    }

    public Banco getBancoId() {
        return bancoId;
    }

    public void setBancoId(Banco bancoId) {
        this.bancoId = bancoId;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public List<CuentaAhorro> getCuentaAhorroList() {
        return cuentaAhorroList;
    }

    public void setCuentaAhorroList(List<CuentaAhorro> cuentaAhorroList) {
        this.cuentaAhorroList = cuentaAhorroList;
    }

    public List<CuentaCorriente> getCuentaCorrienteList() {
        return cuentaCorrienteList;
    }

    public void setCuentaCorrienteList(List<CuentaCorriente> cuentaCorrienteList) {
        this.cuentaCorrienteList = cuentaCorrienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ccc != null ? ccc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.ccc == null && other.ccc != null) || (this.ccc != null && !this.ccc.equals(other.ccc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.unicolombo.ingsistemas.poo.finanzas.entity.Cuenta[ ccc=" + ccc + " ]";
    }
    
}
