/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.exception;

public class RefundAmountExceededException extends DiceStripeException {

	public RefundAmountExceededException(String message) {
		super(message);
	}

	public RefundAmountExceededException(String message, Throwable cause) {
		super(message, cause);
	}

}
