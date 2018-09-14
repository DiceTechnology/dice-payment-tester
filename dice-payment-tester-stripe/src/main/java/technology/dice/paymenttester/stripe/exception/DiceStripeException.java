package technology.dice.paymenttester.stripe.exception;

public class DiceStripeException extends RuntimeException {

	public DiceStripeException(String message) {
		super(message);
	}

	public DiceStripeException(String message, Throwable cause) {
		super(message, cause);
	}

}