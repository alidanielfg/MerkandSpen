import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Usuarios {
    private String nombre;
    private String contrasena;
    private String departamento;

    public Usuarios() {
        crearFormulario();
    }

    private void crearFormulario() {
        JFrame frame = new JFrame("Merks and Spen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, fill:pref:g", // Columnas
                "p, 5dlu, p, 5dlu, p, 10dlu, p" // Filas
        );
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Usuario:", cc.xy(1, 1));
        JTextField nombreField = new JTextField("", 20);
        builder.add(nombreField, cc.xy(3, 1));

        builder.addLabel("Contraseña:", cc.xy(1, 3));
        JPasswordField passField = new JPasswordField("", 20);
        builder.add(passField, cc.xy(3, 3));

        builder.addLabel("Seleccione su departamento:", cc.xy(1, 5));
        JComboBox<String> deptoComboBox = new JComboBox<>(new String[]{"Select...", "Departamento 1", "Departamento 2"});
        builder.add(deptoComboBox, cc.xy(3, 5));

        JButton submitButton = new JButton("Ingresar");
        submitButton.addActionListener(e -> {
            this.nombre = nombreField.getText();
            this.contrasena = new String(passField.getPassword());
            this.departamento = (String) deptoComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(frame, "Usuario registrado con éxito.");

            // Si el usuario es administrador, mostrar los pedidos
            if (this.departamento.equals("Departamento 1")) {
                // Limpiar el panel y mostrar la tabla de pedidos
                frame.getContentPane().removeAll();
                mostrarPedidos(frame);
            }
        });
        builder.add(submitButton, cc.xyw(1, 7, 3));

        // Panel para formulario de usuario
        JPanel panelUsuario = new JPanel(new BorderLayout());
        panelUsuario.add(builder.getPanel(), BorderLayout.CENTER);

        frame.setLayout(new BorderLayout());
        frame.add(panelUsuario, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void mostrarPedidos(JFrame frame) {
        // Crear datos ficticios de pedidos para mostrar en la tabla
        String[] columnas = {"ID Pedido", "Artículo", "Cantidad", "Estado"};
        Object[][] datos = {
                {"001", "Papel", 50, "Pendiente"},
                {"002", "Lápices", 30, "Pendiente"},
                {"003", "Tijeras", 20, "Pendiente"}
        };

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        JTable tablaPedidos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaPedidos);

        // Agregar un botón para aprobar o rechazar
        JPanel panelBotones = new JPanel();
        JButton aprobarButton = new JButton("Aprobar");
        JButton rechazarButton = new JButton("Rechazar");

        aprobarButton.addActionListener(e -> {
            int selectedRow = tablaPedidos.getSelectedRow();
            if (selectedRow != -1) {
                modelo.setValueAt("Aprobado", selectedRow, 3); // Cambiar el estado a "Aprobado"
                JOptionPane.showMessageDialog(frame, "Pedido aprobado.");
            } else {
                JOptionPane.showMessageDialog(frame, "Seleccione un pedido para aprobar.");
            }
        });

        rechazarButton.addActionListener(e -> {
            int selectedRow = tablaPedidos.getSelectedRow();
            if (selectedRow != -1) {
                modelo.setValueAt("Rechazado", selectedRow, 3); // Cambiar el estado a "Rechazado"
                JOptionPane.showMessageDialog(frame, "Pedido rechazado.");
            } else {
                JOptionPane.showMessageDialog(frame, "Seleccione un pedido para rechazar.");
            }
        });

        panelBotones.add(aprobarButton);
        panelBotones.add(rechazarButton);

        // Organizar la vista con la tabla y los botones
        JPanel panelPedidos = new JPanel(new BorderLayout());
        panelPedidos.add(scrollPane, BorderLayout.CENTER);
        panelPedidos.add(panelBotones, BorderLayout.SOUTH);

        // Agregar los paneles al frame
        frame.setLayout(new BorderLayout());
        frame.add(panelPedidos, BorderLayout.CENTER);

        // Actualizar la interfaz
        frame.revalidate();
        frame.repaint();
        frame.pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Usuarios::new);
    }
}
