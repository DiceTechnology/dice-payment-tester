package technology.dice.payment.stripe.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.*;

public class OrderDefinition {
    private final String customerId;
    private final Currency currency;
    private final List<OrderItem> orderItems;
    private Optional<String> description;
    private Optional<String> email;
    private final Map<String, Object> metaData;

    public OrderDefinition(String customerId, Currency currency, List<OrderItem> orderItems) {
        Objects.requireNonNull(customerId, "customer id is compulsory");
        this.customerId = customerId;
        this.currency = currency;
        this.orderItems = orderItems == null ? ImmutableList.of() : ImmutableList.copyOf(orderItems);
        this.metaData = ImmutableMap.of();
        this.description = Optional.empty();
        this.email = Optional.empty();
    }

    public String getCustomerId() {
        return customerId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Map<String, Object> getMetaData() {
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
