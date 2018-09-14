package technology.dice.paymenttester.stripe.model;

public class ProductId {
    private final String id;

    public ProductId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
