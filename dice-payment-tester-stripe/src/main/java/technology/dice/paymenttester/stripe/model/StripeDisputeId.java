package technology.dice.paymenttester.stripe.model;

public class StripeDisputeId {
    private final String id;

    public StripeDisputeId(String id) {
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
