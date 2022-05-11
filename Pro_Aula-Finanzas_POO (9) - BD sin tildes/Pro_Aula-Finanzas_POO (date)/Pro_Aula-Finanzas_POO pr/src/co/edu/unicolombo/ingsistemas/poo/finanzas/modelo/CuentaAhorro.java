
package co.edu.unicolombo.ingsistemas.poo.finanzas.modelo;

public class CuentaAhorro extends Cuenta {
    private String amortizacion;

    public CuentaAhorro() {
        
    }

    public CuentaAhorro(String amortizacion) {
        this.amortizacion = amortizacion;
    }

    public CuentaAhorro(String amortizacion, String id, String nombre, String CCC, int saldoActual, int saldoMedio, Banco banco, Cliente cliente) {
        super(id, nombre, CCC, saldoActual, saldoMedio, banco, cliente);
        this.amortizacion= amortizacion;
    }

    public String getAmortizacion() {
        return amortizacion;
    }

    public void setAmortizacion(String amortizacion) {
        this.amortizacion = amortizacion;
    }
    
    

    
    
    
}

