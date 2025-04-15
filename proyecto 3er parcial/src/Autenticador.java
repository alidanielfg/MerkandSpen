import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Autenticador {
    private static final Logger logger = Logger.getLogger(Autenticador.class.getName());
    private final Connection conexion;
    
    public Autenticador() throws SQLException {
        this.conexion = ConexionDB.conectar();
    }
    
    public ResultadoAutenticacion autenticar(String departamento, String password) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        String sql = "SELECT u.id, r.nombre as rol "
                   + "FROM usuarios u "
                   + "JOIN departamentos d ON u.id_departamento = d.id "
                   + "JOIN roles r ON u.id_rol = r.id "
                   + "WHERE d.nombre = ? AND u.contraseña = ? "
                   + "LIMIT 1";
        
        ps = conexion.prepareStatement(sql);
        ps.setString(1, departamento);
        ps.setString(2, password);
        
        rs = ps.executeQuery();
        
        if(rs.next()) {
            return new ResultadoAutenticacion(
                true, 
                rs.getString("rol"), 
                rs.getInt("id"),
                departamento
            );
        }
        return new ResultadoAutenticacion(false, "", -1, "");
        
    } catch(SQLException e) {
        logger.log(Level.SEVERE, "Error de autenticación", e);
        return new ResultadoAutenticacion("error", "", -1, "");
    } finally {
        cerrarRecursos(ps, rs);
    }
}
    
    private void cerrarRecursos(PreparedStatement ps, ResultSet rs) {
        try { if(rs != null) rs.close(); } catch(Exception e) {
            logger.log(Level.WARNING, "Error al cerrar ResultSet", e);
        }
        try { if(ps != null) ps.close(); } catch(Exception e) {
            logger.log(Level.WARNING, "Error al cerrar PreparedStatement", e);
        }
    }
    
    public void cerrarConexion() {
        try {
            if(conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch(SQLException e) {
            logger.log(Level.WARNING, "Error al cerrar conexión", e);
        }
    }
    
    public static class ResultadoAutenticacion {
        private final boolean autenticado;
        private final String rol;
        private final int userId;
        private final String departamento;
        private final String estadoError;
        
        // Constructor para éxito/fracaso
        public ResultadoAutenticacion(boolean autenticado, String rol, int userId, String departamento) {
            this.autenticado = autenticado;
            this.rol = rol;
            this.userId = userId;
            this.departamento = departamento;
            this.estadoError = "";
        }
        
        // Constructor para error
        public ResultadoAutenticacion(String estadoError, String rol, int userId, String departamento) {
            this.autenticado = false;
            this.rol = rol;
            this.userId = userId;
            this.departamento = departamento;
            this.estadoError = estadoError;
        }
        
        // Getters
        public boolean isAutenticado() { return autenticado; }
        public String getRol() { return rol; }
        public int getUserId() { return userId; }
        public String getDepartamento() { return departamento; }
        public boolean isError() { return !estadoError.isEmpty(); }
        public String getEstadoError() { return estadoError; }
    }
    
    public int obtenerUserId(String departamento, String password) throws SQLException {
    String sql = "SELECT id FROM usuarios u " +
               "JOIN departamentos d ON u.id_departamento = d.id " +
               "WHERE d.nombre = ? AND u.contraseña = ? " +
               "LIMIT 1";
    
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, departamento);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("id") : -1;
            }
        }
    }   
}