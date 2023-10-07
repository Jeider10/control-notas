package com.jml.cloud.control.notas;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorLogin {

    private Login login;

    public ControladorLogin(Login login) {
        this.login = login;
    }

    public void validarInicioSesion() {
        // Validar que el usuario y la contraseña no sean nulos o vacíos
        if (login.txtUsuario.getText().isEmpty() || login.txtContrasena.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El usuario o la contraseña están vacíos.");
            return;
        }

        // Validar que el usuario y la contraseña sean correctos
        String usuario = login.txtUsuario.getText();
        String contrasena = login.txtContrasena.getText();

        Connection conexion = null;
        try {
            ConexionBD conexionBD = new ConexionBD();
            conexion = conexionBD.getConnection();

            // Consultar la base de datos para validar el usuario y la contraseña
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?");
            consulta.setString(1, usuario);
            consulta.setString(2, contrasena);

            ResultSet resultado = consulta.executeQuery();

            if (!resultado.next()) {
                // El usuario o la contraseña son incorrectos
                JOptionPane.showMessageDialog(null, "El usuario o la contraseña son incorrectos.");
                return;
            }

            // El usuario es administrador
            login.txtUsuario.setText("");
            login.txtContrasena.setText("");
            new Menu().setVisible(true);
            login.dispose();
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