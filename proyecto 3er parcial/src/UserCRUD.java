import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

public class UserCRUD {
    private Connection conexion;

    public UserCRUD() throws SQLException {
        conexion = ConexionDB.conectar();
        if(conexion == null || conexion.isClosed()) {
            throw new SQLException("No se pudo establecer conexión con la base de datos");
        }
    }

    public boolean crearUsuario(String departamento, String contrasena, String rol) {
        try {
            // Validar conexión
            if(conexion.isClosed()) {
                conexion = ConexionDB.conectar();
            }

            // Obtener o crear departamento
            int idDepartamento = obtenerOCrearDepartamento(departamento);
            if(idDepartamento == -1) {
                JOptionPane.showMessageDialog(null, "Error al procesar el departamento");
                return false;
            }

            // Obtener ID del rol
            int idRol = obtenerIdRol(rol);
            if(idRol == -1) {
                JOptionPane.showMessageDialog(null, "Rol no encontrado en la base de datos");
                return false;
            }

            // Insertar usuario
            String sql = "INSERT INTO usuarios (contraseña, id_departamento, id_rol) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, contrasena);
                ps.setInt(2, idDepartamento);
                ps.setInt(3, idRol);
                
                int filasAfectadas = ps.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en base de datos: " + e.getMessage());
            return false;
        }
    }

    private int obtenerOCrearDepartamento(String nombre) throws SQLException {
        // Buscar departamento existente
        String sqlBuscar = "SELECT id FROM departamentos WHERE nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sqlBuscar)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getInt("id");
        }

        // Crear nuevo departamento si no existe
        String sqlInsert = "INSERT INTO departamentos (nombre) VALUES (?)";
        try (PreparedStatement ps = conexion.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
            
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()) {
                // Notificar que se ha creado un nuevo departamento
                JOptionPane.showMessageDialog(null, 
                    "Departamento '" + nombre + "' creado exitosamente", 
                    "Nuevo departamento", JOptionPane.INFORMATION_MESSAGE);
                return keys.getInt(1);
            }
        }
        
        return -1;
    } 

    private int obtenerIdRol(String nombre) throws SQLException {
        String sql = "SELECT id FROM roles WHERE nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("id") : -1;
        }
    }

    public List<String> obtenerRoles() throws SQLException {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT nombre FROM roles ORDER BY nombre";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                roles.add(rs.getString("nombre"));
            }
        }
        return roles;
    }

    public void cerrar() {
        try {
            if(conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }//fin cerrar
}