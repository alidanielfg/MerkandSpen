import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;
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
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class inventarioCRUD {
    private Connection conexion;
    
    public inventarioCRUD(){
        conexion = ConexionDB.conectar();
    }
    
    // Métodos básicos CRUD
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
    }
    
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
                     "JOIN usuarios u ON p.id_usuario = u.id " +
                     "JOIN departamentos d ON u.id_departamento = d.id " +
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
                     "p.fecha_solicitud, p.cantidad, e.nombre AS estado " +
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
    }
    
    public boolean eliminarArticulo(int idArticulo) {
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
    
    // Métodos para generar reportes
    public void generarReporteInventarioCompleto() {
        try {
            Document document = new Document();
            String fileName = "reporte_inventario_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Reporte de Inventario Completo", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            String[] headers = {"ID", "Nombre", "Descripción", "Cantidad", "Stock"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            
            ResultSet resultados = obtenerInventarioCompleto();
            
            while (resultados != null && resultados.next()) {
                table.addCell(String.valueOf(resultados.getInt("id")));
                table.addCell(resultados.getString("nombre"));
                table.addCell(resultados.getString("descripcion"));
                table.addCell(String.valueOf(resultados.getInt("cantidad")));
                table.addCell(String.valueOf(resultados.getInt("stock")));
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
    
    public void generarReportePedidosPorDepartamento() {
        try {
            Document document = new Document();
            String fileName = "reporte_pedidos_departamento_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            
            document.open();
            
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Reporte de Pedidos por Departamento", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            document.add(new Paragraph(" "));
            
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            
            String[] headers = {"Departamento", "Cantidad de Pedidos"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            
            ResultSet resultados = obtenerPedidosPorDepartamento();
            
            while (resultados != null && resultados.next()) {
                table.addCell(resultados.getString("departamento"));
                table.addCell(String.valueOf(resultados.getInt("cantidad_pedidos")));
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
            
            ResultSet resultados = obtenerPedidosPorFechas(fechaInicio, fechaFin);
            
            while (resultados != null && resultados.next()) {
                table.addCell(String.valueOf(resultados.getInt("id")));
                table.addCell(resultados.getString("articulo"));
                table.addCell(resultados.getString("departamento"));
                table.addCell(new SimpleDateFormat("yyyy-MM-dd").format(resultados.getDate("fecha_solicitud")));
                table.addCell(String.valueOf(resultados.getInt("cantidad")));
                table.addCell(resultados.getString("estado"));
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
}