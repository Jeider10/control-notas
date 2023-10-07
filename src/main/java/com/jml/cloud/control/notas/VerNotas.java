package com.jml.cloud.control.notas;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VerNotas extends JFrame {

    private JTextField txtIdentificacion;
    private JButton btnConsultar;
    private JTable tblNotas;

    public VerNotas() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Ver notas");
        setSize(600, 400);
        setLocationRelativeTo(null);

        txtIdentificacion = new JTextField();
        btnConsultar = new JButton("Consultar");
        tblNotas = new JTable();

        JPanel panel = new JPanel();
        panel.add(new JLabel("Identificación:"));
        panel.add(txtIdentificacion);
        panel.add(btnConsultar);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(tblNotas), BorderLayout.CENTER);

        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarNotas();
            }
        });
    }

    private void consultarNotas() {
        String identificacion = txtIdentificacion.getText();

        if (identificacion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese la identificación del estudiante.");
            return;
        }

        // Obtener las notas del estudiante
        List<Nota> notas = obtenerNotas(identificacion);

        // Actualizar la tabla
        tblNotas.setModel(new DefaultTableModel(
                (Object[][]) notas.stream().map(nota -> new Object[]{nota.getIdentificacion(), nota.getMateria(), nota.getNota(), nota.getCorte()}).toArray(),
                new String[]{"Identificación", "Materia", "Nota", "Corte"}
        ));
    }

    private List<Nota> obtenerNotas(String identificacion) {
        // Conectarse a la base de datos
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/notas", "root", "");
            sentencia = conexion.prepareStatement("SELECT * FROM notas WHERE identificacion = ?");
            sentencia.setString(1, identificacion);
            resultado = sentencia.executeQuery();

            List<Nota> notas = new ArrayList<>();

            while (resultado.next()) {
                // Crear una nueva instancia de la clase Nota
                Nota nota = new Nota(
                        resultado.getInt("id"),
                        resultado.getString("identificacion"),
                        resultado.getString("materia"),
                        resultado.getDouble("nota"),
                        resultado.getInt("corte")
                );

                // Agregar la nueva instancia de la clase Nota a la lista de notas
                notas.add(nota);
            }

            return notas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }

                if (sentencia != null) {
                    sentencia.close();
                }

                if (conexion != null) {
                    conexion.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}