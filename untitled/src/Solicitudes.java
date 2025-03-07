import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;

public class Solicitudes{

}//cierre solicitudes    public Solicitudes() {
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
        JComboBox<String> nombreComboBox = new JComboBox<>(new String[]{"Select...", "Alí Daniel","Tania Marquez"
        ,"Joshua Lozano","Rodrigo Caballero"});
        builder.add(nombreComboBox, cc.xy(3, 1));

        builder.addLabel("Artículo:", cc.xy(1, 3));
        JTextField articuloField = new JTextField("", 20);
        builder.add(articuloField, cc.xy(3, 3));

        builder.addLabel("Cantidad:", cc.xy(1, 5));
        JTextField cantidadField = new JTextField("", 20);
        builder.add(cantidadField, cc.xy(3, 5));

        JButton aprobarButton = new JButton("Aprobar solicitud");
        aprobarButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Solicitud aprobada"));
        builder.add(aprobarButton, cc.xy(1, 7));

        JButton rechazarButton = new JButton("Rechazar solicitud");
        rechazarButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Solicitud rechazada"));
        builder.add(rechazarButton, cc.xy(3, 7));

        JButton entregarButton = new JButton("Marcar como entregado");
        entregarButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Solicitud marcada como entregada"));
        builder.add(entregarButton, cc.xyw(1, 9, 3));

        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(builder.getPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Solicitudes::new);
    }
}