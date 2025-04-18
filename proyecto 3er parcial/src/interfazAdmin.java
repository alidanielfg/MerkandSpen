import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author alida
 */
public class interfazAdmin extends javax.swing.JFrame {
    private CRUD crud;
    private int userId; // Nuevo atributo
    private String departamento;

    // Constructor modificado
    public interfazAdmin() throws SQLException {
    Sesion sesion = Sesion.getInstance();
    
    try {
        // Verificar sesión y rol
        if (!sesion.isActiva() || !"admin".equalsIgnoreCase(sesion.getRol())) {
            JOptionPane.showMessageDialog(null, 
                "Acceso no autorizado. Se requiere rol de administrador.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            sesion.cerrarSesion();
            new login().setVisible(true);
            this.dispose();
            return;
        }
        
        this.userId = sesion.getUserId();
        this.departamento = sesion.getDepartamento();
        this.crud = new CRUD();
        
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Administrador: " + departamento);
    } catch (IllegalStateException e) {
        JOptionPane.showMessageDialog(null, 
            "Sesión no válida. Redirigiendo al login...", 
            "Error", JOptionPane.ERROR_MESSAGE);
        new login().setVisible(true);
        this.dispose();
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnPerfil = new javax.swing.JToggleButton();
        btnCierre = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        btnInventario = new javax.swing.JButton();
        btnAdminUsua = new javax.swing.JButton();
        btnPedidos = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(137, 166, 124));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 2, 24)); // NOI18N
        jLabel1.setText("Bienvenido Administrador");

        btnPerfil.setText("Perfil");
        btnPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerfilActionPerformed(evt);
            }
        });

        btnCierre.setText("Cerrar Sesión");
        btnCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCierreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(246, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(212, 212, 212))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPerfil)
                    .addComponent(btnCierre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel2.setText("Que actividad realizaremos el dia de hoy?");

        btnInventario.setText("Consulta  de inventario");
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });

        btnAdminUsua.setText("Administrar Usuarios");
        btnAdminUsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminUsuaActionPerformed(evt);
            }
        });

        btnPedidos.setText("Administrar Pedidos");
        btnPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedidosActionPerformed(evt);
            }
        });

        jButton1.setText("Registrar Artículo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(245, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(220, 220, 220))
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(btnAdminUsua, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInventario, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addComponent(btnPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdminUsua, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdminUsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminUsuaActionPerformed
        try {
            new FormAdminUsuarios(0).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(interfazAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btnAdminUsuaActionPerformed

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        try {
            new Inventario().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(interfazAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilActionPerformed
                                     
        try {
            new FromPerfil().setVisible(true); // Pasar userId y crud
        } catch (SQLException ex) {
            Logger.getLogger(interfazAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPerfilActionPerformed

    private void btnCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCierreActionPerformed
    try {
        crud.cerrarConexion();

        this.dispose();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error al cerrar la aplicación: " + e.getMessage(),
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    
        try {
            new login().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(interfazAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCierreActionPerformed

    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        try {
            new FormAdmiSoli().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(interfazAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btnPedidosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            new FormRegistroArticulo().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(interfazAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(interfazAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(interfazAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(interfazAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(interfazAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                // Verificar sesión
                Sesion sesion = Sesion.getInstance();
                if (!sesion.isActiva()) {
                    JOptionPane.showMessageDialog(null,
                        "Debe iniciar sesión primero",
                        "Sesión requerida", JOptionPane.WARNING_MESSAGE);
                    new login().setVisible(true);
                    return;
                }
                
                // Verificar rol de administrador
                if (!"admin".equalsIgnoreCase(sesion.getRol())) {
                    JOptionPane.showMessageDialog(null,
                        "Acceso restringido a administradores",
                        "Permiso denegado", JOptionPane.ERROR_MESSAGE);
                    new interfazUsuario().setVisible(true); // Redirigir a interfaz de usuario
                    return;
                }
                
                // Si todo está bien, mostrar la interfaz
                new interfazAdmin().setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(interfazAdmin.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null,
                    "Error al iniciar la interfaz: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdminUsua;
    private javax.swing.JToggleButton btnCierre;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnPedidos;
    private javax.swing.JToggleButton btnPerfil;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
