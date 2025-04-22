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

/**
 *
 * @author samue
 */
public class Biblioteca {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new BibliotecaApp().setVisible(true));
}


}
