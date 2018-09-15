/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.model;

import java.time.Instant;

public class StripeCharge {
    private final StripeCustomerId customerId;
    private final StripeChargeId chargeId;
    private final ChargeablePrice amount;
    private final boolean paid;
    private final Instant whenCreated;

    // TODO : we can add things like disputed / refunded; so we can use other api to do the search

    public StripeCharge(StripeCustomerId customerId, StripeChargeId chargeId, ChargeablePrice amount, boolean paid, Instant whenCreated) {
        this.customerId = customerId;
        this.chargeId = chargeId;
        this.amount = amount;
        this.paid = paid;
        this.whenCreated = whenCreated;
    }

    public StripeCustomerId getCustomerId() {
        return customerId;
    }

    public StripeChargeId getChargeId() {
        return chargeId;
    }

    public ChargeablePrice getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }
}
