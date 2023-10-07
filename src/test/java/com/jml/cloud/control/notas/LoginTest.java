package com.jml.cloud.control.notas;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private Properties connectionProperties = new Properties();
    private ConexionBD conexionBD = new ConexionBD();
    private Login login = new Login(conexionBD);

    @Test
    void inicioSesionValido_Test() throws SQLException {
        // Configurar el comportamiento esperado de ConexionBD para pruebas

        String connectionUrl = "jdbc:mysql://localhost:3307/notas";
        String user = "root";
        String password = "1234";

        connectionProperties.put("connectionUrl", connectionUrl);
        connectionProperties.put("user", user);
        connectionProperties.put("password", password);

        conexionBD.setConnectionProperties(connectionProperties);

        conexionBD.getConnection();
        System.out.println("Conexion establecida");

        // Configurar las credenciales de inicio de sesión válidas
        login.txtUsuario.setText("jeider");
        login.txtContrasena.setText("123");

        // Ejecutar el método de validación de inicio de sesión
        login.validarInicioSesion();

        // Verificar que el usuario sea redirigido al menú
        assertTrue(login.isVisible()); // o alguna otra aserción que confirme que el usuario fue redirigido
    }
}