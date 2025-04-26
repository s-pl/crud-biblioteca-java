package es.daw.samuel.biblioteca.vista;

import javax.swing.*;
import java.awt.*;

public class BibliotecaApp extends JFrame {

    private JTabbedPane tabbedPane;

    public BibliotecaApp() {
        setTitle("Gestión de Biblioteca");
        setSize(1000, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);
       
        tabbedPane.addTab("Autores", new VistaAutores());
        tabbedPane.addTab("Categorías", new VistaCategorias());
       
        VistaLibros vistaLibros = new VistaLibros();
        tabbedPane.addTab("Libros", vistaLibros);
   
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 2) {
                vistaLibros.cargarAutores(); 
                vistaLibros.cargarCategorias();
            }
        });
      
    }
   
}