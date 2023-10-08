package com.jml.cloud.control.notas;

import javax.swing.*;

public class Menu extends JFrame {

    private JButton btnVerNotas;
    private JButton btnModificarNotas;

    private final ConexionBD conexionBD;

    public Menu(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        initComponents();
    }

    private void initComponents() {
        setTitle("Menú");
        setSize(300, 200);
        setLocationRelativeTo(null);

        btnVerNotas = new JButton("Ver notas");
        btnModificarNotas = new JButton("Modificar notas");

        JPanel panel = new JPanel();
        panel.add(btnVerNotas);
        panel.add(btnModificarNotas);

        add(panel);

        btnVerNotas.addActionListener(e -> new VerNotas(conexionBD).setVisible(true));

        btnModificarNotas.addActionListener(e -> new ModificarNotas().setVisible(true));
    }
}