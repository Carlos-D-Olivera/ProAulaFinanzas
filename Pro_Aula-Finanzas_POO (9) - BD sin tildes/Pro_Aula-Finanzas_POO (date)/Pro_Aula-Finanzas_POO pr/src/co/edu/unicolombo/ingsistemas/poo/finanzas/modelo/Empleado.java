
package co.edu.unicolombo.ingsistemas.poo.finanzas.modelo;

import java.util.Date;
class Empleado {
   private String dni;
   private String nombre;
   private Date fechaNacimiento;
   private String sexo;
   private Sucursal sucursal;

    public Empleado() {
        
    }

    public Empleado(String dni, String nombre, Date fechaNacimiento, String sexo, Sucursal sucursal) {
        this.dni = dni;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.sucursal = sucursal;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
   
   
}
