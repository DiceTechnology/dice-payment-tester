package technology.dice.paymenttester.stripe.model;

public class StripeWebhookSignatureKey {
    private final String key;

    public StripeWebhookSignatureKey(String key) {
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
