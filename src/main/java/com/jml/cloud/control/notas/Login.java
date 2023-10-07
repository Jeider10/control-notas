package com.jml.cloud.control.notas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    JTextField txtUsuario;
    JPasswordField txtContrasena;
    private JButton btnIniciarSesion;

    private final ConexionBD conexionBD;

    public Login(ConexionBD conexionBD) {
        this.conexionBD = conexionBD; // Inyecta la instancia de ConexionBD a través del constructor
        initComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Inicio de sesión");
        // Establecer un tamaño predeterminado para el JPanel
        setSize(600, 300);

        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();
        btnIniciarSesion = new JButton("Iniciar sesión");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Dimension dimension = new Dimension(120, 20);

        // Configurar el JLabel y JTextField del Usuario
        JLabel usuarioLabel = new JLabel("Usuario: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        txtUsuario.setPreferredSize(dimension);
        panel.add(usuarioLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtUsuario, gbc);

        // Configurar el JLabel y JPasswordField de la Contraseña
        JLabel contrasenaLabel = new JLabel("Contraseña: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(contrasenaLabel, gbc);

        gbc.gridx = 1;
        txtContrasena.setPreferredSize(dimension);
        panel.add(txtContrasena, gbc);

        // Configurar el botón "Iniciar sesión" en el centro
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnIniciarSesion, gbc);

        // Agregar el JPanel al centro del JFrame
        add(panel, BorderLayout.CENTER);

        btnIniciarSesion.addActionListener(e -> validarInicioSesion());
    }

    protected void validarInicioSesion() {
        // Validar que el usuario y la contraseña no sean nulos o vacíos
        if (txtUsuario.getText().isEmpty() || txtContrasena.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El usuario o la contraseña están vacíos.");
            return;
        }

        // Validar que el usuario y la contraseña sean correctos
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        Connection conexion = null;
        try {
            conexion = conexionBD.getConnection();

            // Consultar la base de datos para validar el usuario y la contraseña
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM usuarios WHERE usuario = ? AND password = ?");
            consulta.setString(1, usuario);
            consulta.setString(2, contrasena);

            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                // El usuario es administrador
                String tipoUsuario = resultado.getString("tipo_usuario");
                if (tipoUsuario.equals("administrador")) {
                    // El usuario es administrador
                    txtUsuario.setText("");
                    txtContrasena.setText("");
                    new Menu().setVisible(true);
                    this.dispose();
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