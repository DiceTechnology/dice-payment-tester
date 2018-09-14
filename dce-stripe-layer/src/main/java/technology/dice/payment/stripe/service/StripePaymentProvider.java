package technology.dice.payment.stripe.service;

import com.stripe.net.RequestOptions;
import technology.dice.payment.stripe.model.*;

import javax.inject.Inject;
import java.util.Optional;

public class StripePaymentProvider {
    private final StripeConfig config;

    // TODO: best to wrap up these numerous api layers ... unweirdy

    private final RequestOptions requestOptions;
    private final CustomerApiLayer customerApiLayer;
    private final TokenApiLayer tokenApiLayer;
    private final CardApiLayer cardApiLayer;
    private final SubscriptionApiLayer subscriptionApiLayer;
    private final OrderApiLayer orderApiLayer;

    @Inject
    public StripePaymentProvider(StripeConfig config,
                                 CustomerApiLayer customerApiLayer,
                                 TokenApiLayer tokenApiLayer, CardApiLayer cardApiLayer,
                                 SubscriptionApiLayer subscriptionApiLayer, OrderApiLayer orderApiLayer) {
        this.config = config;
        this.customerApiLayer = customerApiLayer;
        this.tokenApiLayer = tokenApiLayer;
        this.cardApiLayer = cardApiLayer;
        this.subscriptionApiLayer = subscriptionApiLayer;
        this.requestOptions = RequestOptions.builder().setApiKey(config.getApiKey().getKey()).build();
        this.orderApiLayer = orderApiLayer;
    }

    public StripeMode getMode() {
        return config.getMode();
    }

    public Customer createCustomer(CustomerDefinition customerDefinition) {
        return customerApiLayer.createCustomer(requestOptions, customerDefinition, Optional.empty());
    }

    public StripeCardToken generateCardToken(StripeCardDefinition cardDefinition) {
        return tokenApiLayer.generateCardToken(requestOptions, cardDefinition);
    }

    // create customer

    // generate token (for testing) -- this is dangerous, but ok for testing purposes

    // bind token to customer

    // pay -- order / subscription

    // cancel

    // refund

    // dispute

    // fake renewal

    // end subscription -- fake it ...

    // look at what we have for customer ...

}
