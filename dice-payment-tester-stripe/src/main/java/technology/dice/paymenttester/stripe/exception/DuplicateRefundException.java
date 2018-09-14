package technology.dice.paymenttester.stripe.exception;

public class DuplicateRefundException extends DiceStripeException {

	public DuplicateRefundException() {
		super("refunded already");
	}

}
