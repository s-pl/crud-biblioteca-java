package es.daw.samuel.biblioteca.dao;

import es.daw.samuel.biblioteca.config.ConexionDB;
import es.daw.samuel.biblioteca.model.Libro;
import java.sql.*;
import java.util.ArrayList;

public class LibroDAO {
    private ConexionDB conexion;

    public LibroDAO() {
        this.conexion = new ConexionDB();
    }

    public ArrayList<Libro> obtenerTodosLosLibros() {
        ArrayList<Libro> libros = new ArrayList<>();
        Connection conn = conexion.getConn();
        if (conn == null) {
            return libros;
        }
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Libro");
            while (rs.next()) {
                libros.add(new Libro(
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    rs.getInt("anio_publicacion"),
                    rs.getInt("autor_id"),
                    rs.getInt("categoria_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener libros: " + e.getMessage());
        }
        return libros;
    }

    public boolean actualizarLibroDB(Libro l) {
        Connection conn = conexion.getConn();
        if (conn == null) {
            System.out.println("No estás conectado a la db");
            return false;
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("UPDATE Libro SET titulo = ?, anio_publicacion = ?, autor_id = ?, categoria_id = ? WHERE isbn = ?");
            pstmt.setString(1, l.getTitulo());
            pstmt.setInt(2, l.getAnio_pub());
            pstmt.setInt(3, l.getAutor());
            pstmt.setInt(4, l.getCategoria());
            pstmt.setString(5, l.getIsbn());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarLibro(Libro l) {
        Connection conn = conexion.getConn();
        if (conn == null) {
            System.out.println("No estás conectado a la db");
            return false;
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("DELETE FROM Libro WHERE isbn = ?");
            pstmt.setString(1, l.getIsbn());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean añadirLibro(Libro l) {
        Connection conn = conexion.getConn();
        if (conn == null) {
            System.out.println("No estás conectado a la db");
            return false;
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO Libro (isbn, titulo, anio_publicacion, autor_id, categoria_id) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, l.getIsbn());
            pstmt.setString(2, l.getTitulo());
            pstmt.setInt(3, l.getAnio_pub());
            pstmt.setInt(4, l.getAutor());
            pstmt.setInt(5, l.getCategoria());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
