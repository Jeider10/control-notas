package com.jml.cloud.control.notas;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerNotas extends JFrame {

    private JLabel identificacionLabel;
    private JTextField txtIdentificacion;
    private JButton btnConsultar;
    private JButton btnLimpiar;
    private JButton btnVolverAlMenu;
    private JTable tblNotas;
    private JScrollPane jScrollPaneTblNotas;
    private JComboBox<String> cmbCorte;
    private String corteSeleccionado;

    private final ConexionBD conexionBD;

    public VerNotas(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setTitle("Ver notas");
        setSize(600, 400);
        setLocationRelativeTo(null);

        createComponents();
        setupListeners();
    }

    private void createComponents() {
        createTextFieldsAndButtons();
        createTableModel();
        createPanels();
    }

    private void createTextFieldsAndButtons() {
        identificacionLabel = new JLabel("Identificación:");
        txtIdentificacion = new JTextField();
        btnConsultar = new JButton("Consultar");
        btnLimpiar = new JButton("Limpiar");
        btnVolverAlMenu = new JButton("Volver");
    }

    private void createTableModel() {
        DefaultTableModel modeloVacio = new DefaultTableModel(
                new String[]{"Identificación", "Materia", "Nota", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido", "Tipo Documento", "Corte"},
                0
        );

        tblNotas = new JTable(modeloVacio);
    }

    private void createPanels() {
        JPanel panel = new JPanel();
        Dimension dimension = new Dimension(120, 20);
        panel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        String[] cortes = {"Corte I", "Corte II", "Corte III"};
        cmbCorte = new JComboBox<>(cortes);

        centerPanel.add(identificacionLabel);
        centerPanel.add(txtIdentificacion);
        txtIdentificacion.setPreferredSize(dimension);

        centerPanel.add(cmbCorte);
        centerPanel.add(btnConsultar);
        centerPanel.add(btnLimpiar);
        centerPanel.add(btnVolverAlMenu);

        panel.add(centerPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);
        jScrollPaneTblNotas = new JScrollPane(tblNotas);
        add(jScrollPaneTblNotas, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnConsultar.addActionListener(e -> consultarNotas());
        btnLimpiar.addActionListener(e -> limpiarPantalla());
        btnVolverAlMenu.addActionListener(e -> volverAlMenu());

        setupTxtIdentificacionListener();
        setupCorteListener();

        corteSeleccionado = (String) cmbCorte.getSelectedItem();
        actualizarEstadoBotonLimpiar();
    }

    private void setupTxtIdentificacionListener() {
        txtIdentificacion.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarEstadoBotonLimpiar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarEstadoBotonLimpiar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarEstadoBotonLimpiar();
            }
        });
    }

    private void setupCorteListener() {
        cmbCorte.addActionListener(e -> {
            corteSeleccionado = (String) cmbCorte.getSelectedItem();
        });
    }

    private void consultarNotas() {
        String identificacion = txtIdentificacion.getText();

        if (identificacion == null || identificacion.isEmpty()) {
            mostrarMensajeError("La identificación no puede estar vacía.");
            return;
        }

        int corteNumerico = mapearCorteSeleccionado(corteSeleccionado);

        limpiarTabla();

        List<Nota> notas = obtenerNotasParaIdentificacion(identificacion, corteNumerico);

        if (notas.isEmpty()) {
            mostrarMensajeInfo("No existen registros para la identificación y corte seleccionados.");
            return;
        }

        actualizarTabla(notas);
    }

    private List<Nota> obtenerNotasParaIdentificacion(String identificacion, int corteNumerico) {
        try {
            BigInteger bigInteger = obtenerIdentificacionValida(identificacion);
            return obtenerNotas(bigInteger, corteNumerico);
        } catch (NumberFormatException e) {
            mostrarMensajeError("La identificación debe ser un número válido.");
            return Collections.emptyList();
        }
    }

    private void limpiarTabla() {
        DefaultTableModel modeloVacio = new DefaultTableModel(
                new String[]{"Identificación", "Materia", "Nota", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido", "Tipo Documento", "Corte"}, 0);
        tblNotas.setModel(modeloVacio);
    }

    private BigInteger obtenerIdentificacionValida(String identificacion) throws NumberFormatException {
        return BigInteger.valueOf(Long.parseLong(identificacion));
    }

    private void actualizarTabla(List<Nota> notas) {
        DefaultTableModel modeloTabla = new DefaultTableModel(
                new String[]{"Identificación", "Materia", "Nota", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido", "Tipo Documento", "Corte"}, 0);

        for (Nota nota : notas) {
            modeloTabla.addRow(new Object[]{nota.getIdentificacion(), nota.getMateria(), nota.getNota(), nota.getPrimerNombre(), nota.getSegundoNombre(),
                    nota.getPrimerApellido(), nota.getSegundoApellido(), nota.getTipoDocumento(), nota.getCorte()});
        }

        tblNotas.setModel(modeloTabla);
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensajeInfo(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    // Modifica este método para incluir el corte como parámetro
    private List<Nota> obtenerNotas(BigInteger identificacion, int corte) {
        // Conectarse a la base de datos
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = conexionBD.getConnection();

            // Modifica tu consulta SQL para incluir la cláusula WHERE por el corte seleccionado
            sentencia = conexion.prepareStatement("SELECT * FROM notas WHERE identificacion = ? AND corte = ?");
            sentencia.setString(1, String.valueOf(identificacion));
            sentencia.setInt(2, corte); // Establece el corte seleccionado en la consulta

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

    private void volverAlMenu() {
        setVisible(false); // Oculta la ventana actual
        Menu menu = Menu.getInstance(conexionBD);
        menu.setVisible(true); // Muestra la instancia única de la ventana del menú
    }

    private void limpiarPantalla() {
        txtIdentificacion.setText("");
        DefaultTableModel modeloVacio = new DefaultTableModel(new String[]{"Identificación", "Materia", "Nota", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido", "Tipo Documento", "Corte"}, 0);
        tblNotas.setModel(modeloVacio);

        // Establece el enfoque en el cuadro de texto txtIdentificacion
        txtIdentificacion.requestFocus();

        // Deshabilita el botón "Limpiar" después de limpiar
        btnLimpiar.setEnabled(false);
    }

    // Método para habilitar o deshabilitar el botón "Limpiar" según el contenido del cuadro de texto
    private void actualizarEstadoBotonLimpiar() {
        if (txtIdentificacion.getText().isEmpty()) {
            btnLimpiar.setEnabled(false);
        } else {
            btnLimpiar.setEnabled(true);
        }
    }

    // Método para mapear el corte seleccionado a su valor numérico
    private int mapearCorteSeleccionado(String corteSeleccionado) {
        switch (corteSeleccionado) {
            case "Corte I":
                return 1;
            case "Corte II":
                return 2;
            case "Corte III":
                return 3;
            default:
                return 0;
        }
    }
}