package technology.dice.payment.stripe.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Customer {
    private final String customerId;
    private final Map<String, String> metaData;
    private Optional<String> description;
    private Optional<String> email;

    public Customer(String customerId, Map<String, String> metaData) {
        Objects.requireNonNull(customerId, "customer id is compulsory");
        this.customerId = customerId;
        this.metaData = metaData == null ? ImmutableMap.of() : ImmutableMap.copyOf(metaData);
    }

    public String getCustomerId() {
        return customerId;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Optional.ofNullable(description);
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Optional.ofNullable(email);
    }
}
