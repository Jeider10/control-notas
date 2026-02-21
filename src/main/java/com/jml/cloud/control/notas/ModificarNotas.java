package com.jml.cloud.control.notas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                int identificacion = Integer.parseInt(txtIdentificacion.getText());
                String materia = txtMateria.getText();
                int corte = Integer.parseInt(txtCorte.getText());

                // Verificar si los campos están vacíos
                if (identificacion != 0 || materia.isEmpty() || txtNota.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                } else {
                    // Verificar si la entrada existe en la base de datos antes de actualizar
                    if (existeEntrada(conexion, identificacion, materia, corte)) {
                        sentencia = conexion.prepareStatement("UPDATE notas SET nota = ? WHERE identificacion = ? AND materia = ? AND corte = ?");
                        sentencia.setDouble(1, Double.parseDouble(txtNota.getText()));
                        sentencia.setInt(2, identificacion);
                        sentencia.setString(3, materia);
                        sentencia.setInt(4, corte);

                        sentencia.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Nota modificada correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "La entrada no existe en la base de datos o algún dato está erróneo.");
                    }
                }
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

    // Verifica si la entrada existe en la base de datos
    private boolean existeEntrada(Connection conexion, int identificacion, String materia, int corte) throws Exception {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            sentencia = conexion.prepareStatement("SELECT COUNT(*) FROM notas WHERE identificacion = ? AND materia = ? AND corte = ?");
            sentencia.setInt(1, identificacion);
            sentencia.setString(2, materia);
            sentencia.setInt(3, corte);

            resultado = sentencia.executeQuery();
            resultado.next();

            int count = resultado.getInt(1);

            return count > 0;
        } finally {
            if (resultado != null) {
                resultado.close();
            }

            if (sentencia != null) {
                sentencia.close();
            }
        }
    }
}