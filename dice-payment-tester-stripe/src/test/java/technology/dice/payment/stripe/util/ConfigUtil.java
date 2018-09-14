package technology.dice.payment.stripe.util;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import technology.dice.payment.stripe.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class ConfigUtil {

    public static StripeConfig readConfig(String configFile) throws IOException {
        Properties properties = new Properties();
        properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream(configFile));

        StripeMode mode = StripeMode.valueOf(properties.getProperty("mode", StripeMode.TEST.name()));
        StripeApiKey apiKey = new StripeApiKey(properties.getProperty("apiKey"));
        StripePublishableKey publishableApiKey = new StripePublishableKey(properties.getProperty("publishableApiKey"));
        Optional<StripeWebhookSignatureKey> webhookSignatureKey = properties.getProperty("webhookSignatureKey") == null ? Optional.empty() : Optional.of(new StripeWebhookSignatureKey(properties.getProperty("webhookSignatureKey")));

        return new StripeConfig(mode, apiKey, publishableApiKey, webhookSignatureKey);
    }

    public static class TokenUtil {
        public static final String DEFAULT_TEST_CARD = "4242424242424242";
        public static final String DEFAULT_CV2 = "123";
        public static final int DEFAULT_EXPIRY_MONTH = LocalDate.now().getMonthValue();
        public static final int DEFAULT_EXPIRY_YEAR = LocalDate.now().getYear() + 1;
        public static final YearMonth DEFAULT_EXPIRY_DATE = YearMonth.of(DEFAULT_EXPIRY_YEAR, DEFAULT_EXPIRY_MONTH);

        public static Token createTestToken(StripeConfig config) throws StripeException {
            return createTestToken(config, DEFAULT_TEST_CARD);
        }

        public static Token createTestToken(StripeConfig config, String cardNumber) throws StripeException {
            return createToken(config, cardNumber, DEFAULT_EXPIRY_MONTH, DEFAULT_EXPIRY_YEAR, DEFAULT_CV2);
        }

        public static Token createTestToken(StripeConfig config, String cardNumber, int expiryMonth, int expiryYear, String cv2) throws StripeException {
            return createToken(config, cardNumber, expiryMonth, expiryYear, cv2);
        }

        public static Token createToken(StripeConfig config, String cardNumber, int expiryMonth, int expiryYear, String cv2) throws StripeException {
            Stripe.apiKey = config.getPublishableKey().getKey();

            Map<String, Object> tokenParams = new HashMap<>();
            Map<String, Object> cardParams = new HashMap<>();
            cardParams.put("number", cardNumber);
            cardParams.put("exp_month", expiryMonth);
            cardParams.put("exp_year", expiryYear);
            cardParams.put("cvc", cv2);
            cardParams.put("address_country", "GB");
            cardParams.put("name", "test card holder");
            tokenParams.put("card", cardParams);

            return Token.create(tokenParams);
        }
    }
}
