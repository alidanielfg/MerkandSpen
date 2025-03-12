import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Decision Interface");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Elige una acción:");
        userLabel.setBounds(10, 20, 160, 25);
        panel.add(userLabel);

        String[] options = {"Ingresar", "Nuevo Usuario", "Inventario", "Solicitudes", "Salir"};
        JComboBox<String> actionList = new JComboBox<>(options);
        actionList.setBounds(180, 20, 160, 25);
        panel.add(actionList);

        JButton actionButton = new JButton("Ejecutar");
        actionButton.setBounds(10, 80, 100, 25);
        panel.add(actionButton);

        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(10, 110, 300, 25);
        panel.add(resultLabel);

        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean continueLoop = true;  // Declaración para salir del bucle while
                while (continueLoop) {
                    String selectedAction = (String) actionList.getSelectedItem();
                    switch (selectedAction) {
                        case "Ingresar":
                            Usuarios login = new Usuarios();
                            resultLabel.setText("Has elegido Ingresar");
                            break;
                        case "Nuevo Usuario":
                            NuevoUsuario newusuario = new NuevoUsuario();
                            resultLabel.setText("Has elegido Nuevo Usuario");
                            break;
                        case "Inventario":
                            Inventario inventario = new Inventario();
                            resultLabel.setText("Has elegido Inventario");
                            break;
                        case "Solicitudes":
                            Solicitudes solicitud = new Solicitudes();
                            resultLabel.setText("Has elegido Solicitudes");
                            break;
                        case "Salir":
                            System.exit(0);
                    }
                    // Preguntar al usuario si desea realizar otra acción
                    int response = JOptionPane.showConfirmDialog(panel, "¿Deseas realizar otra acción?", "Continuar", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.NO_OPTION) {
                        continueLoop = false;
                    }
                }
            }
        });

        frame.setVisible(true);
    }
}