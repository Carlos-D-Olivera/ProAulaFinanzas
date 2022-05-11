
package co.edu.unicolombo.ingsistemas.poo.finanzas.modelo;

import java.util.List;


class Cliente {
   private String nombre;
   private String direccion;
   private String codigo;
   private String email; 
   private Banco banco;
   private List<Cuenta> cuentas;

    public Cliente() {
        
    }

    public Cliente(String nombre, String direccion, String codigo, String email, Banco banco, List<Cuenta> cuentas) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.codigo = codigo;
        this.email = email;
        this.banco = banco;
        this.cuentas = cuentas;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
   
   
}
