/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.stripe.exception.StripeException;
import com.stripe.model.Plan;
import com.stripe.model.Subscription;
import com.stripe.net.RequestOptions;
import technology.dice.paymenttester.stripe.exception.DiceStripeException;
import technology.dice.paymenttester.stripe.exception.LicenceAlreadyCancelledException;
import technology.dice.paymenttester.stripe.model.*;
import technology.dice.paymenttester.stripe.util.ModelConvertor;

import javax.inject.Singleton;
import java.time.Period;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import static technology.dice.paymenttester.stripe.util.CollectionUtil.isNotEmpty;

@Singleton
public class SubscriptionApiLayer {

    public StripeSubscriptionId paySubscription(RequestOptions options, StripeCustomerId stripeCustomerId, SubscriptionDefinition subscriptionDefinition, String idempotentKey) throws DiceStripeException {
        try {
            PlanDefinition mainPlanDefinition = subscriptionDefinition.getPlanDefinition();
            Map<String, Object> planParams = buildPlanParams(mainPlanDefinition, mainPlanDefinition.getPrice().getAmount(), mainPlanDefinition.getPrice().getCurrency());
            Plan skuPlan = Plan.create(planParams, options);

            // here is key to multi-plan ...
            // NB : we must not set plan or quantity param when using multi-plan mechanism; or else InvalidRequestException
            List<Map<String, Object>> planItems = ImmutableList.of(ImmutableMap.of("plan", skuPlan.getId()));

            // pay subscription
            ImmutableMap.Builder<String, Object> payParams = ImmutableMap.builder();

            payParams.put("customer", stripeCustomerId.getId());        // we expect source to have been bound to customer first
            payParams.put("items", planItems);

            // NB : ignore the trial period in the plans (they are pretty much for show); use the trial period *on* the subscription instead
            payParams.put("trial_period_days", mainPlanDefinition.getSubscriptionSetting().getTrialPeriodDuration());

            // meta-data
            if (isNotEmpty(mainPlanDefinition.getMetaData())) {
                payParams.put("metadata", mainPlanDefinition.getMetaData());
            }

            RequestOptions payOptions = buildPayRequestOptions(options, idempotentKey);
            return ModelConvertor.convert(Subscription.create(payParams.build(), payOptions));
        } catch (StripeException e) {
            throw new DiceStripeException(String.format("error creating order : customer='%s'", stripeCustomerId), e);
        }
    }

    private Map<String, Object> buildPlanParams(PlanDefinition planDefinition, long amount, Currency currency) {
        ImmutableMap.Builder<String, Object> planParams = ImmutableMap.builder();

        planParams.put("id", planDefinition.getPlanId());
        planParams.put("name", planDefinition.getPlanName());

        planParams.put("amount", amount);
        planParams.put("currency", currency.getCurrencyCode());

        SubscriptionSetting subscriptionSetting = planDefinition.getSubscriptionSetting();

        SubscriptionInterval subscriptionInterval = toStripeInterval(subscriptionSetting);
        planParams.put("interval", subscriptionInterval.getSubscriptionType().getAlias());
        planParams.put("interval_count", subscriptionInterval.getIntervalCount());

        planParams.put("trial_period_days", subscriptionSetting.getTrialPeriodDuration());

        planDefinition.getStatementDescriptor().ifPresent(sd -> planParams.put("statement_descriptor", sd));

        if (isNotEmpty(planDefinition.getMetaData())) {
            planParams.put("metadata", planDefinition.getMetaData());
        }

        return planParams.build();
    }

    private SubscriptionInterval toStripeInterval(SubscriptionSetting subscriptionSetting) {
        Period subscriptionPeriod = subscriptionSetting.getSubscriptionPeriod().orElse(Period.ZERO);

        switch (subscriptionSetting.getSubscriptionType()) {
            case SUBSCRIPTION:
                return toStripeSubscriptionInterval(subscriptionPeriod);
            case MONTHLY:
                return new SubscriptionInterval(SubscriptionType.MONTHLY, subscriptionPeriod.getMonths());
            case DAILY:
                return new SubscriptionInterval(SubscriptionType.DAILY, subscriptionPeriod.getDays());
            case ANNUALLY:
            default:            // Q : being very pessemistic here; at least give us time to cancel it ...
                return new SubscriptionInterval(SubscriptionType.ANNUALLY, subscriptionPeriod.getYears());
        }
    }

    // Q : do we support period with mixed annual/month/day components ??? we can, up to certain point ...
    private static SubscriptionInterval toStripeSubscriptionInterval(Period subscriptionPeriod) {
        if (subscriptionPeriod.getYears() > 0) {
            return new SubscriptionInterval(SubscriptionType.ANNUALLY, subscriptionPeriod.getYears());
        } else if (subscriptionPeriod.getMonths() > 0) {
            return new SubscriptionInterval(SubscriptionType.MONTHLY, subscriptionPeriod.getMonths());
        } else {
            return new SubscriptionInterval(SubscriptionType.DAILY, subscriptionPeriod.getDays());
        }
    }

    public boolean cancelSubscription(RequestOptions options, StripeSubscriptionId stripeSubscriptionId, boolean cancelAtPeriodEnd) throws LicenceAlreadyCancelledException {
        try {
            Subscription subscriptionInQuestion = Subscription.retrieve(stripeSubscriptionId.getId(), options);

            if (subscriptionInQuestion.getCanceledAt() != null) {
                throw new LicenceAlreadyCancelledException(null);
            }

            subscriptionInQuestion.cancel(ImmutableMap.of("at_period_end", cancelAtPeriodEnd), options);
            return true;
        } catch (StripeException e) {
            // ideally, if subscription has already been cancelled (e.g. externally), then this is not an error to re-cancel it (being idempotent)
            // however, Stripe considers cancelling such an cancelled subscription to be InvalidRequestException -- "No such subscription"
            // in other words, Stripe doesn't really distinguish if this is an invalid subscription or an cancelled one
            throw new DiceStripeException(String.format("error cancelling subscription '%s'", stripeSubscriptionId.getId()), e);
        }
    }

    public void endTrial(RequestOptions options, StripeSubscriptionId stripeSubscriptionId) {
        try {
            Subscription subscriptionInQuestion = Subscription.retrieve(stripeSubscriptionId.getId(), options);

            if (subscriptionInQuestion != null) {
                subscriptionInQuestion.update(ImmutableMap.of("trial_end", "now"), options);
            }
        } catch (StripeException e) {
            throw new DiceStripeException("error ending trial");
        }
    }

    private RequestOptions buildPayRequestOptions(RequestOptions options, String idempotentKey) {
        if (idempotentKey == null) {
            return options;         // existing one
        } else {
            return RequestOptions.builder().setApiKey(options.getApiKey()).setIdempotencyKey(idempotentKey).build();
        }
    }
}
