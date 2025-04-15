import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class FormPedidos extends javax.swing.JFrame {
    private final CRUD crud;
    private DefaultTableModel modeloTabla;
    private final int userId;
    private final String departamento;
    
    public FormPedidos(int userId) throws SQLException {
        Sesion sesion = Sesion.getInstance();
        this.userId = userId;
        this.departamento = sesion.getDepartamento();
        this.crud = new CRUD();
        
        initComponents();
        iniTabla();
        cargarPedidosPorDepartamento();
        setLocationRelativeTo(null);
        setTitle("Pedidos - " + departamento);
    }

    
    private void iniTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Integer.class : String.class;
            }
        };
        
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Artículo");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Estado");
        
        jTable1.setModel(modeloTabla);
    }
    
    private void cargarPedidosPorDepartamento() {
        try {
            modeloTabla.setRowCount(0);
            List<Map<String, Object>> pedidos = crud.obtenerPedidosPorDepartamento(departamento);
            
            for (Map<String, Object> pedido : pedidos) {
                modeloTabla.addRow(new Object[]{
                    pedido.get("id"),
                    pedido.get("articulo"),
                    pedido.get("cantidad"),
                    pedido.get("fecha_solicitud"),
                    pedido.get("estado")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormPedidos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, 
                "Error al cargar pedidos: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String obtenerDepartamentoUsuario(int idUsuario) throws SQLException {
        List<CRUD.Usuario> usuarios = crud.obtenerUsuarios();
        for (CRUD.Usuario usuario : usuarios) {
            if (usuario.getId() == idUsuario) {
                return usuario.getDepartamento();
            }
        }
        return null;
    }
    
    private void buscarPedido() {
        String idPedido = txtIdPedido.getText().trim();
        if (idPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese un ID de pedido", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(idPedido);
            modeloTabla.setRowCount(0);
            
            // Verificar si el pedido pertenece al departamento del usuario
            List<Map<String, Object>> pedidos = crud.obtenerPedidosPorId(id);
            
            if (!pedidos.isEmpty()) {
                Map<String, Object> pedido = pedidos.get(0);
                String departamentoPedido = (String) pedido.get("departamento");
                String departamentoUsuario = obtenerDepartamentoUsuario(userId);
                
                if (departamentoPedido.equals(departamentoUsuario)) {
                    modeloTabla.addRow(new Object[]{
                        pedido.get("id"),
                        pedido.get("articulo"),
                        pedido.get("cantidad"),
                        pedido.get("fecha_solicitud"),
                        pedido.get("estado")
                    });
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No tiene permisos para ver este pedido", 
                        "Acceso denegado", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró el pedido con ID: " + idPedido, 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El ID debe ser un número válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(FormPedidos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, 
                "Error al buscar pedido: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtIdPedido = new javax.swing.JTextField();
        btnRegresar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        jLabel1.setText("Adminstración de usuarios");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        jLabel2.setText("Historial de pedidos");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Departamento", "Articulo", "Estatus"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel3.setText("Id pedido:");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnRegresar))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(319, 319, 319)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(335, 335, 335)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnRegresar)
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIdPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        try {
            new interfazUsuario().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(FormPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
     buscarPedido();
    }//GEN-LAST:event_btnBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(FormPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Error setting look and feel: " + ex.getMessage());
        }

            java.awt.EventQueue.invokeLater(() -> {
            try {
                new login().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(FormPedidos.class.getName()).log(Level.SEVERE, null, ex);
            }
            });
        }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtIdPedido;
    // End of variables declaration//GEN-END:variables
}
