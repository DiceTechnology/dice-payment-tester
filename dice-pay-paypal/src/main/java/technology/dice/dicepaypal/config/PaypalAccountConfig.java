package technology.dice.dicepaypal.config;

import java.util.Optional;

public class PaypalAccountConfig {

    private final String mode = "sandbox";
    private final String clientId;
    private final String clientSecret;
    private final String clientSignature;
    private final Optional<String> endpoint;

    public PaypalAccountConfig(String clientId, String clientSecret, String clientSignature) {
        this(clientId, clientSecret, clientSignature, Optional.empty());
    }

    public PaypalAccountConfig(String clientId, String clientSecret, String clientSignature, String endpoint) {
        this(clientId, clientSecret, clientSignature, Optional.of(endpoint));
    }

    private PaypalAccountConfig(String clientId, String clientSecret, String clientSignature, Optional<String> endpoint) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientSignature = clientSignature;
        this.endpoint = endpoint;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getClientSignature() {
        return clientSignature;
    }

    public String getMode() {
        return mode;
    }

    public Optional<String> getEndpoint() {
        return endpoint;
    }
}
