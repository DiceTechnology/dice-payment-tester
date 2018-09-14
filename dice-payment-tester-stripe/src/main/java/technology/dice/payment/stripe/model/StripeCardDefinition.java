package technology.dice.payment.stripe.model;

import java.time.YearMonth;

public class StripeCardDefinition {
    private final String cardHolder;
    private final String cardNumber;
    private final String cvc;
    private final YearMonth yymm;


    public StripeCardDefinition(String cardHolder, String cardNumber, String cvc, YearMonth yymm) {
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.yymm = yymm;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvc() {
        return cvc;
    }

    public YearMonth getYearMonth() {
        return yymm;
    }

}
