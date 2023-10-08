package com.jml.cloud.control.notas;

public class ControladorMenu {

    private final Menu menu;
    private final ConexionBD conexionBD;

    public ControladorMenu(Menu menu, ConexionBD conexionBD) {
        this.menu = menu;
        this.conexionBD = conexionBD;
    }

    public void verNotas() {
        new VerNotas(conexionBD).setVisible(true);
    }

    public void modificarNotas() {
        new ModificarNotas().setVisible(true);
    }
}