import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;
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
        String sql = "SELECT a.nombre, COUNT(p.id) AS solicitudes "+
                "FROM pedidos p "+
                "JOIN articulos a ON p.id_articulo = a.id "+
                "GROUP BY a.nombre "+
                "ORDER BY solicitudes DESC "+
                "LIMIT 5";
        try(PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                dataset.addValue(rs.getInt("solicitudes"),"Solicitudes",rs.getString("nombre"));
            }
        }catch (SQLException e){
            System.out.println("Error al obtener solicitudes: "+ e.getMessage());
        }
        return dataset;
    }
    public ResultSet obtenerArticulos(){
        String sql = "SELECT a.id, a.nombre, a.descripcion, a.cantidad, i.stock "+
                "FROM articulos a "+
                "JOIN inventarios i ON a.id = i.id_articulo";
        try{
            PreparedStatement ps = conexion.prepareStatement(sql);
            return ps.executeQuery();
        }catch(SQLException e){
            System.out.println("Error al obtener los articulos"+e.getMessage());
            return null;
        }
    }
    
    public ResultSet obtenerInventarioCompleto() {
    String sql = "SELECT a.id, a.nombre, a.descripcion, a.cantidad, i.stock " +
                 "FROM articulos a " +
                 "JOIN inventarios i ON a.id = i.id_articulo " +
                 "ORDER BY a.nombre";
    try {
        PreparedStatement ps = conexion.prepareStatement(sql);
        return ps.executeQuery();
    } catch(SQLException e) {
        System.out.println("Error al obtener inventario completo: " + e.getMessage());
        return null;
    }
}

    public ResultSet obtenerPedidosPorDepartamento() {
        String sql = "SELECT d.nombre AS departamento, COUNT(p.id) AS cantidad_pedidos " +
                     "FROM pedidos p " +
                     "JOIN departamentos d ON p.id_departamento = d.id " +
                     "GROUP BY d.nombre " +
                     "ORDER BY cantidad_pedidos DESC";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            return ps.executeQuery();
        } catch(SQLException e) {
            System.out.println("Error al obtener pedidos por departamento: " + e.getMessage());
            return null;
        }
    }

    public ResultSet obtenerPedidosPorFechas(Date fechaInicio, Date fechaFin) {
        String sql = "SELECT p.id, a.nombre AS articulo, d.nombre AS departamento, " +
                     "p.fecha_solicitud, p.cantidad, p.estado " +
                     "FROM pedidos p " +
                     "JOIN articulos a ON p.id_articulo = a.id " +
                     "JOIN departamentos d ON p.id_departamento = d.id " +
                     "WHERE p.fecha_solicitud BETWEEN ? AND ? " +
                     "ORDER BY p.fecha_solicitud";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            ps.setDate(2, new java.sql.Date(fechaFin.getTime()));
            return ps.executeQuery();
        } catch(SQLException e) {
            System.out.println("Error al obtener pedidos por fechas: " + e.getMessage());
            return null;
        }
    }
}