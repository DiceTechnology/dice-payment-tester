package technology.dice.payment.stripe.model;

import com.google.common.collect.ImmutableMap;

import java.util.Currency;
import java.util.Map;
import java.util.Optional;

public class SubscriptionDefinition {
    private final Currency currency;
    private final Optional<String> description;
    private final PlanDefinition planDefinition;
    private final Map<String, Object> metaData;
    private Optional<String> email;

    public SubscriptionDefinition(Currency currency, String description, PlanDefinition planDefinition) {
        this.currency = currency;
        this.description = Optional.ofNullable(description);
        this.planDefinition = planDefinition;
        this.email = Optional.empty();
        this.metaData = ImmutableMap.of();
    }

    public Currency getCurrency() {
        return currency;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public PlanDefinition getPlanDefinition() {
        return planDefinition;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }
}
