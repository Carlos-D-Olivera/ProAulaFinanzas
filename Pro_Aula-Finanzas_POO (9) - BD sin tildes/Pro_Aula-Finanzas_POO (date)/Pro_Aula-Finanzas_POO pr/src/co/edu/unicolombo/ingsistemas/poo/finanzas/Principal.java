package co.edu.unicolombo.ingsistemas.poo.finanzas;

import co.edu.unicolombo.ingsistemas.poo.finanzas.gui.VentanaPrincipal;

public class Principal {
    public static void main(String[] args) {
        VentanaPrincipal y = new VentanaPrincipal();
        y.setVisible(true);
        y.setTitle("Finanzas");
        y.setExtendedState(VentanaPrincipal.MAXIMIZED_BOTH);
         
    }
}
