package technology.dice.paymenttester.stripe.model;

import java.util.Optional;

public class StripeConfig {
    private final StripeMode mode;
    private final StripeApiKey apiKey;
    private final StripePublishableKey publishableKey;
    private final Optional<StripeWebhookSignatureKey> signatureKey;

    public StripeConfig(StripeMode mode, StripeApiKey apiKey, StripePublishableKey publishableKey, Optional<StripeWebhookSignatureKey> signatureKey) {
        this.mode = mode;
        this.apiKey = apiKey;
        this.publishableKey = publishableKey;
        this.signatureKey = signatureKey;
    }

    public StripeMode getMode() {
        return mode;
    }

    public StripeApiKey getApiKey() {
        return apiKey;
    }

    public StripePublishableKey getPublishableKey() {
        return publishableKey;
    }

    public Optional<StripeWebhookSignatureKey> getSignatureKey() {
        return signatureKey;
    }

}