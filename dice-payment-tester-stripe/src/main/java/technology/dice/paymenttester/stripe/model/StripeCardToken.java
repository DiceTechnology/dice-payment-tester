package technology.dice.paymenttester.stripe.model;

public class StripeCardToken {
    private final String token;

    public StripeCardToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token;
    }
}
