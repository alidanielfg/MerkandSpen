import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Inventario {
    private JFrame frame;

    public Inventario() {
        crearFormulario();
    }

    private void crearFormulario() {
        frame = new JFrame("Merk and Spen - Inventario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear barra de herramientas con botones superiores
        JToolBar toolBar = new JToolBar();
        JButton articulosButton = new JButton("Artículos");
        JButton inventarioButton = new JButton("Inventario");
        JButton solicitudButton = new JButton("Solicitud");
        JButton perfilButton = new JButton("Perfil");

        toolBar.add(articulosButton);
        toolBar.add(inventarioButton);
        toolBar.add(solicitudButton);
        toolBar.add(perfilButton);

        // Asignar acciones a los botones
        articulosButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Ir a Artículos"));
        inventarioButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Ir a Inventario"));
        solicitudButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Ir a Solicitud"));
        perfilButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Ir a Perfil"));

        FormLayout layout = new FormLayout(
                "center:pref, 4dlu, center:pref:g", // Columnas
                "p, 5dlu, p, 5dlu, p, 5dlu, p, 10dlu, p, fill:pref:g" // Filas
        );
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Inventario de Artículos", cc.xyw(1, 1, 3));

        // Crear la tabla de inventario
        String[] columnNames = {"Artículo", "Nombre", "Descripción", "Cantidad", "Stock"};
        Object[][] data = {
                {/*Imagen*/"", ""/*nombre del articulo*/,/*descripcion general*/"",""/*cantidad disponible*/, /*aqui va la cantidad de stock*/},
                {/*Imagen*/"", ""/*nombre del articulo*/,/*descripcion general*/"",""/*cantidad disponible*/, /*aqui va la cantidad de stock*/},
                {/*Imagen*/"", ""/*nombre del articulo*/,/*descripcion general*/"",""/*cantidad disponible*/, /*aqui va la cantidad de stock*/},
        };

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        builder.add(scrollPane, cc.xyw(1, 3, 3));

        JButton actualizarButton = new JButton("Actualizar inventario");
        actualizarButton.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new GestionInventario().crearFormulario());
        });
        builder.add(actualizarButton, cc.xyw(1, 7, 3));

        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(builder.getPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Inventario::new);
    }
}

class GestionInventario {
    private JFrame frame;

    public GestionInventario() {}

    public void crearFormulario() {
        frame = new JFrame("Gestión de Inventario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FormLayout layout = new FormLayout(
                "center:pref, 4dlu, center:pref:g", // Columnas
                "p, 5dlu, p, 5dlu, p, 10dlu, p" // Filas
        );
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Nombre de producto:", cc.xy(1, 1));
        JTextField nombreField = new JTextField(20);
        builder.add(nombreField, cc.xy(3, 1));

        builder.addLabel("Descripción:", cc.xy(1, 3));
        JTextField descripcionField = new JTextField(20);
        builder.add(descripcionField, cc.xy(3, 3));

        builder.addLabel("Cantidad:", cc.xy(1, 5));
        JTextField cantidadField = new JTextField(20);
        builder.add(cantidadField, cc.xy(3, 5));

        JButton agregarButton = new JButton("Agregar producto");
        agregarButton.addActionListener(e -> {
            String nombre = nombreField.getText();
            String descripcion = descripcionField.getText();
            String cantidad = cantidadField.getText();
            JOptionPane.showMessageDialog(frame, "Producto agregado: " + nombre);
        });
        builder.add(agregarButton, cc.xy(1, 7));

        JButton eliminarButton = new JButton("Eliminar producto");
        eliminarButton.addActionListener(e -> {
            String nombre = nombreField.getText();
            JOptionPane.showMessageDialog(frame, "Producto eliminado: " + nombre);
        });
        builder.add(eliminarButton, cc.xy(3, 7));

        frame.setLayout(new BorderLayout());
        frame.add(builder.getPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}