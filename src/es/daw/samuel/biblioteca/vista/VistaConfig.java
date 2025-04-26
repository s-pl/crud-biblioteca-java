package es.daw.samuel.biblioteca.vista;

import es.daw.samuel.biblioteca.config.ConexionDB;
import javax.swing.*;
import java.awt.*;

public class VistaConfig extends JPanel {

    private JButton botonProbarConexion;
    private JButton botonCrearBD, botonDestruirDB;
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
        panelBotones.add(botonProbarConexion);
        panelBotones.add(botonCrearBD);
        panelBotones.add(botonDestruirDB);

        add(panelBotones, BorderLayout.CENTER);
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

}
