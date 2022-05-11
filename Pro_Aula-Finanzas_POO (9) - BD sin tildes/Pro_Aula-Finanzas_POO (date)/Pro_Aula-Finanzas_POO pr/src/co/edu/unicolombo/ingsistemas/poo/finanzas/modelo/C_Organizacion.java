
package co.edu.unicolombo.ingsistemas.poo.finanzas.modelo;

import java.util.List;


public class C_Organizacion extends Cliente{
   private String tipoOrganizacion;
   private int NoEmpleados;
   private Representante reprensente;

    public C_Organizacion() {
        
    }

    public C_Organizacion(String tipoOrganizacion, int NoEmpleados, Representante reprensente) {
        this.tipoOrganizacion = tipoOrganizacion;
        this.NoEmpleados = NoEmpleados;
        this.reprensente = reprensente;
    }

    public String getTipoOrganizacion() {
        return tipoOrganizacion;
    }

    public void setTipoOrganizacion(String tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public int getNoEmpleados() {
        return NoEmpleados;
    }

    public void setNoEmpleados(int NoEmpleados) {
        this.NoEmpleados = NoEmpleados;
    }

    public Representante getReprensente() {
        return reprensente;
    }

    public void setReprensente(Representante reprensente) {
        this.reprensente = reprensente;
    }
   
   
}
