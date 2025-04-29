package es.daw.samuel.biblioteca.vista;

import es.daw.samuel.biblioteca.config.ConexionDB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaConfig extends JPanel {

    private JButton botonProbarConexion;
    private JButton botonCrearBD, botonDestruirDB;
    private JComboBox<String> comboTemas;
    String arrayTemas[] = {"Metal", "Nimbus", "Motif", "Windows", "Windows Classic"};
    ConexionDB conexionConfig = new ConexionDB();
    public VistaConfig() {
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Gestión de Base de Datos"));

        botonProbarConexion = new JButton("Probar conexión");
        botonProbarConexion.addActionListener(e -> probarConexion());

        botonCrearBD = new JButton("Crear base de datos");
        botonCrearBD.addActionListener(e -> crearBaseDeDatos());
        botonDestruirDB = new JButton("Eliminar base de datos");
        botonDestruirDB.addActionListener(e -> EliminarBaseDeDatos());
        
        JPanel panelTemas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelTemas.setBorder(BorderFactory.createTitledBorder("Temas"));
        
        comboTemas = new JComboBox<>();
        comboTemas.addActionListener(e -> aplicarTema());

        panelBotones.add(botonProbarConexion);
        panelBotones.add(botonCrearBD);
        panelBotones.add(botonDestruirDB);
        panelTemas.add(comboTemas);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panelBotones, BorderLayout.CENTER);
        mainPanel.add(panelTemas, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
        cargarTemas();
    }

    private void probarConexion() {
        JOptionPane.showMessageDialog(this, conexionConfig.saludDB() ? "Conexion con la base de datos estable" : "Hay algun problema con la DB", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void crearBaseDeDatos() {
        conexionConfig.crearTablas();
        String x = "Base de datos creada con éxito.";
        JOptionPane.showMessageDialog(this, x, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void EliminarBaseDeDatos() {
        ConexionDB conexionDB = new ConexionDB();
        conexionDB.DROP();
    }

   public void cargarTemas() {
    comboTemas.removeAllItems(); 
    for (String x : arrayTemas) {
        comboTemas.addItem(x);
    }
}

      public JComboBox<String> getComboTemas() {
        return comboTemas;
    }

    private void aplicarTema() {
        String temaSeleccionado = (String) comboTemas.getSelectedItem();
        try {
            switch (temaSeleccionado) {
                case "Metal":
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    break;
                case "Nimbus":
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    break;
                case "Motif":
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    break;
                case "Windows":
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    break;
                case "Windows Classic":
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                    break;
            }
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
