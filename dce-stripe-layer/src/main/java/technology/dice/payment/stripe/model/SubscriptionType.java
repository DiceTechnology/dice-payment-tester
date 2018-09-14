package technology.dice.payment.stripe.model;

import java.util.Arrays;
import java.util.Optional;

public enum SubscriptionType {
    NONE("none", false),
    SUBSCRIPTION("subscription", true),
    ANNUALLY("year", true),
    MONTHLY("month", true),
    DAILY("day", true),
    PPV("PPV", false);

    private final String alias;
    private final boolean recurring;

    SubscriptionType(String alias, boolean recurring) {
        this.alias = alias;
        this.recurring = recurring;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public static Optional<SubscriptionType> fromString(String value) {
        return Arrays.stream(values()).filter(st -> st.name().equalsIgnoreCase(value) || st.alias.equalsIgnoreCase(value)).findFirst();
    }

    public static SubscriptionType fromString(String value, SubscriptionType defaultValue) {
        return fromString(value).orElse(defaultValue);
    }

    public static boolean isRecurringSubscription(String subscriptionType) {
        return fromString(subscriptionType).orElse(SubscriptionType.NONE).isRecurring();
    }

}
