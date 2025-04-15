import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class login extends javax.swing.JFrame {
    private final DepartamentoDAO departamentoDAO;
    private final Autenticador autenticador;
    
    public login() throws SQLException {
        this.departamentoDAO = new DepartamentoDAO();
        this.autenticador = new Autenticador();
        initComponents();
        verificarConexion();
        cargarDepartamentos();
        setLocationRelativeTo(null);
    }
    
    private void cargarDepartamentos() {
        try {
            opcionesBox.removeAllItems();
            List<String> departamentos = departamentoDAO.obtenerTodosDepartamentos();

            if(departamentos.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "No se encontraron departamentos en la base de datos",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            for(String departamento : departamentos) {
                opcionesBox.addItem(departamento);
            }

            if(departamentos.contains("Almacén")) {
                opcionesBox.setSelectedItem("Almacén");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al cargar departamentos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void verificarConexion() {
    try {
        Connection testConn = ConexionDB.conectar();
        if (testConn != null && !testConn.isClosed()) {
            System.out.println("Conexión establecida correctamente");
            testConn.close();
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "No se pudo conectar a la base de datos: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        System.exit(1);
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnIngresar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        opcionesBox = new javax.swing.JComboBox<>();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(166, 118, 118));

        jPanel1.setBackground(new java.awt.Color(137, 166, 124));

        jLabel1.setFont(new java.awt.Font("STXihei", 3, 36)); // NOI18N
        jLabel1.setText("Merk and Spen");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(166, 118, 118));

        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel2.setText("Departamento");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setText("Contraseña");

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        opcionesBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        opcionesBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionesBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(opcionesBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addComponent(opcionesBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void opcionesBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionesBoxActionPerformed
    String departamentoSeleccionado = (String)opcionesBox.getSelectedItem();
    }//GEN-LAST:event_opcionesBoxActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
    String password = new String(txtPassword.getPassword()).trim();
        String departamento = (String) opcionesBox.getSelectedItem();
        
        if(validarCampos(password, departamento)) {
            Autenticador.ResultadoAutenticacion resultado = autenticador.autenticar(departamento, password);
            procesarResultadoAutenticacion(resultado);
        }
    }
    
    private boolean validarCampos(String password, String departamento) {
        if(departamento == null || departamento.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un departamento", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if(password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "La contraseña no puede estar vacía", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocus();
            return false;
        }
        return true;
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void procesarResultadoAutenticacion(Autenticador.ResultadoAutenticacion resultado) {
    if(resultado.isError()) {
        JOptionPane.showMessageDialog(this, 
            "Error de conexión con la base de datos", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if(resultado.isAutenticado()) {
        try {
            Sesion sesion = Sesion.getInstance();
            sesion.crearSesion(
                resultado.getUserId(), 
                resultado.getDepartamento(), 
                resultado.getRol()
            );
            
            abrirInterfazSegunRol();
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear la sesión: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, 
            "Departamento o contraseña incorrectos", 
            "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        txtPassword.setText("");
        txtPassword.requestFocus();
    }
}

    
    private void abrirInterfazSegunRol() {
    try {
        Sesion sesion = Sesion.getInstance();
        String rol = sesion.getRol().toLowerCase();
        
        switch(rol) {
            case "admin":
                new interfazAdmin().setVisible(true);
                break;
            case "usuario":
                new interfazUsuario().setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, 
                    "Rol no configurado: " + rol, 
                    "Error", JOptionPane.ERROR_MESSAGE);
                sesion.cerrarSesion();
                new login().setVisible(true);
        }
    } catch (Exception ex) {
        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this,
            "Error al cargar la interfaz: " + ex.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    @Override
    public void dispose() {
        try {
            departamentoDAO.cerrarConexion();
            autenticador.cerrarConexion();
        } finally {
            super.dispose();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (Exception ex) {
        System.err.println("Error al configurar el look and feel: " + ex.getMessage());
    }

    // Ejecución segura de la aplicación
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                // Verificar conexión antes de crear el login
                Connection testConn = ConexionDB.conectar();
                if (testConn != null) {
                    testConn.close();
                }
                
                new login().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Error al iniciar la aplicación: " + e.getMessage(),
                    "Error crítico", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
        }
    });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> opcionesBox;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
