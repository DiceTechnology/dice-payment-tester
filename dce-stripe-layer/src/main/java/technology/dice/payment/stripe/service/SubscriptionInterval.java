package technology.dice.payment.stripe.service;

import technology.dice.payment.stripe.model.SubscriptionType;

public class SubscriptionInterval {
    private final SubscriptionType subscriptionType;
    private final int intervalCount;

    public SubscriptionInterval(SubscriptionType subscriptionType, int intervalCount) {
        this.subscriptionType = subscriptionType;
        this.intervalCount = intervalCount;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public int getIntervalCount() {
        return intervalCount;
    }
}
