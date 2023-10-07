package com.jml.cloud.control.notas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    private JButton btnVerNotas;
    private JButton btnModificarNotas;

    public Menu() {
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

        btnVerNotas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VerNotas().setVisible(true);
            }
        });

        btnModificarNotas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModificarNotas().setVisible(true);
            }
        });
    }
}