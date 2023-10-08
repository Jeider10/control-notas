package com.jml.cloud.control.notas;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    JTextField txtUsuario;
    JPasswordField txtContrasena;
    private JButton btnIniciarSesion;

    private final ControladorLogin controladorLogin;

    public Login(ControladorLogin controladorLogin) {
        this.controladorLogin = controladorLogin;
        controladorLogin.setLogin(this);
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

        btnIniciarSesion.addActionListener(e -> validarInicioSesionLogin());
    }

    protected void validarInicioSesionLogin() {
        controladorLogin.validarInicioSesion();
    }
}