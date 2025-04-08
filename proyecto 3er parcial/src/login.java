import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class login extends javax.swing.JFrame {    
     //constructor del jframe
    public login() {
        initComponents();
        verificarConexion();
        cargarDepartamentos(); // Llamada al nuevo método
        setLocationRelativeTo(null);
    }//fin jframe

    private void cargarDepartamentos() {
        try{
            // Limpiar cualquier item existente
            opcionesBox.removeAllItems();

            //Sacar departamentos desde DB
            List<String> departamentos = obtenerDepartamentosDesdeDB();

            if(departamentos.isEmpty()){
                JOptionPane.showMessageDialog(null,"No se encontro departamentos en la base de datos","Advertencia",JOptionPane.WARNING_MESSAGE);
                return;
            }
            for(String departamento : departamentos){
                opcionesBox.addItem(departamento);
            }

            if(departamentos.contains("Almacén")){
                opcionesBox.setSelectedItem("Almacén");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error al cargar departamentos: "+e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    }//FIN CARGARDEPARTAMENTOS
    
    private List<String> obtenerDepartamentosDesdeDB() throws SQLException {
        List<String> departamentos = new ArrayList<>();
        Connection conexion = ConexionDB.conectar();
        
        if(conexion == null) {
            throw new SQLException("No se pudo conectar a la base de datos");
        }
        
        String sql = "SELECT nombre FROM departamentos ORDER BY nombre";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while(rs.next()) {
                departamentos.add(rs.getString("nombre"));
            }
        } finally {
            if(conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        }
        
        return departamentos;
    }
    
    public void actualizarListaDepartamentos() {
        cargarDepartamentos();
    }
    
    private void verificarConexion(){ //Método para realizar la verificacion la conexion de la base de datos a el programa
        
        Connection conexion = ConexionDB.conectar();
        
        if(conexion != null){
            JOptionPane.showMessageDialog(this,"Conectado","Conexion",JOptionPane.INFORMATION_MESSAGE);
        }
        else{
           JOptionPane.showMessageDialog(this,"No se pudo Conectar","Conexion",JOptionPane.ERROR); 
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
    
    // Validación básica
    if(password.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "La contraseña no puede estar vacía", 
            "Validación", JOptionPane.WARNING_MESSAGE);
        txtPassword.requestFocus();
        return;
    }
    
    // Autenticación
    Object[] resultado = Autenticador.autenticar(departamento, password);
    
    if(Boolean.TRUE.equals(resultado[0])) {
        String rol = (String) resultado[1];
        int userId = (int) resultado[2];
        
        // Mostrar interfaz según rol
        abrirInterfazSegunRol(rol, userId, departamento);
        this.dispose();
        
    } else if(Boolean.FALSE.equals(resultado[0])) {
        JOptionPane.showMessageDialog(this, 
            "Credenciales incorrectas", 
            "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        txtPassword.setText("");
        txtPassword.requestFocus();
    }
    // El caso "error" ya se maneja en la clase Autenticador
}

private void abrirInterfazSegunRol(String rol, int userId, String departamento) {
    switch(rol.toLowerCase()) {
        case "admin":
        {
            try {
                new interfazAdmin().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            break;

        case "usuario":
            new interfazUsuario().setVisible(true);
            break;
        default:
            JOptionPane.showMessageDialog(this, 
                "Rol no reconocido: " + rol, 
                "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnIngresarActionPerformed

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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
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
