/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alida
 */
// ProductService.java
public class ProductService {
    private Map<String, Product> products;
    private List<Order> orders;
    
    public ProductService() {
        this.products = new HashMap<>();
        this.orders = new ArrayList<>();
    }
    
    public void addProduct(Product product) {
        if (products.containsKey(product.getId())) {
            throw new IllegalStateException("Product already exists");
        }
        products.put(product.getId(), product);
    }
    
    public Order placeOrder(String orderId, List<ProductQuantity> items) {
        Order order = new Order(orderId);
        
        for (ProductQuantity item : items) {
            Product product = products.get(item.getProductId());
            if (product == null || product.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException("Invalid product or insufficient stock");
            }
            
            order.addItem(product, item.getQuantity());
            // Update product quantity
            product.setQuantity(product.getQuantity() - item.getQuantity());
        }
        
        orders.add(order);
        return order;
    }
    
    public List<Product> getTopProducts(int limit) {
        return products.values().stream()
            .sorted((p1, p2) -> Integer.compare(
                getOrderCountForProduct(p2.getId()), 
                getOrderCountForProduct(p1.getId())
            ))
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    private int getOrderCountForProduct(String productId) {
        return (int)orders.stream()
            .flatMap(o -> o.getItems().stream())
            .filter(i -> i.getProduct().getId().equals(productId))
            .count();
    }
}

// ProductQuantity.java
public class ProductQuantity {
    private String productId;
    private int quantity;
    
    public ProductQuantity(String productId, int quantity) {
        validate(productId, quantity);
        this.productId = productId;
        this.quantity = quantity;
    }
    
    private void validate(String productId, int quantity) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
}
