package technology.dice.payment.stripe.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;

public class PlanDefinition {
    private final PlanId planId;
    private final String planName;
    private final Optional<String> statementDescriptor;
    private final ChargeablePrice price;
    private final SubscriptionSetting subscriptionSetting;
    private final Map<String, Object> metaData;

    public PlanDefinition(PlanId planId, String planName, String statementDescriptor, ChargeablePrice price, SubscriptionSetting subscriptionSetting) {
        this.planId = planId;
        this.planName = planName;
        this.statementDescriptor = Optional.ofNullable(statementDescriptor);
        this.price = price;
        this.subscriptionSetting = subscriptionSetting;
        this.metaData = ImmutableMap.of();
    }

    public PlanId getPlanId() {
        return planId;
    }

    public String getPlanName() {
        return planName;
    }

    public Optional<String> getStatementDescriptor() {
        return statementDescriptor;
    }

    public ChargeablePrice getPrice() {
        return price;
    }

    public SubscriptionSetting getSubscriptionSetting() {
        return subscriptionSetting;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }
}
