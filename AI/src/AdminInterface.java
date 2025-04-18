/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author alida
 */
// AdminInterface.java
public class AdminInterface extends JFrame {
    private ProductService productService;
    private JTextField productIdField;
    private JTextField productNameField;
    private JTextField priceField;
    private JTextField quantityField;
    
    public AdminInterface(ProductService productService) {
        this.productService = productService;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("Stationery Supply Management - Administrator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Add product panel
        JPanel addPanel = new JPanel(new GridLayout(5, 2));
        addPanel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        addPanel.add(productIdField);
        
        addPanel.add(new JLabel("Name:"));
        productNameField = new JTextField();
        addPanel.add(productNameField);
        
        addPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        addPanel.add(priceField);
        
        addPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        addPanel.add(quantityField);
        
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(e -> handleAddProduct());
        addPanel.add(addButton);
        
        // Statistics panel
        JPanel statsPanel = new JPanel();
        JButton viewStatsButton = new JButton("View Top Products");
        viewStatsButton.addActionListener(e -> handleViewStatistics());
        statsPanel.add(viewStatsButton);
        
        add(addPanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.PAGE_END);
        setSize(600, 400);
    }
    
    private void handleAddProduct() {
        try {
            String id = productIdField.getText();
            String name = productNameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            
            Product product = new Product(id, name, price, quantity);
            productService.addProduct(product);
            JOptionPane.showMessageDialog(this, "Product added successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleViewStatistics() {
        List<Product> topProducts = productService.getTopProducts(5);
        StringBuilder stats = new StringBuilder("Top 5 Most Requested Products:\n\n");
        topProducts.forEach(p -> stats.append(p.getName()).append("\n"));
        
        JTextArea area = new JTextArea(stats.toString(), 10, 30);
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        
        JDialog dialog = new JDialog(this, "Product Statistics", true);
        dialog.getContentPane().add(scrollPane);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void clearFields() {
        productIdField.setText("");
        productNameField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(AdminInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
