package technology.dice.paymenttester.stripe.util;

import technology.dice.paymenttester.stripe.model.*;

import java.io.IOException;
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

}
