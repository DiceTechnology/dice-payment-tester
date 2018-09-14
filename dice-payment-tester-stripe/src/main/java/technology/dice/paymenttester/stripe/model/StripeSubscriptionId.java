package technology.dice.paymenttester.stripe.model;

public class StripeSubscriptionId {
    private final String id;

    public StripeSubscriptionId(String id) {
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
