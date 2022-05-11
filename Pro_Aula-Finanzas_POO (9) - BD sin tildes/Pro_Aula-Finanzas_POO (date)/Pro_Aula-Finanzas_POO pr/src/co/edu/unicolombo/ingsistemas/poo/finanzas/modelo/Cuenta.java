package co.edu.unicolombo.ingsistemas.poo.finanzas.modelo;

class Cuenta {

    protected String id;
    protected String nombre;
    protected String CCC;
    protected int saldoActual;
    protected int saldoMedio;
    protected Banco banco;
    protected Cliente cliente;

    public Cuenta() {

    }

    public Cuenta(String id, String nombre, String CCC, int saldoActual, int saldoMedio, Banco banco, Cliente cliente) {
        this.id = id;
        this.nombre = nombre;
        this.CCC = CCC;
        this.saldoActual = saldoActual;
        this.saldoMedio = saldoMedio;
        this.banco = banco;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCCC() {
        return CCC;
    }
    
    public String CalcularCCC() {
        return this.CCC;
    }

    public int getsaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(int saldoActual) {
        this.saldoActual = saldoActual;
    }

    public int getSaldoMedio() {
        return saldoMedio;
    }

    public void setSaldoMedio(int saldoMedio) {
        this.saldoMedio = saldoMedio;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
