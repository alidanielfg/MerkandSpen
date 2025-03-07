import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;

public class NuevoUsuario {
    private String nombre;
    private String contrasena;
    private String departamento;
    private String rol;

    public NuevoUsuario() {
        crearFormulario();
    }

    private void crearFormulario() {
        JFrame frame = new JFrame("Nuevo Usuario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, fill:pref:g", // Columnas
                "p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 10dlu, p" // Filas
        );
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Nombre completo:", cc.xy(1, 1));
        JTextField nombreField = new JTextField(20);
        builder.add(nombreField, cc.xy(3, 1));

        builder.addLabel("Contraseña:", cc.xy(1, 3));
        JTextField passField = new JTextField(20);  // Cambio: JTextField para contraseña visible
        builder.add(passField, cc.xy(3, 3));

        builder.addLabel("Verificar contraseña:", cc.xy(1, 5));
        JTextField verifyPassField = new JTextField(20);  // Cambio: Añadido campo para verificar contraseña
        builder.add(verifyPassField, cc.xy(3, 5));

        builder.addLabel("Seleccione su departamento:", cc.xy(1, 7));
        JComboBox<String> deptoComboBox = new JComboBox<>(new String[]{"Select...", "Departamento 1", "Departamento 2"});
        builder.add(deptoComboBox, cc.xy(3, 7));

        builder.addLabel("Seleccione su rol:", cc.xy(1, 9));
        JComboBox<String> rolComboBox = new JComboBox<>(new String[]{"Select...", "Rol 1", "Rol 2"});
        builder.add(rolComboBox, cc.xy(3, 9));

        JCheckBox termsCheckBox = new JCheckBox("Términos y Condiciones de Uso.");
        builder.add(termsCheckBox, cc.xy(3, 11));

        JButton submitButton = new JButton("Registrar usuario");
        submitButton.addActionListener(e -> {
            if (!termsCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(frame, "Debe aceptar los términos y condiciones.");
                return;
            }
            if (!passField.getText().equals(verifyPassField.getText())) {  // Cambio: Validación de que las contraseñas coincidan
                JOptionPane.showMessageDialog(frame, "Las contraseñas no coinciden.");
                return;
            }
            this.nombre = nombreField.getText();
            this.contrasena = passField.getText();
            this.departamento = (String) deptoComboBox.getSelectedItem();
            this.rol = (String) rolComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(frame, "Usuario registrado con éxito.");
        });
        builder.add(submitButton, cc.xyw(1, 13, 3));

        frame.add(builder.getPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Usuarios::new);
    }
}
