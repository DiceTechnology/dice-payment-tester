package technology.dice.payment.stripe.service;

import com.stripe.net.RequestOptions;
import technology.dice.payment.stripe.exception.DiceStripeException;
import technology.dice.payment.stripe.model.*;

import javax.inject.Inject;
import java.util.List;
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
    private final ProductApiLayer productApiLayer;
    private final SkuApiLayer skuApiLayer;
    private final RefundApiLayer refundApiLayer;
    private final DisputeApiLayer disputeApiLayer;
    private final ChargeApiLayer chargeApiLayer;

    @Inject
    public StripePaymentProvider(StripeConfig config,
                                 CustomerApiLayer customerApiLayer,
                                 TokenApiLayer tokenApiLayer, CardApiLayer cardApiLayer,
                                 SubscriptionApiLayer subscriptionApiLayer, OrderApiLayer orderApiLayer,
                                 ProductApiLayer productApiLayer, SkuApiLayer skuApiLayer,
                                 RefundApiLayer refundApiLayer, DisputeApiLayer disputeApiLayer, ChargeApiLayer chargeApiLayer) {
        this.config = config;
        this.customerApiLayer = customerApiLayer;
        this.tokenApiLayer = tokenApiLayer;
        this.cardApiLayer = cardApiLayer;
        this.subscriptionApiLayer = subscriptionApiLayer;
        this.requestOptions = RequestOptions.builder().setApiKey(config.getApiKey().getKey()).build();
        this.orderApiLayer = orderApiLayer;
        this.productApiLayer = productApiLayer;
        this.skuApiLayer = skuApiLayer;
        this.refundApiLayer = refundApiLayer;
        this.disputeApiLayer = disputeApiLayer;
        this.chargeApiLayer = chargeApiLayer;
    }

    public StripeMode getMode() {
        return config.getMode();
    }

    public Customer createCustomer(CustomerDefinition customerDefinition) {
        return customerApiLayer.createCustomer(requestOptions, customerDefinition, Optional.empty());
    }

    public Customer bindCardToken(StripeCustomerId stripeCustomerId, StripeCardToken stripeCardToken) {
        return customerApiLayer.bindCardToken(requestOptions, stripeCustomerId, stripeCardToken);
    }

    public StripeCardToken generateCardToken(StripeCardDefinition cardDefinition) {
        return tokenApiLayer.generateCardToken(requestOptions, cardDefinition);
    }

    public Product createProduct(ProductDefinition productDefinition) {
        return productApiLayer.createProduct(requestOptions, productDefinition);
    }

    public SkuId createSku(SkuDefinition skuDefinition) throws DiceStripeException {
        return skuApiLayer.createSku(requestOptions, skuDefinition);
    }

    public String payOrderByCardToken(StripeCustomerId stripeCustomerId, StripeCardToken stripeCardToken, OrderDefinition orderDefinition, String idempotentKey) throws DiceStripeException {
        return orderApiLayer.payOrderByCardToken(requestOptions, stripeCustomerId, stripeCardToken, orderDefinition, idempotentKey);
    }

    // TODO
    public String payOrderByCardId(StripeCustomerId stripeCustomerId, StripeCardToken stripeCardToken, OrderDefinition orderDefinition, String idempotentKey) throws DiceStripeException {
        return orderApiLayer.payOrderByCardToken(requestOptions, stripeCustomerId, stripeCardToken, orderDefinition, idempotentKey);
    }
    // pay -- subscription

    // cancel

    public StripeRefundId refund(StripeCustomerId stripeCustomerId,
                                 StripeChargeId stripeChargeId,
                                 Optional<ChargeablePrice> refundAmount,
                                 RefundReason refundReason,
                                 String comment) throws DiceStripeException {
        return refundApiLayer.refund(requestOptions, stripeCustomerId, stripeChargeId, refundAmount, refundReason, comment);

    }

    public List<StripeCharge> listCharges(StripeCustomerId stripeCustomerId) throws DiceStripeException {
        return chargeApiLayer.listCharges(requestOptions, stripeCustomerId);

    }

    // dispute

    // fake renewal

    // end subscription -- fake it ...


}
