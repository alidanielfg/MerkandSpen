import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

public class Inventario extends javax.swing.JFrame {
    private CRUD crud;
    private Timer timerActualizacion;
    private ChartPanel chartPanel;
    private ArticuloDAO articuloDAO;
    
    public Inventario() throws SQLException {
        initComponents();
        crud = new CRUD();
        initGrafica();
        iniciarActualizacionAutomatica();
        llenarTabla();
        setLocationRelativeTo(null);
        articuloDAO = new ArticuloDAO(crud.getConexion());
        
        opcionDescarga.setModel(new DefaultComboBoxModel<>(new String[] {
            "Descargar reporte...",
            "Inventario Completo",
            "Pedidos por Departamento",
            "Pedidos por Fechas"
        }));
    }
    
    void llenarTabla() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setRowCount(0);
            
            String sql = "SELECT a.id, a.nombre, a.descripcion, a.cantidad, a.id_categoria, i.stock " +
                         "FROM articulos a JOIN inventarios i ON a.id = i.id_articulo ORDER BY a.nombre";
            
            try (PreparedStatement ps = crud.getConexion().prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getInt("stock")
                    });               
                }
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar inventario: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void initGrafica() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
    JFreeChart chart = ChartFactory.createBarChart(
        "5 Artículos más solicitados",
        "Artículos",  // Cambiar eje X
        "Cantidad de solicitudes",  // Cambiar eje Y
        dataset,
        PlotOrientation.HORIZONTAL,
        true,  // Mostrar leyenda
        true,
        false
    );
    
    // Configurar renderer para mostrar nombres
    CategoryPlot plot = chart.getCategoryPlot();
    plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    
    // Desactivar que todas las barras usen el mismo color (rojo por defecto)
    renderer.setDrawBarOutline(true);
    renderer.setShadowVisible(false);
    
    // Asignar colores diferentes a cada ítem
    renderer.setSeriesPaint(0, new Color(65, 105, 225));   // Azul real
    renderer.setSeriesPaint(1, new Color(50, 205, 50));    // Lima
    renderer.setSeriesPaint(2, new Color(255, 140, 0));    // Naranja oscuro
    renderer.setSeriesPaint(3, new Color(153, 50, 204));   // Púrpura
    renderer.setSeriesPaint(4, new Color(0, 139, 139));    // Cyan oscuro
    
    // Aplicar los mismos colores por categoría individual
    renderer.setDrawBarOutline(true);
    renderer.setItemMargin(0.1);
    
    chartPanel = new ChartPanel(chart);
    chartPanel.setMouseWheelEnabled(true);
    chartPanel.setPreferredSize(new Dimension(650, 200));
    
    jPanel1.setLayout(new BorderLayout());
    jPanel1.add(chartPanel, BorderLayout.NORTH);
    
    pack();
    repaint();
    
    // Cargar datos iniciales
    try {
        actualizarGrafica();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al cargar datos de la gráfica: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private Map<String, Color> coloresArticulos = new HashMap<>();
private Color[] paletaColores = {
    new Color(255, 0, 0),    // Rojo
    new Color(0, 255, 0),    // Verde
    new Color(0, 0, 255),    // Azul
    new Color(255, 255, 0),  // Amarillo
    new Color(255, 0, 255)   // Magenta
};

private void actualizarGrafica() throws SQLException {
    DefaultCategoryDataset nuevosDatos = crud.obtenerTopSolicitudes();
    CategoryPlot plot = chartPanel.getChart().getCategoryPlot();
    
    // Mantener colores existentes y asignar nuevos
    for (int i = 0; i < nuevosDatos.getRowCount(); i++) {
        String articulo = (String) nuevosDatos.getRowKey(i);
        if (!coloresArticulos.containsKey(articulo)) {
            int colorIndex = coloresArticulos.size() % paletaColores.length;
            coloresArticulos.put(articulo, paletaColores[colorIndex]);
        }
    }
    
    BarRenderer renderer = new BarRenderer();
    for (int i = 0; i < plot.getDataset().getRowCount(); i++) {
        renderer.setSeriesPaint(i, coloresArticulos.get(plot.getDataset().getRowKey(i)));
    }
    
    plot.setDataset(nuevosDatos);
    plot.setRenderer(renderer);
}
    
    private void iniciarActualizacionAutomatica() {
    timerActualizacion = new Timer(5000, e -> {
        try {
            // Solo actualiza datos sin resetear la gráfica
            actualizarGrafica();
            chartPanel.repaint();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    });
    timerActualizacion.start();
}
    
    @Override
    public void dispose() {
        if(timerActualizacion != null) timerActualizacion.stop();
        crud.cerrarConexion();
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
        jPanel1 = new javax.swing.JPanel();
        opcionDescarga = new javax.swing.JComboBox<>();
        btnEliminar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        Opciones = new javax.swing.JMenu();
        btnUsuarios = new javax.swing.JMenuItem();
        btnPedidos = new javax.swing.JMenuItem();
        btnVolver = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        opcionDescarga.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        opcionDescarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionDescargaActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar Articulo");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar Artículo");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        jMenuBar1.setBackground(new java.awt.Color(51, 51, 255));

        jMenu4.setText("Merk and Spen");
        jMenuBar1.add(jMenu4);

        Opciones.setText("Opciones");

        btnUsuarios.setText("Administrar Usuario");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        Opciones.add(btnUsuarios);

        btnPedidos.setText("Administrar Pedidos");
        btnPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedidosActionPerformed(evt);
            }
        });
        Opciones.add(btnPedidos);

        btnVolver.setText("Regresar");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        Opciones.add(btnVolver);

        jMenuBar1.add(Opciones);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(358, 358, 358))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtArticulo)
                            .addComponent(btnBuscarNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(opcionDescarga, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(43, 43, 43))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarNombre)
                        .addGap(18, 18, 18)
                        .addComponent(opcionDescarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarNombreActionPerformed
    String nombreBusqueda = txtArticulo.getText().trim();
            
        if(nombreBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Debe ingresar un nombre para buscar", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setRowCount(0);
            
            boolean encontrado = false;
            for (CRUD.Articulo articulo : crud.buscarArticulosPorNombre(nombreBusqueda)) {
                encontrado = true;
                modelo.addRow(new Object[]{
                    articulo.getId(),
                    articulo.getNombre(),
                    articulo.getDescripcion(),
                    articulo.getCantidad(),
                    articulo.getStock()
                });
            }
            
            if(!encontrado) {
                JOptionPane.showMessageDialog(this, 
                    "No se encontraron artículos con ese nombre", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al buscar en la base de datos: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
        limpiar();
    }//GEN-LAST:event_btnBuscarNombreActionPerformed

    private void opcionDescargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionDescargaActionPerformed
        String seleccion = (String) opcionDescarga.getSelectedItem();
        
        if (seleccion == null || seleccion.equals("Descargar reporte...")) {
            return;
        }
        
        switch (seleccion) {
            case "Inventario Completo":
                crud.generarReporteInventarioCompleto();
                break;
            case "Pedidos por Departamento":
                crud.generarReportePedidosPorDepartamento();
                break;
            case "Pedidos por Fechas":
                JTextField fechaInicioField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JTextField fechaFinField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                
                Object[] message = {
                    "Fecha Inicio (yyyy-MM-dd):", fechaInicioField,
                    "Fecha Fin (yyyy-MM-dd):", fechaFinField
                };
                
                int option = JOptionPane.showConfirmDialog(this, message, "Seleccione el rango de fechas", 
                    JOptionPane.OK_CANCEL_OPTION);
                
                if (option == JOptionPane.OK_OPTION) {
                    crud.generarReportePedidosPorFechas(fechaInicioField, fechaFinField);
                }
                break;
        }
        
        opcionDescarga.setSelectedIndex(0);
    }//GEN-LAST:event_opcionDescargaActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        try {
            new FormAdminUsuarios(0).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        try {
            new FormAdmiSoli().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
       this.dispose();
    }//GEN-LAST:event_btnPedidosActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        try {
            new interfazAdmin().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = jTable1.getSelectedRow();
        
        if(filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, 
                "Por favor seleccione un artículo para eliminar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idArticulo = (int) jTable1.getValueAt(filaSeleccionada, 0);
        String nombreArticulo = (String) jTable1.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro que desea eliminar el artículo: " + nombreArticulo + "?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if(confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        if(crud.eliminarArticulo(idArticulo)) {
            JOptionPane.showMessageDialog(null, 
                "Artículo eliminado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            llenarTabla();
        } else {
            JOptionPane.showMessageDialog(null, 
                "Error al eliminar el artículo", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int filaSeleccionada = jTable1.getSelectedRow();
    
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un artículo para editar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        int idArticulo = (int) jTable1.getValueAt(filaSeleccionada, 0);
        FormEditarArti formEditar = new FormEditarArti(idArticulo, this, crud.getConexion());
        formEditar.setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void limpiar() {
    txtArticulo.setText("");
}

public static void main(String args[]) {
     java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                // Verificar sesión
                Sesion sesion = Sesion.getInstance();
                if (!sesion.isActiva()) {
                    JOptionPane.showMessageDialog(null,
                        "Acceso restringido a usuarios autenticados",
                        "Sesión requerida", JOptionPane.WARNING_MESSAGE);
                    new login().setVisible(true);
                    return;
                }
                
                // Verificar permisos (solo admin o almacén)
                String rol = sesion.getRol();
                if (!"admin".equalsIgnoreCase(rol) && !"almacen".equalsIgnoreCase(rol)) {
                    JOptionPane.showMessageDialog(null,
                        "No tiene permisos para acceder al inventario",
                        "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                        
                    // Redirigir según rol
                    if ("usuario".equalsIgnoreCase(rol)) {
                        new interfazUsuario().setVisible(true);
                    } else {
                        new login().setVisible(true);
                    }
                    return;
                }
                
                // Si todo está bien, mostrar la interfaz
                new Inventario().setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null,
                    "Error al iniciar el inventario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Opciones;
    private javax.swing.JButton btnBuscarNombre;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JMenuItem btnPedidos;
    private javax.swing.JMenuItem btnUsuarios;
    private javax.swing.JMenuItem btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> opcionDescarga;
    private javax.swing.JTextField txtArticulo;
    // End of variables declaration//GEN-END:variables
}