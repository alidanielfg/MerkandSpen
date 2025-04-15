/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormSolicitarArti extends javax.swing.JFrame {
    private CRUD crud;
    private int idUsuarioActual;
    private DefaultTableModel modeloTabla;
    
    
    public FormSolicitarArti(int userId) throws SQLException {       
        this.idUsuarioActual = userId;
        initComponents();
        crud = new CRUD();
        
        // Inicializar el modelo de tabla
        modeloTabla = new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID Artículo", "Nombre", "Cantidad"}
        );
        jTable1.setModel(modeloTabla);
        
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
        btnAgregar = new javax.swing.JButton();
        comboArticulos = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnEnviar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
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

        btnAgregar.setBackground(new java.awt.Color(76, 84, 89));
        btnAgregar.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar artículo");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        comboArticulos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Artículo", "Nombre", "Cantidad"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnEnviar.setBackground(new java.awt.Color(153, 255, 153));
        btnEnviar.setText("Enviar solicitud");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 0, 51));
        btnEliminar.setText("Eliminar de pedido");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
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
                                .addComponent(btnAgregar)))
                        .addGap(0, 83, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(202, 202, 202))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addComponent(btnAgregar)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if(comboArticulos.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(this, "Seleccione un artículo", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String cantidadStr = txtCantidad.getText().trim();
    if(cantidadStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese la cantidad", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    try {
        int cantidad = Integer.parseInt(cantidadStr);
        if(cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String nombreArticulo = comboArticulos.getSelectedItem().toString();
        int idArticulo = obtenerIdArticulo(nombreArticulo);
        CRUD.Articulo articulo = crud.obtenerArticuloPorId(idArticulo);
        
        if(articulo == null) {
            JOptionPane.showMessageDialog(this, "Artículo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(articulo.getStock() < cantidad) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + articulo.getStock(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si ya existe en la tabla
        for(int i = 0; i < modeloTabla.getRowCount(); i++) {
            if((int) modeloTabla.getValueAt(i, 0) == idArticulo) {
                JOptionPane.showMessageDialog(this, "El artículo ya está en el pedido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        modeloTabla.addRow(new Object[]{idArticulo, nombreArticulo, cantidad});
        txtCantidad.setText("");
        
    } catch(NumberFormatException | SQLException e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnAgregarActionPerformed
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

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = jTable1.getSelectedRow();
        if(filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un artículo de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        modeloTabla.removeRow(filaSeleccionada);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        if(modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Agregue artículos al pedido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conexion = crud.getConexion();
            conexion.setAutoCommit(false);
            boolean error = false;
            
            for(int i = 0; i < modeloTabla.getRowCount(); i++) {
                int idArticulo = (Integer) modeloTabla.getValueAt(i, 0);
                int cantidad = (Integer) modeloTabla.getValueAt(i, 2);
                
                if(!crud.solicitarArticulo(idUsuarioActual, idArticulo, String.valueOf(cantidad))) {
                    error = true;
                    break;
                }
            }
            
            if(error) {
                conexion.rollback();
                JOptionPane.showMessageDialog(null, "Error al procesar el pedido", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                conexion.commit();
                JOptionPane.showMessageDialog(null, "Pedido enviado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                modeloTabla.setRowCount(0);
            }
        } catch(SQLException ex) {
            try { crud.getConexion().rollback(); } catch(SQLException e) {}
            JOptionPane.showMessageDialog(null, "Error en la transacción: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { crud.getConexion().setAutoCommit(true); } catch(SQLException ex) {}
        }
    }//GEN-LAST:event_btnEnviarActionPerformed
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
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
                    FormSolicitarArti form = new FormSolicitarArti(userId);
                    form.setVisible(true);
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
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JMenuItem btnInventario;
    private javax.swing.JMenuItem btnPerfil;
    private javax.swing.JComboBox<String> comboArticulos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables
}
