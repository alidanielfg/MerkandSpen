/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CRUD {
    private Connection conexion;
    public CRUD() {
        conexion = ConexionDB.conectar();
    
}

public boolean RegistrarArticulo (String nombre, String descripcion, String cantidadStr, String categoria ){

        String sqlInsert = "INSERT INTO articulos (nombre, descripcion, cantidad, categoria) VALUES (?, ?, ? ,?)";
        
    try (PreparedStatement ps = conexion.prepareStatement(sqlInsert)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, cantidadStr);
            ps.setString(4, categoria);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al intentar insertar: " + e.getMessage());
            return false;
        }


}

public boolean SolicitarArtinombre (String nombre_articulo, String cantidad_solicitada ){
        
        String sqlInsert = "INSERT INTO solicitudes (nombre_articulo, cantidad_solicitada) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sqlInsert)) {
            ps.setString(1, nombre_articulo);
            ps.setString(2, cantidad_solicitada);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al intentar insertar: " + e.getMessage());
            return false;
        }
}



}








