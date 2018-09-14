package technology.dice.payment.stripe.model;

public class StripeApiKey {
    private final String key;

    public StripeApiKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }
}
