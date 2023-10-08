package com.jml.cloud.control.notas;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorLogin {

    private Login login;

    private final ConexionBD conexionBD;

    public ControladorLogin(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    protected void validarInicioSesion() {
        // Validar que el usuario y la contraseña no sean nulos o vacíos
        if (login.txtUsuario.getText().isEmpty() || login.txtContrasena.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El usuario o la contraseña están vacíos.");
            return;
        }

        // Validar que el usuario y la contraseña sean correctos
        String usuario = login.txtUsuario.getText();
        String contrasena = login.txtContrasena.getText();

        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = conexionBD.getConnection();

            // Consultar la base de datos para validar el usuario y la contraseña
            sentencia = conexion.prepareStatement("SELECT * FROM usuarios WHERE usuario = ? AND password = ?");
            sentencia.setString(1, usuario);
            sentencia.setString(2, contrasena);

            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                // El usuario es administrador
                String tipoUsuario = resultado.getString("rol");
                if (tipoUsuario.equals("administrador")) {
                    // El usuario es administrador
                    login.txtUsuario.setText("");
                    login.txtContrasena.setText("");
                    new Menu(conexionBD).setVisible(true);
                    this.login.dispose();
                } else {
                    // El usuario no es administrador
                    JOptionPane.showMessageDialog(null, "El usuario no es administrador.");
                }
            } else {
                // El usuario o la contraseña son incorrectos.
                JOptionPane.showMessageDialog(null, "El usuario o la contraseña son incorrectos.");
            }
        } catch (SQLException e) {
            // Error al conectar con la base de datos
            e.printStackTrace();
        } finally {
            // Cerrar la conexión a la base de datos
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}