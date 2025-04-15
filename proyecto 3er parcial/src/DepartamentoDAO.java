import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {
    private final Connection conexion;
    
    public DepartamentoDAO() throws SQLException {
        this.conexion = ConexionDB.conectar();
    }
    
    public List<String> obtenerTodosDepartamentos() throws SQLException {
        List<String> departamentos = new ArrayList<>();
        
        if(conexion == null) {
            throw new SQLException("No se pudo conectar a la base de datos");
        }
        
        String sql = "SELECT nombre FROM departamentos ORDER BY nombre";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while(rs.next()) {
                departamentos.add(rs.getString("nombre"));
            }
        }
        
        return departamentos;
    }
    
    public void cerrarConexion() {
        try {
            if(conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch(SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}