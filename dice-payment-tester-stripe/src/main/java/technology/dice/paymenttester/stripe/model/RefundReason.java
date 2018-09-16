/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.model;

import java.util.Arrays;
import java.util.Optional;

public enum RefundReason {
    UNKNOWN("unknown"), REQUESTED_BY_CUSTOMER("requested_by_customer"), DUPLICATED_PAYMENT("duplicate"), SUSPECT_PAYMENT("fraudulent"), UPGRADE_DOWNGRADE("upgrade_downgrade");

    private String value;

    RefundReason(String value) {
        this.value = value;
    }

    public static Optional<RefundReason> fromString(String value) {
        return Arrays.stream(values()).filter(r -> r.reason().equalsIgnoreCase(value) || r.name().equalsIgnoreCase(value)).findFirst();
    }

    public String reason() {
        return value;
    }
}
