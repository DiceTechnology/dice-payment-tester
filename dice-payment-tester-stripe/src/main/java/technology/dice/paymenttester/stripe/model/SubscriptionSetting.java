package technology.dice.paymenttester.stripe.model;

import java.time.Period;
import java.util.Optional;

public class SubscriptionSetting {
    private final SubscriptionType subscriptionType;
    private final Optional<Period> subscriptionPeriod;          // TODO : this has to match the scription type (brittle ...)
    private final int trialPeriodDuration;        // in days only

    public SubscriptionSetting(SubscriptionType subscriptionType, Period subscriptionPeriod, int trialPeriodDuration) {
        this.subscriptionType = subscriptionType;
        this.subscriptionPeriod = Optional.ofNullable(subscriptionPeriod);
        this.trialPeriodDuration = trialPeriodDuration;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public Optional<Period> getSubscriptionPeriod() {
        return subscriptionPeriod;
    }

    public int getTrialPeriodDuration() {
        return trialPeriodDuration;
    }
}
