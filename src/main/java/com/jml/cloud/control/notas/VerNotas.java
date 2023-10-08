package com.jml.cloud.control.notas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VerNotas extends JFrame {

    private JTextField txtIdentificacion;
    private JButton btnConsultar;
    private JTable tblNotas;

    private final ConexionBD conexionBD;

    public VerNotas(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
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
        Dimension dimension = new Dimension(120, 20);
        JLabel identificacionLabel = new JLabel("Identificación:");

        panel.add(identificacionLabel);
        txtIdentificacion.setPreferredSize(dimension);
        panel.add(txtIdentificacion);
        panel.add(btnConsultar);

        add(panel, BorderLayout.NORTH);
        JScrollPane jScrollPaneTblNotas = new JScrollPane(tblNotas);
        add(jScrollPaneTblNotas, BorderLayout.CENTER);

        btnConsultar.addActionListener(e -> consultarNotas());
    }

    private void consultarNotas() {
        String identificacion = txtIdentificacion.getText();

        if (identificacion == null || identificacion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La identificación no puede estar vacía.");
            return;
        }

        // Limpiar la tabla antes de consultar
        DefaultTableModel modeloVacio = new DefaultTableModel(new String[]{"Identificación", "Materia", "Nota", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido", "Tipo Documento", "Corte"}, 0);
        tblNotas.setModel(modeloVacio);

        try {
            // Obtener las notas del estudiante
            BigInteger bigInteger = BigInteger.valueOf(Long.parseLong(identificacion));
            List<Nota> notas = obtenerNotas(bigInteger);

            if (notas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No existe registro para la identificación ingresada.");
                return;
            }

            // Actualizar la tabla
            DefaultTableModel modeloTabla = new DefaultTableModel(
                    new String[]{"Identificación", "Materia", "Nota", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido", "Tipo Documento", "Corte"}, 0);

            for (Nota nota : notas) {
                modeloTabla.addRow(new Object[]{nota.getIdentificacion(), nota.getMateria(), nota.getNota(), nota.getPrimerNombre(), nota.getSegundoNombre(),
                        nota.getPrimerApellido(), nota.getSegundoApellido(), nota.getTipoDocumento(), nota.getCorte()});
            }

            tblNotas.setModel(modeloTabla);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La identificación debe ser un número válido.");
        }
    }

    private List<Nota> obtenerNotas(BigInteger identificacion) {
        // Conectarse a la base de datos
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = conexionBD.getConnection();

            sentencia = conexion.prepareStatement("SELECT * FROM notas WHERE identificacion = ?");
            sentencia.setString(1, String.valueOf(identificacion));

            resultado = sentencia.executeQuery();

            List<Nota> notas = new ArrayList<>();

            while (resultado.next()) {
                // Crear una nueva instancia de la clase Nota
                Nota nota = new Nota(
                        resultado.getString("identificacion"),
                        resultado.getString("materia"),
                        resultado.getDouble("nota"),
                        resultado.getString("primer_nombre"),
                        resultado.getString("segundo_nombre"),
                        resultado.getString("primer_apellido"),
                        resultado.getString("segundo_apellido"),
                        resultado.getString("tipo_documento"),
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