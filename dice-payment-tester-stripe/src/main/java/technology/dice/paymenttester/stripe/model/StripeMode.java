package technology.dice.paymenttester.stripe.model;

public enum StripeMode {
	TEST, LIVE;

	@Override
	public String toString() {
		return name().toUpperCase();
	}
}
