package technology.dice.paymenttester.stripe.exception;

public class RefundAmountExceededException extends DiceStripeException {

	public RefundAmountExceededException(String message) {
		super(message);
	}

	public RefundAmountExceededException(String message, Throwable cause) {
		super(message, cause);
	}

}
