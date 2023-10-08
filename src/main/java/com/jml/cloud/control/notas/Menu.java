package com.jml.cloud.control.notas;

import javax.swing.*;

public class Menu extends JFrame {

    private JButton btnVerNotas;
    private JButton btnModificarNotas;

    private static Menu instance;

    private final ConexionBD conexionBD;

    public static Menu getInstance(ConexionBD conexionBD) {
        if (instance == null) {
            instance = new Menu(conexionBD);
        }
        return instance;
    }

    public Menu(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setTitle("Menú");
        setSize(600, 400);
        setLocationRelativeTo(null);

        btnVerNotas = new JButton("Ver notas");
        btnModificarNotas = new JButton("Modificar notas");

        JPanel panel = new JPanel();
        panel.add(btnVerNotas);
        panel.add(btnModificarNotas);

        add(panel);

        btnVerNotas.addActionListener(e -> new VerNotas(conexionBD).setVisible(true));

        btnModificarNotas.addActionListener(e -> new ModificarNotas(conexionBD).setVisible(true));
    }
}