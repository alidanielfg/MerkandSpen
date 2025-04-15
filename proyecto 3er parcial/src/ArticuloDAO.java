/**
 *
 * @author alida
 */
// ArticuloDAO.java
import java.sql.*;
import javax.swing.JOptionPane;

public class ArticuloDAO {
    private Connection conexion;
    private int cantidad;

    public ArticuloDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean actualizarArticulo(int id, String nombre, String descripcion, int cantidad) throws SQLException {
    conexion.setAutoCommit(false);
    try {
        // Actualizar datos del artículo
        String sqlArticulo = "UPDATE articulos SET nombre = ?, descripcion = ?, cantidad = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sqlArticulo)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setInt(3, cantidad);
            ps.setInt(4, id);
            int filasActualizadas = ps.executeUpdate();
            
            if (filasActualizadas == 0) {
                conexion.rollback();
                return false;
            }
        }
        
        // Actualizar el inventario
        String sqlInventario = "UPDATE inventarios SET stock = ? WHERE id_articulo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sqlInventario)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
        
        conexion.commit();
        return true;
    } catch (SQLException ex) {
        conexion.rollback();
        JOptionPane.showMessageDialog(null, 
            "Error al actualizar el artículo e inventario: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    } finally {
        conexion.setAutoCommit(true);
    }
}

    public CRUD.Articulo obtenerArticuloPorId(int id) throws SQLException {
        String sql = "SELECT a.id, a.nombre, a.descripcion, a.cantidad, a.id_categoria, i.stock " +
                     "FROM articulos a JOIN inventarios i ON a.id = i.id_articulo WHERE a.id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CRUD.Articulo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getInt("id_categoria"),
                        rs.getInt("stock")
                    );
                }
            }
        }
        return null;
    }

    public String obtenerNombreCategoria(int idCategoria) throws SQLException {
        String sql = "SELECT nombre FROM categorias WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        }
        return "Desconocida";
    }

    Object getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public boolean actualizarStock(int idArticulo, int cantidad) throws SQLException {
    String sql = "UPDATE inventarios SET stock = stock - ? WHERE id_articulo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idArticulo);
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean verificarStockDisponible(int idArticulo, int cantidadRequerida) throws SQLException {
        String sql = "SELECT stock FROM inventarios WHERE id_articulo = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, idArticulo);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int stockActual = rs.getInt("stock");
                return stockActual >= cantidadRequerida && stockActual > 0;
            }
        }
    }
    return false;
    }
    
    public boolean obtenerStockDisponible(int idArticulo) throws SQLException {
        // Verificar que no estamos intentando sacar más de lo disponible
        if (!verificarStockDisponible(idArticulo, cantidad)) {
            return false;
        }

        String sql = "UPDATE inventarios SET stock = stock - ? WHERE id_articulo = ? AND stock >= ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idArticulo);
            ps.setInt(3, cantidad);
            return ps.executeUpdate() > 0;
        }
    }
}
