import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.plot.PlotOrientation;
import javax.swing.table.DefaultTableModel;
import org.jfree.data.category.DefaultCategoryDataset;

public class InventarioUsu extends javax.swing.JFrame {

    private inventarioCRUD crud;
    private Timer timerActualizacion;
    private ChartPanel chartPanel;
    
    public InventarioUsu() {
        initComponents();
        crud= new inventarioCRUD();
        initGrafica();
        iniciarActualizacionAutomatica();
    }
    
    private void initGrafica(){
        JFreeChart chart = ChartFactory.createBarChart("5 Articulos mas solicitados","Articulos","Solicitudes",new DefaultCategoryDataset(),PlotOrientation.VERTICAL,true,true,false);
        
        chartPanel= new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(700,300));
        add(chartPanel,BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void actualizarGrafica() throws SQLException{
        DefaultCategoryDataset nuevosDatos = crud.obtenerTopSolicitudes();
        JFreeChart chart = chartPanel.getChart();
        chart.getCategoryPlot().setDataset(nuevosDatos);
    }
    
    private void iniciarActualizacionAutomatica(){
        timerActualizacion = new Timer(5000,e -> {
            try {
                actualizarGrafica();
            } catch (SQLException ex) {
                Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        timerActualizacion.start();
    }
    
    public void dispose(){
        if(timerActualizacion != null) timerActualizacion.stop();
        super.dispose();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtArticulo = new javax.swing.JTextField();
        btnBuscarNombre = new javax.swing.JButton();
        btnSolicitar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        btnUsuarios = new javax.swing.JMenu();
        btnSolicitudes = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 2, 24)); // NOI18N
        jLabel1.setText("Inventario");

        jTable1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Articulo", "Nombre", "Descripción", "Cantidad", "Stock"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnBuscarNombre.setText("Buscar por nombre");
        btnBuscarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarNombreActionPerformed(evt);
            }
        });

        btnSolicitar.setText("Solicitar Artículo");
        btnSolicitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolicitarActionPerformed(evt);
            }
        });

        jMenuBar1.setBackground(new java.awt.Color(51, 51, 255));

        jMenu4.setText("Merk and Spen");
        jMenuBar1.add(jMenu4);

        btnUsuarios.setText("Usuarios");
        jMenuBar1.add(btnUsuarios);

        btnSolicitudes.setText("Solicitudes");
        jMenuBar1.add(btnSolicitudes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(357, 357, 357))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtArticulo)
                            .addComponent(btnBuscarNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(btnSolicitar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(336, 336, 336))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSolicitar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarNombreActionPerformed
    // 1. Obtener el texto de búsqueda
    String nombreBusqueda = txtArticulo.getText().trim();
    
    // 2. Validar que no esté vacío
    if(nombreBusqueda.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Debe ingresar un nombre para buscar", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // 3. Realizar la búsqueda en la base de datos
    try {
        ResultSet resultados = crud.obtenerArticuloPorNombre(nombreBusqueda);
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0); // Limpiar la tabla antes de agregar nuevos resultados
        
        // 4. Llenar la tabla con los resultados
        boolean encontrado = false;
        while(resultados != null && resultados.next()) {
            encontrado = true;
            modelo.addRow(new Object[]{
                resultados.getInt("id"),
                resultados.getString("nombre"),
                resultados.getString("descripcion"),
                resultados.getInt("cantidad"),
                resultados.getInt("stock")
            });
        }
        
        // 5. Mostrar mensaje si no hay resultados
        if(!encontrado) {
            JOptionPane.showMessageDialog(this, 
                "No se encontraron artículos con ese nombre", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    } 
    catch(SQLException ex) {
        JOptionPane.showMessageDialog(this, 
            "Error al buscar en la base de datos: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    
    limpiar();
    }//GEN-LAST:event_btnBuscarNombreActionPerformed

    private void btnSolicitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolicitarActionPerformed
        new FormSolicitarArti().setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_btnSolicitarActionPerformed

    private void limpiar(){
        txtArticulo.setText("");
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
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarNombre;
    private javax.swing.JButton btnSolicitar;
    private javax.swing.JMenu btnSolicitudes;
    private javax.swing.JMenu btnUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtArticulo;
    // End of variables declaration//GEN-END:variables
}
