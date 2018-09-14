package technology.dice.payment.stripe.model;

public enum StripeMode {
	TEST, LIVE;

	@Override
	public String toString() {
		return name().toUpperCase();
	}
}
