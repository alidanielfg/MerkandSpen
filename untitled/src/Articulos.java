import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;

public class Articulos {
    private String nombre;
    private String descripcion;
    private int cantidad;
    private String categoria;

    // Constructor
    public Articulos(String nombre, String descripcion, int cantidad, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    public static void main(String[] args) {
        Articulos articulo = new Articulos("Laptop", "Laptop HP 15", 10, "Electrónica");

        // Crear el layout utilizando JGoodies
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, fill:pref:g", // Columnas
                "p, 5dlu, p, 5dlu, p, 5dlu, p"  // Filas
        );

        // Construir el panel con los elementos de información
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Nombre:", cc.xy(1, 1));
        builder.add(new JTextField(articulo.getNombre()), cc.xy(3, 1));

        builder.addLabel("Descripción:", cc.xy(1, 3));
        builder.add(new JTextField(articulo.getDescripcion()), cc.xy(3, 3));

        builder.addLabel("Cantidad:", cc.xy(1, 5));
        builder.add(new JTextField(String.valueOf(articulo.getCantidad())), cc.xy(3, 5));

        builder.addLabel("Categoría:", cc.xy(1, 7));
        builder.add(new JTextField(articulo.getCategoria()), cc.xy(3, 7));

        // Crear el JFrame para mostrar el panel
        JFrame frame = new JFrame("Información del Artículo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(builder.getPanel());

        // Ajustar el tamaño del JFrame
        frame.setSize(600, 400); // Cambié el tamaño a 600x400
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        frame.setVisible(true);
    }

    // ZONA GETS Y SETS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Metodo toString para mostrar la información del artículo
    @Override
    public String toString() {
        return "Articulos{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}

