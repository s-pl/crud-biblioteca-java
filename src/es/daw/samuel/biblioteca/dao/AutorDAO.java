/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.daw.samuel.biblioteca.dao;

import es.daw.samuel.biblioteca.config.ConexionDB;
import es.daw.samuel.biblioteca.model.Autor;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Samuel Ponce Luna
 */
public class AutorDAO {

    private ConexionDB conexion;

    public AutorDAO() {
        this.conexion = new ConexionDB();
    }

    public ArrayList<Autor> obtenerTodosLosAutores() {
        ArrayList<Autor> autores = new ArrayList<>();
        Connection conn = conexion.getConn();
        if (conn == null) {
            return autores;
        }

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Autor");

            while (rs.next()) {
                autores.add(new Autor(rs.getInt("id"), rs.getString("nombre"), rs.getString("nacionalidad")));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener autores: " + e.getMessage());
        } 
        

        return autores;
    }

    public boolean actualizarAutorDB(Autor a) {
        Connection conn = conexion.getConn();
        if (conn == null) {
            System.out.println("No estás conectado a la db");
        }

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("UPDATE Autor SET nombre = ?, nacionalidad = ? WHERE id = ?");
            pstmt.setString(1, a.getNombre());
            pstmt.setString(2, a.getNacionalidad());
            pstmt.setInt(3, a.getId());
            pstmt.executeUpdate();
            return true; // se da por entendido que la actualizacion ha ido ok

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false; //mal
        } 

    }

    public boolean eliminarAutor(Autor a) {
             Connection conn = conexion.getConn();
        if (conn == null) {
            System.out.println("No estás conectado a la db");
        }

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("DELETE FROM Autor WHERE id = ?");
            pstmt.setInt(1, a.getId());
           
            pstmt.executeUpdate();
            return true; // se da por entendido que la actualizacion ha ido ok

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false; //mal
        }
    }
    public boolean añadirAutor(Autor a) {
      Connection conn = conexion.getConn();
        if (conn == null) {
            System.out.println("No estás conectado a la db");
        }

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO Autor (nombre, nacionalidad) VALUES (?, ?)");
            pstmt.setString(1, a.getNombre());
            pstmt.setString(2, a.getNacionalidad());
            pstmt.executeUpdate();
            return true; // se da por entendido que la actualizacion ha ido ok

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false; //mal
        }
        

    }
}
