package technology.dice.paymenttester.stripe.model;

import java.util.Currency;

public class ChargeablePrice {
    private final Currency currency;
    private final long amount;

    public ChargeablePrice(long amount, Currency currency) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public long getAmount() {
        return amount;
    }
}
