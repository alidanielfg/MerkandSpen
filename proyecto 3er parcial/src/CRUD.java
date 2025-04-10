/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CRUD {
    Connection conexion;
    public CRUD() {
        conexion = ConexionDB.conectar();
    
}

public boolean RegistrarArticulo(String nombre, String descripcion, String cantidad, String categoria) {
    try {
        Integer.parseInt(cantidad);
    } catch (NumberFormatException e) {
        System.out.println("La cantidad debe ser un número válido");
        return false;
    }

    //Obtener ID de la categoría
    int idCategoria;
    String sqlGetCategoria = "SELECT id FROM categorias WHERE nombre = ?";
    
    try (PreparedStatement ps = conexion.prepareStatement(sqlGetCategoria)) {
        ps.setString(1, categoria);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            idCategoria = rs.getInt("id");
        } else {
            String sqlInsertCategoria = "INSERT INTO categorias (nombre) VALUES (?)";
            try (PreparedStatement psInsert = conexion.prepareStatement(sqlInsertCategoria, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psInsert.setString(1, categoria);
                psInsert.executeUpdate();
                ResultSet generatedKeys = psInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idCategoria = generatedKeys.getInt(1);
                } else {
                    System.out.println("Error al obtener ID de categoría creada");
                    return false;
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener/insertar categoría: " + e.getMessage());
        return false;
    }

    try {
        conexion.setAutoCommit(false);
        
        // 1. Insertar el artículo
        String sqlInsert = "INSERT INTO articulos (nombre, descripcion, cantidad, id_categoria) VALUES (?, ?, ?, ?)";
        int idArticulo = 0;
        
        try (PreparedStatement ps = conexion.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setInt(3, Integer.parseInt(cantidad));
            ps.setInt(4, idCategoria);
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                conexion.rollback();
                return false;
            }
            
            // Obtener el ID del artículo insertado
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idArticulo = generatedKeys.getInt(1);
                } else {
                    conexion.rollback();
                    System.out.println("Error al obtener ID de artículo creado");
                    return false;
                }
            }
        }
        
        // 2. Insertar registro en inventario con el mismo stock inicial
        String sqlInsertInventario = "INSERT INTO inventarios (id_articulo, stock) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sqlInsertInventario)) {
            ps.setInt(1, idArticulo);
            ps.setInt(2, Integer.parseInt(cantidad));
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows == 0) {
                conexion.rollback();
                return false;
            }
        }
        
        conexion.commit();
        return true;
        
    } catch (SQLException e) {
        try {
            conexion.rollback();
        } catch (SQLException ex) {
            System.out.println("Error al hacer rollback: " + ex.getMessage());
        }
        System.out.println("Error en transacción: " + e.getMessage());
        return false;
    } finally {
        try {
            conexion.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error al restaurar autocommit: " + e.getMessage());
        }
    }
}

    public boolean SolicitarArtinombre(int id_usuario, int id_articulo, String cantidad) {
    try {
        Integer.parseInt(cantidad);
    } catch (NumberFormatException e) {
        System.out.println("La cantidad debe ser un número válido");
        return false;
    }

    int cantidadSolicitada = Integer.parseInt(cantidad);
    
    // Verificar stock
    String sqlCheckStock = "SELECT cantidad FROM articulos WHERE id = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sqlCheckStock)) {
        ps.setInt(1, id_articulo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int stockActual = rs.getInt("cantidad");
            
            if (cantidadSolicitada > stockActual) {
                System.out.println("Stock insuficiente");
                return false;
            }
        } else {
            System.out.println("Artículo no encontrado");
            return false;
        }
    } catch (SQLException e) {
        System.out.println("Error al verificar stock: " + e.getMessage());
        return false;
    }
    
    int idEstatus = 1;

    try {
        conexion.setAutoCommit(false);
        
        //1. Insertar la solicitud con estatus "En proceso"
        String sqlInsert = "INSERT INTO pedidos (id_usuario, id_articulo, cantidad, id_estatus) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sqlInsert)) {
            ps.setInt(1, id_usuario);
            ps.setInt(2, id_articulo);
            ps.setInt(3, cantidadSolicitada);
            ps.setInt(4, idEstatus); // Estatus "En proceso"
            int filasInsertadas = ps.executeUpdate();
            
            if (filasInsertadas <= 0) {
                conexion.rollback();
                return false;
            }
        }
        
        //2. Actualizar el inventario
        if (!actualizarInventario(id_articulo, cantidadSolicitada)) {
            conexion.rollback();
            return false;
        }
        
        conexion.commit();
        return true;
        
    } catch (SQLException e) {
        try {
            conexion.rollback();
        } catch (SQLException ex) {
            System.out.println("Error al hacer rollback: " + ex.getMessage());
        }
        System.out.println("Error en transacción: " + e.getMessage());
        return false;
    } finally {
        try {
            conexion.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error al restaurar autocommit: " + e.getMessage());
        }
    }
}

    public boolean actualizarInventario(int idArticulo, int cantidad) {
        String sql = "UPDATE articulos SET cantidad = cantidad - ? WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idArticulo);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar inventario: " + e.getMessage());
            return false;
        }
    }//CIERRE ACTUALIZAR INVENTARIO  
    
 public ResultSet obtenerPedidos() {
    String sql = "SELECT p.id, d.nombre AS departamento, a.nombre AS articulo, e.nombre AS estatus " +
                 "FROM pedidos p " +
                 "INNER JOIN usuarios u ON p.id_usuario = u.id " +
                 "INNER JOIN departamentos d ON u.id_departamento = d.id " +
                 "INNER JOIN articulos a ON p.id_articulo = a.id " +
                 "LEFT JOIN estatus e ON p.id_estatus = e.id";
     
    try {
        PreparedStatement ps = conexion.prepareStatement(sql);
        return ps.executeQuery();
    } catch(SQLException e) {
        System.out.println("Error al obtener pedidos: " + e.getMessage());
        return null;
    }
}//FIN LLENAR TABLA
 
 public ResultSet PedidoporID(String idPedido) {
    try {
        Integer.parseInt(idPedido);
    } catch(NumberFormatException e) {
        System.out.println("El ID debe ser un número válido");
        return null;
    }
    
    String sql = "SELECT p.id, d.nombre AS departamento, a.nombre AS articulo, e.nombre AS estatus " +
                 "FROM pedidos p " +
                 "INNER JOIN usuarios u ON p.id_usuario = u.id " +
                 "INNER JOIN departamentos d ON u.id_departamento = d.id " +
                 "INNER JOIN articulos a ON p.id_articulo = a.id " +
                 "LEFT JOIN estatus e ON p.id_estatus = e.id " +
                 "WHERE p.id = ?";
     
    try {
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(idPedido));
        return ps.executeQuery();
    } catch(SQLException e) {
        System.out.println("Error al buscar pedido: " + e.getMessage());
        return null;
    }
}//CIERRE PEDIDOSPORID
 
    public ResultSet obtenerPedidosPorUsuario(int idUsuario) {
        String sql = "SELECT p.id, d.nombre AS departamento, a.nombre AS articulo, p.cantidad, e.nombre AS estatus " +
                     "FROM pedidos p " +
                     "INNER JOIN usuarios u ON p.id_usuario = u.id " +
                     "INNER JOIN departamentos d ON u.id_departamento = d.id " +
                     "INNER JOIN articulos a ON p.id_articulo = a.id " +
                     "LEFT JOIN estatus e ON p.id_estatus = e.id " +
                     "WHERE p.id_usuario = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            return ps.executeQuery();
        } catch(SQLException e) {
            System.out.println("Error al obtener pedidos por usuario: " + e.getMessage());
            return null;
        }
    }//FIN PEDIDOSPORUSUARIO

    // Método para obtener el ID de departamento de un usuario
    public int obtenerIdDepartamentoUsuario(int idUsuario) {
        String sql = "SELECT id_departamento FROM usuarios WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_departamento");
            }
        } catch(SQLException e) {
            System.out.println("Error al obtener departamento del usuario: " + e.getMessage());
        }
        return -1;
    }//FIN OIDU
}