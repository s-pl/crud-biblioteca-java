/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.daw.samuel.biblioteca;
import es.daw.samuel.biblioteca.vista.BibliotecaApp;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author samue
 */
public class Biblioteca {

  public static void main(String[] args) {
       try {
        javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // https://es.stackoverflow.com/questions/174175/como-configurar-correctamente-el-lookandfeel-de-java
    } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
    }
    SwingUtilities.invokeLater(() -> new BibliotecaApp().setVisible(true));
}


}