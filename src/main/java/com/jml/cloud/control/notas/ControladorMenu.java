package com.jml.cloud.control.notas;

public class ControladorMenu {

    private Menu menu;

    public ControladorMenu(Menu menu) {
        this.menu = menu;
    }

    public void verNotas() {
        new VerNotas().setVisible(true);
    }

    public void modificarNotas() {
        new ModificarNotas().setVisible(true);
    }
}