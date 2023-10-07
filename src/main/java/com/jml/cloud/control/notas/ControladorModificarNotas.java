package com.jml.cloud.control.notas;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ControladorModificarNotas {

    private ModificarNotas modificarNotas;

    public ControladorModificarNotas(ModificarNotas modificarNotas) {
        this.modificarNotas = modificarNotas;
    }

    public void modificarNota() {
        String identificacion = modificarNotas.txtIdentificacion.getText();
        String materia = modificarNotas.txtMateria.getText();
        double nota = Double.parseDouble(modificarNotas.txtNota.getText());
        int corte = Integer.parseInt(modificarNotas.txtCorte.getText());

        if (identificacion.isEmpty() || materia.isEmpty() || nota <= 0 || corte <= 0) {
            JOptionPane.showMessageDialog(null, "Ingrese todos los datos correctamente.");
            return;
        }

        // Conectarse a la base de datos
        Connection conexion = null;
        PreparedStatement sentencia = null;

        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/notas", "root", "");
            sentencia = conexion.prepareStatement("UPDATE notas SET nota = ? WHERE identificacion = ? AND materia = ? AND corte = ?");
            sentencia.setDouble(1, nota);
            sentencia.setString(2, identificacion);
            sentencia.setString(3, materia);
            sentencia.setInt(4, corte);
            sentencia.executeUpdate();

            JOptionPane.showMessageDialog(null, "Nota modificada correctamente.");
            modificarNotas.txtIdentificacion.setText("");
            modificarNotas.txtMateria.setText("");
            modificarNotas.txtNota.setText("");
            modificarNotas.txtCorte.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al modificar la nota.");
        } finally {
            try {
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