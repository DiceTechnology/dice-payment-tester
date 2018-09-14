package technology.dice.paymenttester.stripe.model;

public class SkuId {
    private final String id;

    public SkuId(String id) {
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
