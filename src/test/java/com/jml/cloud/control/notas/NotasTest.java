package com.jml.cloud.control.notas;

import java.sql.SQLException;
import java.util.Properties;

class NotasTest {

    private static ConexionBD conexionBD = new ConexionBD();
    private static Properties connectionProperties = new Properties();

    public static void main(String[] args) throws SQLException {
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

        new Login(conexionBD).setVisible(true);
//        new Menu().setVisible(true);
//        new VerNotas().setVisible(true);
//        new ModificarNotas().setVisible(true);
    }
}