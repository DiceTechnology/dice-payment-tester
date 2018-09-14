package technology.dice.payment.stripe.model;

public class StripeChargeId {
    private final String id;

    public StripeChargeId(String id) {
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
