package technology.dice.payment.stripe.model;

public class StripeCustomer {
    private final StripeCustomerId customerId;

    public StripeCustomer(StripeCustomerId customerId) {
        this.customerId = customerId;
    }

    public StripeCustomerId getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return customerId.toString();
    }
}
