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
@Table(name = "cuentacorriente", catalog = "bd_finanzas_proaula", schema = "")
@NamedQueries({
    @NamedQuery(name = "CuentaCorriente.findAll", query = "SELECT c FROM CuentaCorriente c"),
    @NamedQuery(name = "CuentaCorriente.findById", query = "SELECT c FROM CuentaCorriente c WHERE c.id = :id"),
    @NamedQuery(name = "CuentaCorriente.findBySobregiro", query = "SELECT c FROM CuentaCorriente c WHERE c.sobregiro = :sobregiro"),
    @NamedQuery(name = "CuentaCorriente.findByIntereses", query = "SELECT c FROM CuentaCorriente c WHERE c.intereses = :intereses")})
public class CuentaCorriente extends Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "sobregiro", nullable = false)
    private float sobregiro;
    @Basic(optional = false)
    @Column(name = "intereses", nullable = false)
    private float intereses;
    @JoinColumn(name = "cuenta_id", referencedColumnName = "ccc", nullable = false)
    @ManyToOne(optional = false)
    private Cuenta cuentaId;

    public CuentaCorriente() {
    }

    public CuentaCorriente(Integer id) {
        this.id = id;
    }

    public CuentaCorriente(Integer id, float sobregiro, float intereses) {
        this.id = id;
        this.sobregiro = sobregiro;
        this.intereses = intereses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getSobregiro() {
        return sobregiro;
    }

    public void setSobregiro(float sobregiro) {
        this.sobregiro = sobregiro;
    }

    public float getIntereses() {
        return intereses;
    }

    public void setIntereses(float intereses) {
        this.intereses = intereses;
    }

    public Cuenta getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Cuenta cuentaId) {
        this.cuentaId = cuentaId;
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
        if (!(object instanceof CuentaCorriente)) {
            return false;
        }
        CuentaCorriente other = (CuentaCorriente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.unicolombo.ingsistemas.poo.finanzas.entity.CuentaCorriente[ id=" + id + " ]";
    }
    
}
