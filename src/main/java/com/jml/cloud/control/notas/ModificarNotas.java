package com.jml.cloud.control.notas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ModificarNotas extends JFrame {

    JTextField txtIdentificacion;
    JTextField txtMateria;
    JTextField txtNota;
    JTextField txtCorte;

    private JButton btnModificar;
    private JButton btnVolverAlMenu;

    private final ConexionBD conexionBD;

    public ModificarNotas(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        initComponents();
    }

    private void initComponents() {
        setTitle("Modificar notas");
        setSize(600, 400);
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
        btnVolverAlMenu = new JButton("Volver");

        JPanel panel = new JPanel();
        Dimension dimension = new Dimension(120, 20);

        panel.add(lblIdentificacion);
        txtIdentificacion.setPreferredSize(dimension);
        panel.add(txtIdentificacion);

        panel.add(lblMateria);
        txtMateria.setPreferredSize(dimension);
        panel.add(txtMateria);

        panel.add(lblNota);
        txtNota.setPreferredSize(dimension);
        panel.add(txtNota);

        panel.add(lblCorte);
        txtCorte.setPreferredSize(dimension);
        panel.add(txtCorte);

        panel.add(btnModificar);
        panel.add(btnVolverAlMenu);

        add(panel);

        btnModificar.addActionListener(e -> {
            // Conectar con la base de datos
            Connection conexion = null;
            PreparedStatement sentencia = null;

            try {
                conexion = conexionBD.getConnection();
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
        });

        btnVolverAlMenu.addActionListener(e -> volverAlMenu()); // Agrega la acción para el botón "Volver"
    }

    private void volverAlMenu() {
        setVisible(false); // Oculta la ventana actual
        Menu menu = Menu.getInstance(conexionBD);
        menu.setVisible(true); // Muestra la instancia única de la ventana del menú
    }
}