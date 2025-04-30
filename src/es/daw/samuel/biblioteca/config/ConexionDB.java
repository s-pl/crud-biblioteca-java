package es.daw.samuel.biblioteca.config;
/**
 *
 * @author Samuel Ponce Luna
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ConexionDB {

 
    private Connection conn;
    private Statement stmt;
    public String sqlAutor = """
            CREATE TABLE IF NOT EXISTS Autor (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(100) NOT NULL,
                nacionalidad VARCHAR(50)
            );
        """;

    public String sqlCategoria = """
            CREATE TABLE IF NOT EXISTS Categoria (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(100) NOT NULL
            );
        """;

    public String sqlLibro = """
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
    public ConexionDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:biblioteca.db");
            stmt = conn.createStatement();
          
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void crearTablas() {

        if (conn == null) {
            return;
        }
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
    
  public String returnTablas(int eleccion) {
        return switch (eleccion) {
            case 0 -> sqlAutor;
            case 1 -> sqlCategoria;
            case 2 -> sqlLibro;
            default -> "Tabla desconocida";
        };
    }
  
  public boolean saludDB(){
        return conn != null;
  }
  
  
  public void DROP() {
    try {
        if (conn == null) {
            return;
        }
        stmt.execute("DROP TABLE IF EXISTS Libro");
        stmt.execute("DROP TABLE IF EXISTS Autor");
        stmt.execute("DROP TABLE IF EXISTS Categoria");
        System.out.println("Tablas eliminadas correctamente.");
    } catch (SQLException ex) {
        Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
    }
}


}