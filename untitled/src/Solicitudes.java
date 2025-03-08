import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Solicitudes {

    private Map<String, Integer> productosSolicitados = new HashMap<>();

    public Solicitudes() {
        crearFormulario();
    }

    private void crearFormulario() {
        JFrame frame = new JFrame("Merk and Spen - Administración de Solicitudes");
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
                "right:pref, 4dlu, fill:pref:g", // Columnas
                "p, 5dlu, p, 5dlu, p, 5dlu, p, 10dlu, p, 5dlu, p" // Filas
        );
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Nombre:", cc.xy(1, 1));
        JComboBox<String> nombreComboBox = new JComboBox<>(new String[]{"Select...", "Alí Daniel", "Tania Marquez"
                , "Joshua Lozano", "Rodrigo Caballero"});
        builder.add(nombreComboBox, cc.xy(3, 1));

        builder.addLabel("Artículo:", cc.xy(1, 3));
        JTextField articuloField = new JTextField("", 20);
        builder.add(articuloField, cc.xy(3, 3));

        builder.addLabel("Cantidad:", cc.xy(1, 5));
        JTextField cantidadField = new JTextField("", 20);
        builder.add(cantidadField, cc.xy(3, 5));

        JButton aprobarButton = new JButton("Aprobar solicitud");
        aprobarButton.addActionListener(e -> {
            String articulo = articuloField.getText();
            int cantidad = Integer.parseInt(cantidadField.getText());
            productosSolicitados.put(articulo, productosSolicitados.getOrDefault(articulo, 0) + cantidad);
            JOptionPane.showMessageDialog(frame, "Solicitud aprobada");
        });
        builder.add(aprobarButton, cc.xy(1, 7));

        JButton rechazarButton = new JButton("Rechazar solicitud");
        rechazarButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Solicitud rechazada"));
        builder.add(rechazarButton, cc.xy(3, 7));

        JButton entregarButton = new JButton("Marcar como entregado");
        entregarButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Solicitud marcada como entregada"));
        builder.add(entregarButton, cc.xyw(1, 9, 3));

        // Botón para exportar a PDF
        JButton exportarButton = new JButton("Exportar a PDF");
        exportarButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Exportando a PDF (funcionalidad no implementada)"));
        builder.add(exportarButton, cc.xy(1, 11));

        // Botón para ver la gráfica de los productos más solicitados
        JButton verGraficaButton = new JButton("Ver Gráfica de Solicitudes");
        verGraficaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGrafica();
            }
        });
        builder.add(verGraficaButton, cc.xy(3, 11));

        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(builder.getPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void mostrarGrafica() {
        StringBuilder grafica = new StringBuilder("Gráfica de Productos Más Solicitados:\n");

        // Si hay productos solicitados, se generan sus barras
        if (productosSolicitados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se han realizado solicitudes");
            return;
        }

        // Calcular el total de solicitudes para determinar los porcentajes
        int totalSolicitudes = productosSolicitados.values().stream().mapToInt(Integer::intValue).sum();

        // Crear una "gráfica" simple con barras de caracteres
        for (Map.Entry<String, Integer> entry : productosSolicitados.entrySet()) {
            String producto = entry.getKey();
            int cantidad = entry.getValue();
            int porcentaje = (int) ((double) cantidad / totalSolicitudes * 100); // Calcular el porcentaje

            // Mostrar la barra de porcentaje
            grafica.append(producto).append(": ");
            for (int i = 0; i < porcentaje / 2; i++) { // Cada barra representa un 2% de la cantidad
                grafica.append("|");
            }

            // Agregar el porcentaje
            grafica.append(" ").append(porcentaje).append("%\n");
        }

        JOptionPane.showMessageDialog(null, grafica.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Solicitudes::new);
    }
}
