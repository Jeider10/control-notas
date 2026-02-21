package com.jml.cloud.control.notas;

import org.junit.Test;

import java.sql.SQLException;
import java.util.Properties;

public class ConexionIT {

    private Properties connectionProperties = new Properties();
    private ConexionBD conexionBD = new ConexionBD();


    @Test
    public void getConnection_IT() throws SQLException {

        String connectionUrl = "jdbc:mysql://localhost:3308/fantastic_school_admin_db";
        String user = "root";
        String password = "";

        connectionProperties.put("connectionUrl", connectionUrl);
        connectionProperties.put("user", user);
        connectionProperties.put("password", password);

        conexionBD.setConnectionProperties(connectionProperties);

        conexionBD.getConnection();
        System.out.println("Conexion establecida");
    }
}