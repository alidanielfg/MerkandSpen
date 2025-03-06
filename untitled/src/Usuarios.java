import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;

public class Usuarios {
    private String nombre;
    private String departamento;
    private String rol;
    private String contrasena;

    public Usuarios() {
        crearFormulario();
    }

    private void crearFormulario() {
        JFrame frame = new JFrame("Registro de Usuario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, fill:pref:g", // Columnas
                "p, 5dlu, p, 5dlu, p, 5dlu, p, 10dlu, p" // Filas
        );
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Nombre:", cc.xy(1, 1));
        JTextField nombreField = new JTextField(20);
        builder.add(nombreField, cc.xy(3, 1));

        builder.addLabel("Departamento:", cc.xy(1, 3));
        JTextField deptoField = new JTextField(20);
        builder.add(deptoField, cc.xy(3, 3));

        builder.addLabel("Rol:", cc.xy(1, 5));
        JTextField rolField = new JTextField(20);
        builder.add(rolField, cc.xy(3, 5));

        builder.addLabel("Contraseña:", cc.xy(1, 7));
        JPasswordField passField = new JPasswordField(20);
        builder.add(passField, cc.xy(3, 7));

        JButton submitButton = new JButton("Guardar");
        submitButton.addActionListener(e -> {
            this.nombre = nombreField.getText();
            this.departamento = deptoField.getText();
            this.rol = rolField.getText();
            this.contrasena = new String(passField.getPassword());
            JOptionPane.showMessageDialog(frame, "Usuario registrado con éxito.");
        });
        builder.add(submitButton, cc.xyw(1, 9, 3));

        frame.add(builder.getPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Usuarios::new);
    }
}//CIERRE USUARIOS