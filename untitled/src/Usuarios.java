import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;

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
        });
        builder.add(submitButton, cc.xyw(1, 7, 3));

        frame.add(builder.getPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Usuarios::new);
    }
}//CIERRE DE USUARIOS