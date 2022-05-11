
package co.edu.unicolombo.ingsistemas.poo.finanzas.modelo;

import java.util.List;

class Representante {
   private int id;
   private String nombre;
   private String telefono;
   private String genero;
   private String email;
   private List<C_Organizacion> organizaciones;

    public Representante() {
        
    }

    public Representante(int id, String nombre, String telefono, String genero, String email, List<C_Organizacion> organizaciones) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.genero = genero;
        this.email = email;
        this.organizaciones = organizaciones;
    }

    public int getId() {
        return id;
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

    public List<C_Organizacion> getOrganizaciones() {
        return organizaciones;
    }

    public void setOrganizaciones(List<C_Organizacion> organizaciones) {
        this.organizaciones = organizaciones;
    }
   
   
}
