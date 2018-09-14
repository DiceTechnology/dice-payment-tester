package technology.dice.payment.stripe.util;

import technology.dice.payment.stripe.model.Customer;
import technology.dice.payment.stripe.model.StripeCardToken;

public class ModelConvertor {

    public static Customer convert(com.stripe.model.Customer stripeCustomer) {
        if (isTrue(stripeCustomer.getDeleted())) {
            return null;
        }

        Customer customer = new Customer(stripeCustomer.getId(), stripeCustomer.getMetadata());
        customer.setDescription(stripeCustomer.getDescription());
        // Q : should we return such sensitive email info ???
        customer.setEmail(stripeCustomer.getEmail());

        return customer;
    }


    public static StripeCardToken convert(com.stripe.model.Token stripeCardToken) {
        return new StripeCardToken(stripeCardToken.getId());
    }

    private static boolean isTrue(Boolean b) {
        return b != null && b.booleanValue();
    }

}
