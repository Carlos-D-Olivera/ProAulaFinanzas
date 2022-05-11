/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicolombo.ingsistemas.poo.finanzas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jimmy
 */
@Entity
@Table(name = "cuentasahorro", catalog = "bd_finanzas_proaula", schema = "")
@NamedQueries({
    @NamedQuery(name = "CuentaAhorro.findAll", query = "SELECT c FROM CuentaAhorro c"),
    @NamedQuery(name = "CuentaAhorro.findById", query = "SELECT c FROM CuentaAhorro c WHERE c.id = :id"),
    @NamedQuery(name = "CuentaAhorro.findByAmortizacion", query = "SELECT c FROM CuentaAhorro c WHERE c.amortizacion = :amortizacion")})
public class CuentaAhorro extends Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amortizacion", nullable = false)
    private int amortizacion;
    @JoinColumn(name = "cuentas_id", referencedColumnName = "ccc", nullable = false)
    @ManyToOne(optional = false)
    private Cuenta cuentasId;

    public CuentaAhorro() {
    }

    public CuentaAhorro(Integer id) {
        this.id = id;
    }

    public CuentaAhorro(Integer id, int amortizacion) {
        this.id = id;
        this.amortizacion = amortizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmortizacion() {
        return amortizacion;
    }

    public void setAmortizacion(int amortizacion) {
        this.amortizacion = amortizacion;
    }

    public Cuenta getCuentasId() {
        return cuentasId;
    }

    public void setCuentasId(Cuenta cuentasId) {
        this.cuentasId = cuentasId;
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
        if (!(object instanceof CuentaAhorro)) {
            return false;
        }
        CuentaAhorro other = (CuentaAhorro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.unicolombo.ingsistemas.poo.finanzas.entity.CuentaAhorro[ id=" + id + " ]";
    }
    
}
