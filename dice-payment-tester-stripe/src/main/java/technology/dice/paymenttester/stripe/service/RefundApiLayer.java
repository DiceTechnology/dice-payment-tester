package technology.dice.paymenttester.stripe.service;

import com.google.common.collect.ImmutableMap;
import com.stripe.exception.StripeException;
import com.stripe.net.RequestOptions;
import technology.dice.paymenttester.stripe.exception.DiceStripeException;
import technology.dice.paymenttester.stripe.exception.DuplicateRefundException;
import technology.dice.paymenttester.stripe.exception.RefundAmountExceededException;
import technology.dice.paymenttester.stripe.model.*;
import technology.dice.paymenttester.stripe.util.ModelConvertor;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class RefundApiLayer {

    public StripeRefundId refund(RequestOptions options,
                                 StripeCustomerId stripeCustomerId,
                                 StripeChargeId stripeChargeId,
                                 Optional<ChargeablePrice> refundAmount,
                                 RefundReason refundReason,
                                 String comment) throws DiceStripeException {
        try {
            ImmutableMap.Builder<String, Object> refundParams = ImmutableMap.builder();

            refundParams.put("charge", stripeChargeId.getId());
            refundAmount.ifPresent(amt -> refundParams.put("amount", amt.getAmount()));
            refundParams.put("reason", refundReason.reason());

            refundParams.put("metadata", ImmutableMap.of("comment", comment));

            return ModelConvertor.convert(com.stripe.model.Refund.create(refundParams.build(), options));
        } catch (StripeException e) {
            if (e.getMessage().contains("is greater than charge amount")) {
                throw new RefundAmountExceededException("refund amount exceeds original amount", e);
            } else if (e.getMessage().contains("is greater than unrefunded amount")) {
                throw new RefundAmountExceededException("refund amount exceeds remaining amount", e);
            } else if (e.getMessage().contains("has already been refunded")) {
                throw new DuplicateRefundException();
            } else if (e.getMessage().contains("No such charge")) {
                throw new DiceStripeException("error refunding - charge is invalid");
            } else {
                throw new DiceStripeException("error refunding", e);
            }
        }
    }
}
