/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class FormSolicitarArti extends javax.swing.JFrame {
    private CRUD crud;
    private int idUsuarioActual;
    
    
    public FormSolicitarArti(int userId) throws SQLException {       
        this.idUsuarioActual = userId;
        initComponents();
        crud = new CRUD();
    try {
        Sesion sesion = Sesion.getInstance();
        
        if (!sesion.isActiva() || sesion.getUserId() != userId) {
            JOptionPane.showMessageDialog(null, 
                "Sesión no válida o expirada", 
                "Error de sesión", JOptionPane.ERROR_MESSAGE);
            sesion.cerrarSesion();
            new login().setVisible(true);
            this.dispose();
            return;
        }

        cargarArticulos();
        setLocationRelativeTo(null);
        
    } catch (SQLException ex) {
        Logger.getLogger(FormSolicitarArti.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null,
            "Error de conexión con la base de datos",
            "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
    
    private void cargarArticulos() {
        comboArticulos.removeAllItems();
        comboArticulos.addItem("-- Seleccione un artículo --");
        
        try {
            for (CRUD.Articulo articulo : crud.obtenerArticulos()) {
                comboArticulos.addItem(articulo.getNombre());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar artículos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnPedirArti = new javax.swing.JButton();
        comboArticulos = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        btnInventario = new javax.swing.JMenuItem();
        btnPerfil = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel1.setText("Solicitar Articulo ");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel3.setText("Articulo: ");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel2.setText("Cantidad:");

        btnPedirArti.setBackground(new java.awt.Color(76, 84, 89));
        btnPedirArti.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        btnPedirArti.setForeground(new java.awt.Color(255, 255, 255));
        btnPedirArti.setText("Pedir Articulo");
        btnPedirArti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedirArtiActionPerformed(evt);
            }
        });

        comboArticulos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jMenuBar1.setBackground(new java.awt.Color(76, 84, 89));

        jMenu6.setText("Merk and Spen");

        btnInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/proveedor-alternativo.png"))); // NOI18N
        btnInventario.setText("Inventario");
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });
        jMenu6.add(btnInventario);

        btnPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/deshacer.png"))); // NOI18N
        btnPerfil.setText("Regresar");
        btnPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerfilActionPerformed(evt);
            }
        });
        jMenu6.add(btnPerfil);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(202, 202, 202))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(comboArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(btnPedirArti)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnPedirArti)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPedirArtiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedirArtiActionPerformed
         if(comboArticulos.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un artículo", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String articulo = comboArticulos.getSelectedItem().toString();
    String cantidad = txtCantidad.getText().trim();
    
    if(cantidad.isEmpty()) {
        JOptionPane.showMessageDialog(this, "La cantidad es obligatoria", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
        try {
            int cant = Integer.parseInt(cantidad);
            if(cant <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idArticulo = obtenerIdArticulo(articulo);
            if(idArticulo == -1) {
                JOptionPane.showMessageDialog(this, "Error al obtener artículo", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar stock antes de mostrar mensaje de éxito
            CRUD.Articulo art = crud.obtenerArticuloPorId(idArticulo);
            if (art == null || art.getStock() < cant) {
                JOptionPane.showMessageDialog(this, 
                    "Stock insuficiente. Disponible: " + (art != null ? art.getStock() : 0), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean status = crud.solicitarArticulo(idUsuarioActual, idArticulo, cantidad);

            if (status) {
                JOptionPane.showMessageDialog(this, "Artículo solicitado correctamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                cargarArticulos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al solicitar artículo: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPedirArtiActionPerformed
private int obtenerIdArticulo(String nombreArticulo) {
        try {
            for (CRUD.Articulo articulo : crud.obtenerArticulos()) {
                if (articulo.getNombre().equals(nombreArticulo)) {
                    return articulo.getId();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        try {
            new InventarioUsua().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(FormSolicitarArti.class.getName()).log(Level.SEVERE, null, ex);
        }
       this.dispose();
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilActionPerformed
        try {
            new interfazUsuario().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(FormSolicitarArti.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btnPerfilActionPerformed
private void limpiar(){
        txtCantidad.setText("");
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
            java.util.logging.Logger.getLogger(FormSolicitarArti.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormSolicitarArti.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormSolicitarArti.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormSolicitarArti.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                
                int userId = sesion.getUserId();
               
                // Mostrar formulario con el ID de usuario válido
                new FormSolicitarArti(userId).setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(FormSolicitarArti.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null,
                    "Error al iniciar el formulario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnInventario;
    private javax.swing.JButton btnPedirArti;
    private javax.swing.JMenuItem btnPerfil;
    private javax.swing.JComboBox<String> comboArticulos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables
}
