import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserCRUD {
    private Connection conexion;
    
    public UserCRUD() throws SQLException {
        establecerConexion();
    }
    
    private void establecerConexion() throws SQLException {
        conexion = ConexionDB.conectar();
        if(conexion == null || conexion.isClosed()) {
            throw new SQLException("Error al conectar con la base de datos");
        }
    }

    // Métodos CRUD para usuarios
    public List<Usuario> obtenerUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.id, d.nombre AS departamento, r.nombre AS rol " +
                     "FROM usuarios u " +
                     "LEFT JOIN departamentos d ON u.id_departamento = d.id " +
                     "LEFT JOIN roles r ON u.id_rol = r.id " +
                     "ORDER BY u.id";
        
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

    public boolean usuarioExiste(int idUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // Métodos para gestión de contraseñas
    public boolean cambiarContrasena(int userId, String nuevaContrasena) throws SQLException {
        String sql = "UPDATE usuarios SET contraseña = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevaContrasena);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    // Métodos para gestión de roles y departamentos
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

    public int obtenerIdRol(String rol) throws SQLException {
        String sql = "SELECT id FROM roles WHERE nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, rol);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("id") : -1;
            }
        }
    }

    public int obtenerOCrearDepartamento(String departamento) throws SQLException {
        String sqlBuscar = "SELECT id FROM departamentos WHERE nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sqlBuscar)) {
            ps.setString(1, departamento);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return rs.getInt("id");
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

    // Método para creación de usuarios
    public boolean crearUsuario(String departamento, String contrasena, String rol) throws SQLException {
        int idDepartamento = obtenerOCrearDepartamento(departamento);
        int idRol = obtenerIdRol(rol);
        
        if(idDepartamento == -1 || idRol == -1) return false;
        
        String sql = "INSERT INTO usuarios (contraseña, id_departamento, id_rol) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, contrasena);
            ps.setInt(2, idDepartamento);
            ps.setInt(3, idRol);
            return ps.executeUpdate() > 0;
        }
    }

    public Usuario obtenerUsuario(int userId) throws SQLException {
    String sql = "SELECT u.id, d.nombre AS departamento, r.nombre AS rol " +
                 "FROM usuarios u " +
                 "LEFT JOIN departamentos d ON u.id_departamento = d.id " +
                 "LEFT JOIN roles r ON u.id_rol = r.id " +
                 "WHERE u.id = ?";
    
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, userId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("departamento"),
                    rs.getString("rol")
                );
            }
        }
    }
    return null; // O lanzar una excepción si el usuario no existe
}

    // Clase interna para representar usuarios
    public static class Usuario {
        private final int id;
        private final String departamento;
        private final String rol;

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

    public void cerrarConexion() {
        try {
            if(conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}