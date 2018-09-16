/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.exception;

public class DuplicateRefundException extends DiceStripeException {

	public DuplicateRefundException() {
		super("refunded already");
	}

}
