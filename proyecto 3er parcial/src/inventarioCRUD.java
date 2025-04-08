import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import org.jfree.data.category.DefaultCategoryDataset;

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
    
    public DefaultCategoryDataset obtenerTopSolicitudes() throws SQLException{
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT a.nombre, COUNT(s.id) AS solicitudes "+
                "FROM solicitudes s "+
                "JOIN articulos a ON s.id_articulo = a.id "+
                "GROUP BY a.nombre "+
                "ORDER BY solicitudes DESC "+
                "LIMIT 5";
        try(PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                dataset.addValue(rs.getInt("solicitudes"),"Solicitudes",rs.getString("nombre"));
            }
        }catch (SQLException e){
            System.out.println("Error al obtener solicitueds: "+ e.getMessage());
        }
        return dataset;
    }
}