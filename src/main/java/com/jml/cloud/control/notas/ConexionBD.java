package com.jml.cloud.control.notas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD implements AutoCloseable {

    private Properties connectionProperties;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3307/notas";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "1234";

    @Override
    public void close() throws Exception {

    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connection = null;
        this.connectionProperties = connectionProperties;
    }

    public Connection getConnection() throws SQLException {

        String connectionUrl = connectionProperties.getProperty("connectionUrl", URL);
        String user = connectionProperties.getProperty("user", USUARIO);
        String password = connectionProperties.getProperty("password", CONTRASENA);

        connection = DriverManager.getConnection(connectionUrl, user, password);
        return connection;
    }
}