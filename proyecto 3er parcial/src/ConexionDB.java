import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto_poo?serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "Gael5FEBRERO&";

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC no encontrado", e);
        } catch (SQLException e) {
            System.err.println("🚨 Error en la conexión: " + e.getMessage());
            throw e; // Relanza la excepción para manejo superior
        }
    }
}