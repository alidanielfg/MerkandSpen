/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.*;
import java.util.*;
import javax.swing.JTextField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.jfree.data.category.DefaultCategoryDataset;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;

public class CRUD {
    private Connection conexion;
    private ArticuloDAO articuloDAO;

    public CRUD() throws SQLException {
        conexion = ConexionDB.conectar();
        this.articuloDAO = new ArticuloDAO(conexion);
    }

    public Connection getConexion() {
    return conexion;
}

    // ------------------------- Clases de Modelo -------------------------
    public static class Articulo {
        private int id;
        private String nombre;
        private String descripcion;
        private int cantidad;
        private int idCategoria;
        private int stock;

        public Articulo(int id, String nombre, String descripcion, int cantidad, int idCategoria, int stock) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.cantidad = cantidad;
            this.idCategoria = idCategoria;
            this.stock = stock;
        }

        // Getters y Setters
        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public String getDescripcion() { return descripcion; }
        public int getCantidad() { return cantidad; }
        public int getIdCategoria() { return idCategoria; }
        public int getStock() { return stock; }
    }

    public static class Usuario {
        private int id;
        private String departamento;
        private String rol;

        public Usuario(int id, String departamento, String rol) {
            this.id = id;
            this.departamento = departamento;
            this.rol = rol;
        }

        // Getters
        public int getId() { return id; }
        public String getDepartamento() { return departamento; }
        public String getRol() { return rol; }
    }

    // ------------------------- CRUD: Artículos & Inventario -------------------------
    public boolean registrarArticulo(String nombre, String descripcion, String cantidadStr, String categoria) {
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            int idCategoria = obtenerIdCategoria(categoria);
            
            if (idCategoria == -1) {
                JOptionPane.showMessageDialog(null, "Categoría no válida", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            Articulo articulo = new Articulo(0, nombre, descripcion, cantidad, idCategoria, cantidad);
            return registrarArticulo(articulo);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean registrarArticulo(Articulo articulo) {
        String sqlInsert = "INSERT INTO articulos (nombre, descripcion, cantidad, id_categoria) VALUES (?, ?, ?, ?)";
        String sqlInventario = "INSERT INTO inventarios (id_articulo, stock) VALUES (?, ?)";
        
        try {
            conexion.setAutoCommit(false);
            try (PreparedStatement psArticulo = conexion.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                psArticulo.setString(1, articulo.getNombre());
                psArticulo.setString(2, articulo.getDescripcion());
                psArticulo.setInt(3, articulo.getCantidad());
                psArticulo.setInt(4, articulo.getIdCategoria());

                if (psArticulo.executeUpdate() == 0) {
                    conexion.rollback();
                    return false;
                }

                try (ResultSet generatedKeys = psArticulo.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idArticulo = generatedKeys.getInt(1);
                        try (PreparedStatement psInventario = conexion.prepareStatement(sqlInventario)) {
                            psInventario.setInt(1, idArticulo);
                            psInventario.setInt(2, articulo.getCantidad());
                            if (psInventario.executeUpdate() == 0) {
                                conexion.rollback();
                                return false;
                            }
                        }
                    }
                }
            }
            conexion.commit();
            return true;
        } catch (SQLException e) {
            rollback(conexion);
            return false;
        } finally {
            setAutoCommitTrue(conexion);
        }
    }

    public List<Articulo> obtenerArticulos() throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT a.id, a.nombre, a.descripcion, a.cantidad, a.id_categoria, i.stock " +
                     "FROM articulos a JOIN inventarios i ON a.id = i.id_articulo ORDER BY a.nombre";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                articulos.add(new Articulo(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("cantidad"),
                    rs.getInt("id_categoria"),
                    rs.getInt("stock")
                ));
            }
        }
        return articulos;
    }

    public List<Articulo> buscarArticulosPorNombre(String nombre) throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT a.id, a.nombre, a.descripcion, a.cantidad, a.id_categoria, i.stock " +
                     "FROM articulos a JOIN inventarios i ON a.id = i.id_articulo " +
                     "WHERE a.nombre LIKE ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    articulos.add(new Articulo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getInt("id_categoria"),
                        rs.getInt("stock")
                    ));
                }
            }
        }
        return articulos;
    }
    
    public Articulo obtenerArticuloPorId(int id) throws SQLException {
        String sql = "SELECT a.id, a.nombre, a.descripcion, a.cantidad, a.id_categoria, i.stock " +
                     "FROM articulos a JOIN inventarios i ON a.id = i.id_articulo WHERE a.id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Articulo(
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

    public boolean eliminarArticulo(int idArticulo) {
        try {
            conexion.setAutoCommit(false);
            String sqlInventario = "DELETE FROM inventarios WHERE id_articulo = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sqlInventario)) {
                ps.setInt(1, idArticulo);
                ps.executeUpdate();
            }

            String sqlArticulo = "DELETE FROM articulos WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sqlArticulo)) {
                ps.setInt(1, idArticulo);
                if (ps.executeUpdate() > 0) {
                    conexion.commit();
                    return true;
                }
            }
            conexion.rollback();
            return false;
        } catch (SQLException e) {
            try { conexion.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            try { conexion.setAutoCommit(true); } catch (SQLException e) {}
        }
    }

    // ------------------------- CRUD: Usuarios -------------------------
    public boolean crearUsuario(String departamento, String contrasena, String rol) throws SQLException {
        int idDepartamento = obtenerOCrearDepartamento(departamento);
        int idRol = obtenerIdRol(rol);
        if (idDepartamento == -1 || idRol == -1) return false;

        String sql = "INSERT INTO usuarios (contraseña, id_departamento, id_rol) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, contrasena);
            ps.setInt(2, idDepartamento);
            ps.setInt(3, idRol);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Usuario> obtenerUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.id, d.nombre AS departamento, r.nombre AS rol " +
                     "FROM usuarios u LEFT JOIN departamentos d ON u.id_departamento = d.id " +
                     "LEFT JOIN roles r ON u.id_rol = r.id ORDER BY u.id";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("departamento"),
                    rs.getString("rol")
                ));
            }
        }
        return usuarios;
    }

    public boolean eliminarUsuario(int idUsuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean cambiarContrasena(int userId, String nuevaContrasena) throws SQLException {
        String sql = "UPDATE usuarios SET contraseña = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevaContrasena);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean actualizarRolUsuario(int userId, String nuevoRol) throws SQLException {
    int idRol = obtenerIdRol(nuevoRol);
    if (idRol == -1) return false;

    String sql = "UPDATE usuarios SET id_rol = ? WHERE id = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, idRol);
        ps.setInt(2, userId);
        return ps.executeUpdate() > 0;
    }
}

    public boolean actualizarUsuario(int userId, String nuevaContrasena, String nuevoRol) throws SQLException {
        int idRol = obtenerIdRol(nuevoRol);
        if (idRol == -1) return false;

        String sql = "UPDATE usuarios SET contraseña = ?, id_rol = ? WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, nuevaContrasena);
                ps.setInt(2, idRol);
                ps.setInt(3, userId);
                return ps.executeUpdate() > 0;
            }
        }

        // ------------------------- CRUD: Pedidos -------------------------
            public boolean solicitarArticulo(int idUsuario, int idArticulo, String cantidadStr) {
                try {
            // Convertir la cantidad a entero
            int cantidad = Integer.parseInt(cantidadStr);

            // Verificar que el usuario existe en la base de datos
            if (!verificarUsuarioExiste(idUsuario)) {
                JOptionPane.showMessageDialog(null,
                    "Error: El ID de usuario no existe en la base de datos",
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Verificar stock disponible
            Articulo articulo = obtenerArticuloPorId(idArticulo);
            if (articulo == null) {
                JOptionPane.showMessageDialog(null,
                    "Artículo no encontrado",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Verificar stock antes de proceder
            if (!articuloDAO.verificarStockDisponible(idArticulo, cantidad)) {
                boolean stockDisponible = articuloDAO.obtenerStockDisponible(idArticulo);
                JOptionPane.showMessageDialog(null,
                    "Stock insuficiente. Disponible: " + stockDisponible,
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Iniciar transacción
            conexion.setAutoCommit(false);
            try {
                // Registrar pedido
                String sqlPedido = "INSERT INTO pedidos (id_usuario, id_articulo, cantidad, fecha_solicitud, id_estatus) " +
                                  "VALUES (?, ?, ?, CURRENT_TIMESTAMP, (SELECT id FROM estatus WHERE nombre = 'En proceso'))";
                try (PreparedStatement psPedido = conexion.prepareStatement(sqlPedido)) {
                    psPedido.setInt(1, idUsuario);
                    psPedido.setInt(2, idArticulo);
                    psPedido.setInt(3, cantidad);
                    psPedido.executeUpdate();
                }

                // Actualizar inventario
                if (!articuloDAO.actualizarStock(idArticulo, cantidad)) {
                    conexion.rollback();
                    JOptionPane.showMessageDialog(null,
                        "Error al actualizar el inventario",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                conexion.commit();
                JOptionPane.showMessageDialog(null, 
                    "Solicitud registrada exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } catch (SQLException e) {
                conexion.rollback();
                JOptionPane.showMessageDialog(null,
                    "Error en la base de datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } finally {
                conexion.setAutoCommit(true);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "La cantidad debe ser un número válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error en la base de datos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
        
    public boolean verificarUsuarioExiste(int idUsuario) throws SQLException {
    String sql = "SELECT id FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Devuelve true si el usuario existe
            }
        }
    }

    public List<Map<String, Object>> obtenerPedidosPorDepartamento() throws SQLException {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT d.nombre AS departamento, COUNT(p.id) AS cantidad_pedidos " +
                     "FROM pedidos p JOIN usuarios u ON p.id_usuario = u.id " +
                     "JOIN departamentos d ON u.id_departamento = d.id " +
                     "GROUP BY d.nombre ORDER BY cantidad_pedidos DESC";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("departamento", rs.getString("departamento"));
                fila.put("cantidad_pedidos", rs.getInt("cantidad_pedidos"));
                resultados.add(fila);
            }
        }
        return resultados;
    }

    public List<Map<String, Object>> obtenerPedidosPorFechas(Date inicio, Date fin) throws SQLException {
        List<Map<String, Object>> resultados = new ArrayList<>();
        // Consulta SQL que obtiene todos los pedidos entre fechas
    String sql = "SELECT p.id, a.nombre AS articulo, d.nombre AS departamento, "
               + "p.fecha_solicitud, p.cantidad, e.nombre AS estado "
               + "FROM pedidos p "
               + "JOIN articulos a ON p.id_articulo = a.id "
               + "JOIN usuarios u ON p.id_usuario = u.id "
               + "JOIN departamentos d ON u.id_departamento = d.id "
               + "JOIN estatus e ON p.id_estatus = e.id "
               + "WHERE p.fecha_solicitud BETWEEN ? AND ?"; 
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(inicio.getTime()));
            ps.setDate(2, new java.sql.Date(fin.getTime()));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put("id", rs.getInt("id"));
                    fila.put("articulo", rs.getString("articulo"));
                    fila.put("departamento", rs.getString("departamento"));
                    fila.put("fecha_solicitud", rs.getDate("fecha_solicitud"));
                    fila.put("cantidad", rs.getInt("cantidad"));
                    fila.put("estado", rs.getString("estado"));
                    resultados.add(fila);
                }
            }
        }
        return resultados;
    }

    public DefaultCategoryDataset obtenerTopSolicitudes() throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT a.nombre, COUNT(p.id) AS solicitudes " +
                     "FROM pedidos p JOIN articulos a ON p.id_articulo = a.id " +
                     "GROUP BY a.nombre ORDER BY solicitudes DESC LIMIT 5";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dataset.addValue(rs.getInt("solicitudes"), "Solicitudes", rs.getString("nombre"));
            }
        }
        return dataset;
    }
    
    public boolean actualizarEstatusPedido(int idPedido, String nuevoEstatus) throws SQLException {
    String sql = "UPDATE pedidos SET id_estatus = (SELECT id FROM estatus WHERE nombre = ?) WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevoEstatus);
            ps.setInt(2, idPedido);
            return ps.executeUpdate() > 0;
        }
    }

    // ------------------------- Reportes PDF -------------------------
    public void generarReporteInventarioCompleto() {
        try {
            Document document = new Document();
            String fileName = "reporte_inventario_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            document.add(new Paragraph("Reporte de Inventario Completo", titleFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            String[] headers = {"ID", "Nombre", "Descripción", "Cantidad", "Stock"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            List<Articulo> articulos = obtenerArticulos();
            for (Articulo articulo : articulos) {
                table.addCell(String.valueOf(articulo.getId()));
                table.addCell(articulo.getNombre());
                table.addCell(articulo.getDescripcion());
                table.addCell(String.valueOf(articulo.getCantidad()));
                table.addCell(String.valueOf(articulo.getStock()));
            }

            document.add(table);
            document.close();
            JOptionPane.showMessageDialog(null, "Reporte generado: " + fileName);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void generarReportePedidosPorDepartamento() {
        try {
            Document document = new Document();
            String fileName = "reporte_pedidos_departamento_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            document.add(new Paragraph("Reporte de Pedidos por Departamento", titleFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            String[] headers = {"Departamento", "Cantidad de Pedidos"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            List<Map<String, Object>> resultados = obtenerPedidosPorDepartamento();
            for (Map<String, Object> fila : resultados) {
                table.addCell((String) fila.get("departamento"));
                table.addCell(String.valueOf(fila.get("cantidad_pedidos")));
            }

            document.add(table);
            document.close();
            JOptionPane.showMessageDialog(null, "Reporte generado: " + fileName);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void generarReportePedidosPorFechas(JTextField fechaInicioField, JTextField fechaFinField) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = sdf.parse(fechaInicioField.getText());
            Date fechaFin = sdf.parse(fechaFinField.getText());
            
            Document document = new Document();
            String fileName = "reporte_pedidos_fechas_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            
            document.open();
            
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Reporte de Pedidos por Fechas", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph subTitle = new Paragraph(
                "Del " + sdf.format(fechaInicio) + " al " + sdf.format(fechaFin), subTitleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            
            document.add(new Paragraph(" "));
            
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            
            String[] headers = {"ID", "Artículo", "Departamento", "Fecha Solicitud", "Cantidad", "Estado"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            
            List<Map<String, Object>> resultados = obtenerPedidosPorFechas(fechaInicio, fechaFin);
            for (Map<String, Object> fila : resultados) {
                table.addCell(String.valueOf(fila.get("id")));
                table.addCell((String) fila.get("articulo"));
                table.addCell((String) fila.get("departamento"));
                table.addCell(sdf.format((Date) fila.get("fecha_solicitud")));
                table.addCell(String.valueOf(fila.get("cantidad")));
                table.addCell((String) fila.get("estado"));
            }
            
            document.add(table);
            document.close();
            
            JOptionPane.showMessageDialog(null, 
                "Reporte generado exitosamente: " + new File(fileName).getAbsolutePath(),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al generar reporte: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // ------------------------- Métodos auxiliares -------------------------
    private int obtenerIdCategoria(String nombreCategoria) {
        Map<String, Integer> categorias = new HashMap<>();
        categorias.put("Escritura y corrección", 1);
        categorias.put("Papel y cuadernos", 2);
        categorias.put("Organización y archivo", 3);
        categorias.put("Adhesivos y cortes", 4);
        return categorias.getOrDefault(nombreCategoria, -1);
    }

    private int obtenerIdRol(String rol) throws SQLException {
        String sql = "SELECT id FROM roles WHERE nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, rol);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("id") : -1;
            }
        }
    }

    private int obtenerOCrearDepartamento(String departamento) throws SQLException {
        String sqlBuscar = "SELECT id FROM departamentos WHERE nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sqlBuscar)) {
            ps.setString(1, departamento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        }

        String sqlInsert = "INSERT INTO departamentos (nombre) VALUES (?)";
        try (PreparedStatement ps = conexion.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, departamento);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        }
    }

    private void rollback(Connection conn) {
        try { if (conn != null) conn.rollback(); } catch (SQLException ignored) {}
    }

    private void setAutoCommitTrue(Connection conn) {
        try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException ignored) {}
    }

    public void cerrarConexion() {
        try { if (conexion != null && !conexion.isClosed()) conexion.close(); }
        catch (SQLException e) { System.err.println("Error al cerrar conexión: " + e.getMessage()); }
    }
    
    // Añadir estos métodos a la clase CRUD

    public List<Map<String, Object>> obtenerPedidosPorDepartamento(String departamento) throws SQLException {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT p.id, a.nombre AS articulo, p.cantidad, " +
                     "p.fecha_solicitud, e.nombre AS estado " +
                     "FROM pedidos p " +
                     "JOIN articulos a ON p.id_articulo = a.id " +
                     "JOIN usuarios u ON p.id_usuario = u.id " +
                     "JOIN departamentos d ON u.id_departamento = d.id " +
                     "JOIN estatus e ON p.id_estatus = e.id " +
                     "WHERE d.nombre = ? " +
                     "ORDER BY p.fecha_solicitud DESC";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, departamento);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put("id", rs.getInt("id"));
                    fila.put("articulo", rs.getString("articulo"));
                    fila.put("cantidad", rs.getInt("cantidad"));
                    fila.put("fecha_solicitud", rs.getDate("fecha_solicitud"));
                    fila.put("estado", rs.getString("estado"));
                    resultados.add(fila);
                }
            }
        }
        return resultados;
    }

    public List<Map<String, Object>> obtenerPedidosPorId(int idPedido) throws SQLException {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT p.id, a.nombre AS articulo, d.nombre AS departamento, " +
                     "p.cantidad, p.fecha_solicitud, e.nombre AS estado " +
                     "FROM pedidos p " +
                     "JOIN articulos a ON p.id_articulo = a.id " +
                     "JOIN usuarios u ON p.id_usuario = u.id " +
                     "JOIN departamentos d ON u.id_departamento = d.id " +
                     "JOIN estatus e ON p.id_estatus = e.id " +
                     "WHERE p.id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put("id", rs.getInt("id"));
                    fila.put("articulo", rs.getString("articulo"));
                    fila.put("departamento", rs.getString("departamento"));
                    fila.put("cantidad", rs.getInt("cantidad"));
                    fila.put("fecha_solicitud", rs.getDate("fecha_solicitud"));
                    fila.put("estado", rs.getString("estado"));
                    resultados.add(fila);
                }
            }
        }
        return resultados;
    }
    
    public List<String> obtenerRolesDisponibles() throws SQLException {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT nombre FROM roles";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                roles.add(rs.getString("nombre"));
            }
        }
        return roles;
    }

    public boolean usuarioExiste(int idUsuario) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void close() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}