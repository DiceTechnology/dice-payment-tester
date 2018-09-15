/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.exception;

public class DiceStripeException extends RuntimeException {

	public DiceStripeException(String message) {
		super(message);
	}

	public DiceStripeException(String message, Throwable cause) {
		super(message, cause);
	}

}
