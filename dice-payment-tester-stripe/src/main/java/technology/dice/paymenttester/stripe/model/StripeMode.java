/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.model;

public enum StripeMode {
	TEST, LIVE;

	@Override
	public String toString() {
		return name().toUpperCase();
	}
}
