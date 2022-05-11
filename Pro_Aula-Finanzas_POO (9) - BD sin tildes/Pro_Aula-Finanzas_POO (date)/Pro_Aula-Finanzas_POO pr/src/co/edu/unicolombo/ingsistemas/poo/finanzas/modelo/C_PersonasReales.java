
package co.edu.unicolombo.ingsistemas.poo.finanzas.modelo;

import java.util.Date;


public class C_PersonasReales extends Cliente {
   private String apellido;
   private Date f_nacimiento;
   private String genero;

    public C_PersonasReales() {
        
    }

    public C_PersonasReales(String apellido, Date f_nacimiento, String genero) {
        this.apellido = apellido;
        this.f_nacimiento = f_nacimiento;
        this.genero = genero;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getF_nacimiento() {
        return f_nacimiento;
    }

    public void setF_nacimiento(Date f_nacimiento) {
        this.f_nacimiento = f_nacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
   
   
}

