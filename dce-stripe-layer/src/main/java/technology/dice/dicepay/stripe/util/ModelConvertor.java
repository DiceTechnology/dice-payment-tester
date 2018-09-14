package technology.dice.dicepay.stripe.util;

import technology.dice.payment.stripe.model.*;

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

    public static Product convert(com.stripe.model.Product stripeProduct) {
        return new Product(new ProductId(stripeProduct.getId()));
    }

    public static SkuId convert(com.stripe.model.SKU stripeSku) {
        return new SkuId(stripeSku.getId());
    }

    public static String convert(com.stripe.model.Order stripeOrder) {
        return stripeOrder.getId();
    }
}
