
package co.edu.unicolombo.ingsistemas.poo.finanzas.modelo;

public class CuentaCorriente extends Cuenta{
   private int sobregiro;
   private int intereses;

    public CuentaCorriente() {
    }

    public CuentaCorriente(int sobregiro, int intereses) {
        this.sobregiro = sobregiro;
        this.intereses = intereses;
    }

    public CuentaCorriente(int sobregiro, int intereses, String id, String nombre, String CCC, int saldoActual, int saldoMedio, Banco banco, Cliente cliente) {
        super(id, nombre, CCC, saldoActual, saldoMedio, banco, cliente);
        this.sobregiro = sobregiro;
        this.intereses = intereses;
    }

    public int getSobregiro() {
        return sobregiro;
    }

    public void setSobregiro(int sobregiro) {
        this.sobregiro = sobregiro;
    }

    public int getIntereses() {
        return intereses;
    }

    public void setIntereses(int intereses) {
        this.intereses = intereses;
    }
   
   
}
