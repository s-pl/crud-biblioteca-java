package es.daw.samuel.biblioteca.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ConexionDB {

    private Connection conn;
    private Statement stmt;
   
    public ConexionDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:biblioteca.db");
            stmt = conn.createStatement();
            System.out.println("Conectado a la DB");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void crearTablas() {

        if (conn == null) {
            return;
        }

        String sqlAutor = """
            CREATE TABLE IF NOT EXISTS Autor (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(100) NOT NULL,
                nacionalidad VARCHAR(50)
            );
        """;

        String sqlCategoria = """
            CREATE TABLE IF NOT EXISTS Categoria (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(100) NOT NULL
            );
        """;

        String sqlLibro = """
            CREATE TABLE IF NOT EXISTS Libro (
                isbn VARCHAR(20) PRIMARY KEY,
                titulo VARCHAR(200) NOT NULL,
                anio_publicacion INTEGER,
                autor_id INTEGER,
                categoria_id INTEGER,
                FOREIGN KEY (autor_id) REFERENCES Autor(id),
                FOREIGN KEY (categoria_id) REFERENCES Categoria(id)
            );
        """;

        try {

            stmt.execute(sqlAutor);
            stmt.execute(sqlCategoria);
            stmt.execute(sqlLibro);
            System.out.println("creadas correctamente.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cerrarConexion() {
    try {
        if (stmt != null){ stmt.close();}
        if (conn != null){ conn.close();}
        System.out.println("desconectado de la DB");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}


    
    
}
