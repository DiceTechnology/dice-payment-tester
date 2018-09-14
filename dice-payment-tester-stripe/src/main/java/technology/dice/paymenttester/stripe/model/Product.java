package technology.dice.paymenttester.stripe.model;

public class Product {
    private final ProductId productId;

    public Product(ProductId productId) {
        this.productId = productId;
    }

    public ProductId getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return productId.toString();
    }
}
