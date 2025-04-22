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
        getContentPane().add(tabbedPane);
        
      
        tabbedPane.addTab("Autores", new VistaAutores());
        tabbedPane.addTab("Categorías", new VistaCategorias());
        tabbedPane.addTab("Libros", new VistaLibros());
    }
}