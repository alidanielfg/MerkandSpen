/**
 *
 * @author alida
 */

public class Sesion {
    private static Sesion instance;
    private int userId;
    private String departamento;
    private String rol;
    private boolean activa;
    private long lastActivity;
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30 minutos de timeout

    /**
     * Obtiene la instancia única (Singleton)
     */
    public static synchronized Sesion getInstance() {
        if (instance == null) {
            instance = new Sesion();
        }
        return instance;
    }

    /**
     * Crea una nueva sesión de usuario
     */
    public void crearSesion(int userId, String departamento, String rol) {
        this.userId = userId;
        this.departamento = departamento;
        this.rol = rol;
        this.activa = true;
        this.lastActivity = System.currentTimeMillis();
    }

    /**
     * Cierra la sesión actual
     */
    public void cerrarSesion() {
        this.userId = -1;
        this.departamento = null;
        this.rol = null;
        this.activa = false;
        this.lastActivity = 0;
    }

    /**
     * Actualiza el timestamp de última actividad
     */
    public void actualizarActividad() {
        if (activa) {
            this.lastActivity = System.currentTimeMillis();
        }
    }

    /**
     * Verifica si la sesión ha expirado por inactividad
     */
    public boolean isExpirada() {
        return activa && (System.currentTimeMillis() - lastActivity) > SESSION_TIMEOUT;
    }

    // Getters con validación de sesión activa
    public int getUserId() {
        validarSesion();
        return userId;
    }

    public String getDepartamento() {
        validarSesion();
        return departamento;
    }

    public String getRol() {
        validarSesion();
        return rol;
    }

    public boolean isActiva() {
        return activa && !isExpirada();
    }

    /**
     * Método estático para verificar si hay sesión activa
     */
    public static boolean haySesionActiva() {
        return instance != null && instance.isActiva();
    }

    /**
     * Método estático para obtener el ID del usuario actual
     */
    public static int getCurrentUserId() {
        validarSesionEstatica();
        return instance.getUserId();
    }

    // Métodos privados de validación
    private void validarSesion() {
        if (!activa) {
            throw new IllegalStateException("Sesión no activa");
        }
        if (isExpirada()) {
            cerrarSesion();
            throw new IllegalStateException("Sesión expirada por inactividad");
        }
        actualizarActividad();
    }

    private static void validarSesionEstatica() {
        if (instance == null || !instance.isActiva()) {
            throw new IllegalStateException("No hay sesión activa. Debe autenticarse primero.");
        }
    }
}
