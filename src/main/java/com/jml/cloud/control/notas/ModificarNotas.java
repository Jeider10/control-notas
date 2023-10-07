package com.jml.cloud.control.notas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ModificarNotas extends JFrame {

    JTextField txtIdentificacion;
    JTextField txtMateria;
    JTextField txtNota;
    JTextField txtCorte;
    private JButton btnModificar;

    public ModificarNotas() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Modificar notas");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JLabel lblIdentificacion = new JLabel("Identificación:");
        JLabel lblMateria = new JLabel("Materia:");
        JLabel lblNota = new JLabel("Nota:");
        JLabel lblCorte = new JLabel("Corte:");

        txtIdentificacion = new JTextField();
        txtMateria = new JTextField();
        txtNota = new JTextField();
        txtCorte = new JTextField();

        btnModificar = new JButton("Modificar");

        JPanel panel = new JPanel();
        panel.add(lblIdentificacion);
        panel.add(txtIdentificacion);
        panel.add(lblMateria);
        panel.add(txtMateria);
        panel.add(lblNota);
        panel.add(txtNota);
        panel.add(lblCorte);
        panel.add(txtCorte);
        panel.add(btnModificar);

        add(panel);

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Conectar con la base de datos
                Connection conexion = null;
                PreparedStatement sentencia = null;

                try {
                    conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/notas", "root", "");
                    sentencia = conexion.prepareStatement("UPDATE notas SET nota = ? WHERE identificacion = ? AND materia = ? AND corte = ?");

                    sentencia.setDouble(1, Double.parseDouble(txtNota.getText()));
                    sentencia.setString(2, txtIdentificacion.getText());
                    sentencia.setString(3, txtMateria.getText());
                    sentencia.setInt(4, Integer.parseInt(txtCorte.getText()));

                    sentencia.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Nota modificada correctamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al modificar la nota.");
                } finally {
                    try {
                        if (sentencia != null) {
                            sentencia.close();
                        }

                        if (conexion != null) {
                            conexion.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}