import javax.swing.JOptionPane;

public class FormAdmiSoli extends javax.swing.JFrame {
   private String nombreSeleccionado;
   private String articuloSeleccionado;
   private String cantidadSeleccionada;
    public FormAdmiSoli() {
        initComponents();
        //Modificar los listeners para usar lambdas
        nombreSeleccionado = jNombre.getSelectedItem().toString();
        articuloSeleccionado = jArtículos.getSelectedItem().toString();
        cantidadSeleccionada = jCantidad.getSelectedItem().toString();
    }
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jAprobar_Solicitud = new javax.swing.JButton();
        jRechazar_solicitud = new javax.swing.JButton();
        jMarcar_como_entregado = new javax.swing.JButton();
        jNombre = new javax.swing.JComboBox<>();
        jArtículos = new javax.swing.JComboBox<>();
        jCantidad = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Administración de Solicitudes");

        jLabel2.setText("Nombre");

        jLabel3.setText("Artículo");

        jLabel4.setText("Cantidad");

        jAprobar_Solicitud.setBackground(new java.awt.Color(0, 153, 102));
        jAprobar_Solicitud.setText("Aprobar Solicitud");
        jAprobar_Solicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAprobar_SolicitudActionPerformed(evt);
            }
        });

        jRechazar_solicitud.setBackground(new java.awt.Color(102, 102, 102));
        jRechazar_solicitud.setText("Rechazar Solicitud");
        jRechazar_solicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRechazar_solicitudActionPerformed(evt);
            }
        });

        jMarcar_como_entregado.setBackground(new java.awt.Color(153, 153, 153));
        jMarcar_como_entregado.setText("Marca como entregado");
        jMarcar_como_entregado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMarcar_como_entregadoActionPerformed(evt);
            }
        });

        jNombre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jArtículos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCantidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jMenuBar1.setBackground(new java.awt.Color(102, 153, 0));

        jMenu1.setText("Merk and Spen");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Inventario");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Solicitud");
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Peril");
        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jAprobar_Solicitud)
                                .addGap(18, 18, 18)
                                .addComponent(jRechazar_solicitud)
                                .addGap(18, 18, 18)
                                .addComponent(jMarcar_como_entregado))
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jArtículos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jArtículos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jAprobar_Solicitud)
                    .addComponent(jRechazar_solicitud)
                    .addComponent(jMarcar_como_entregado))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void jAprobar_SolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAprobar_SolicitudActionPerformed
        String mensaje = String.format("Solicitud arobada:\nNombre: %s\nArtículo: %s\nCantidad: %s", nombreSeleccionado, articuloSeleccionado, cantidadSeleccionada);
        JOptionPane.showMessageDialog(this,mensaje,"Solicitud Aprobada",JOptionPane.INFORMATION_MESSAGE);
        
    }//GEN-LAST:event_jAprobar_SolicitudActionPerformed

    private void jRechazar_solicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRechazar_solicitudActionPerformed
        String mensaje = String.format("Solicitud rechazada:\nNombre: %s\nArtículo: &s\nCantidad: &s",
        nombreSeleccionado,articuloSeleccionado,cantidadSeleccionada);
        JOptionPane.showMessageDialog(this,mensaje,"Solicitud Rechazada",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_jRechazar_solicitudActionPerformed

    private void jMarcar_como_entregadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMarcar_como_entregadoActionPerformed
        String mensaje = String.format("Artículo marcado como entregado:'\nNombre: %s\nArtículo: %s\nCantidad: %s",
                nombreSeleccionado,articuloSeleccionado,cantidadSeleccionada);
        JOptionPane.showMessageDialog(this,mensaje,"AArtículo Entregado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMarcar_como_entregadoActionPerformed

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

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormAdmiSoli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAprobar_Solicitud;
    private javax.swing.JComboBox<String> jArtículos;
    private javax.swing.JComboBox<String> jCantidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jMarcar_como_entregado;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JComboBox<String> jNombre;
    private javax.swing.JButton jRechazar_solicitud;
    // End of variables declaration//GEN-END:variables
}
