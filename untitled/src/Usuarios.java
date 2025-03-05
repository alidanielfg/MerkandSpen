public class Usuarios {
    private String nombre;
    private String departamento;
    private String rol;
    private String contrasena;

    public Usuarios(String nombre, String departamento, String rol, String contrasena) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.rol = rol;
        this.contrasena = contrasena;

    }//CIERRE CONSTRUCTOR




    //Zona de gets y sets
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}//CIERRE USUARIOS