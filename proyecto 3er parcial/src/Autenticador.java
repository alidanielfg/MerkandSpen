import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Autenticador {
    private static final Logger logger = Logger.getLogger(Autenticador.class.getName());
    
    public static Object[] autenticar(String departamento, String password) {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexion = ConexionDB.conectar();
            
            String sql = "SELECT u.id, r.nombre as rol FROM usuarios u " +
                         "JOIN departamentos d ON u.id_departamento = d.id " +
                         "JOIN roles r ON u.id_rol = r.id " +
                         "WHERE d.nombre = ? AND u.contraseña = ? " +
                         "LIMIT 1";
            
            ps = conexion.prepareStatement(sql);
            ps.setString(1, departamento);
            ps.setString(2, password);
            
            rs = ps.executeQuery();
            
            if(rs.next()) {
                return new Object[]{true, rs.getString("rol"), rs.getInt("id")};
            }
            return new Object[]{false, "", -1};
            
        } catch(SQLException e) {
            logger.log(Level.SEVERE, "Error de autenticación", e);
            JOptionPane.showMessageDialog(null, 
                "Error al conectar con la base de datos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return new Object[]{"error", "", -1};
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(ps != null) ps.close(); } catch(Exception e) {}
            try { if(conexion != null) conexion.close(); } catch(Exception e) {}
        }
    }
}