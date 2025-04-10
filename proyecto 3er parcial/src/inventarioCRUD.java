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
    }//FIN OBTENERARTICULO
    
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
}//OBTIENE EL INVENTARIO COMPLETO

    public ResultSet obtenerPedidosPorDepartamento() {
        String sql = "SELECT d.nombre AS departamento, COUNT(p.id) AS cantidad_pedidos " +
                     "FROM pedidos p " +
                     "JOIN usuarios u ON p.id_usuario = u.id " +  // Relación correcta
                     "JOIN departamentos d ON u.id_departamento = d.id " +  // A través de usuarios
                     "GROUP BY d.nombre " +
                     "ORDER BY cantidad_pedidos DESC";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            return ps.executeQuery();
        } catch(SQLException e) {
            System.out.println("Error al obtener pedidos por departamento: " + e.getMessage());
            return null;
        }
    }//FIN OBTENEMOSLOSPEDIDOSPORDEPARTAMENTO

    public ResultSet obtenerPedidosPorFechas(Date fechaInicio, Date fechaFin) {
        String sql = "SELECT p.id, a.nombre AS articulo, d.nombre AS departamento, " +
                     "p.fecha_solicitud, p.cantidad, e.nombre AS estado " +  // Usar e.nombre en lugar de p.estado
                     "FROM pedidos p " +
                     "JOIN articulos a ON p.id_articulo = a.id " +
                     "JOIN usuarios u ON p.id_usuario = u.id " +
                     "JOIN departamentos d ON u.id_departamento = d.id " +
                     "JOIN estatus e ON p.id_estatus = e.id " +
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
    }//FIN OBTENER POR FECHAS
    
        public boolean eliminarArticulo(int idArticulo) {
        // Iniciamos transacción para eliminar tanto el artículo como su registro de inventario
        try {
            conexion.setAutoCommit(false);

            // 1. Eliminar de inventarios (por la FK)
            String sqlInventario = "DELETE FROM inventarios WHERE id_articulo = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sqlInventario)) {
                ps.setInt(1, idArticulo);
                ps.executeUpdate();
            }

            // 2. Eliminar el artículo
            String sqlArticulo = "DELETE FROM articulos WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sqlArticulo)) {
                ps.setInt(1, idArticulo);
                int filasAfectadas = ps.executeUpdate();

                if(filasAfectadas > 0) {
                    conexion.commit();
                    return true;
                }
            }

            conexion.rollback();
            return false;

        } catch(SQLException e) {
            try {
                conexion.rollback();
            } catch(SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.out.println("Error al eliminar artículo: " + e.getMessage());
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch(SQLException e) {
                System.out.println("Error al restaurar autocommit: " + e.getMessage());
            }
        }
    }
}