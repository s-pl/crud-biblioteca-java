package es.daw.samuel.biblioteca.vista;

import javax.swing.*;
import java.awt.*;

public class BibliotecaApp extends JFrame {

    private JTabbedPane tabbedPane;

    public BibliotecaApp() {
        setTitle("Gestión de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        
        VistaLibros vistaLibros = new VistaLibros();
        VistaAutores vistaAutores = new VistaAutores();
        VistaCategorias vistaCategorias = new VistaCategorias();
        VistaConfig vistaConfig = new VistaConfig();
        
        tabbedPane.addTab("Libros", vistaLibros);
        tabbedPane.addTab("Autores", vistaAutores);
        tabbedPane.addTab("Categorias", vistaCategorias);
        tabbedPane.addTab("Configuracion", vistaConfig);
       
        add(tabbedPane);
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 0) {
                vistaLibros.cargarLibros();
                vistaLibros.cargarAutores();
                vistaLibros.cargarCategorias();
            }
        });
        
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 1) {
                vistaAutores.actualizarDatos();
                
            }
        });
        
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 2) {
             vistaCategorias.actualizarDatos();
            }
        });
        
    }
    
}
