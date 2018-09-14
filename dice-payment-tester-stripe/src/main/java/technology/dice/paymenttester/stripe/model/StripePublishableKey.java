package technology.dice.paymenttester.stripe.model;

public class StripePublishableKey {
    private final String key;

    public StripePublishableKey(String key) {
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
