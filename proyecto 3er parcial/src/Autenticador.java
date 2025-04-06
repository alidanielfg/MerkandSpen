import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Autenticador {
    
    public static boolean verificarCredenciales(String password, String departamento) {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexion = ConexionDB.conectar();
            String sql = "SELECT u.id FROM usuarios u " +
                         "JOIN departamentos ON u.id_departamento = d.id " +
                         "WHERE u.contraseña = ? AND LOWER(d.nombre) = LOWER(?)";
            
            ps = conexion.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, departamento);
            
            rs = ps.executeQuery();
            return rs.next(); // Si hay resultados, credenciales válidas
            
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            
        }
    }
}