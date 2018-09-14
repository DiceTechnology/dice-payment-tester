package technology.dice.paymenttester.stripe.model;

public class StripeCustomerId {
    private final String id;

    public StripeCustomerId(String id) {
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
