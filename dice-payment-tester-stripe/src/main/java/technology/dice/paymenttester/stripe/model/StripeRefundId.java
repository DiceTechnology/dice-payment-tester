package technology.dice.paymenttester.stripe.model;

public class StripeRefundId {
    private final String id;

    public StripeRefundId(String id) {
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