package technology.dice.payment.stripe.model;

import java.util.Objects;

public class OrderItem {
    private final ProductId productId;
    private final String sku;
    private final int quantity;

    public OrderItem(ProductId productId, String sku, int quantity) {
        this.productId = productId;
        Objects.requireNonNull(sku, "sku is compulsory");
        this.sku = sku;
        this.quantity = quantity;
    }

    public OrderItem(ProductId productId, String sku) {
        this(productId, sku, 1);
    }


    public ProductId getProductId() {
        return productId;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return sku;
    }
}
