import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

 /*
 * @author lozan
 */
public class FormAdmiSoli extends javax.swing.JFrame {
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private CRUD crud;
    
    public FormAdmiSoli(){
        initComponents();
        setLocationRelativeTo(null);
        crud = new CRUD();
        cargarPedidos();
    }
private void cargarPedidos(){
    DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
    model.setRowCount(0);
    
    try{
        ResultSet rs = crud.obtenerPedidos();
        while(rs.next()){
            Object[] row={
              rs.getInt("id"),
              rs.getString("departamento"),
              rs.getString("articulo"),
              "",
              rs.getString("estatus")
            };/*cierre object*/
            model.addRow(row);
        }//CIERRE WHILE
    }catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Error al cargar pedidos: "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
    }
}//CIERRE CARGARPEDIDOS

private boolean actualizarEstatus(int idPedido, String nuevoEstatus){
    String sql = "UPDATE pedidos SET id_estatus = (SELECT id FROM estatus WHERE nombre = ?) WHERE id = ?";
    try (PreparedStatement ps = crud.conexion.prepareStatement(sql)){
        ps.setString(1, nuevoEstatus);
        ps.setInt(2, idPedido);
        
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas>0;
    }catch(SQLException e){
        System.out.println("Error al actualiza Estatus: "+e.getMessage());
        return false;
    }
}//FIN ACTUALIZAR
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAceptar = new javax.swing.JButton();
        btnRechazar = new javax.swing.JButton();
        btnCompletado = new javax.swing.JButton();
        txtSolicitud = new javax.swing.JTextField();
        btnBuscarSoli = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Administración de Solicitudes");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "idPedido", "Departamento", "Artículo", "Cantidad", "Estatus"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnAceptar.setBackground(new java.awt.Color(0, 102, 0));
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setText("Aceptar Solicitud");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnRechazar.setBackground(new java.awt.Color(102, 102, 0));
        btnRechazar.setForeground(new java.awt.Color(255, 255, 255));
        btnRechazar.setText("Rechazar Solicitud");
        btnRechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechazarActionPerformed(evt);
            }
        });

        btnCompletado.setBackground(new java.awt.Color(102, 102, 102));
        btnCompletado.setForeground(new java.awt.Color(255, 255, 255));
        btnCompletado.setText("Marcar como Completado");
        btnCompletado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompletadoActionPerformed(evt);
            }
        });

        btnBuscarSoli.setText("Buscar Solicitud");
        btnBuscarSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarSoliActionPerformed(evt);
            }
        });

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jMenu1.setText("Merk and Spen");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnAceptar)
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSolicitud)
                            .addComponent(btnBuscarSoli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRechazar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addComponent(btnCompletado)))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnRegresar))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnRegresar)
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarSoli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnRechazar)
                    .addComponent(btnCompletado))
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
         int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un pedido", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idPedido = (int) jTable1.getValueAt(filaSeleccionada, 0);
        if (actualizarEstatus(idPedido, "Disponible para recoleccion")) {
            JOptionPane.showMessageDialog(this, "Pedido aceptado correctamente");
            cargarPedidos(); // Refrescar la tabla
        } else {
            JOptionPane.showMessageDialog(this, "Error al aceptar el pedido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnRechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechazarActionPerformed
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un pedido", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idPedido = (int) jTable1.getValueAt(filaSeleccionada, 0);
        if (actualizarEstatus(idPedido, "Artiuclos no disponibles")) {
            JOptionPane.showMessageDialog(this, "Pedido rechazado correctamente");
            cargarPedidos(); // Refrescar la tabla
        } else {
            JOptionPane.showMessageDialog(this, "Error al rechazar el pedido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRechazarActionPerformed

    private void btnCompletadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompletadoActionPerformed
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un pedido", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idPedido = (int) jTable1.getValueAt(filaSeleccionada, 0);
        if (actualizarEstatus(idPedido, "Entregado")) {
            JOptionPane.showMessageDialog(this, "Pedido marcado como completado");
            cargarPedidos(); // Refrescar la tabla
        } else {
            JOptionPane.showMessageDialog(this, "Error al completar el pedido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCompletadoActionPerformed

    private void btnBuscarSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarSoliActionPerformed
         String idPedido = txtSolicitud.getText().trim();
        if (idPedido.isEmpty()) {
            cargarPedidos(); // Si está vacío, cargar todos
            return;
        }
        
        try {
            ResultSet rs = crud.PedidoporID(idPedido);
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Limpiar la tabla
            
            if (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("departamento"),
                    rs.getString("articulo"),
                    "", // Cantidad no está en el ResultSet actual
                    rs.getString("estatus")
                };
                model.addRow(row);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el pedido con ID: " + idPedido, 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar pedido: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarSoliActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        try {
            new interfazAdmin().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(FormAdmiSoli.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

   
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
            java.util.logging.Logger.getLogger(FormAdmiSoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAdmiSoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAdmiSoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAdmiSoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try{
            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(ClassNotFoundException ex){
            java.util.logging.Logger.getLogger(FormAdmiSoli.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
        }catch(InstantiationException ex){
            java.util.logging.Logger.getLogger(FormAdmiSoli.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
        }catch(IllegalAccessException ex){
            java.util.logging.Logger.getLogger(FormAdmiSoli.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
        }catch(javax.swing.UnsupportedLookAndFeelException ex){
            java.util.logging.Logger.getLogger(FormAdmiSoli.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormAdmiSoli().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscarSoli;
    private javax.swing.JButton btnCompletado;
    private javax.swing.JButton btnRechazar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtSolicitud;
    // End of variables declaration//GEN-END:variables
}