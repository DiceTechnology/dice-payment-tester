package technology.dice.payment.stripe.service;

import com.google.common.collect.ImmutableMap;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import technology.dice.payment.stripe.exception.DiceStripeException;
import technology.dice.payment.stripe.model.StripeCardDefinition;
import technology.dice.payment.stripe.model.StripeCardToken;
import technology.dice.dicepay.stripe.util.ModelConvertor;

public class TokenApiLayer {
    private static final Logger LOG = LoggerFactory.getLogger(TokenApiLayer.class);

    public StripeCardToken generateCardToken(RequestOptions options, StripeCardDefinition cardDefinition) throws DiceStripeException {
        ImmutableMap.Builder<String, Object> cardParams = ImmutableMap.builder();

        cardParams.put("number", cardDefinition.getCardNumber());
        cardParams.put("exp_month", cardDefinition.getYearMonth().getMonth().getValue());
        cardParams.put("exp_year", cardDefinition.getYearMonth().getYear());
        cardParams.put("cvc", cardDefinition.getCvc());
        cardParams.put("name", cardDefinition.getCardHolder());

        try {
            return ModelConvertor.convert(Token.create(ImmutableMap.of("card", cardParams.build()), options));
        } catch (StripeException e) {
            throw new DiceStripeException("error creating stripe card token", e);
        }
    }

}
