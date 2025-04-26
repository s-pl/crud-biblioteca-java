/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.daw.samuel.biblioteca;

import es.daw.samuel.biblioteca.config.ConexionDB;
import es.daw.samuel.biblioteca.dao.AutorDAO;
import es.daw.samuel.biblioteca.dao.CategoriaDAO;
import es.daw.samuel.biblioteca.model.Autor;

import es.daw.samuel.biblioteca.model.Categoria;
import es.daw.samuel.biblioteca.vista.BibliotecaApp;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author samue
 */
public class Biblioteca {

  public static void main(String[] args) {
       try {
        javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // https://es.stackoverflow.com/questions/174175/como-configurar-correctamente-el-lookandfeel-de-java
    } catch (UnsupportedLookAndFeelException e) {
    } catch (IllegalAccessException e) {
    } catch (InstantiationException e) {
    } catch (ClassNotFoundException e) {
    }
    SwingUtilities.invokeLater(() -> new BibliotecaApp().setVisible(true));
}


}
