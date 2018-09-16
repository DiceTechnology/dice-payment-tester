/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.model;

public class StripeCustomer {
    private final StripeCustomerId customerId;

    public StripeCustomer(StripeCustomerId customerId) {
        this.customerId = customerId;
    }

    public StripeCustomerId getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return customerId.toString();
    }
}
