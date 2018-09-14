package technology.dice.payment.stripe.exception;

public class DuplicateRefundException extends DiceStripeException {

	public DuplicateRefundException() {
		super("refunded already");
	}

}
