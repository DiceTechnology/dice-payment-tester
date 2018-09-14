package technology.dice.paymenttester.stripe.service;


import technology.dice.paymenttester.stripe.model.SubscriptionType;

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
