import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class inventarioCRUD {
        
    private Connection conexion;
    
    public inventarioCRUD(){
        conexion= ConexionDB.conectar();
    }
    
    public ResultSet obtenerArticuloPorNombre(String nombre){
        String sql = "SELECT a.id, a.nombre, a.descripcion, a.cantidad, i.stock " +
                     "FROM articulos a " +
                     "JOIN inventarios i ON a.id = i.id_articulo " +
                     "WHERE a.nombre LIKE ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            return ps.executeQuery();
        }
        catch(SQLException e) {
            System.out.println("Error al buscar artículo: " + e.getMessage());
            return null;
        }
    }//fin buscar por nombre
    
    public ResultSet obtenerProductosMasSolicitados() {
        String sql = "SELECT a.nombre, SUM(i.stock) as total_stock " +
                     "FROM articulos a " +
                     "JOIN inventarios i ON a.id = i.id_articulo " +
                     "GROUP BY a.nombre " +
                     "ORDER BY total_stock DESC " +
                     "LIMIT 5";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            return ps.executeQuery();
        } catch(SQLException e) {
            System.out.println("Error al obtener productos más solicitados: " + e.getMessage());
            return null;
        }
    }
}
