package co.edu.unicolombo.ingsistemas.poo.finanzas.modelo;

import java.util.List;

class Sucursal {

    private int id;
    private String nombre;
    private String direccion;
    private String codigoPostal;
    private String ciudad;
    private Banco banco;
    private List<Empleado> empleados;

    public Sucursal() {

    }

    public Sucursal(int id, String nombre, String direccion, String codigoPostal, String ciudad, Banco banco) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.banco = banco;
    }

    public Sucursal(int id, String nombre, String direccion, String codigoPostal, String ciudad, Banco banco, List<Empleado> empleados) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.banco = banco;
        this.empleados = empleados;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void set(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Banco getBanco() {
        return this.banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}
