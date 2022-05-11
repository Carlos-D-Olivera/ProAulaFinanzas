package co.edu.unicolombo.ingsistemas.poo.finanzas.modelo;

import java.util.List;

public class Banco {

    private String email;
    private String telefono;
    private String direccion;

    private List<Cliente> clientes;
    private List<Sucursal> sucursales;
    private List<Cuenta> cuentas;
    private List<Empleado> empleados;

    public Banco() {

    }

    public Banco(String email, String telefono, String direccion) {
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Banco(String email, String telefono, String direccion, List<Cliente> clientes, List<Sucursal> sucursales, List<Cuenta> cuentas, List<Empleado> empleados) {
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.clientes = clientes;
        this.sucursales = sucursales;
        this.cuentas = cuentas;
        this.empleados = empleados;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Cliente> getClientes() {
        return this.clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Sucursal> getSucursales() {
        return this.sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public List<Cuenta> getCuentas() {
        return this.cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public List<Empleado> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

}
