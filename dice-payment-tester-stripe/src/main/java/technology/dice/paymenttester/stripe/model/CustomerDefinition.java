/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CustomerDefinition {
    private final String customerId;
    private final Map<String, Object> metaData;
    private Optional<String> description;
    private Optional<String> email;

    public CustomerDefinition(String customerId, Map<String, Object> metaData) {
        Objects.requireNonNull(customerId, "customer id is compulsory");
        this.customerId = customerId;
        this.metaData = metaData == null ? ImmutableMap.of() : ImmutableMap.copyOf(metaData);
        this.description = Optional.empty();
        this.email = Optional.empty();
    }

    public String getCustomerId() {
        return customerId;
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
