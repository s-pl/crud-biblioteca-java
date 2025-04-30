/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.daw.samuel.biblioteca.dao;

import es.daw.samuel.biblioteca.config.ConexionDB;
import es.daw.samuel.biblioteca.model.Categoria;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author Samuel Ponce Luna
 */
public class CategoriaDAO {
   private ConexionDB conexion;
   
   
   public CategoriaDAO(){
       this.conexion = new ConexionDB();
   }
   
   
   
  public ArrayList<Categoria> obtenerTodasLasCategorias() {
    ArrayList<Categoria> categorias = new ArrayList<>();
    Connection conn = conexion.getConn();
    if (conn == null) {
        return categorias;
    }
    Statement stmt = null;
    ResultSet rs = null;
    try {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM Categoria");
        while (rs.next()) {
            categorias.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener categorias: " + e.getMessage());
    } 
    return categorias;
}

public boolean actualizarCategoriaDB(Categoria c) {
    Connection conn = conexion.getConn();
    if (conn == null) {
        System.out.println("No estás conectado a la db");
        return false;
    }
    PreparedStatement pstmt = null;
    try {
        pstmt = conn.prepareStatement("UPDATE Categoria SET nombre = ? WHERE id = ?");
        pstmt.setString(1, c.getNombre());
        pstmt.setInt(2, c.getId());
        pstmt.executeUpdate();
        return true; // se da por entendido que la actualizacion ha ido ok
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return false; //mal
    } 
}

public boolean eliminarCategoria(Categoria c) {
    Connection conn = conexion.getConn();
    if (conn == null) {
        System.out.println("No estás conectado a la db");
        return false;
    }
    PreparedStatement pstmt = null;
    try {
        pstmt = conn.prepareStatement("DELETE FROM Categoria WHERE id = ?");
        pstmt.setInt(1, c.getId());
        
        pstmt.executeUpdate();
        return true; // se da por entendido que la actualizacion ha ido ok
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return false; //mal
    } 
}

public boolean añadirCategoria(Categoria c) {
    Connection conn = conexion.getConn();
    if (conn == null) {
        System.out.println("No estás conectado a la db");
        return false;
    }
    PreparedStatement pstmt = null;
    try {
        pstmt = conn.prepareStatement("INSERT INTO Categoria (nombre) VALUES (?)");
        pstmt.setString(1, c.getNombre());
        pstmt.executeUpdate();
        return true; // se da por entendido que la actualizacion ha ido ok
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return false; //mal
    } 
}
}